package org.launchcode.Piri.controllers;

import org.launchcode.Piri.models.City;
import org.launchcode.Piri.models.data.PagingAndSortingCityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private PagingAndSortingCityRepository pagingAndSortingCityRepository;

    @GetMapping("")
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
