package org.launchcode.Piri.models.dto;

import org.launchcode.Piri.models.City;
import org.launchcode.Piri.models.Review;
import org.launchcode.Piri.models.User;

import javax.validation.constraints.NotNull;

public class ReviewFormDTO {

    @NotNull
    private User user;

    @NotNull
    private Review review;

    @NotNull
    private City city;

    public ReviewFormDTO() {
    }

    public ReviewFormDTO(Review review, User user, City city) {
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

