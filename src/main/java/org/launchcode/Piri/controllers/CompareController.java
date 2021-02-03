package org.launchcode.Piri.controllers;

import org.launchcode.Piri.models.City;
import org.launchcode.Piri.models.data.CityRepository;
import org.launchcode.Piri.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class CompareController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityService cityService;


    @GetMapping("compare")
    public String compare(Model model){
        return "compare";
    }

    // should look like /compare?
    @GetMapping("compare/{cityIdOne}/{cityIdTwo}")
    public String compareWith2QueryParam(Model model, @PathVariable String cityIdOne, @PathVariable String cityIdTwo){
        model.addAttribute("cityOne",cityRepository.findById(Integer.valueOf(cityIdOne)).get());
        model.addAttribute("cityTwo",cityRepository.findById(Integer.valueOf(cityIdTwo)).get());
        return "compare";
    }

    @GetMapping("compare/{cityIdOne}")
    public String compareWith1QueryParam(Model model, @PathVariable String cityIdOne){
        model.addAttribute("cityOne",cityRepository.findById(Integer.valueOf(cityIdOne)).get());
        return "compare";
    }


    @GetMapping(value = "compare/{cityIdOne}/page/{pageNo}", params = "searchTerm")
    public String findPaginated(@PathVariable(value = "cityIdOne") int cityOne, @PathVariable(value = "pageNo") int pageNo, Model model,@RequestParam String searchTerm,@RequestParam(required = false) String sortField, @RequestParam(required = false) String sortDirection, @RequestParam(required = false) Integer starRating){

        int cityCount = 6;

        if(starRating == null){
            starRating = 0;
        }
        if(sortField == null) {
            sortField = "cityName";
        }
        if(sortDirection == null){
            sortDirection = "asc";
        }

        Page<City> page = cityService.findPaginatedByValue(pageNo, cityCount, searchTerm, sortField, sortDirection, starRating);
        List<City> cities = page.getContent();

        model.addAttribute("cityOne", cityRepository.findById(Integer.valueOf(cityOne)).get() );
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("cities", cities);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("starRating", starRating);
        return "compare-search";
    }



}
