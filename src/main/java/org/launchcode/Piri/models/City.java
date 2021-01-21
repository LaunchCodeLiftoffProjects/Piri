package org.launchcode.Piri.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


@Entity
public class City extends AbstractEntity{


    private String cityName;

    private String stateName;

    private String stateID;

    private String zipCodes;

    private String county;

    private Double latitude;

    private Double longitude;

    private ArrayList<String> images;

    @OneToMany(mappedBy = "city")
    //@JoinColumn (name="reviews_id")
    private final List<Review> reviews = new ArrayList<>();

    public City(){

    }

    public City(String cityName,String stateName,String stateID, String zipCodes, String county, Double latitude,
                Double longitude, ArrayList<String> images){
        super();
        this.cityName = cityName;
        this.stateName = stateName;
        this.stateID = stateID;
        this.zipCodes = zipCodes;
        this.county = county;
        this.latitude = latitude;
        this.longitude = longitude;
        this.images = images;
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
                "Longitude: %s\n", cityName, stateName, zipCodes, county, latitude, longitude);
        return output;
    }


    public String getCityName() {
        return cityName;
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

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    public String getZipCodes() {
        return zipCodes;
    }

    public void setZipCodes(String zipCodes) {
        this.zipCodes = zipCodes;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }
}
