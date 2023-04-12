package com.example.entrevoisins_mvvm.view.list;

import static com.example.entrevoisins_mvvm.utils.TestUtil.getValueForTesting;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.entrevoisins_mvvm.data.entities.NeighbourEntity;
import com.example.entrevoisins_mvvm.data.repository.NeighboursRepository;
import com.example.entrevoisins_mvvm.utils.TestExecutor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class NeighbourListViewModelTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    private final NeighboursRepository repository = mock(NeighboursRepository.class);

    private NeighbourListViewModel viewModel;

    private MutableLiveData<List<NeighbourEntity>> neighbourListMutableLiveData;

    @Before
    public void setUp() {
        neighbourListMutableLiveData = new MutableLiveData<>();

        doReturn(neighbourListMutableLiveData).when(repository).getNeighbourEntitiesLiveData();

        List<NeighbourEntity> dummyNeighbourList = getNoFavoriteNeighbourListTest();
        neighbourListMutableLiveData.setValue(dummyNeighbourList);

        viewModel = new NeighbourListViewModel(repository, new TestExecutor());
    }

    @Test
    public void nominal_case() {
        // WHEN
        List<NeighbourViewStateItem> result = getValueForTesting(viewModel.getNeighbourViewStateItemLiveData(false));

        // THEN
        assertEquals(3, result.size());
        verify(repository).getNeighbourEntitiesLiveData();
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void nominal_case_neighbours_not_fav_by_default() {
        // WHEN
        List<NeighbourViewStateItem> result = getValueForTesting(viewModel.getNeighbourViewStateItemLiveData(true));

        // THEN
        assertEquals(0, result.size());
        verify(repository).getNeighbourEntitiesLiveData();
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void nominal_case_one_favorite_neighbour() {
        // GIVEN
        neighbourListMutableLiveData.setValue(getNeighbourWithFirstOneFavoriteListTest());

        // WHEN
        List<NeighbourViewStateItem> result = getValueForTesting(viewModel.getNeighbourViewStateItemLiveData(true));

        // THEN
        assertEquals(1, result.size());
        verify(repository).getNeighbourEntitiesLiveData();
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void edge_case_no_neighbour() {
        // GIVEN
        List<NeighbourEntity> emptyTaskList = new ArrayList<>();

        // WHEN
        neighbourListMutableLiveData.setValue(emptyTaskList);

        // THEN
        List<NeighbourViewStateItem> result = getValueForTesting(viewModel.getNeighbourViewStateItemLiveData(false));
        assertEquals(0, result.size());
        verify(repository).getNeighbourEntitiesLiveData();
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void nominal_case_delete_one_neighbour() {
        // GIVEN
        long neighbourId = 1;

        // WHEN
        viewModel.deleteNeighbour(neighbourId);

        // THEN
        verify(repository).deleteNeighbour(neighbourId);
        verifyNoMoreInteractions(repository);
    }

    //region helper methods
    private List<NeighbourEntity> getNoFavoriteNeighbourListTest() {
        List<NeighbourEntity> neighbourEntityList = new ArrayList<>();
        neighbourEntityList.add(
            new NeighbourEntity(
                1,
                false,
                "Nikaido",
                "https://i.pravatar.cc/150?u=a042581f4e29026704d",
                "Saint-Pierre-du-Mont ; 5km",
                "+33 6 86 57 90 14",
                "Bonjour ! Je souhaite rencontrer des gens pour jouer à des jeux de cartes et discuter de tout et de rien."));

        neighbourEntityList.add(
            new NeighbourEntity(
                2,
                false,
                "Kaiman",
                "https://i.pravatar.cc/150?u=a042581f4e2902898",
                "Saint-Pierre-du-Mont ; 5km",
                "+33 6 86 57 90 14",
                "Bonjour ! Je souhaite rencontrer des gens pour jouer à des jeux de cartes et discuter de tout et de rien."));

        neighbourEntityList.add(
            new NeighbourEntity(
                3,
                false,
                "Ebisu",
                "https://i.pravatar.cc/150?u=a042581f4e29026704d",
                "Saint-Pierre-du-Mont ; 5km",
                "+33 6 86 57 90 14",
                "Bonjour ! Je souhaite rencontrer des gens pour jouer à des jeux de cartes et discuter de tout et de rien."));

        return neighbourEntityList;
    }

    private List<NeighbourEntity> getNeighbourWithFirstOneFavoriteListTest() {
        List<NeighbourEntity> neighbourEntityList = new ArrayList<>();
        neighbourEntityList.add(
            new NeighbourEntity(
                1,
                true,
                "Nikaido",
                "https://i.pravatar.cc/150?u=a042581f4e29026704d",
                "Saint-Pierre-du-Mont ; 5km",
                "+33 6 86 57 90 14",
                "Bonjour ! Je souhaite rencontrer des gens pour jouer à des jeux de cartes et discuter de tout et de rien."));

        neighbourEntityList.add(
            new NeighbourEntity(
                2,
                false,
                "Kaiman",
                "https://i.pravatar.cc/150?u=a042581f4e2902898",
                "Saint-Pierre-du-Mont ; 5km",
                "+33 6 86 57 90 14",
                "Bonjour ! Je souhaite rencontrer des gens pour jouer à des jeux de cartes et discuter de tout et de rien."));

        neighbourEntityList.add(
            new NeighbourEntity(
                3,
                false,
                "Ebisu",
                "https://i.pravatar.cc/150?u=a042581f4e29026704d",
                "Saint-Pierre-du-Mont ; 5km",
                "+33 6 86 57 90 14",
                "Bonjour ! Je souhaite rencontrer des gens pour jouer à des jeux de cartes et discuter de tout et de rien."));

        return neighbourEntityList;
    }
    //endregion
}
