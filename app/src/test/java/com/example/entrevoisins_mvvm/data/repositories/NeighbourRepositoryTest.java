package com.example.entrevoisins_mvvm.data.repositories;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.entrevoisins_mvvm.config.BuildConfigResolver;
import com.example.entrevoisins_mvvm.data.dao.NeighbourDao;
import com.example.entrevoisins_mvvm.data.entities.NeighbourEntity;
import com.example.entrevoisins_mvvm.data.repository.NeighboursRepository;
import com.example.entrevoisins_mvvm.utils.TestExecutor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

public class NeighbourRepositoryTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();
    // for livedata

    private final NeighbourDao neighbourDao = mock(NeighbourDao.class);

    private NeighboursRepository repository;

    @Before
    public void setUp() {
        repository = new NeighboursRepository(neighbourDao);
    }

    @Test
    public void verify_addNeighbour() {
        // GIVEN
        NeighbourEntity neighbour = mock(NeighbourEntity.class);

        // WHEN
        repository.addNeighbour(neighbour);

        // THEN
        verify(neighbourDao).insertNeighbour(neighbour);
        verifyNoMoreInteractions(neighbourDao);
    }

    @Test
    public void verify_deleteNeighbour() {
        // GIVEN
        NeighbourEntity neighbour = mock(NeighbourEntity.class);

        // WHEN
        repository.deleteNeighbour(neighbour.getId());

        // THEN
        verify(neighbourDao).deleteNeighbour(neighbour.getId());
        verifyNoMoreInteractions(neighbourDao);
    }

    @Test
    public void verify_getNeighbourEntitiesLiveData() {
        // GIVEN
        LiveData<List<NeighbourEntity>> listNeighbourLiveData = spy(new MutableLiveData<>());
        doReturn(listNeighbourLiveData).when(neighbourDao).getNeighbourEntities();

        // WHEN
        LiveData<List<NeighbourEntity>> result = repository.getNeighbourEntitiesLiveData();

        // THEN
        assertEquals(listNeighbourLiveData, result);
        verify(neighbourDao).getNeighbourEntities();
        verifyNoMoreInteractions(neighbourDao);
    }

    @Test
    public void verify_updateNeighbourFavorite() {
        // GIVEN
        NeighbourEntity neighbour = mock(NeighbourEntity.class);

        // WHEN
        repository.updateFavorite(neighbour.getId(), neighbour.isFavorite());

        // THEN
        verify(neighbourDao).updateFavorite(neighbour.getId(), neighbour.isFavorite());
        verifyNoMoreInteractions(neighbourDao);
    }

    @Test
    public void verify_getDetailNeighbourInfo() {
        // GIVEN
        NeighbourEntity neighbour = mock(NeighbourEntity.class);

        // WHEN
        repository.getDetailNeighbourInfo(neighbour.getId());

        // THEN
        verify(neighbourDao).getDetailNeighbourInfo(neighbour.getId());
        verifyNoMoreInteractions(neighbourDao);
    }

    @Test
    public void verify_getNeighbourEntity() {
        // GIVEN
        NeighbourEntity neighbour = mock(NeighbourEntity.class);

        // WHEN
        repository.getNeighbourEntity(neighbour.getId());

        // THEN
        verify(neighbourDao).getNeighbour(neighbour.getId());
        verifyNoMoreInteractions(neighbourDao);
    }
}
