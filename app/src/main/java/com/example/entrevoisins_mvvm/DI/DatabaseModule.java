package com.example.entrevoisins_mvvm.DI;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.entrevoisins_mvvm.data.AppDatabase;
import com.example.entrevoisins_mvvm.data.dao.NeighbourDao;
import com.example.entrevoisins_mvvm.view.utils.MainThreadExecutor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.Clock;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Qualifier;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module
public class DatabaseModule {

    @Provides
    @Singleton
    @IoExecutor
    public Executor provideIoExecutor() {
        return Executors.newFixedThreadPool(4);
    }

    @Provides
    @Singleton
    @MainExecutor
    public Executor provideMainExecutor() {
        return new MainThreadExecutor();
    }

    @Provides
    @Singleton
    public AppDatabase provideDatabase(Application application, @IoExecutor Executor ioExecutor) {
        return AppDatabase.getDatabase(application, ioExecutor);
    }

    @Provides
    @Singleton
    public NeighbourDao provideNeighbourDao(@NonNull AppDatabase appDatabase) {
        return appDatabase.getNeighbourDao();
    }

    @Provides
    public Clock provideClock() {
        return Clock.systemDefaultZone();
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface IoExecutor {
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface MainExecutor {
    }
}
