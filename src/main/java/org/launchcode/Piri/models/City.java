package org.launchcode.Piri.models;

import java.util.Objects;

public class City {

    private int id;

    private  static int nextId = 1;

    private String cityName;

    private Integer zipCode;

    //Initialize a unique Id
    public City(){
        id =nextId;
        nextId++;
    }

    public City(String cityName, Integer zipCode){
        this();
        this.cityName = cityName;
        this.zipCode = zipCode;
    }

    @Override
    public String toString(){
        String output = "";
        if (cityName.equals("")){
            cityName = "Data not available";
        }

        output = String.format("\nID: %d\n" +
                "City Name: %s\n" +
                "Zip Code: %s\n", id, cityName, zipCode);
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

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }
}
