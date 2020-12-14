package org.launchcode.Piri.controllers;

import org.launchcode.Piri.models.City;
import org.launchcode.Piri.models.CityData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequestMapping("index")
public class SearchController {

    @RequestMapping(value = "results")
    public String displaySearchResults(Model model, @RequestParam(required = false) String searchTerm){
        ArrayList<City> cities;
        if(searchTerm.isEmpty()){
            cities = CityData.findAll();
            model.addAttribute("title", "All Cities");
        }else{
            cities = CityData.findByValue(searchTerm);
            model.addAttribute("title", "Cities with: " + searchTerm);
       }

        model.addAttribute("cities", cities);
        return "search";

    }
}
