package org.launchcode.Piri.controllers;


import com.mysql.cj.protocol.x.XAuthenticationProvider;
import org.launchcode.Piri.models.Review;
import org.launchcode.Piri.models.User;
import org.launchcode.Piri.models.UserData;
import org.launchcode.Piri.models.data.CityRepository;
import org.launchcode.Piri.models.data.ReviewRepository;
import org.launchcode.Piri.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @GetMapping("view-profile")
    public String displayViewUserProfile(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        Optional<User> optUser = userRepository.findById(user.getId());
        if (optUser.isPresent()) {
            List<Review> reviews = user.getReviews();
            model.addAttribute("user", user);
            model.addAttribute("reviews", reviews);

            return "view-profile";
        } else {
            model.addAttribute("title", "user does not exist");
            return "redirect: ";
        }
    }

    @PostMapping("view-profile")
    public String processUploadProfilePicture(HttpServletRequest request, Model model, @RequestParam(value = "file") MultipartFile file){
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
            model.addAttribute("user", user);
            UserData.uploadProfilePicture(file, user);
            userRepository.save(user);
        return "view-profile";
    }
}


