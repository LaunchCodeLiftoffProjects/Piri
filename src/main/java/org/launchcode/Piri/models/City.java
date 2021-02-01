package org.launchcode.Piri.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;


@Entity
public class City extends AbstractEntity {


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
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne
    private User user;


    private @Min(1) double overallCityRating;

    private double overallAffordabilityRating;

    private double overallSafetyRating;

    private double overallTransportationRating;

    private double overallJobGrowthRating;

    private double overallSchoolRating;

    public City() {

    }


    public City(String cityName, String stateName, String stateID, String zipCodes, String county, Double latitude,
                Double longitude, @Min(1) int overallCityRating, int overallAffordabilityRating, int overallSafetyRating, int overallTransportationRating, int overallJobGrowthRating, int overallSchoolRating, ArrayList<String> images) {

        super();
        this.cityName = cityName;
        this.stateName = stateName;
        this.stateID = stateID;
        this.zipCodes = zipCodes;
        this.county = county;
        this.latitude = latitude;
        this.longitude = longitude;
        this.overallCityRating = overallCityRating;
        this.overallAffordabilityRating = overallAffordabilityRating;
        this.overallTransportationRating = overallTransportationRating;
        this.overallSafetyRating = overallSafetyRating;
        this.overallJobGrowthRating = overallJobGrowthRating;
        this.overallSchoolRating = overallSchoolRating;
        this.images = images;
    }

    @Override
    public String toString() {
        String output = "";
        if (cityName.equals("")) {
            cityName = "Data not available";
        }
        if (stateName.equals("")) {
            stateName = "Data not available";
        }
        if (county.equals("")) {
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


    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public @Min(1) double getOverallCityRating() {
        return overallCityRating;
    }

    public void setOverallCityRating(@Min(1) double overallCityRating) {
        this.overallCityRating = overallCityRating;
    }

    public double getOverallAffordabilityRating() {
        return overallAffordabilityRating;
    }

    public void setOverallAffordabilityRating(double overallAffordabilityRating) {
        this.overallAffordabilityRating = overallAffordabilityRating;
    }

    public double getOverallSafetyRating() {
        return overallSafetyRating;
    }

    public void setOverallSafetyRating(double overallSafetyRating) {
        this.overallSafetyRating = overallSafetyRating;
    }

    public double getOverallTransportationRating() {
        return overallTransportationRating;
    }

    public void setOverallTransportationRating(double overallTransportationRating) {
        this.overallTransportationRating = overallTransportationRating;
    }

    public double getOverallJobGrowthRating() {
        return overallJobGrowthRating;
    }

    public void setOverallJobGrowthRating(double overallJobGrowthRating) {
        this.overallJobGrowthRating = overallJobGrowthRating;
    }

    public double getOverallSchoolRating() {
        return overallSchoolRating;
    }

    public void setOverallSchoolRating(double overallSchoolRating) {
        this.overallSchoolRating = overallSchoolRating;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {

        ArrayList<String> imagesArr = new ArrayList<>();
        if (!images.isEmpty()) {
            imagesArr.addAll(images);
        }
        if (getImages() != null) {
            imagesArr.addAll(getImages());
        }
        this.images = imagesArr;

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
