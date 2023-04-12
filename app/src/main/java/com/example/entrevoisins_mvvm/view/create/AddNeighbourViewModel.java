package com.example.entrevoisins_mvvm.view.create;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.entrevoisins_mvvm.DI.AppModule;
import com.example.entrevoisins_mvvm.data.entities.NeighbourEntity;
import com.example.entrevoisins_mvvm.data.repository.NeighboursRepository;
import com.example.entrevoisins_mvvm.view.utils.SingleLiveEvent;

import java.time.Clock;
import java.time.Instant;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddNeighbourViewModel extends ViewModel {

    @NonNull
    private final NeighboursRepository repository;

    private final String randomImageUrl;

    private final MutableLiveData<String> nameMutableLiveData = new MutableLiveData<>(null);

    private final MutableLiveData<String> addressMutableLiveData = new MutableLiveData<>(null);

    private final MutableLiveData<String> phoneNumberMutableLiveData = new MutableLiveData<>(null);

    private final MutableLiveData<String> aboutMeMutableLiveData = new MutableLiveData<>(null);

    private final MediatorLiveData<AddNeighbourViewState> viewStateMediatorLiveData = new MediatorLiveData<>();

    private final SingleLiveEvent<Void> closeSingleLiveEvent = new SingleLiveEvent<>();

    @NonNull
    private final Executor ioExecutor;

    @Inject
    public AddNeighbourViewModel(
        @NonNull NeighboursRepository repository,
        @NonNull @AppModule.IoExecutor Executor ioExecutor,
        @NonNull Clock clock
    ) {
        this.repository = repository;
        this.ioExecutor = ioExecutor;

        randomImageUrl = "https://i.pravatar.cc/150?u=" + Instant.now(clock).toEpochMilli();

        viewStateMediatorLiveData.addSource(nameMutableLiveData, name ->
            combine(name, addressMutableLiveData.getValue(), phoneNumberMutableLiveData.getValue(), aboutMeMutableLiveData.getValue())
        );
        viewStateMediatorLiveData.addSource(addressMutableLiveData, address ->
            combine(nameMutableLiveData.getValue(), address, phoneNumberMutableLiveData.getValue(), aboutMeMutableLiveData.getValue())
        );
        viewStateMediatorLiveData.addSource(phoneNumberMutableLiveData, phoneNumber ->
            combine(nameMutableLiveData.getValue(), addressMutableLiveData.getValue(), phoneNumber, aboutMeMutableLiveData.getValue())
        );
        viewStateMediatorLiveData.addSource(aboutMeMutableLiveData, aboutMe ->
            combine(nameMutableLiveData.getValue(), addressMutableLiveData.getValue(), phoneNumberMutableLiveData.getValue(), aboutMe)
        );
    }

    private void combine(
        @Nullable String name,
        @Nullable String address,
        @Nullable String phone,
        @Nullable String aboutMe
    ) {
        if (name != null && address != null && phone != null && aboutMe != null) {
            viewStateMediatorLiveData.setValue(
                new AddNeighbourViewState(
                    randomImageUrl,
                    !name.isEmpty()
                        && !address.isEmpty()
                        && !phone.isEmpty()
                        && !aboutMe.isEmpty()
                )
            );
        } else {
            viewStateMediatorLiveData.setValue(
                new AddNeighbourViewState(
                    randomImageUrl,
                    false
                )
            );
        }
    }

    public void addNeighbour(
        @NonNull String name,
        @NonNull String address,
        @NonNull String phoneNumber,
        @NonNull String aboutMe
    ) {
        ioExecutor.execute(() ->
            repository.addNeighbour(
                new NeighbourEntity(
                    0,
                    false,
                    name,
                    randomImageUrl,
                    address,
                    phoneNumber,
                    aboutMe
                )
            )
        );

        closeSingleLiveEvent.call();
    }

    public SingleLiveEvent<Void> getCloseSingleLiveData() {
        return closeSingleLiveEvent;
    }

    public LiveData<AddNeighbourViewState> getViewStateLiveData() {
        return viewStateMediatorLiveData;
    }

    private void setValueForCompletion(@NonNull MutableLiveData<String> mutableLiveData, @NonNull String value) {
        mutableLiveData.setValue(value);
    }

    public void setValueForName(@NonNull String name) {
        setValueForCompletion(nameMutableLiveData, name);
    }

    public void setValueForAddress(@NonNull String address) {
        setValueForCompletion(addressMutableLiveData, address);
    }

    public void setValueForPhoneNumber(@NonNull String phoneNumber) {
        setValueForCompletion(phoneNumberMutableLiveData, phoneNumber);
    }

    public void setValueForAboutMe(@NonNull String aboutMe) {
        setValueForCompletion(aboutMeMutableLiveData, aboutMe);
    }
}
