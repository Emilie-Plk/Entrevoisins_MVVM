package com.example.entrevoisins_mvvm.view.detail;

import static com.example.entrevoisins_mvvm.utils.TestUtil.getValueForTesting;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.annotation.DrawableRes;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.entrevoisins_mvvm.R;
import com.example.entrevoisins_mvvm.data.entities.NeighbourEntity;
import com.example.entrevoisins_mvvm.data.repository.NeighboursRepository;
import com.example.entrevoisins_mvvm.utils.TestExecutor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

public class DetailProfileNeighbourViewModelTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    private final NeighboursRepository repository = mock(NeighboursRepository.class);

    private MutableLiveData<NeighbourEntity> neighbourEntityMutableLiveData;

    private DetailProfileNeighbourViewModel viewModel;

    private static final long NEIGHBOUR_ID = 1;

    private static final int NOT_FAV_STAR_DRAWABLE = R.drawable.ic_star_border_white_24dp;

    private static final int FAV_STAR_DRAWABLE = R.drawable.baseline_star_24;

    private static final NeighbourEntity NEIGHBOUR_ENTITY = new NeighbourEntity(
        1,
        false,
        "Usagi",
        "https://i.pravatar.cc/150?u=" + System.currentTimeMillis(),
        "Hawaii",
        "123456789",
        "My favorite song is The Matte Kudasai");

    @Before
    public void setUp() {
        neighbourEntityMutableLiveData = new MutableLiveData<>();

        doReturn(neighbourEntityMutableLiveData).when(repository).getDetailNeighbourInfo(NEIGHBOUR_ID);

        neighbourEntityMutableLiveData.setValue(getNeighbourEntity(false));

        doReturn(NEIGHBOUR_ENTITY).when(repository).getNeighbourEntity(NEIGHBOUR_ID);

        viewModel = new DetailProfileNeighbourViewModel(repository, new TestExecutor());
    }

    @Test
    public void nominal_case() {
        // WHEN
        viewModel.getDetailNeighbourViewStateItem(NEIGHBOUR_ID);

        // THEN
        verify(repository).getDetailNeighbourInfo(NEIGHBOUR_ID);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void nominal_case_neighbour_is_not_favorite() {
        // GIVEN
        neighbourEntityMutableLiveData.setValue(getNeighbourEntity(false));

        // WHEN
        DetailNeighbourViewStateItem result = getValueForTesting(viewModel.getDetailNeighbourViewStateItem(NEIGHBOUR_ID));
        DetailNeighbourViewStateItem neighbourViewStateItemTest = getNeighbourViewStateItem(NOT_FAV_STAR_DRAWABLE);

        // THEN
        assertEquals(neighbourViewStateItemTest.getName(), result.getName());
        assertEquals(neighbourViewStateItemTest.getAddress(), result.getAddress());
        assertEquals(neighbourViewStateItemTest.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(neighbourViewStateItemTest.getAboutMe(), result.getAboutMe());
        assertEquals(neighbourViewStateItemTest.getFavoriteDrawable(), result.getFavoriteDrawable());
    }

    @Test
    public void nominal_case_neighbour_is_favorite() {
        // GIVEN
        neighbourEntityMutableLiveData.setValue(getNeighbourEntity(true));

        // WHEN
        DetailNeighbourViewStateItem result = getValueForTesting(viewModel.getDetailNeighbourViewStateItem(NEIGHBOUR_ID));
        DetailNeighbourViewStateItem neighbourViewStateItemTest = getNeighbourViewStateItem(FAV_STAR_DRAWABLE);

        // THEN
        assertEquals(neighbourViewStateItemTest.getName(), result.getName());
        assertEquals(neighbourViewStateItemTest.getAddress(), result.getAddress());
        assertEquals(neighbourViewStateItemTest.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(neighbourViewStateItemTest.getAboutMe(), result.getAboutMe());
        assertEquals(neighbourViewStateItemTest.getFavoriteDrawable(), result.getFavoriteDrawable());
    }

    @Test
    public void nominal_case_on_toggle_neighbour_favorite() {
        // WHEN
        viewModel.onToggleNeighbourFavorite(NEIGHBOUR_ID);

        // THEN
        verify(repository).getNeighbourEntity(NEIGHBOUR_ID);
        verify(repository).updateFavorite(NEIGHBOUR_ID, !NEIGHBOUR_ENTITY.isFavorite());
        verifyNoMoreInteractions(repository);
    }

    // region helper methods
    private NeighbourEntity getNeighbourEntity(boolean isFavorite) {
        return new NeighbourEntity(
            1,
            isFavorite,
            "Usagi",
            "https://i.pravatar.cc/150?u=" + Clock.fixed(Instant.ofEpochMilli(1681110562L), ZoneOffset.UTC),
            "Hawaii",
            "123456789",
            "My favorite song is The Matte Kudasai");
    }

    private DetailNeighbourViewStateItem getNeighbourViewStateItem(@DrawableRes int drawableFavStar) {
        return new DetailNeighbourViewStateItem(
            NEIGHBOUR_ENTITY.getNeighbourName(),
            NEIGHBOUR_ENTITY.getAvatarUrl(),
            NEIGHBOUR_ENTITY.getAddress(),
            NEIGHBOUR_ENTITY.getPhoneNumber(),
            NEIGHBOUR_ENTITY.getAboutMe(),
            drawableFavStar
        );
    }
    // endregion
}
