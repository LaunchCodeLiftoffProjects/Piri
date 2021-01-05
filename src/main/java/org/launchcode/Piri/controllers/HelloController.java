package org.launchcode.Piri.controllers;

import org.launchcode.Piri.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HelloController {


        public String hello(){

            return "index";
        }

        @GetMapping("user/index")
        public String viewProfile(Model model, User user) {
            model.addAttribute("user", user);

            return "user/index";
        }

        @GetMapping("list-cities")
        public String listCities(Model model) { return "list-cities";}

}

