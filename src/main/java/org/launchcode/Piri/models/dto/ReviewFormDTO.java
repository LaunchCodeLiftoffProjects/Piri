package org.launchcode.Piri.models.dto;

import org.launchcode.Piri.models.City;
import org.launchcode.Piri.models.Review;
import org.launchcode.Piri.models.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ReviewFormDTO {

    @NotNull
    private User user;

    @NotNull
    private Review review;

    @NotNull
    private City city;

    @NotBlank
    @Size(min = 3, max = 20, message = "Invalid username. Must be between 3 and 30 characters.")
    private String title;

    public ReviewFormDTO(){}

    public ReviewFormDTO(Review review, User user, City city){
        this.review = review;
        this.user = user;
        this.city = city;
        
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }


}