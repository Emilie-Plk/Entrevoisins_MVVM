package com.example.entrevoisins_mvvm.view.list;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.entrevoisins_mvvm.DI.AppModule;
import com.example.entrevoisins_mvvm.data.entities.NeighbourEntity;
import com.example.entrevoisins_mvvm.data.repository.NeighboursRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class NeighbourListViewModel extends ViewModel {

    @NonNull
    private final NeighboursRepository repository;

    @NonNull
    private final Executor ioExecutor;

    @Inject
    public NeighbourListViewModel(@NonNull NeighboursRepository repository, @AppModule.IoExecutor @NonNull Executor ioExecutor) {
        this.repository = repository;
        this.ioExecutor = ioExecutor;
    }

    public LiveData<List<NeighbourViewStateItem>> getNeighbourViewStateItemLiveData(boolean isFav) {
        return Transformations.map(repository.getNeighbourEntitiesLiveData(), neighbours -> {
                List<NeighbourViewStateItem> neighboursViewStateItems = new ArrayList<>();
                for (NeighbourEntity neighbour : neighbours) {
                    if (neighbour.isFavorite() || !isFav) {
                        neighboursViewStateItems.add(
                            new NeighbourViewStateItem(
                                neighbour.getId(),
                                neighbour.getNeighbourName(),
                                neighbour.getAvatarUrl()
                            )
                        );
                    }
                }
                return neighboursViewStateItems;
            }
        );
    }

    public void deleteNeighbour(long id) {
        ioExecutor.execute(() -> repository.deleteNeighbour(id));
    }
}
