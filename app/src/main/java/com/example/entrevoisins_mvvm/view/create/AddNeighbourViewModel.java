package com.example.entrevoisins_mvvm.view.create;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.entrevoisins_mvvm.data.entities.NeighbourEntity;
import com.example.entrevoisins_mvvm.data.repository.NeighboursRepository;
import com.example.entrevoisins_mvvm.view.utils.SingleLiveEvent;

import java.time.Clock;
import java.time.Instant;

public class AddNeighbourViewModel extends ViewModel {

    @NonNull
    private final NeighboursRepository repository;

    // TODO: Maybe create a new object (List<String> or NeighbourData) to encapsulate all neighbour data?
    private final String randomImageUrl;
    private final MutableLiveData<String> randomImageUrlMutableLiveData;

    private final MutableLiveData<String> nameMutableLiveData = new MutableLiveData<>("");

    private final MutableLiveData<String> addressMutableLiveData = new MutableLiveData<>("");

    private final MutableLiveData<String> phoneNumberMutableLiveData = new MutableLiveData<>("");

    private final MutableLiveData<String> aboutMeMutableLiveData = new MutableLiveData<>("");

    private final MutableLiveData<Boolean> isButtonEnabledMutableLiveData = new MutableLiveData<>(false);

    private final SingleLiveEvent<Void> closeSingleLiveEvent = new SingleLiveEvent<>();

    public AddNeighbourViewModel(
        @NonNull NeighboursRepository repository,
        @NonNull Clock clock
    ) {
        this.repository = repository;

        randomImageUrl = "https://i.pravatar.cc/150?u=" + Instant.now(clock).toEpochMilli();

        randomImageUrlMutableLiveData = new MutableLiveData<>(randomImageUrl);
    }

    public void addNeighbour(@NonNull String name, @NonNull String address, @NonNull String phoneNumber, @NonNull String aboutMe) {
        // TODO: wondering if that's the proper way to add a new neighbour (issue with UT and randomImg's url)
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
        );
        closeSingleLiveEvent.call();
    }

    public SingleLiveEvent<Void> getCloseSingleLiveData() {
        return closeSingleLiveEvent;
    }

    public MutableLiveData<String> getRandomImageUrl() {
        return randomImageUrlMutableLiveData;
    }

    public MutableLiveData<Boolean> getIsButtonEnabledMutableLiveData() {
        return isButtonEnabledMutableLiveData;
    }

    private void updateForNeighbourInfoCompletion() {
        isButtonEnabledMutableLiveData.setValue(
            // TODO: getValue() => not good except inside of combine()!
            !randomImageUrlMutableLiveData.getValue().isEmpty()
                && !nameMutableLiveData.getValue().isEmpty()
                && !addressMutableLiveData.getValue().isEmpty()
                && !phoneNumberMutableLiveData.getValue().isEmpty()
                && !aboutMeMutableLiveData.getValue().isEmpty()
        );
    }

    private void setValueForCompletion(MutableLiveData<String> mutableLiveData, String value) {
        mutableLiveData.setValue(value);
        updateForNeighbourInfoCompletion();
    }

    public void setValueForName(String name) {
        setValueForCompletion(nameMutableLiveData, name);
    }

    public void setValueForAddress(String address) {
        setValueForCompletion(addressMutableLiveData, address);
    }

    public void setValueForPhoneNumber(String phoneNumber) {
        setValueForCompletion(phoneNumberMutableLiveData, phoneNumber);
    }

    public void setValueForAboutMe(String aboutMe) {
        setValueForCompletion(aboutMeMutableLiveData, aboutMe);
    }
}
