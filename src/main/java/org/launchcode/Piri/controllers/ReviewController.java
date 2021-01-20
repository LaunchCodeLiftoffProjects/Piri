package org.launchcode.Piri.controllers;

import org.launchcode.Piri.models.*;
import org.launchcode.Piri.models.data.ReviewRepository;
import org.launchcode.Piri.models.data.UserRepository;
import org.launchcode.Piri.models.dto.ReviewFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.launchcode.Piri.models.City;
import org.launchcode.Piri.models.data.CityRepository;
import org.springframework.web.servlet.ModelAndView;

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
                                     HttpServletRequest request){


        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        model.addAttribute("user", user);

        Optional<City> optCity = cityRepository.findById(cityId);
        City city = optCity.get();
        model.addAttribute("city", city);

        if (errors.hasErrors()){
            return "review";
        }

        newReview.setCity(city);
        newReview.setUser(user);
        reviewRepository.save(newReview);

        return "redirect:../view/{cityId}";
    }

    @GetMapping("edit/{reviewId}")
        public String displayEditForm(Model model, @PathVariable int reviewId) {


            Optional<Review> optReview = reviewRepository.findById(reviewId);

            Review review = optReview.get();
            City city = review.getCity();
            int cityId = city.getId();
            model.addAttribute("cityId", cityId);
            User user = review.getUser();

        if(review != null) {
            ReviewFormDTO reviewFormDTO = new ReviewFormDTO(review, user, city);
            model.addAttribute("reviewFormDTO", reviewFormDTO);

            model.addAttribute("review", review);
            model.addAttribute("city", city);
            model.addAttribute("user", user);
        }
            //model.addAttribute("title", "Edit Event " + event.getName() + " (ID=" + event.getId() + ")");
            return "edit";
    }


    @PostMapping("edit/{reviewId}")
        public String processEditForm(@ModelAttribute @Valid Review review, Errors errors,
                                      Model model, String title, String comment,
                                      int overallRating, int affordabilityRating, int safetyRating,
                                      int transportationRating, int jobGrowthRating, int schoolRating,
                                      @PathVariable int reviewId) {


        Optional<Review> optReview = reviewRepository.findById(reviewId);
        review = optReview.get();
        model.addAttribute("review", review);
        City city = review.getCity();
        model.addAttribute("city", city);
        //int cityId = city.getId();
        //model.addAttribute("cityId", cityId);
        User user = review.getUser();
        model.addAttribute("user", user);

        if (errors.hasErrors()){
            model.addAttribute("errorMsg", "Bad data!");
            return "edit";
        }

        review.setOverallRating(overallRating);
        review.setAffordabilityRating(affordabilityRating);
        review.setSafetyRating(safetyRating);
        review.setTransportationRating(transportationRating);
        review.setJobGrowthRating(jobGrowthRating);
        review.setSchoolRating(schoolRating);
        review.setTitle(title);
        review.setComment(comment);



        reviewRepository.save(review);
        return "redirect:../../home";
    }


}
