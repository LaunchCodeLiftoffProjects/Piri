package org.launchcode.Piri.models;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.awt.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class Review extends AbstractEntity implements Serializable {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max=20, message = "Title must be between 3 and 20 characters long")
    private String title;

    @NotBlank(message = "Comment is required")
    @Size(min = 10, message = "Comment must be at least 10 characters long")
    private String comment;

    //@NotNull(message = "Rating is required")

    @Min(1)
    private int overallRating;
    private LocalDate reviewDate = LocalDate.now();

    private int affordabilityRating;

    private int safetyRating;

    private int transportationRating;

    private int jobGrowthRating;

    private int schoolRating;



    @ManyToOne
    private User username;

    @Lob
    @Column(name = "city_image")
    private ArrayList<String> cityImage;

    @ManyToOne
    private User username;

    @ManyToOne
    private City city;

    @ManyToOne
    private User user;

    @Lob
    @Column(name = "city_image")
    private ArrayList<String> cityImage;

    public Review(){
    }


    public Review(String title, String comment, int overallRating, int affordabilityRating, int safetyRating, int transportationRating, int jobGrowthRating, int schoolRating, City aCity, User aUser, ArrayList<String> cityImage) {

        this.title = title;
        this.comment = comment;
        this.overallRating = overallRating;
        this.affordabilityRating = affordabilityRating;
        this.safetyRating = safetyRating;
        this.transportationRating = transportationRating;
        this.jobGrowthRating = jobGrowthRating;
        this.schoolRating = schoolRating;
        this.reviewDate = reviewDate;
        this.cityImage = cityImage;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(int overallRating) {
        this.overallRating = overallRating;
    }


    public int getAffordabilityRating() {
        return affordabilityRating;
    }

    public void setAffordabilityRating(int affordabilityRating) {
        this.affordabilityRating = affordabilityRating;
    }

    public int getSafetyRating() {
        return safetyRating;
    }

    public void setSafetyRating(int safetyRating) {
        this.safetyRating = safetyRating;
    }

    public int getTransportationRating() {
        return transportationRating;
    }

    public void setTransportationRating(int transportationRating) {
        this.transportationRating = transportationRating;
    }

    public int getJobGrowthRating() {
        return jobGrowthRating;
    }

    public void setJobGrowthRating(int jobGrowthRating) {
        this.jobGrowthRating = jobGrowthRating;
    }

    public int getSchoolRating() {
        return schoolRating;
    }

    public void setSchoolRating(int schoolRating) {
        this.schoolRating = schoolRating;

    public ArrayList<String> getCityImage() {
        return cityImage;
    }

    public void setCityImage(ArrayList<String> cityImage) {
        this.cityImage = cityImage;

    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //public User getUsername() {
    //    return username;
    //}

    //public void setUsername(User username) {
    //    this.username = username;
    //}


}
