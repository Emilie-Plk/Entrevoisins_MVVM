package com.example.entrevoisins_mvvm.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.entrevoisins_mvvm.data.entities.NeighbourEntity;

import java.util.List;

@Dao
public interface NeighbourDao {

    @Insert
    void insertNeighbour(NeighbourEntity neighbourEntity);

    @Query("SELECT * FROM neighbours WHERE id=:id LIMIT 1")
    NeighbourEntity getNeighbour(long id);

    @Query("DELETE FROM neighbours WHERE id = :neighbourId")
    void deleteNeighbour(long neighbourId);

    @Query("SELECT * FROM neighbours")
    LiveData<List<NeighbourEntity>> getNeighbourEntities();

    @Query("UPDATE neighbours SET isFavorite = :isFavorite WHERE id = :neighbourId")
    void updateFavorite(long neighbourId, boolean isFavorite);

    @Query("SELECT * FROM neighbours WHERE id = :neighbourId LIMIT 1")
    LiveData<NeighbourEntity> getDetailNeighbourInfo(long neighbourId);
}
