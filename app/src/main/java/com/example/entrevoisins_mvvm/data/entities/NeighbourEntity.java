package com.example.entrevoisins_mvvm.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "neighbours")
public class NeighbourEntity {

    @PrimaryKey(autoGenerate = true)
    private final long id;

    @ColumnInfo(name = "isFavorite")
    private final boolean isFavorite;

    @NonNull
    @ColumnInfo(name = "neighbourName")
    private final String neighbourName;

    @NonNull
    @ColumnInfo(name = "avatarUrl")
    private final String avatarUrl;

    @NonNull
    @ColumnInfo(name = "address")
    private final String address;

    @NonNull
    @ColumnInfo(name = "phoneNumber")
    private final String phoneNumber;

    @NonNull
    @ColumnInfo(name = "aboutMe")
    private final String aboutMe;

    public NeighbourEntity(
        long id,
        boolean isFavorite,
        @NonNull String neighbourName,
        @NonNull String avatarUrl,
        @NonNull String address,
        @NonNull String phoneNumber,
        @NonNull String aboutMe
    ) {
        this.id = id;
        this.isFavorite = isFavorite;
        this.neighbourName = neighbourName;
        this.avatarUrl = avatarUrl;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.aboutMe = aboutMe;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getNeighbourName() {
        return neighbourName;
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

    public boolean isFavorite() {
        return isFavorite;
    }

    @NonNull
    @Override
    public String toString() {
        return "NeighbourEntity{" +
            "id=" + id +
            ", isFavorite=" + isFavorite +
            ", neighbourName='" + neighbourName + '\'' +
            ", avatarUrl='" + avatarUrl + '\'' +
            ", address='" + address + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", aboutMe='" + aboutMe + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NeighbourEntity neighbour = (NeighbourEntity) o;
        return id == neighbour.id && isFavorite == neighbour.isFavorite && neighbourName.equals(neighbour.neighbourName) && avatarUrl.equals(neighbour.avatarUrl) && address.equals(neighbour.address) && phoneNumber.equals(neighbour.phoneNumber) && aboutMe.equals(neighbour.aboutMe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isFavorite, neighbourName, avatarUrl, address, phoneNumber, aboutMe);
    }
}
