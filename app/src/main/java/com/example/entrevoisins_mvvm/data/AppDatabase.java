package com.example.entrevoisins_mvvm.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.entrevoisins_mvvm.data.dao.NeighbourDao;
import com.example.entrevoisins_mvvm.data.entities.NeighbourEntity;

import java.util.concurrent.Executor;

@Database(entities = {NeighbourEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract NeighbourDao getNeighbourDao();

    public static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(
            @NonNull final Application application,
            @NonNull final Executor executor
    ) {  // SINGLETON (getDatabase() returns it)
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    application,
                                    AppDatabase.class,
                                    "app_database"
                            )
                            .addCallback(new RoomDatabaseCallback(executor))
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static class RoomDatabaseCallback extends RoomDatabase.Callback {
        private final Executor executor;

        public RoomDatabaseCallback(Executor executor) {
            this.executor = executor;
        }

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            executor.execute(() -> {
              NeighbourDao dao = INSTANCE.getNeighbourDao();
                dao.insertNeighbour(new NeighbourEntity(1, false, "Caroline", "https://i.pravatar.cc/150?u=a042581f4e29026704d", "Saint-Pierre-du-Mont ; 5km",
                        "+33 6 86 57 90 14", "Bonjour ! Je souhaite rencontrer des gens pour jouer à des jeux de cartes et discuter de tout et de rien."));
            });
        }
    }
}
