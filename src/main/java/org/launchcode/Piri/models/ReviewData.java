package org.launchcode.Piri.models;

import org.launchcode.Piri.models.data.CityRepository;
import org.launchcode.Piri.models.data.PagingAndSortingReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static java.lang.StrictMath.toIntExact;

@Service
public class ReviewData {

    @Autowired
    private PagingAndSortingReviewRepository pagingAndSortingReviewRepository;

    public static double calculateAverageOverallRating(City city){
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
        city.setOverallCityRating(1);
        return rating;
    }

    public static double calculateAverageAffordabilityRating(City city){
        double rating = 0;
        double total = 0;
        List<Review> reviews = city.getReviews();

        for (int i =0; i<reviews.size(); i++){
            int a = reviews.get(i).getAffordabilityRating();
            total = total + a;
        }

        rating = total/reviews.size();

        rating = Math.round(rating*10) / 10.0;

        if (reviews.size() == 0){
            rating = 0;
        }
        return rating;
    }


    public static double calculateAverageSafetyRating(City city){
        double rating = 0;
        double total = 0;
        List<Review> reviews = city.getReviews();

        for (int i =0; i<reviews.size(); i++){
            int a = reviews.get(i).getSafetyRating();
            total = total + a;
        }

        rating = total/reviews.size();

        rating = Math.round(rating*10) / 10.0;

        if (reviews.size() == 0){
            rating = 0;
        }

        return rating;
    }

    public static double calculateAverageTransportationRating(City city){
        double rating = 0;
        double total = 0;
        List<Review> reviews = city.getReviews();

        for (int i =0; i<reviews.size(); i++){
            int a = reviews.get(i).getTransportationRating();
            total = total + a;
        }

        rating = total/reviews.size();

        rating = Math.round(rating*10) / 10.0;

        if (reviews.size() == 0){
            rating = 0;
        }

        return rating;
    }

    public static double calculateAverageJobGrowthRating(City city){
        double rating = 0;
        double total = 0;
        List<Review> reviews = city.getReviews();

        for (int i =0; i<reviews.size(); i++){
            int a = reviews.get(i).getJobGrowthRating();
            total = total + a;
        }

        rating = total/reviews.size();

        rating = Math.round(rating*10) / 10.0;

        if (reviews.size() == 0){
            rating = 0;
        }

        return rating;
    }

    public static double calculateAverageSchoolRating(City city){
        double rating = 0;
        double total = 0;
        List<Review> reviews = city.getReviews();

        for (int i =0; i<reviews.size(); i++){
            int a = reviews.get(i).getSchoolRating();
            total = total + a;
        }

        rating = total/reviews.size();

        rating = Math.round(rating*10) / 10.0;

        if (reviews.size() == 0){
            rating = 0;
        }

        return rating;
    }

    public static void uploadImagesToDB(MultipartFile[] files, Optional<City> optionalCity, Review review){
        ArrayList<String> fileNames = new ArrayList<>();
        ArrayList<String> byteToStringArray = new ArrayList<>();
        try {
            for(MultipartFile file : files) {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                fileNames.add(fileName);
                if (fileName.contains("..")) {
                    System.out.println("not a a valid file");
                }
                String byteTostring = Base64.getEncoder().encodeToString(file.getBytes());
                if(byteTostring.length() > 61) {
                    byteToStringArray.add(byteTostring);
                }
            }

            if(byteToStringArray.size() > 0) {
                review.setCityImage(byteToStringArray);
            }

            if(optionalCity.isPresent()){
                City city = optionalCity.get();
                review.setCity(city);
                if(byteToStringArray.size() > 0) {
                    city.setImages(byteToStringArray);
                }

//                double averageOverallRate = ReviewData.calculateAverageOverallRating(city);

                double averageOverallSafetyRate = ReviewData.calculateAverageSafetyRating(city);

                double averageOverallTransportationRate = ReviewData.calculateAverageTransportationRating(city);

                double averageOverallJobGrowthRate = ReviewData.calculateAverageJobGrowthRating(city);

                double averageOverallSchoolRate = ReviewData.calculateAverageSchoolRating(city);

                double averageOverallAffordabilityRate = ReviewData.calculateAverageAffordabilityRating(city);

//                city.setOverallCityRating(averageOverallRate);
//                if(city.getOverallCityRating() != 0){
//                    city.setOverallCityRating(averageOverallRate);
//                }
//                if(city.getOverallCityRating() == 0){
//                    city.setOverallCityRating(review.getOverallRating());
//                }
//                if(city.getOverallCityRating() != 0){
//                    city.setOverallCityRating(averageOverallRate);
//                }
//                if(city.getOverallCityRating() == 0){
//                    city.setOverallCityRating(review.getOverallRating());
//                }

//                if(city.getOverallAffordabilityRating() != 0){
//                    city.setOverallAffordabilityRating(averageOverallAffordabilityRate);
//                }
//                if(city.getOverallAffordabilityRating() == 0){
//                    city.setOverallAffordabilityRating(review.getAffordabilityRating());
//                }
//
//                if(city.getOverallSafetyRating() != 0){
//                    city.setOverallSafetyRating(averageOverallSafetyRate);
//                }
//                if(city.getOverallSafetyRating() == 0){
//                    city.setOverallSafetyRating(review.getSafetyRating());
//                }
//
//                if(city.getOverallTransportationRating() != 0){
//                    city.setOverallTransportationRating(averageOverallTransportationRate);
//                }
//                if(city.getOverallTransportationRating() == 0){
//                    city.setOverallTransportationRating(review.getTransportationRating());
//                }
//
//                if(city.getOverallJobGrowthRating() != 0){
//                    city.setOverallJobGrowthRating(averageOverallJobGrowthRate);
//                }
//                if(city.getOverallJobGrowthRating() == 0){
//                    city.setOverallJobGrowthRating(review.getJobGrowthRating());
//                }
//
//                if(city.getOverallSchoolRating() != 0){
//                    city.setOverallSchoolRating(averageOverallSchoolRate);
//                }
//                if(city.getOverallSchoolRating() == 0){
//                    city.setOverallSchoolRating(review.getSchoolRating());
//                }


            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public Page<Review> findPaginatedReviews(int pageNo, int reviewCount, City city){
        Iterable<Review> reviews = this.pagingAndSortingReviewRepository.findAll();
        ArrayList<Review> results = new ArrayList<>();

        for(Review review : reviews){
            if(city.getId() == review.getCity().getId()){
                results.add(review);
            }
        }
        Pageable pageable = PageRequest.of(pageNo - 1, reviewCount);

        int total = results.size();
        int start = toIntExact(pageable.getOffset());
        int end = Math.min((start + pageable.getPageSize()), total);

        List<Review> output = new ArrayList<>();

        if (start <= end) {
            output = results.subList(start, end);
        }

        return new PageImpl<>(
                output,
                pageable,
                total);
       }



}