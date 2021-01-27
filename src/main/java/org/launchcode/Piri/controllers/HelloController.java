package org.launchcode.Piri.controllers;

import org.launchcode.Piri.models.City;
import org.launchcode.Piri.models.Review;
import org.launchcode.Piri.models.ReviewData;
import org.launchcode.Piri.models.data.CityRepository;
import org.launchcode.Piri.models.data.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.List;


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


    @GetMapping("view/{cityId}/{pageNo}")
    public String displayView(Model model, @PathVariable int cityId,@PathVariable(value = "pageNo") int pageNo,@RequestParam(required = false, value = "") String searchTermForReviews,@RequestParam(required = false) String sortField, @RequestParam(required = false) String sortDirection, @RequestParam(required = false) Integer starRatingForReviews){

        if(starRatingForReviews == null){
            starRatingForReviews = 0;
        }
        if(searchTermForReviews == null){
            searchTermForReviews = "";
        }
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

        Page<Review> page = reviewData.findPaginatedReviews(pageNo, reviewCount,searchTermForReviews, city, sortField, sortDirection,starRatingForReviews);
        List<Review> reviews= page.getContent();

        Comparator<Review> byDate = new Comparator<Review>() {
            public int compare(Review c1, Review c2) {
                return Long.valueOf(java.sql.Date.valueOf(c1.getReviewDate()).getTime()).compareTo(java.sql.Date.valueOf(c2.getReviewDate()).getTime());
            }
        };

        List<Review> modifiableList = new ArrayList<Review>(reviews);

        if(sortField != null && sortField.equals("reviewDate") && sortDirection != null && sortDirection.equals("desc")){
            Collections.sort(modifiableList, byDate);
            reviews = modifiableList;
        }else if(sortField != null && sortField.equals("reviewDate") && sortDirection != null && sortDirection.equals("asc")){
            Collections.sort(modifiableList, byDate.reversed());
            reviews = modifiableList;
        }else{
        }
        model.addAttribute("reviews", reviews);

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        if(optCity.isPresent()) {
            model.addAttribute("city", city);
            model.addAttribute("cityId", cityId);
            model.addAttribute("overallRating", ReviewData.calculateAverageOverallRating(city));
            model.addAttribute("affordabilityRating", ReviewData.calculateAverageAffordabilityRating(city));
            model.addAttribute("safetyRating", ReviewData.calculateAverageSafetyRating(city));
            model.addAttribute("transportationRating", ReviewData.calculateAverageTransportationRating(city));
            model.addAttribute("jobGrowthRating", ReviewData.calculateAverageJobGrowthRating(city));
            model.addAttribute("schoolRating", ReviewData.calculateAverageSchoolRating(city));
            model.addAttribute("sizeOfReviews", sizeOfReviews);
        }

        ArrayList<String> wordListToHighlight = ReviewData.findWordToHighlight(reviews,searchTermForReviews);
        model.addAttribute("wordListToHighlight", wordListToHighlight);
        model.addAttribute("highlightWordStyle", "<span STYLE='background-color:  #00cc44;color: #ffffff'>");
        model.addAttribute("searchTermForReviews", searchTermForReviews);
        model.addAttribute("starRatingForReviews", starRatingForReviews);

        String[] wordsForFilterReviews ={"safe", "education", "quiet", "walkable", "affordable", "friendly", "job", "school", "historic", "cultural", "transportation", "urban", "suburban", "sport",};
        model.addAttribute("wordsForFilterReviews", wordsForFilterReviews);

        return "view";
    }

}