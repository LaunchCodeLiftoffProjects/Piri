package org.launchcode.Piri.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Review extends AbstractEntity {

    private String title;
    private String comment;
    private int overallRating;

    //@ManyToOne
    //private User username;

    @ManyToOne
    private City city;

    public Review(String title, String comment, int overallRating, City city) {
        this.title = title;
        this.comment = comment;
        this.overallRating = overallRating;
        ///\this.city = city;
    }

    public Review(){

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


    //public User getUsername() {
    //    return username;
    //}

    //public void setUsername(User username) {
    //    this.username = username;
    //}

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
