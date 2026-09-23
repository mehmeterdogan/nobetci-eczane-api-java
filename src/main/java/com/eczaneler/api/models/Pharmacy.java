package com.eczaneler.api.models;

import com.google.gson.annotations.SerializedName;

public class Pharmacy {
    @SerializedName("id")
    private int id;

    @SerializedName("city_id")
    private int cityId;

    @SerializedName("district_id")
    private int districtId;

    @SerializedName("name")
    private String name;

    @SerializedName("phone")
    private String phone;

    @SerializedName("city")
    private String city;

    @SerializedName("district")
    private String district;

    @SerializedName("locality")
    private String locality;

    @SerializedName("address")
    private String address;

    @SerializedName("address_description")
    private String addressDescription;

    @SerializedName("coordinates")
    private Coordinates coordinates;

    @SerializedName("map_link")
    private String mapLink;

    @SerializedName("is_sentry")
    private boolean isSentry;

    @SerializedName("workingHours")
    private String workingHours;

    @SerializedName("distance_km")
    private Double distanceKm;

    public Pharmacy() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCityId() { return cityId; }
    public void setCityId(int cityId) { this.cityId = cityId; }

    public int getDistrictId() { return districtId; }
    public void setDistrictId(int districtId) { this.districtId = districtId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getLocality() { return locality; }
    public void setLocality(String locality) { this.locality = locality; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getAddressDescription() { return addressDescription; }
    public void setAddressDescription(String addressDescription) { this.addressDescription = addressDescription; }

    public Coordinates getCoordinates() { return coordinates; }
    public void setCoordinates(Coordinates coordinates) { this.coordinates = coordinates; }

    public String getMapLink() { return mapLink; }
    public void setMapLink(String mapLink) { this.mapLink = mapLink; }

    public boolean isSentry() { return isSentry; }
    public void setSentry(boolean sentry) { isSentry = sentry; }

    public String getWorkingHours() { return workingHours; }
    public void setWorkingHours(String workingHours) { this.workingHours = workingHours; }

    public Double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(Double distanceKm) { this.distanceKm = distanceKm; }
}
