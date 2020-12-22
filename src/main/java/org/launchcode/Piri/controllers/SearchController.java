package org.launchcode.Piri.controllers;

import org.launchcode.Piri.models.City;
import org.launchcode.Piri.models.CityData;
import org.launchcode.Piri.models.data.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequestMapping("index")
public class SearchController {

    @Autowired
    private CityRepository cityRepository;

    @PostMapping("results")
    public String displaySearchResults(Model model, @RequestParam String searchTerm){
        Iterable<City> cities;
        if(searchTerm.equals("")){
            cities = cityRepository.findAll();
        }else{
            cities = CityData.findByValue(searchTerm, cityRepository.findAll());
        }
        model.addAttribute("cities", cities);

        return "search";
    }

}
