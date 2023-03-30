package com.example.entrevoisins_mvvm.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.entrevoisins_mvvm.MainApplication;
import com.example.entrevoisins_mvvm.data.AppDatabase;
import com.example.entrevoisins_mvvm.data.repository.NeighboursRepository;
import com.example.entrevoisins_mvvm.view.create.AddNeighbourActivityViewModel;
import com.example.entrevoisins_mvvm.view.detail.DetailProfileNeighbourViewModel;
import com.example.entrevoisins_mvvm.view.list.NeighbourListFragmentViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final NeighboursRepository repository;

    private static final int THREADS = Runtime.getRuntime().availableProcessors();
    private static final Executor EXECUTOR = Executors.newFixedThreadPool(THREADS);
    private final Executor ioExecutor = Executors.newFixedThreadPool(4);

    private static volatile ViewModelFactory factory;

    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory();
                }
            }
        }
        return factory;
    }


    public ViewModelFactory() {
        AppDatabase db = AppDatabase.getDatabase(MainApplication.getApplication(), EXECUTOR);
        repository = new NeighboursRepository(db.getNeighbourDao(), EXECUTOR);
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NeighbourListFragmentViewModel.class)) {
            return (T) new NeighbourListFragmentViewModel(repository);
        } else if (modelClass.isAssignableFrom(AddNeighbourActivityViewModel.class)) {
            return (T) new AddNeighbourActivityViewModel(repository);
        } else if (modelClass.isAssignableFrom(DetailProfileNeighbourViewModel.class)) {
            return (T) new DetailProfileNeighbourViewModel(repository, ioExecutor);
        } else
            throw new IllegalArgumentException("Unknown model class!");
    }
}
