package com.springmvc.p03_model_view_and_thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FormController {

    // Display registration form.
    @GetMapping("/register")
    public String showForm() {

        return "register";
    }

    // Process submitted form data.
    @PostMapping("/register")
    public String registerUser(

            // Read name from HTML input.
            @RequestParam("name") String name,

            // Read email from HTML input.
            @RequestParam("email") String email,

            Model model) {

        // Add submitted data to Model.
        model.addAttribute("name", name);

        model.addAttribute("email", email);

        // Display success page.
        return "register-success";
    }

    @GetMapping("/search")
    public String search(

            // Required parameter.
            @RequestParam("keyword") String keyword,

            // Optional parameter with default value.
            @RequestParam(value = "page", defaultValue = "1") int page) {

        return "search";
    }
}