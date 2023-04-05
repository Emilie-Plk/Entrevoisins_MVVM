package com.example.entrevoisins_mvvm.view.create;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.entrevoisins_mvvm.data.entities.NeighbourEntity;
import com.example.entrevoisins_mvvm.data.repository.NeighboursRepository;
import com.example.entrevoisins_mvvm.utils.TestUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

public class AddNeighbourViewModelTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    private final NeighboursRepository repository = mock(NeighboursRepository.class);

    // 05/04/2023 - 18:31:58
    private final static long EPOCH_MILLI = 1680712318000L;
    private final Clock clock = Clock.fixed(Instant.ofEpochMilli(EPOCH_MILLI), ZoneOffset.UTC);

    private AddNeighbourViewModel viewModel;

    private final static String NAME = "Usagi";
    private final static String ADDRESS = "Hawaii";
    private final static String PHONE_NUMBER = "123456789";
    private final static String ABOUT_ME = "My fav song is The Matte Kudasai";

    @Before
    public void setUp() {
        viewModel = new AddNeighbourViewModel(repository, clock);
    }

    @Test
    public void addNeighbour_nominal_case() {
        // WHEN
        viewModel.addNeighbour(
            NAME,
            ADDRESS,
            PHONE_NUMBER,
            ABOUT_ME
        );
        int emitCount = TestUtil.getEmitCountForTesting(viewModel.getCloseSingleLiveData());

        // THEN
        assertEquals(1, emitCount);
        verify(repository).addNeighbour(
            new NeighbourEntity(
                0,
                false,
                NAME,
                "https://i.pravatar.cc/150?u=" + EPOCH_MILLI,
                ADDRESS,
                PHONE_NUMBER,
                ABOUT_ME
            )
        );
        verifyNoMoreInteractions(repository);
    }
//
//    @Test
//    public void closeActivity_whenAddingNewNeighbour_closeActivityObserverShouldBeTriggeredOnce() {
//        // TODO: not sure at all about this (SingleLiveEvent testing)
//        // GIVEN
//        NeighbourEntity neighbourEntity = mock(NeighbourEntity.class);
//        viewModel.getCloseActivity().observeForever(closeActivityObserver);
//
//        // WHEN
//        viewModel.addNeighbour(
//            neighbourEntity.getNeighbourName(),
//            neighbourEntity.getAddress(),
//            neighbourEntity.getPhoneNumber(),
//            neighbourEntity.getAboutMe()
//        );
//
//        // THEN
//        verify(closeActivityObserver).onChanged(null);
//        verifyNoMoreInteractions(closeActivityObserver);
//    }
//
//    @Test
//    public void nominal_case_imageUrl() {
//        String imageUrl = getValueForTesting(viewModel.getRandomImageUrl());
//        assertTrue(imageUrl.startsWith("https://i.pravatar.cc/150?u="));
//    }
//
//    // region Field completion
//    @Test
//    public void on_no_field_filled() {
//        // WHEN
//        boolean isButtonEnabled = getValueForTesting(viewModel.getIsButtonEnabledMutableLiveData());
//
//        // THEN
//        assertFalse(isButtonEnabled);
//    }
//
//    @Test
//    public void on_fields_all_filled() {
//        // WHEN
//        viewModel.setValueForName(NAME);
//        viewModel.setValueForAddress(ADDRESS);
//        viewModel.setValueForPhoneNumber(PHONE_NUMBER);
//        viewModel.setValueForAboutMe(ABOUT_ME);
//        boolean isButtonEnabled = getValueForTesting(viewModel.getIsButtonEnabledMutableLiveData());
//
//        // THEN
//        assertTrue(isButtonEnabled);
//    }
//
//    @Test
//    public void on_fields_partially_filled() {
//        // WHEN
//        viewModel.setValueForName(NAME);
//        viewModel.setValueForAddress(ADDRESS);
//        viewModel.setValueForPhoneNumber("");
//        viewModel.setValueForAboutMe(ABOUT_ME);
//
//        boolean isButtonEnabled = getValueForTesting(viewModel.getIsButtonEnabledMutableLiveData());
//
//        // THEN
//        assertFalse(isButtonEnabled);
//    }
//
//    @Test
//    public void onAllFieldsFilled_whenOneFieldEmptied_shouldReturnFalse() {
//        // GIVEN
//        viewModel.setValueForName(NAME);
//        viewModel.setValueForAddress(ADDRESS);
//        viewModel.setValueForPhoneNumber(PHONE_NUMBER);
//        viewModel.setValueForAboutMe(ABOUT_ME);
//
//        // WHEN
//        viewModel.setValueForPhoneNumber("");
//
//        // THEN
//        boolean isButtonEnabled = getValueForTesting(viewModel.getIsButtonEnabledMutableLiveData());
//        assertFalse(isButtonEnabled);
//    }
//    // endregion

}
