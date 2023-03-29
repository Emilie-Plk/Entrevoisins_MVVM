package com.example.entrevoisins_mvvm.data.repository;

import androidx.lifecycle.LiveData;

import com.example.entrevoisins_mvvm.data.dao.NeighbourDao;
import com.example.entrevoisins_mvvm.data.entities.NeighbourEntity;

import java.util.List;
import java.util.concurrent.Executor;

public class NeighboursRepository {

    private final NeighbourDao dao;

    private final Executor executor;

    public NeighboursRepository(NeighbourDao dao, Executor executor) {
   /*     if (buildConfigResolver.isDebug()) {
            generateNeighbours();
        }*/
        this.dao = dao;
        this.executor = executor;
    }

    public void addNeighbour(NeighbourEntity neighbour) {
        executor.execute(() ->
                dao.insertNeighbour(neighbour));
    }

    public void deleteNeighbour(long neighbourID) {
        executor.execute(() ->
                dao.deleteNeighbour(neighbourID));
    }

    public LiveData<List<NeighbourEntity>> getNeighbourEntitiesLiveData() {
        return dao.getNeighbourEntities();
    }

    public void updateFavorite(long neighbourId, boolean isFavorite) {
        executor.execute(() -> dao.updateFavorite(neighbourId, isFavorite));
    }

    public LiveData<NeighbourEntity> getDetailNeighbourInfo(long neighbourId) {
        return dao.getDetailNeighbourInfo(neighbourId);
    }

   public NeighbourEntity getNeighbourEntity(long neighbourId) {
    return dao.getNeighbour(neighbourId);
    }

}
