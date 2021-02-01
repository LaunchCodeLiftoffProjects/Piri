package org.launchcode.Piri.controllers;

import org.launchcode.Piri.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.attoparser.IDocumentHandler;
import org.launchcode.Piri.models.City;
import org.launchcode.Piri.models.Review;
import org.launchcode.Piri.models.ReviewData;
import org.launchcode.Piri.models.User;
import org.launchcode.Piri.models.data.CityRepository;
import org.launchcode.Piri.models.data.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.text.DefaultHighlighter;
import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import org.launchcode.Piri.models.City;
import org.launchcode.Piri.models.data.PagingAndSortingCityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {


    @Autowired
    private PagingAndSortingCityRepository pagingAndSortingCityRepository;


    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewData reviewData;

    @GetMapping("home")
    public String hello(){

        return "index";
    }



    @GetMapping("list-cities")
    public String listCities(Model model) { return "list-cities";}

    @GetMapping("view/{cityId}/{pageNo}")
    public String displayView(Model model, HttpServletRequest request, @PathVariable int cityId, @PathVariable(value
            = "pageNo") int pageNo, @RequestParam(required = false, value = "") String searchTermForReviews,
                              @RequestParam(required = false) String sortField, @RequestParam(required = false) String sortDirection, @RequestParam(required = false) Integer starRatingForReviews){

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        model.addAttribute("user", user);

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

        Page<Review> page = reviewData.findPaginatedReviews(pageNo, reviewCount,searchTermForReviews, city, sortField
                , sortDirection, starRatingForReviews);
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

        Iterable<Review> someReviews;
        someReviews = city.getReviews();


        if(optCity.isPresent()) {
            model.addAttribute("city", city);
            model.addAttribute("cityId", cityId);
            model.addAttribute("overallRating", ReviewData.calculateAverageOverallRating(city));
            model.addAttribute("reviews", someReviews);
            model.addAttribute("affordabilityRating", ReviewData.calculateAverageAffordabilityRating(city));
            model.addAttribute("safetyRating", ReviewData.calculateAverageSafetyRating(city));
            model.addAttribute("transportationRating", ReviewData.calculateAverageTransportationRating(city));
            model.addAttribute("jobGrowthRating", ReviewData.calculateAverageJobGrowthRating(city));
            model.addAttribute("schoolRating", ReviewData.calculateAverageSchoolRating(city));
            model.addAttribute("sizeOfReviews", sizeOfReviews);
        }

        ArrayList<String> wordListToHighlight = ReviewData.findWordToHighlight(reviews,searchTermForReviews);
        model.addAttribute("wordListToHighlight", wordListToHighlight);
        model.addAttribute("highlightWordStyle", "<span STYLE='background-color:  #0066ff;color: #ffffff'>");
        model.addAttribute("searchTermForReviews", searchTermForReviews);

        String[] wordsForFilterReviews ={"safe", "education", "quiet", "walkable", "affordable", "friendly", "job", "school", "historic", "cultural", "transportation", "urban", "suburban", "sport",};
        model.addAttribute("wordsForFilterReviews", wordsForFilterReviews);

        return "view";
    }




    @GetMapping()
    public String displayTopRated(Model model){

        List<City> bestOverallRated = (List<City>) pagingAndSortingCityRepository.findAll(Sort.by(Sort.Direction.DESC, "overallCityRating"));
        List<City> bestFiveOverallRated = bestOverallRated.subList(0, 5);
        model.addAttribute("topOverallRated", bestFiveOverallRated);

        List<City> mostAffordableRated = (List<City>) pagingAndSortingCityRepository.findAll(Sort.by(Sort.Direction.DESC, "overallAffordabilityRating"));
        List<City> fiveMostAffordableRated = mostAffordableRated.subList(0, 5);
        model.addAttribute("topAffordableRated", fiveMostAffordableRated);

        List<City> safestRated = (List<City>) pagingAndSortingCityRepository.findAll(Sort.by(Sort.Direction.DESC, "overallSafetyRating"));
        List<City> fiveSafestRated = safestRated.subList(0, 5);
        model.addAttribute("topSafestRated", fiveSafestRated);

        List<City> bestJobGrowth = (List<City>) pagingAndSortingCityRepository.findAll(Sort.by(Sort.Direction.DESC, "overallJobGrowthRating"));
        List<City> fiveBestJobGrowth = bestJobGrowth.subList(0, 5);
        model.addAttribute("topBestJobGrowth", fiveBestJobGrowth);

        List<City> bestSchoolRated = (List<City>) pagingAndSortingCityRepository.findAll(Sort.by(Sort.Direction.DESC, "overallSchoolRating"));
        List<City> fiveBestSchoolRated = bestJobGrowth.subList(0, 5);
        model.addAttribute("topBestSchools", fiveBestSchoolRated);

        List<City> bestTransportationRated = (List<City>) pagingAndSortingCityRepository.findAll(Sort.by(Sort.Direction.DESC, "overallTransportationRating"));
        List<City> fiveBestTransportationRated = bestJobGrowth.subList(0, 5);
        model.addAttribute("topBestTransportation", fiveBestTransportationRated);
        return "index";
    }

}
