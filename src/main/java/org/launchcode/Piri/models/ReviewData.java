/*package org.launchcode.Piri.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ReviewData {
    public static ArrayList<Review> findByValue(int value, Iterable<Review> allReviews, City city){

        //String lower_val = value.toLowerCase();

        ArrayList<Review> results = new ArrayList<>();

        for (Review review : allReviews) {

            if(review.getCity().equals(city)) {
                results.add(review);
            }


        }
    return results;
    }
}

*/