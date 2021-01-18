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

//    @GetMapping("review/{cityId}")
//    public String writeReview(Model model, @PathVariable int cityId, HttpServletRequest request){
//
//        Optional<City> optCity = cityRepository.findById(cityId);
//        HttpSession session = request.getSession();
//        User user = authenticationController.getUserFromSession(session);
//
//        if(optCity.isPresent()) {
//            model.addAttribute("city", optCity.get());
//            model.addAttribute("cityId", cityId);
//            model.addAttribute(new Review());
//        }
//        model.addAttribute("user", user);
//        return "review";
//    }

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

        double averageOverallRate = ReviewData.calculateAverageOverallRating(city);
        city.setOverallCityRating(averageOverallRate);

        reviewRepository.save(newReview);

        return "redirect:../view/{cityId}/1";
    }

//
//    @PostMapping("review/{cityId}")
//    public String processWriteReview(@ModelAttribute @Valid Review newReview,
//                                     Errors errors, Model model, @PathVariable int cityId,
//                                     HttpServletRequest request,@RequestParam("files") MultipartFile[] files){
//
//
//        HttpSession session = request.getSession();
//        User user = authenticationController.getUserFromSession(session);
//        model.addAttribute("user", user);
//
//        Optional<City> optCity = cityRepository.findById(cityId);
//        City city = optCity.get();
//        double averageOverallRate = ReviewData.calculateAverageOverallRating(city);
//        ReviewData.uploadImagesToDB(files, optCity, newReview, averageOverallRate);
//        model.addAttribute("city", city);
//
//
//        if (errors.hasErrors()){
//            return "review";
//        }
//
////        newReview.setCity(city);
//        newReview.setUser(user);
//        reviewRepository.save(newReview);
//
//        if(newReview.getOverallRating() != 0){
//            ReviewData.calculateAverageOverallRating(city);
//            ReviewData.calculateAverageAffordabilityRating(city);
//            ReviewData.calculateAverageSafetyRating(city);
//            ReviewData.calculateAverageJobGrowthRating(city);
//            ReviewData.calculateAverageTransportationRating(city);
//            ReviewData.calculateAverageSchoolRating(city);
//        }
//
//
//        model.addAttribute("overallRating", ReviewData.calculateAverageOverallRating(city));
//        model.addAttribute("reviews", city.getReviews());
//        model.addAttribute("affordabilityRating", ReviewData.calculateAverageAffordabilityRating(city));
//        model.addAttribute("safetyRating", ReviewData.calculateAverageSafetyRating(city));
//        model.addAttribute("transportationRating", ReviewData.calculateAverageTransportationRating(city));
//        model.addAttribute("jobGrowthRating", ReviewData.calculateAverageJobGrowthRating(city));
//        model.addAttribute("schoolRating", ReviewData.calculateAverageSchoolRating(city));
//        model.addAttribute("cities", cityRepository.findAll());
//        return "view";
//    }



}
