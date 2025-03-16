package com.example.bttuan7.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Category implements Serializable {
    public Category(String name, int id, String images, String description) {
        this.name = name;
        this.id = id;
        this.images = images;
        this.description = description;
    }

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;

    @SerializedName("image")
    private String images;

    @SerializedName("description")
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
