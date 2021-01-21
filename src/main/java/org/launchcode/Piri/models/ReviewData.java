package org.launchcode.Piri.models;

import org.launchcode.Piri.models.data.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

public class ReviewData {

    public static float calculateAverageOverallRating(int cityId, City city){
        float rating = 0;
        float total = 0;
        List<Review> reviews = city.getReviews();

        for (int i =0; i<reviews.size(); i++){
            int a = reviews.get(i).getOverallRating();
            total = total + a;
        }

        rating = (float)Math.round(total/reviews.size()*10)/10;

        if (reviews.size() == 0){
            rating = 0;
        }

        return rating;
    }

    public static void uploadImagesToDB(MultipartFile[] files, Optional<City> optionalCity, Review review) {
        ArrayList<String> fileNames = new ArrayList<>();
        ArrayList<String> byteToStringArray = new ArrayList<>();

        try {
            for (MultipartFile file : files) {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                fileNames.add(fileName);
                if (fileName.contains("..")) {
                    System.out.println("not a a valid file");
                }
                String byteTostring = Base64.getEncoder().encodeToString(file.getBytes());
                byteToStringArray.add(byteTostring);
            }
            review.setCityImage(byteToStringArray);

            if (optionalCity.isPresent()) {
                City city = optionalCity.get();
                review.setCity(city);
                city.setImages(byteToStringArray);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}