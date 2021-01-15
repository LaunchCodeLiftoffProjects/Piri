package org.launchcode.Piri.models;

import org.launchcode.Piri.models.data.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ReviewData {

    public static double calculateAverageOverallRating(int cityId, City city){
        double rating = 0;
        double total = 0;
        List<Review> reviews = city.getReviews();

        for (int i =0; i<reviews.size(); i++){
            int a = reviews.get(i).getOverallRating();
            total = total + a;
        }

        rating = total/reviews.size();

        rating = Math.round(rating*10) / 10.0;

        if (reviews.size() == 0){
            rating = 0;
        }
        return rating;
    }
}