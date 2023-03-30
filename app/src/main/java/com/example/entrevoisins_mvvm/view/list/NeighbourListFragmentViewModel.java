package com.example.entrevoisins_mvvm.view.list;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.entrevoisins_mvvm.data.entities.NeighbourEntity;
import com.example.entrevoisins_mvvm.data.repository.NeighboursRepository;

import java.util.ArrayList;
import java.util.List;

public class NeighbourListFragmentViewModel extends ViewModel {

    @NonNull
    private final NeighboursRepository repository;

    public NeighbourListFragmentViewModel(@NonNull NeighboursRepository repository) {
        this.repository = repository;
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
        repository.deleteNeighbour(id);
    }
}
