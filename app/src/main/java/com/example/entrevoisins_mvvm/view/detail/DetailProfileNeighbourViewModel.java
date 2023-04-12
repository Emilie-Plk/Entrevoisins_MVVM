package com.example.entrevoisins_mvvm.view.detail;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.entrevoisins_mvvm.DI.DatabaseModule;
import com.example.entrevoisins_mvvm.R;
import com.example.entrevoisins_mvvm.data.entities.NeighbourEntity;
import com.example.entrevoisins_mvvm.data.repository.NeighboursRepository;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DetailProfileNeighbourViewModel extends ViewModel {

    private final NeighboursRepository repository;


    @NonNull
    private final Executor ioExecutor;

    @Inject
    public DetailProfileNeighbourViewModel(
        @NonNull NeighboursRepository repository,
        @NonNull @DatabaseModule.IoExecutor Executor ioExecutor
    ) {
        this.repository = repository;
        this.ioExecutor = ioExecutor;
    }

    public LiveData<DetailNeighbourViewStateItem> getDetailNeighbourViewStateItem(long neighbourId) {
        return Transformations.map(repository.getDetailNeighbourInfo(neighbourId), neighbour -> {
                @DrawableRes int favoriteDrawable;
                if (neighbour.isFavorite()) {
                    favoriteDrawable = R.drawable.baseline_star_24;
                } else {
                    favoriteDrawable = R.drawable.ic_star_border_white_24dp;
                }
                return new DetailNeighbourViewStateItem(
                    neighbour.getNeighbourName(),
                    neighbour.getAvatarUrl(),
                    neighbour.getAddress(),
                    neighbour.getPhoneNumber(),
                    neighbour.getAboutMe(),
                    favoriteDrawable
                );
            }
        );
    }

    public void onToggleNeighbourFavorite(long neighbourId) {
        ioExecutor.execute(() -> {
                NeighbourEntity neighbourEntity = repository.getNeighbourEntity(neighbourId);
                repository.updateFavorite(neighbourId, !neighbourEntity.isFavorite()
                );
            }
        );
    }

}
