package org.launchcode.Piri.controllers;

import org.launchcode.Piri.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.launchcode.Piri.models.City;
import org.launchcode.Piri.models.data.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;


@Controller
public class HelloController {


        @Autowired
        private CityRepository cityRepository;

        public String hello(){

            return "index";
        }

        @GetMapping("list-cities")
        public String listCities(Model model) { return "list-cities";}
        @GetMapping("view/{cityId}")
        public String displayView(Model model, @PathVariable int cityId){
            Optional<City> optCity = cityRepository.findById(cityId);

            if(optCity.isPresent()) {
                model.addAttribute("city", optCity.get());
                model.addAttribute("rating", 4.3);

            }

            return "view";
        }

}

