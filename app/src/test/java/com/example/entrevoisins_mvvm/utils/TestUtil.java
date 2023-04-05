package com.example.entrevoisins_mvvm.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.entrevoisins_mvvm.view.utils.SingleLiveEvent;

public class TestUtil {
    public static <T> T getValueForTesting(@NonNull final LiveData<T> liveData) {
        liveData.observeForever(t -> {
        });

        return liveData.getValue();
    }
    public static int getEmitCountForTesting(@NonNull final SingleLiveEvent singleLiveEvent) {
        final int[] emitCount = {0};
        singleLiveEvent.observeForever(t -> emitCount[0]++);

        return emitCount[0];
    }
}