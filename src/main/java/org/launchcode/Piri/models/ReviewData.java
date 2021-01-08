package org.launchcode.Piri.models;

import org.launchcode.Piri.models.data.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ReviewData {

    public static float calculateAverageOverallRating(int cityId, City city){
        float rating= 0;
        float total = 0;
        List<Review> reviews = city.getReviews();

        for (int i =0; i<reviews.size(); i++){
            int a = reviews.get(i).getOverallRating();
            total = total + a;
        }

        rating = total/reviews.size();

        return rating;
    }
}