package com.eczaneler.api.models;

import com.google.gson.annotations.SerializedName;

public class District {
    @SerializedName("id")
    private int id;

    @SerializedName("city_id")
    private int cityId;

    @SerializedName("name")
    private String name;

    @SerializedName("slug")
    private String slug;

    public District() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCityId() { return cityId; }
    public void setCityId(int cityId) { this.cityId = cityId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
}
