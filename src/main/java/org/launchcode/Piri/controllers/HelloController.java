package org.launchcode.Piri.controllers;

import org.launchcode.Piri.models.City;
import org.launchcode.Piri.models.data.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;


@Controller
public class HelloController {


    @Autowired
    private CityRepository cityRepository;

    public String hello(){

        return "index";
    }

    @GetMapping("view/{cityId}")
    public String displayView(Model model, @PathVariable int cityId){
        Optional<City> optCity = cityRepository.findById(cityId);

        if(optCity.isPresent()) {
            model.addAttribute("city", optCity.get());
            model.addAttribute("cityId", cityId);
            model.addAttribute("overallRating", 4.5);
            model.addAttribute("affordabilityRating", 2.5);
            model.addAttribute("safetyRating", 4);

        }

        return "view";
    }


}

