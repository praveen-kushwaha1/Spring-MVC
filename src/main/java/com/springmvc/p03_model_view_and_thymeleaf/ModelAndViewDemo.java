package com.springmvc.p03_model_view_and_thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ModelAndViewDemo {

    @GetMapping("/model-and-view")

    public ModelAndView showDetails() {

        // Create ModelAndView object.
        ModelAndView mav = new ModelAndView();

        // Set the view name.
        mav.setViewName("user-details");

        // Add data to the model.
        mav.addObject("name", "Praveen");

        mav.addObject("role", "Java Developer");

        mav.addObject("experience", 3);

        // Return both model and view together.
        return mav;

    }

    @GetMapping("/user")
    public ModelAndView getUser() {

        // Create ModelAndView with view name.
        ModelAndView mav = new ModelAndView("user-details");

        // Add attributes.
        mav.addObject("name", "Praveen");

        return mav;
    }
}