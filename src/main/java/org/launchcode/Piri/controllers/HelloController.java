package org.launchcode.Piri.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HelloController {


        public String hello(){

            return "index";
        }

        @GetMapping("user/profile")
        public String viewProfile(Model model) {
            return "user/profile";
        }

}

