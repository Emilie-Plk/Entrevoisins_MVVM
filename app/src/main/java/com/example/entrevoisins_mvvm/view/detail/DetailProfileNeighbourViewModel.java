package com.example.entrevoisins_mvvm.view.detail;

import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.entrevoisins_mvvm.R;
import com.example.entrevoisins_mvvm.data.entities.NeighbourEntity;
import com.example.entrevoisins_mvvm.data.repository.NeighboursRepository;

import java.util.concurrent.Executor;

public class DetailProfileNeighbourViewModel extends ViewModel {

    private final NeighboursRepository repository;

    @NonNull
    private final Resources res;


    // private final MutableLiveData<Boolean> isNeighbourFavorite = new MutableLiveData<>();

    private final MutableLiveData<Integer> favoriteFabResourcesMutableLiveData = new MutableLiveData<>();

    @NonNull
    private final Executor ioExecutor;

    public DetailProfileNeighbourViewModel(
            @NonNull NeighboursRepository repository,
            @NonNull Executor ioExecutor,
            @NonNull Resources res) {
        this.repository = repository;
        this.ioExecutor = ioExecutor;
        this.res = res;
    }

    public LiveData<DetailNeighbourViewStateItem> getDetailNeighbourViewStateItem(long neighbourId) {
        return Transformations.map(repository.getDetailNeighbourInfo(neighbourId), neighbour ->
                new DetailNeighbourViewStateItem(
                        neighbour.getId(),
                        neighbour.getNeighbourName(),
                        neighbour.getAvatarUrl(),
                        neighbour.getAddress(),
                        neighbour.getPhoneNumber(),
                        neighbour.getAboutMe()));
    }

    public void toggleNeighbourFavorite(long neighbourId) {
        ioExecutor.execute(() -> {
            NeighbourEntity neighbourEntity = repository.getNeighbourEntity(neighbourId);
            repository.updateFavorite(neighbourId, !neighbourEntity.isFavorite());
        });
    }

    public void isNeighbourFavorite(long neighbourId) {
        ioExecutor.execute(() -> {
            NeighbourEntity neighbourEntity = repository.getNeighbourEntity(neighbourId);
            if (neighbourEntity.isFavorite()) {
                favoriteFabResourcesMutableLiveData.postValue(R.drawable.baseline_star_24);
            } else {
                favoriteFabResourcesMutableLiveData.postValue(R.drawable.ic_star_border_white_24dp);
            }
        });
    }



    public MutableLiveData<Integer> getFavoriteFabResourcesMutableLiveData() {
        return favoriteFabResourcesMutableLiveData;
    }
}
