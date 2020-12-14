package org.launchcode.Piri.models;

import java.util.Objects;

public class City {

    private int id;

    private  static int nextId = 1;

    private String cityName;

    private String stateName;

    private Integer zipCode;

    private String county;

    private Double latitude;

    private Double longitude;


    //Initialize a unique Id
    public City(){
        id =nextId;
        nextId++;
    }

    public City(String cityName,String stateName, Integer zipCode, String county, Double latitude, Double longitude){
        this();
        this.cityName = cityName;
        this.stateName = stateName;
        this.zipCode = zipCode;
        this.county = county;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString(){
        String output = "";
        if (cityName.equals("")){
            cityName = "Data not available";
        }
        if (stateName.equals("")){
            stateName = "Data not available";
        }
        if (county.equals("")){
            county = "Data not available";
        }

        output = String.format("\nCity: %s\n" +
                "State: %s\n" +
                "Zip Code: %s\n" +
                "County: %s\n" +
                "Latitude: %s\n" +
                "Longitude: %s\n", cityName, stateName, zipCode, county, latitude, longitude);
        return output;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return id == city.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public String getStateName() {
        return stateName;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }
}
