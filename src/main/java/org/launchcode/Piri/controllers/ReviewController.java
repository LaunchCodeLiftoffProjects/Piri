package org.launchcode.Piri.controllers;

import org.launchcode.Piri.models.*;
import org.launchcode.Piri.models.data.ReviewRepository;
import org.launchcode.Piri.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.launchcode.Piri.models.City;
import org.launchcode.Piri.models.data.CityRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class ReviewController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationController authenticationController;

    @GetMapping("review/{cityId}")
    public String writeReview(Model model, @PathVariable int cityId, HttpServletRequest request){

        Optional<City> optCity = cityRepository.findById(cityId);
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        if(optCity.isPresent()) {
            model.addAttribute("city", optCity.get());
            model.addAttribute("cityId", cityId);
            model.addAttribute(new Review());

        }
        model.addAttribute("user", user);
        return "review";
    }

    @PostMapping("review/{cityId}")
    public String processWriteReview(@ModelAttribute @Valid Review newReview,
                                     Errors errors, Model model, @PathVariable int cityId,
                                     HttpServletRequest request){

        if (errors.hasErrors()){
            return "review";
        }

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        Optional<City> optCity = cityRepository.findById(cityId);

        if (optCity.isPresent()) {
            City city = optCity.get();
            newReview.setCity(city);
            newReview.setUser(user);
            model.addAttribute("city", city);
            model.addAttribute("cityId", cityId);
            model.addAttribute("overallRating", ReviewData.calculateAverageOverallRating(cityId, city));
            model.addAttribute("reviews", city.getReviews());
            model.addAttribute("affordabilityRating", 4.5);
            model.addAttribute("safetyRating", 4);
            model.addAttribute("transportationRating", 3);
            model.addAttribute("jobRating", 4);
        }


        model.addAttribute("cities", cityRepository.findAll());

        reviewRepository.save(newReview);
        model.addAttribute("reviews", reviewRepository.findAll());
        model.addAttribute("user", user);
        return "view";
    }


}
