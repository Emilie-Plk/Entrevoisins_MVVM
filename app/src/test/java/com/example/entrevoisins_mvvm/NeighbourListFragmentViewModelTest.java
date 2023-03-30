package com.example.entrevoisins_mvvm;

import static com.example.entrevoisins_mvvm.utils.TestUtil.getValueForTesting;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.entrevoisins_mvvm.data.entities.NeighbourEntity;
import com.example.entrevoisins_mvvm.data.repository.NeighboursRepository;
import com.example.entrevoisins_mvvm.view.list.NeighbourListFragmentViewModel;
import com.example.entrevoisins_mvvm.view.list.NeighbourViewStateItem;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class NeighbourListFragmentViewModelTest {

    @Mock
    private NeighboursRepository repository = mock(NeighboursRepository.class);

    private NeighbourListFragmentViewModel viewModel;

    private MutableLiveData<List<NeighbourEntity>> neighbourListMutableLiveData;

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        neighbourListMutableLiveData = new MutableLiveData<>();

        doReturn(neighbourListMutableLiveData).when(repository).getNeighbourEntitiesLiveData();

        List<NeighbourEntity> dummyNeighbourList = getNeighbourListTest();
        neighbourListMutableLiveData.setValue(dummyNeighbourList);

        viewModel = new NeighbourListFragmentViewModel(repository);
    }

    @Test
    public void nominalCase() {
        // WHEN
        List<NeighbourViewStateItem> result = getValueForTesting(viewModel.getNeighbourViewStateItemLiveData(false));

        // THEN
        // TODO: why should the assertion be declared BEFORE the verification?
        assertEquals(3, result.size());
        verify(repository).getNeighbourEntitiesLiveData();
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void nominalCase_givesNoFavoriteNeighbours() {
        // WHEN
        List<NeighbourViewStateItem> result = getValueForTesting(viewModel.getNeighbourViewStateItemLiveData(true));

        // THEN
        assertEquals(0, result.size());
        verify(repository).getNeighbourEntitiesLiveData();
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void onOneFavoriteNeighbour_verifyRepositoryAddsGFavoriteNeighbour() {
        // GIVEN
        neighbourListMutableLiveData.setValue(getNeighbourWithFavListTest());

        // WHEN
        List<NeighbourViewStateItem> result = getValueForTesting(viewModel.getNeighbourViewStateItemLiveData(true));

        // THEN
        assertEquals(1, result.size());
        verify(repository).getNeighbourEntitiesLiveData();
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void edgeCase_noNeighbour() {
        // GIVEN
        List<NeighbourEntity> emptyTaskList = new ArrayList<>();

        // WHEN
        neighbourListMutableLiveData.setValue(emptyTaskList);

        // THEN
        List<NeighbourViewStateItem> result = getValueForTesting(viewModel.getNeighbourViewStateItemLiveData(false));
        assertEquals(0, result.size());
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void onDeleteGivenNeighbour_verifyRepositoryDeletesGivenNeighbour() {
        // GIVEN
        long neighbourId = 1;

        // WHEN
        viewModel.deleteNeighbour(neighbourId);

        // THEN
        verify(repository).deleteNeighbour(neighbourId);
        verifyNoMoreInteractions(repository);
    }

    //region helper methods
    private List<NeighbourEntity> getNeighbourListTest() {
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

    private List<NeighbourEntity> getNeighbourWithFavListTest() {
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
