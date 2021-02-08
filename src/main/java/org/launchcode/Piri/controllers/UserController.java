package org.launchcode.Piri.controllers;


import com.mysql.cj.protocol.x.XAuthenticationProvider;
import org.launchcode.Piri.models.City;
import org.launchcode.Piri.models.Review;
import org.launchcode.Piri.models.User;
import org.launchcode.Piri.models.UserData;
import org.launchcode.Piri.models.data.CityRepository;
import org.launchcode.Piri.models.data.ReviewRepository;
import org.launchcode.Piri.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    CityRepository cityRepository;
    @Autowired
    AuthenticationController authenticationController;
    @Autowired
    private  UserData userData;

    @GetMapping("profile")
    public String displayViewUserProfile(Model model, HttpServletRequest request) {

        List<City> savedCities = new ArrayList<>();
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        Optional<User> optUser = userRepository.findById(user.getId());
        if (optUser.isPresent()) {
            List<Review> reviews = user.getReviews();
            model.addAttribute("user", user);
            model.addAttribute("reviews", reviews);
            model.addAttribute("favoriteCities", user.getSavedCities());

            return "profile";
        } else {
            model.addAttribute("title", "user does not exist");
            return "redirect: ";
        }
    }

    @GetMapping("view-profile")
    public String displayViewProfile(Model model, @RequestParam int userId) {
        Optional<User> result = userRepository.findById(userId);
        if (result.isPresent()) {
            User user = (User) result.get();
            List<Review> reviews = user.getReviews();
            List<City> favoriteCities = user.getSavedCities();
            model.addAttribute("user", user);
            model.addAttribute("reviews", reviews);
            model.addAttribute("favoriteCities", favoriteCities);

            return "view-profile";
        } else {
            model.addAttribute("title", "user does not exist");
            return "redirect: ";
        }
    }


    @PostMapping("profile")
    public String processUploadProfilePicture(HttpServletRequest request, Model model, @RequestParam(value = "file") MultipartFile file){
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        List<Review> reviews = user.getReviews();
        model.addAttribute("user", user);
        model.addAttribute("reviews", reviews);
        model.addAttribute("favoriteCities", user.getSavedCities());
        UserData.uploadProfilePicture(file, user);
        userRepository.save(user);
        return "profile";
    }


    @PostMapping("save-city")
    public String processSavingCityToFavoritesList(HttpServletRequest request, Model model, @RequestParam int savedCityId){
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        Optional<User> optUser = userRepository.findById(user.getId());

        Optional<City> optionalCity = cityRepository.findById(savedCityId);
        City city = optionalCity.get();

        if(optUser.isPresent()){
            List<Review> reviews = user.getReviews();
            city.setUser(authenticationController.getUserFromSession(session));
            userData.saveCityToFavoritesList(city,user);
            cityRepository.save(city);
            userRepository.save(user);
            model.addAttribute("user", user);
            model.addAttribute("city", city);
            model.addAttribute("favoriteCities", user.getSavedCities());
            model.addAttribute("reviews", reviews);
        }
        return "profile";
    }


    @PostMapping("unSave-city")
    public String processUnSaveCityFromFavoritesList(HttpServletRequest request, Model model, @RequestParam int savedCityId){
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        Optional<User> optUser = userRepository.findById(user.getId());

        Optional<City> optionalCity = cityRepository.findById(savedCityId);
        City city = optionalCity.get();

        if(optUser.isPresent()){
            List<Review> reviews = user.getReviews();
            userData.unSaveCityFromFavoritesList(city,user);
            cityRepository.save(city);
            userRepository.save(user);
            model.addAttribute("user", user);
            model.addAttribute("city", city);
            model.addAttribute("favoriteCities", user.getSavedCities());
            model.addAttribute("reviews", reviews);
        }
        return "profile";
    }

    @GetMapping("/savedCities/page/{pageNo}")
    public String displaySavedCitiesList(@PathVariable(value = "pageNo") int pageNo, Model model,@RequestParam(required = false) String searchTerm, HttpServletRequest request){
        int cityCount = 6;
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        Optional<User> optUser = userRepository.findById(user.getId());
        if(optUser.isPresent()) {
            Page<City> page = userData.findPaginatedSavedCities(user, pageNo, cityCount, searchTerm);
            List<City> cities = page.getContent();
            model.addAttribute("searchTerm", searchTerm);
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("totalItems", page.getTotalElements());
            model.addAttribute("cities", cities);
        }
        return "saved-cities-view";
    }


}