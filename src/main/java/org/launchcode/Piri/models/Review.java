package org.launchcode.Piri.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class Review extends AbstractEntity {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max=20, message = "Title must be between 3 and 20 characters long")
    private String title;

    @NotBlank(message = "Comment is required")
    @Size(min = 10, message = "Comment must be at least 10 characters long")
    private String comment;

    //@NotNull(message = "Rating is required")
    @Min(value = 1, message="Please rate the city")
    private int overallRating;
    private LocalDate reviewDate = LocalDate.now();

    @ManyToOne
    private User username;

    @ManyToOne
    //@JoinColumn (name="city_id")
    private City city;

    @ManyToOne
    private User user;

    public Review(){
    }

    public Review(String title, String comment, int overallRating, City aCity, User aUser) {

        this.title = title;
        this.comment = comment;
        this.overallRating = overallRating;
        //this.city = aCity;
        this.reviewDate = reviewDate;
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
