package com.springmvc.p03_model_view_and_thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class UserViewController {

    @GetMapping("/user/profile")
    public String userProfile(Model model) {

        // Add user information.
        model.addAttribute("name", "Praveen");

        model.addAttribute("email", "praveen@example.com");

        model.addAttribute("age", 26);

        model.addAttribute("active", true);

        // Create a list of skills.
        List<String> skills = Arrays.asList(
                "Java",
                "Spring Boot",
                "Microservices",
                "SQL"
        );

        // Send list to Thymeleaf.
        model.addAttribute("skills", skills);

        return "user-profile";
    }
}