package com.eczaneler.api.models;

import com.google.gson.annotations.SerializedName;

public class City {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("slug")
    private String slug;

    @SerializedName("plate")
    private Integer plate;

    public City() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public Integer getPlate() { return plate; }
    public void setPlate(Integer plate) { this.plate = plate; }
}
