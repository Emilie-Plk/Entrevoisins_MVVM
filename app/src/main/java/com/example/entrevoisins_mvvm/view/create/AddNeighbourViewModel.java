package com.example.entrevoisins_mvvm.view.create;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.entrevoisins_mvvm.DI.DatabaseModule;
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

    // TODO: Maybe create a new object (List<String> or NeighbourData) to encapsulate all neighbour data?
    private final String randomImageUrl;
    private final MutableLiveData<String> randomImageUrlMutableLiveData;

    private final MutableLiveData<String> nameMutableLiveData = new MutableLiveData<>();

    private final MutableLiveData<String> addressMutableLiveData = new MutableLiveData<>();

    private final MutableLiveData<String> phoneNumberMutableLiveData = new MutableLiveData<>();

    private final MutableLiveData<String> aboutMeMutableLiveData = new MutableLiveData<>();

    private final MediatorLiveData<Boolean> isButtonEnabledMediatorLiveData;

    private final SingleLiveEvent<Void> closeSingleLiveEvent = new SingleLiveEvent<>();

    @NonNull
    private final Executor ioExecutor;

    @Inject
    public AddNeighbourViewModel(
        @NonNull NeighboursRepository repository,
        @NonNull @DatabaseModule.IoExecutor Executor ioExecutor,
        @NonNull Clock clock
    ) {
        this.repository = repository;
        this.ioExecutor = ioExecutor;

        randomImageUrl = "https://i.pravatar.cc/150?u=" + Instant.now(clock).toEpochMilli();

        randomImageUrlMutableLiveData = new MutableLiveData<>(randomImageUrl);

        isButtonEnabledMediatorLiveData = new MediatorLiveData<>();

        isButtonEnabledMediatorLiveData.setValue(false); // TODO: is it ok to set initial value like this?

        isButtonEnabledMediatorLiveData.addSource(nameMutableLiveData, name ->
            combine(name, addressMutableLiveData.getValue(), phoneNumberMutableLiveData.getValue(), aboutMeMutableLiveData.getValue()
            )
        );
        isButtonEnabledMediatorLiveData.addSource(addressMutableLiveData, address ->
            combine(nameMutableLiveData.getValue(), address, phoneNumberMutableLiveData.getValue(), aboutMeMutableLiveData.getValue()
            )
        );
        isButtonEnabledMediatorLiveData.addSource(phoneNumberMutableLiveData, phoneNumber ->
            combine(nameMutableLiveData.getValue(), addressMutableLiveData.getValue(), phoneNumber, aboutMeMutableLiveData.getValue()
            )
        );
        isButtonEnabledMediatorLiveData.addSource(aboutMeMutableLiveData, aboutMe ->
            combine(nameMutableLiveData.getValue(), addressMutableLiveData.getValue(), phoneNumberMutableLiveData.getValue(), aboutMe)
        );
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

    public MutableLiveData<String> getRandomImageUrl() {
        return randomImageUrlMutableLiveData;
    }

    public MutableLiveData<Boolean> getIsButtonEnabledMediatorLiveData() {
        return isButtonEnabledMediatorLiveData;
    }

    private void combine(
        @Nullable String name,
        @Nullable String address,
        @Nullable String phone,
        @Nullable String aboutMe
    ) {
        if (name == null || address == null || phone == null || aboutMe == null) {
            return;
        }
        isButtonEnabledMediatorLiveData.setValue(!name.isEmpty()
            && !address.isEmpty()
            && !phone.isEmpty()
            && !aboutMe.isEmpty()
        );
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
