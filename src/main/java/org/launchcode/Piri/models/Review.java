package org.launchcode.Piri.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class Review extends AbstractEntity {

    private String title;
    private String comment;
    private int overallRating;

    //@ManyToOne
    //private User username;

   @ManyToOne
   @JoinColumn
   private City city;

    public Review(String title, String comment, int overallRating, City aCity) {
        super();
        this.title = title;
        this.comment = comment;
        this.overallRating = overallRating;
        this.city = aCity;
    }

    public Review(){
        super();
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


    //public User getUsername() {
    //    return username;
    //}

    //public void setUsername(User username) {
    //    this.username = username;
    //}

}
