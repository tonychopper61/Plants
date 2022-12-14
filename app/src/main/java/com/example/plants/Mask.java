package com.example.plants;

import android.os.Parcel;
import android.os.Parcelable;

public class Mask implements Parcelable {

    private int ID;
    private String Species;
    private int Price;
    private String Image;

    public Mask(int ID, String species, int price, String image) {
        this.ID = ID;
        Species = species;
        Price = price;
        Image = image;
    }

    protected Mask(Parcel in) {
        ID = in.readInt();
        Species = in.readString();
        Price = in.readInt();
        Image = in.readString();
    }

    public static final Creator<Mask> CREATOR = new Creator<Mask>() {
        @Override
        public Mask createFromParcel(Parcel in) {
            return new Mask(in);
        }

        @Override
        public Mask[] newArray(int size) {
            return new Mask[size];
        }
    };

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setSpecies(String species) {
        Species = species;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public void setImage(String image) {
        Image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(ID);
        parcel.writeString(Species);
        parcel.writeInt(Price);
        parcel.writeString(Image);
    }

    public String getSpecies() {
        return Species;
    }

    public int getPrice() {
        return Price;
    }

    public String getImage() {
        return Image;
    }

    public int getID() {
        return ID;
    }
}
