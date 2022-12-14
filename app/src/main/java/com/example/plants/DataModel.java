package com.example.plants;

public class DataModel {
    private String Image;
    private String Species;
    private int Price;
    public DataModel(String Image, String Species, int Price){

        this.Image=Image;
        this.Species=Species;
        this.Price=Price;
    }
    public void setImage(String Image)
    {
        this.Image=Image;
    }
    public void setSpecies(String Name ){this.Species=Species;}
    public void setPrice (int Price)
    {
        this.Price=Price;
    }
    public String getImage()
    {
        return Image;
    }
    public String getSpecies()
    {
        return Species;
    }
    public int getPrice()
    {
        return Price;
    }
}
