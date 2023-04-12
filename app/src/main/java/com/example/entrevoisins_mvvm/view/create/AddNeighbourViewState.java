package com.example.entrevoisins_mvvm.view.create;

import androidx.annotation.NonNull;

import java.util.Objects;

public class AddNeighbourViewState {
    private final String imageUrl;
    private final boolean isButtonEnabled;

    public AddNeighbourViewState(String imageUrl, boolean isButtonEnabled) {
        this.imageUrl = imageUrl;
        this.isButtonEnabled = isButtonEnabled;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isButtonEnabled() {
        return isButtonEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddNeighbourViewState that = (AddNeighbourViewState) o;
        return isButtonEnabled == that.isButtonEnabled && Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageUrl, isButtonEnabled);
    }

    @NonNull
    @Override
    public String toString() {
        return "AddNeighbourViewState{" +
            "imageUrl='" + imageUrl + '\'' +
            ", isButtonEnabled=" + isButtonEnabled +
            '}';
    }
}
