package com.example.entrevoisins_mvvm.view.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.entrevoisins_mvvm.data.repository.NeighboursRepository;

public class DetailProfileNeighbourViewModel extends ViewModel {

    private final NeighboursRepository repository;

    private final MutableLiveData<Boolean> isNeighbourFavorite = new MutableLiveData<>();

    public DetailProfileNeighbourViewModel(NeighboursRepository repository) {
        this.repository = repository;
    }

    public LiveData<DetailNeighbourViewStateItem> getDetailNeighbourViewStateItem(long neighbourId) {
        return Transformations.map(repository.getDetailNeighbourInfo(neighbourId), neighbour ->
                new DetailNeighbourViewStateItem(
                        neighbour.getId(),
                        neighbour.getNeighbourName(),
                        neighbour.getAvatarUrl(),
                        neighbour.getAddress(),
                        neighbour.getPhoneNumber(),
                        neighbour.getAboutMe(),
                        neighbour.isFavorite()));
    }
    public void updateNeighbourToFav(long neighbourId, boolean isFav) {
        repository.updateFavorite(neighbourId, !isFav);
    }

    public LiveData<Boolean> isNeighbourFavorite(long neighbourId) {
        return Transformations.map(repository.getDetailNeighbourInfo(neighbourId),
                neighbour -> {
                    isNeighbourFavorite.setValue(neighbour.isFavorite());
                    return neighbour.isFavorite();
                });
    }


}
