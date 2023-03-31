package com.example.entrevoisins_mvvm.daoTest;

import static com.example.entrevoisins_mvvm.daoTest.utils.TestUtil.getValueForTesting;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.entrevoisins_mvvm.data.AppDatabase;
import com.example.entrevoisins_mvvm.data.dao.NeighbourDao;
import com.example.entrevoisins_mvvm.data.entities.NeighbourEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class NeighbourDaoTest {


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase database;

    private NeighbourDao neighbourDao;

    private static final NeighbourEntity NEIGHBOUR_ENTITY = new NeighbourEntity(
        1,
        false,
        "Usagi",
        "https://i.pravatar.cc/150?u=" + System.currentTimeMillis(),
        "Hawaii",
        "123456789",
        "My favorite song is The Matte Kudasai");

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        neighbourDao = database.getNeighbourDao();
    }


    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void insert_one_neighbour() {
        // WHEN
        neighbourDao.insertNeighbour(NEIGHBOUR_ENTITY);

        List<NeighbourEntity> result = getValueForTesting(neighbourDao.getNeighbourEntities());

        assertEquals(1, result.size());
    }

    @Test
    public void delete_one_neighbour() {
        // GIVEN
        neighbourDao.insertNeighbour(NEIGHBOUR_ENTITY);

        // WHEN
        neighbourDao.deleteNeighbour(NEIGHBOUR_ENTITY.getId());

        // THEN
        List<NeighbourEntity> result = getValueForTesting(neighbourDao.getNeighbourEntities());
        assertEquals(0, result.size());
    }

    @Test
    public void get_neighbour_by_id() {
        // GIVEN
        neighbourDao.insertNeighbour(NEIGHBOUR_ENTITY);

        // WHEN
        NeighbourEntity result = neighbourDao.getNeighbour(1);

        // THEN
        assertEquals("Usagi", result.getNeighbourName());
        assertEquals(1, result.getId());
        assertFalse(result.isFavorite());
        assertThat(result.getAvatarUrl(), startsWith("https://i.pravatar.cc/150?u="));
        assertEquals("Hawaii", result.getAddress());
        assertEquals("123456789", result.getPhoneNumber());
        assertEquals("My favorite song is The Matte Kudasai", result.getAboutMe());
    }

    @Test
    public void update_neighbour_fav() {
        // GIVEN
        neighbourDao.insertNeighbour(NEIGHBOUR_ENTITY);

        // WHEN
        neighbourDao.updateFavorite(NEIGHBOUR_ENTITY.getId(), !NEIGHBOUR_ENTITY.isFavorite());

        // THEN
        NeighbourEntity result = neighbourDao.getNeighbour(NEIGHBOUR_ENTITY.getId());
        assertTrue(result.isFavorite());
    }

    @Test
    public void update_neighbour_fav_twice() {
        // GIVEN
        neighbourDao.insertNeighbour(NEIGHBOUR_ENTITY);

        // WHEN
        neighbourDao.updateFavorite(NEIGHBOUR_ENTITY.getId(), !NEIGHBOUR_ENTITY.isFavorite());
        NeighbourEntity resultAfterOnce = neighbourDao.getNeighbour(NEIGHBOUR_ENTITY.getId());
        assertTrue(resultAfterOnce.isFavorite());

        // WHEN
        // TODO: not sure it's correct to do it so
        neighbourDao.updateFavorite(NEIGHBOUR_ENTITY.getId(), !resultAfterOnce.isFavorite());
        NeighbourEntity result = neighbourDao.getNeighbour(NEIGHBOUR_ENTITY.getId());
        assertFalse(result.isFavorite());
    }
}
