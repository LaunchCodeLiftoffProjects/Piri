package org.launchcode.Piri.controllers;

import org.launchcode.Piri.models.City;
import org.launchcode.Piri.models.Review;
//import org.launchcode.Piri.models.ReviewData;
import org.launchcode.Piri.models.ReviewData;
import org.launchcode.Piri.models.data.CityRepository;
import org.launchcode.Piri.models.data.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;


@Controller
public class HelloController {


    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewData reviewData;

    public String hello(){

        return "index";
    }

//    @GetMapping("view/{cityId}")
//    public String displayView(Model model, @PathVariable int cityId){
//        Optional<City> optCity = cityRepository.findById(cityId);
//        City city = optCity.get();
//
//        Iterable<Review> reviews;
//        reviews = city.getReviews();
//
//        if(optCity.isPresent()) {
//            model.addAttribute("city", city);
//            model.addAttribute("cityId", cityId);
//            model.addAttribute("overallRating", ReviewData.calculateAverageOverallRating(city));
//            model.addAttribute("reviews", reviews);
//            model.addAttribute("affordabilityRating", ReviewData.calculateAverageAffordabilityRating(city));
//            model.addAttribute("safetyRating", ReviewData.calculateAverageSafetyRating(city));
//            model.addAttribute("transportationRating", ReviewData.calculateAverageTransportationRating(city));
//            model.addAttribute("jobGrowthRating", ReviewData.calculateAverageJobGrowthRating(city));
//            model.addAttribute("schoolRating", ReviewData.calculateAverageSchoolRating(city));
//        }
//        return "view";
//    }
//    @GetMapping("view/{cityId}")
//    public String displayView(Model model, @PathVariable int cityId){
//        Optional<City> optCity = cityRepository.findById(cityId);
//        City city = optCity.get();
//
//        Iterable<Review> reviews;
//        reviews = city.getReviews();
//
//        if(optCity.isPresent()) {
//            model.addAttribute("city", city);
//            model.addAttribute("cityId", cityId);
//            model.addAttribute("overallRating", ReviewData.calculateAverageOverallRating(city));
//            model.addAttribute("reviews", reviews);
//            model.addAttribute("affordabilityRating", ReviewData.calculateAverageAffordabilityRating(city));
//            model.addAttribute("safetyRating", ReviewData.calculateAverageSafetyRating(city));
//            model.addAttribute("transportationRating", ReviewData.calculateAverageTransportationRating(city));
//            model.addAttribute("jobGrowthRating", ReviewData.calculateAverageJobGrowthRating(city));
//            model.addAttribute("schoolRating", ReviewData.calculateAverageSchoolRating(city));
//        }
//        return "view";
//    }

    @GetMapping("view/{cityId}/{pageNo}")
    public String displayView(Model model, @PathVariable int cityId,@PathVariable(value = "pageNo") int pageNo,@RequestParam(required = false) String sortField, @RequestParam(required = false) String sortDirection){

        int reviewCount = 6;

        if(sortField == null) {
            sortField = "title";
        }
        if(sortDirection == null){
            sortDirection = "desc";
        }
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);

        Optional<City> optCity = cityRepository.findById(cityId);
        City city = optCity.get();

        int sizeOfReviews = city.getReviews().size();

        Page<Review> page = reviewData.findPaginatedReviews(pageNo, reviewCount, city, sortField, sortDirection);
        List<Review> reviews= page.getContent();

        Comparator<Review> byDate = new Comparator<Review>() {
            public int compare(Review c1, Review c2) {
                return Long.valueOf(java.sql.Date.valueOf(c1.getReviewDate()).getTime()).compareTo(java.sql.Date.valueOf(c2.getReviewDate()).getTime());
            }
        };

        List<Review> modifiableList = new ArrayList<Review>(reviews);

        if(sortField != null && sortField.equals("reviewDate") && sortDirection != null && sortDirection.equals("desc")){
            Collections.sort(modifiableList, byDate);
            model.addAttribute("reviews", modifiableList);
        }else if(sortField != null && sortField.equals("reviewDate") && sortDirection != null && sortDirection.equals("asc")){
            Collections.sort(modifiableList, byDate.reversed());
            model.addAttribute("reviews", modifiableList);
        }else{
            model.addAttribute("reviews", reviews);
        }


        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        if(optCity.isPresent()) {
            model.addAttribute("city", city);
            model.addAttribute("cityId", cityId);
            model.addAttribute("overallRating", ReviewData.calculateAverageOverallRating(city));
//            model.addAttribute("reviews", reviews);
            model.addAttribute("affordabilityRating", ReviewData.calculateAverageAffordabilityRating(city));
            model.addAttribute("safetyRating", ReviewData.calculateAverageSafetyRating(city));
            model.addAttribute("transportationRating", ReviewData.calculateAverageTransportationRating(city));
            model.addAttribute("jobGrowthRating", ReviewData.calculateAverageJobGrowthRating(city));
            model.addAttribute("schoolRating", ReviewData.calculateAverageSchoolRating(city));

            model.addAttribute("sizeOfReviews", sizeOfReviews);

//            model.addAttribute("reviewsPerPage", reviewsPerPage);

        }
        return "view";
    }

}

