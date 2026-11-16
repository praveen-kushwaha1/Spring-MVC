package com.springmvc.p03_model_view_and_thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ModelDemo {

    // Model transfers controller data to the HTML view.
    @GetMapping("/model-demo")
    public String showModel(Model model) {

        // Add a String attribute.
        model.addAttribute("name", "Praveen");

        // Add an Integer attribute.
        model.addAttribute("age", 26);

        // Add a String attribute.
        model.addAttribute("city", "Bengaluru");

        // Add a Boolean attribute.
        model.addAttribute("active", true);

        // Return the view name.
        return "model-demo";
    }
}