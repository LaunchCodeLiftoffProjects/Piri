package org.launchcode.Piri.controllers;


import org.launchcode.Piri.models.User;
import org.launchcode.Piri.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("view-profile/{userId}")
    public String displayViewUserProfile(Model model, @PathVariable int userId) {

        Optional<User> optUser = userRepository.findById(userId);

        if (optUser.isPresent()) {
            User user = (User) optUser.get();
            model.addAttribute("user", user);
            model.addAttribute("title", "${user.username}");
            return "user/view-profile";
        } else {
            model.addAttribute("title", "'Cannot find user with ID: ' + ${userID} ");
            model.addAttribute("error", "'Cannot find user with ID: ' + ${userID}");
            return "user/view-profile";
        }
    }
}
