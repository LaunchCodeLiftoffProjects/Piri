package org.launchcode.Piri.models;

import org.launchcode.Piri.models.data.CityRepository;
import org.launchcode.Piri.models.data.PagingAndSortingReviewRepository;
import org.launchcode.Piri.models.data.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static java.lang.StrictMath.toIntExact;

@Service
public class ReviewData {

    private static final Map<Integer, Review> reviews = new HashMap<>();


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

        rating = (float)Math.round(total/reviews.size()*10)/10;

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

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


//    public Page<Review> findPaginatedReviews(int pageNo, int reviewCount, City city, String sortField, String sortDirection){
//
//        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
//                Sort.by(sortField).descending();
//
//        Iterable<Review> reviews = this.pagingAndSortingReviewRepository.findAll(sort);
//        ArrayList<Review> results = new ArrayList<>();
//
//
//        for(Review review : reviews){
//            if(city.getId() == review.getCity().getId()){
//                results.add(review);
//            }
//        }
//        Pageable pageable = PageRequest.of(pageNo - 1, reviewCount);
//
//        int total = results.size();
//        int start = toIntExact(pageable.getOffset());
//        int end = Math.min((start + pageable.getPageSize()), total);
//
//        List<Review> output = new ArrayList<>();
//
//
//        if (start <= end) {
//            output = results.subList(start, end);
//        }
//
//        return new PageImpl<>(
//                output,
//                pageable,
//                total);
//       }

    public Page<Review> findPaginatedReviews(int pageNo, int reviewCount,String searchTermForReviews, City city, String sortField, String sortDirection){

        String lower_val = null;

        if(searchTermForReviews == null){
            searchTermForReviews = "";
            lower_val = "";
        }else{
            lower_val = searchTermForReviews.toLowerCase();
        }


        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Iterable<Review> reviews = this.pagingAndSortingReviewRepository.findAll(sort);

        ArrayList<Review> results = new ArrayList<>();
        ArrayList<Review> sortedResults = new ArrayList<>();


        for(Review review: reviews){
            if(lower_val == ""){
                results.add(review);
            }
            if(review.getComment().toLowerCase().contains(lower_val)){
                results.add(review);
            }
        }

        for(Review review : results){
            if(city.getId() == review.getCity().getId() && !sortedResults.contains(review)){
                sortedResults.add(review);
            }
        }
        Pageable pageable = PageRequest.of(pageNo - 1, reviewCount);

        int total = sortedResults.size();
        int start = toIntExact(pageable.getOffset());
        int end = Math.min((start + pageable.getPageSize()), total);

        List<Review> output = new ArrayList<>();


        if (start <= end) {
            output = sortedResults.subList(start, end);
        }

        return new PageImpl<>(
                output,
                pageable,
                total);
    }

    public static ArrayList<String> findWordToHighlight(List<Review> reviews, String searchTerm){
        String wordFromIndex = null;
        Integer startIndex = 0;
        ArrayList<String> results = new ArrayList<>();

        for(int i = 0; i<reviews.size(); i++){
            if(reviews.get(i).getComment().toLowerCase().contains(searchTerm)){
                startIndex =reviews.get(i).getComment().toLowerCase().indexOf(searchTerm);
                wordFromIndex = reviews.get(i).getComment().substring(startIndex);
                String[] strArray = wordFromIndex.split(" ");
                if(!results.contains(strArray[0])){
                    results.add(strArray[0]);
                }
            }else{
                results.add("No result");
            }
        }

        return results;

    }

    public static ArrayList<Review> findReviewByUser (Review review, User user) {
        ReviewRepository reviewRepository;
        List<Review> reviews = reviewRepository.findAll();
    }





}