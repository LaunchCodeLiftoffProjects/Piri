package org.launchcode.Piri.controllers;

import org.launchcode.Piri.models.City;
import org.launchcode.Piri.models.Review;
import org.launchcode.Piri.models.data.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.launchcode.Piri.models.City;
import org.launchcode.Piri.models.data.CityRepository;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class ReviewController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @GetMapping("review/{cityId}")
    public String writeReview(Model model, @PathVariable int cityId){
        Optional<City> optCity = cityRepository.findById(cityId);

        if(optCity.isPresent()) {
            model.addAttribute("city", optCity.get());
            model.addAttribute("cityId", cityId);
            model.addAttribute(new Review());
        }
        return "review";
    }

    @PostMapping("review/{cityId}")
    public String processWriteReview(@ModelAttribute @Valid Review newReview, Errors errors, Model model){
        if (errors.hasErrors()){
            return "review";
        }

        reviewRepository.save(newReview);
        return "index";
    }

}
