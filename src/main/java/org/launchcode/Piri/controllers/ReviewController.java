package org.launchcode.Piri.controllers;

import org.launchcode.Piri.models.*;
import org.launchcode.Piri.models.data.ReviewRepository;
import org.launchcode.Piri.models.data.UserRepository;
import org.launchcode.Piri.models.dto.ReviewFormDTO;
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
import java.util.ArrayList;
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
                                    HttpServletRequest request, @RequestParam("files") MultipartFile[] files){

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
        return "redirect:../../";
    }

    /* @GetMapping("delete/{reviewId}")
    public String displayDelete(Model model, @PathVariable int reviewId) {
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
        return "delete";
    }

    @PostMapping("delete/{reviewId}")
    public String completeDelete(@ModelAttribute @Valid Review review, Model model,
                                 @PathVariable int reviewId) {
        Optional<Review> optReview = reviewRepository.findById(reviewId);
        review = optReview.get();

        model.addAttribute("review", review);
        City city = review.getCity();
        model.addAttribute("city", city);

        reviewRepository.delete(review);
        return "redirect:../../";

    }*/

    @GetMapping("delete/{reviewId}")
    public String deleteBuyer(@PathVariable int reviewId){
        reviewRepository.deleteById(reviewId);
        return "redirect:../../";
    }
}
