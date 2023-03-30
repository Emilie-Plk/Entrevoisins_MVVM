package com.example.entrevoisins_mvvm;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.entrevoisins_mvvm.data.entities.NeighbourEntity;
import com.example.entrevoisins_mvvm.data.repository.NeighboursRepository;
import com.example.entrevoisins_mvvm.view.create.AddNeighbourActivityViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AddNeighbourActivityViewModelTest {

    @Mock
    private NeighboursRepository repository = mock(NeighboursRepository.class);

    private AddNeighbourActivityViewModel viewModel;

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        viewModel = new AddNeighbourActivityViewModel(repository);
    }

    @Test
    public void onAddingNewNeighbour_repositoryShouldAddNewNeighbour() {
        // GIVEN
        NeighbourEntity neighbourEntity = mock(NeighbourEntity.class);

        // WHEN
        viewModel.addNeighbour(
            neighbourEntity.getNeighbourName(),
            neighbourEntity.getAddress(),
            neighbourEntity.getPhoneNumber(),
            neighbourEntity.getAboutMe()
        );

        // THEN
        verify(repository).addNeighbour(neighbourEntity);
        verifyNoMoreInteractions(repository);
    }

}
