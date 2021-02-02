package org.launchcode.Piri.controllers;

import org.launchcode.Piri.models.City;
import org.launchcode.Piri.models.CityData;
import org.launchcode.Piri.models.User;
import org.launchcode.Piri.models.UserData;
import org.launchcode.Piri.models.data.CityRepository;
import org.launchcode.Piri.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;


@Controller
@RequestMapping(value = "list")
public class ListController {

    @Autowired
    private CityRepository cityRepository;

    @RequestMapping(value = "cities")
    public String listAllCities(Model model, @RequestParam(required = false) String value) {

        Iterable<City> cities;
        cities = cityRepository.findAll();
        model.addAttribute("cities", cities);

        return "list-cities";
    }

}
