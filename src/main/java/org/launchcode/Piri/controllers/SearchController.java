package org.launchcode.Piri.controllers;

import org.launchcode.Piri.models.City;
import org.launchcode.Piri.models.CityData;
import org.launchcode.Piri.models.data.CityRepository;
import org.launchcode.Piri.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
//@RequestMapping("index")
public class SearchController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityService cityService;

//    @PostMapping("results")
//    public String displaySearchResults(Model model, @RequestParam String searchTerm){
//        Iterable<City> cities;
//        if(searchTerm.equals("")){
//            cities = cityRepository.findAll();
//        }else{
//            cities = CityData.findByValue(searchTerm, cityRepository.findAll());
//        }
//        model.addAttribute("cities", cities);
//
//        return "search";
//    }

//        @PostMapping("results")
//    public Iterable<City> displaySearchResults(Model model, @RequestParam String searchTerm){
//        Iterable<City> cities;
//        if(searchTerm.equals("")){
//            cities = cityRepository.findAll();
//        }else{
//            cities = CityData.findByValue(searchTerm, cityRepository.findAll());
//        }
////        model.addAttribute("cities", cities);
//
//        return cities;
//    }

//        @GetMapping("results")
//    public String viewCities(Model model, @RequestParam String searchTerm){
//        return findPaginated(1, model, searchTerm);
//    }



//    @GetMapping(value = "")
//    public String viewCities(Model model, @RequestParam String searchTerm){
//        return findPaginated(1, model, searchTerm);
//    }


//    @GetMapping(value = "/page/{pageNo}")
    @RequestMapping(value = "/page/{pageNo}", params = "searchTerm")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model,@RequestParam String searchTerm){
        int cityCount = 6;

//        Page<City> paged = cityService.findPaginated(pageNo, cityCount);
//        List<City> cityList = paged.getContent();
//        List<City> cities = CityData.findByValue(searchTerm, cityList);
//        Page<City> page = new PageImpl<>(cities);

            Page<City> page = cityService.findByValue(pageNo, cityCount, searchTerm);
            List<City> cities = page.getContent();


////
//        Page<City> page = cityService.findPaginated(pageNo, cityCount,searchTerm);
//
//        List<City> cities = page.getContent();



//        List<City> cities = CityData.findByValue(searchTerm, pageContent);

        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("cities", cities);

        return "search";
    }

}
