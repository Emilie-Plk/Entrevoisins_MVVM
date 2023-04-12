package com.example.entrevoisins_mvvm.data.repository;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import com.example.entrevoisins_mvvm.data.dao.NeighbourDao;
import com.example.entrevoisins_mvvm.data.entities.NeighbourEntity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NeighboursRepository {

    @NonNull
    private final NeighbourDao dao;

    @Inject
    public NeighboursRepository(
        @NonNull NeighbourDao dao
    ) {
        this.dao = dao;
    }

    @WorkerThread
    public void addNeighbour(@NonNull NeighbourEntity neighbour) {
        dao.insertNeighbour(neighbour);
    }

    @WorkerThread
    public void deleteNeighbour(long neighbourID) {
        dao.deleteNeighbour(neighbourID);
    }

    @MainThread
    public LiveData<List<NeighbourEntity>> getNeighbourEntitiesLiveData() {
        return dao.getNeighbourEntities();
    }

    @WorkerThread
    public void updateFavorite(long neighbourId, boolean isFavorite) {
        dao.updateFavorite(neighbourId, isFavorite);
    }

    @MainThread
    public LiveData<NeighbourEntity> getDetailNeighbourInfo(long neighbourId) {
        return dao.getDetailNeighbourInfo(neighbourId);
    }

    @MainThread
    public NeighbourEntity getNeighbourEntity(long neighbourId) {
        return dao.getNeighbour(neighbourId);
    }
}
