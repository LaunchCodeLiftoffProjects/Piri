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

@Controller
//@RequestMapping("index")
public class SearchController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityService cityService;


    @RequestMapping(value = "/page/{pageNo}", params = "searchTerm")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model,@RequestParam String searchTerm){

        int cityCount = 6;


        Page<City> page = cityService.findPaginatedByValue(pageNo, cityCount, searchTerm);
        List<City> cities = page.getContent();

        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("cities", cities);

        return "search";
    }

}
