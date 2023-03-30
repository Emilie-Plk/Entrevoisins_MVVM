package com.example.entrevoisins_mvvm.view.detail;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import java.util.Objects;

public class DetailNeighbourViewStateItem {

    @NonNull
    private final String name;

    @NonNull
    private final String avatarUrl;

    @NonNull
    private final String address;

    @NonNull
    private final String phoneNumber;

    @NonNull
    private final String aboutMe;

    @DrawableRes
    private final int favoriteDrawable;


    public DetailNeighbourViewStateItem(
        @NonNull String name,
        @NonNull String avatarUrl,
        @NonNull String address,
        @NonNull String phoneNumber,
        @NonNull String aboutMe,
        int favoriteDrawable
    ) {
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.aboutMe = aboutMe;
        this.favoriteDrawable = favoriteDrawable;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getAvatarUrl() {
        return avatarUrl;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    @NonNull
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @NonNull
    public String getAboutMe() {
        return aboutMe;
    }

    public int getFavoriteDrawable() {
        return favoriteDrawable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetailNeighbourViewStateItem that = (DetailNeighbourViewStateItem) o;
        return favoriteDrawable == that.favoriteDrawable && name.equals(that.name) && avatarUrl.equals(that.avatarUrl) && address.equals(that.address) && phoneNumber.equals(that.phoneNumber) && aboutMe.equals(that.aboutMe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, avatarUrl, address, phoneNumber, aboutMe, favoriteDrawable);
    }
}
