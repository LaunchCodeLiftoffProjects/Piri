package org.launchcode.Piri.controllers;

import org.launchcode.Piri.models.City;
import org.launchcode.Piri.models.Review;
//import org.launchcode.Piri.models.ReviewData;
import org.launchcode.Piri.models.ReviewData;
import org.launchcode.Piri.models.data.CityRepository;
import org.launchcode.Piri.models.data.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;


@Controller
public class HelloController {


    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @RequestMapping("home")
    public String hello(){

        return "index";
    }

    @GetMapping("view/{cityId}")
    public String displayView(Model model, @PathVariable int cityId){
        Optional<City> optCity = cityRepository.findById(cityId);
        City city = optCity.get();

        Iterable<Review> reviews;
        reviews = city.getReviews();


        if(optCity.isPresent()) {
            model.addAttribute("city", city);
            model.addAttribute("cityId", cityId);
            model.addAttribute("overallRating", ReviewData.calculateAverageOverallRating(cityId, city));
            model.addAttribute("reviews", reviews);
            model.addAttribute("affordabilityRating", 4.5);
            model.addAttribute("safetyRating", 4);
            model.addAttribute("transportationRating", 3);
            model.addAttribute("jobRating", 4);
        }
        return "view";
    }


}

