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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("review")
public class ReviewController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationController authenticationController;


    @GetMapping("{cityId}")
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

    @PostMapping("{cityId}")
    public String processWriteReview(@ModelAttribute @Valid Review newReview,
                                     Errors errors, Model model, @PathVariable int cityId,
                                     HttpServletRequest request,@RequestParam("files") MultipartFile[] files){


        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        model.addAttribute("user", user);

        Optional<City> optCity = cityRepository.findById(cityId);
        City city = optCity.get();
        model.addAttribute("city", city);

        ReviewData.uploadImagesToDB(files, optCity, newReview);
        if (errors.hasErrors()){
            return "review";
        }

    //        newReview.setCity(city);
        newReview.setUser(user);

        List<Review> reviewList = city.getReviews();
        reviewList.add(newReview);
        city.setReviews(reviewList);
        double averageOverallRate = ReviewData.calculateAverageOverallRating(city);
        city.setOverallCityRating(averageOverallRate);
        double averageOverallSafetyRate = ReviewData.calculateAverageSafetyRating(city);
        city.setOverallSafetyRating(averageOverallSafetyRate);
        double averageOverallTransportationRate = ReviewData.calculateAverageTransportationRating(city);
        city.setOverallTransportationRating(averageOverallTransportationRate);
        double averageOverallJobGrowthRate = ReviewData.calculateAverageJobGrowthRating(city);
        city.setOverallJobGrowthRating(averageOverallJobGrowthRate);
        double averageOverallSchoolRate = ReviewData.calculateAverageSchoolRating(city);
        city.setOverallSchoolRating(averageOverallSchoolRate);
        double averageOverallAffordabilityRate = ReviewData.calculateAverageAffordabilityRating(city);
        city.setOverallAffordabilityRating(averageOverallAffordabilityRate);

        reviewRepository.save(newReview);

        return "redirect:../view/{cityId}/1";
    }




}
