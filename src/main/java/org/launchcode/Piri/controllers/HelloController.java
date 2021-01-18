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

import java.util.List;
import java.util.Optional;


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
    public String displayView(Model model, @PathVariable int cityId,@PathVariable(value = "pageNo") int pageNo){

        int reviewCount = 6;


        Optional<City> optCity = cityRepository.findById(cityId);
        City city = optCity.get();

        Iterable<Review> reviews;
        reviews = city.getReviews();

        Page<Review> page = reviewData.findPaginatedReviews(pageNo, reviewCount, city);
        List<Review> reviewsPerPage= page.getContent();

        if(optCity.isPresent()) {
            model.addAttribute("city", city);
            model.addAttribute("cityId", cityId);
            model.addAttribute("overallRating", ReviewData.calculateAverageOverallRating(city));
            model.addAttribute("reviews", reviews);
            model.addAttribute("affordabilityRating", ReviewData.calculateAverageAffordabilityRating(city));
            model.addAttribute("safetyRating", ReviewData.calculateAverageSafetyRating(city));
            model.addAttribute("transportationRating", ReviewData.calculateAverageTransportationRating(city));
            model.addAttribute("jobGrowthRating", ReviewData.calculateAverageJobGrowthRating(city));
            model.addAttribute("schoolRating", ReviewData.calculateAverageSchoolRating(city));

            model.addAttribute("currentPage", pageNo);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("totalItems", page.getTotalElements());
            model.addAttribute("reviewsPerPage", reviewsPerPage);
        }
        return "view";
    }

}

