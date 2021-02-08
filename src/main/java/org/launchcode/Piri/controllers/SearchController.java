package org.launchcode.Piri.controllers;

import org.launchcode.Piri.models.City;
import org.launchcode.Piri.models.User;
import org.launchcode.Piri.models.data.CityRepository;
import org.launchcode.Piri.models.data.UserRepository;
import org.launchcode.Piri.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
//@RequestMapping("index")
public class SearchController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityService cityService;

    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "/page/{pageNo}", params = "searchTerm")
    public String findPaginated(HttpServletRequest request, @PathVariable(value = "pageNo") int pageNo, Model model, @RequestParam String searchTerm, @RequestParam(required = false) String sortField, @RequestParam(required = false) String sortDirection, @RequestParam(required = false) Integer starRating){

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        Optional<User> optUser = userRepository.findById(user.getId());
        if(optUser.isPresent()){
            model.addAttribute("user", user);
        }

        int cityCount = 6;

        if(starRating == null){
           starRating = 0;
        }
        if(sortField == null) {
        sortField = "overallCityRating";
        }
        if(sortDirection == null){
        sortDirection = "desc";
        }

        Page<City> page = cityService.findPaginatedByValue(pageNo, cityCount, searchTerm, sortField, sortDirection, starRating);
        List<City> cities = page.getContent();

        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("cities", cities);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("starRating", starRating);
        return "search";
    }


}
