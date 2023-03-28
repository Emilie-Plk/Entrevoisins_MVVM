package com.example.entrevoisins_mvvm.view.create;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.entrevoisins_mvvm.data.entities.NeighbourEntity;
import com.example.entrevoisins_mvvm.data.repository.NeighboursRepository;
import com.example.entrevoisins_mvvm.view.utils.SingleLiveEvent;

public class AddNeighbourActivityViewModel extends ViewModel {

    @NonNull
    private final NeighboursRepository repository;

    // TODO: Maybe create a new object (List<String> or NeighbourData) to encapsulate all neighbour data?
    private final String randomImageUrl = "https://i.pravatar.cc/150?u=" + System.currentTimeMillis();
    private final MutableLiveData<String> randomImageUrlMutableLiveData = new MutableLiveData<>(randomImageUrl);

    private final MutableLiveData<String> nameMutableLiveData = new MutableLiveData<>("");

    private final MutableLiveData<String> addressMutableLiveData = new MutableLiveData<>("");

    private final MutableLiveData<String> phoneNumberMutableLiveData = new MutableLiveData<>("");

    private final MutableLiveData<String> aboutMeMutableLiveData = new MutableLiveData<>("");

    private final MutableLiveData<Boolean> isButtonEnabled = new MutableLiveData<>();

    private final SingleLiveEvent<Void> closeActivity = new SingleLiveEvent<>();

    public AddNeighbourActivityViewModel(
            @NonNull NeighboursRepository repository
    ) {
        this.repository = repository;

        randomImageUrlMutableLiveData.setValue("https://i.pravatar.cc/150?u=" + System.currentTimeMillis());

        isButtonEnabled.setValue(
                !randomImageUrlMutableLiveData.getValue().isEmpty()
                        && !nameMutableLiveData.getValue().isEmpty()
                        && !addressMutableLiveData.getValue().isEmpty()
                        && !phoneNumberMutableLiveData.getValue().isEmpty()
                        && !aboutMeMutableLiveData.getValue().isEmpty());
    }


    public void addNeighbour(String name, String address, String phoneNumber, String aboutMe) {
        // should I create the new NeighbourEntity here or in the fragment?
        repository.addNeighbour(new NeighbourEntity(0,
                false,
                name,
                randomImageUrl,
                address,
                phoneNumber,
                aboutMe));
        closeActivity.call();
    }

    public SingleLiveEvent<Void> getCloseActivity() {
        return closeActivity;
    }

    public MutableLiveData<String> getRandomImageUrl() {
        return randomImageUrlMutableLiveData;
    }

    public MutableLiveData<Boolean> getIsButtonEnabled() {
        return isButtonEnabled;
    }

    public void updateForNeighbourInfoCompletion() {
        isButtonEnabled.setValue(
                // TODO: getValue() => not good except inside of combine()!
                !randomImageUrlMutableLiveData.getValue().isEmpty()
                        && !nameMutableLiveData.getValue().isEmpty()
                        && !addressMutableLiveData.getValue().isEmpty()
                        && !phoneNumberMutableLiveData.getValue().isEmpty()
                        && !aboutMeMutableLiveData.getValue().isEmpty());
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
