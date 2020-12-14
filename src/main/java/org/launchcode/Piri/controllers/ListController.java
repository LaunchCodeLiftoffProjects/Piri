package org.launchcode.Piri.controllers;

import org.launchcode.Piri.models.City;
import org.launchcode.Piri.models.CityData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping(value = "list")
public class ListController {

    @RequestMapping(value = "cities")
    public String listCitiesByValue(Model model, @RequestParam(required = false) String value) {

        ArrayList<City> cities;

        cities = CityData.findAll();
            model.addAttribute("title", "All Cities");
            model.addAttribute("cities", cities);

        return "list-cities";
    }
}
