package org.launchcode.Piri.controllers;


import com.mysql.cj.protocol.x.XAuthenticationProvider;
import org.launchcode.Piri.models.City;
import org.launchcode.Piri.models.Review;
import org.launchcode.Piri.models.User;
import org.launchcode.Piri.models.data.CityRepository;
import org.launchcode.Piri.models.data.ReviewRepository;
import org.launchcode.Piri.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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

    @GetMapping("profile")
    public String displayViewUserProfile(Model model, HttpServletRequest request) {

        List<City> savedCities = new ArrayList<>();
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        Optional<User> optUser = userRepository.findById(user.getId());
        if (optUser.isPresent()) {
            List<Integer> cityIds = user.getSavedCities();
            for (Integer id: cityIds){
                Optional<City> optCity =cityRepository.findById(id);
                if(optCity.isPresent()) {
                    City city = (City) optCity.get();
                    savedCities.add(city);
                }
            }
            List<Review> reviews = user.getReviews();
            model.addAttribute("user", user);
            model.addAttribute("reviews", reviews);
            model.addAttribute("savedCities",savedCities);

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
            model.addAttribute("user", user);
            model.addAttribute("reviews", reviews);

            return "view-profile";
        } else {
            model.addAttribute("title", "user does not exist");
            return "redirect: ";
        }
    }


}


