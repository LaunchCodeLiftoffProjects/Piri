package org.launchcode.Piri.controllers;


import com.mysql.cj.protocol.x.XAuthenticationProvider;
import org.launchcode.Piri.models.User;
import org.launchcode.Piri.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationController authenticationController;

    @GetMapping("view-profile")
    public String displayViewUserProfile(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        Optional<User> optUser = userRepository.findById(user.getId());

        if (optUser.isPresent()) {
            model.addAttribute("user", user);
            model.addAttribute("title", "${user.username}");
            return "user/view-profile";
        } else {
            model.addAttribute("title", "'Cannot find user with ID: ' + ${user.getId()} ");
            model.addAttribute("error", "'Cannot find user with ID: ' + ${user.getId()}");
            return "user/view-profile";
        }
    }
}
