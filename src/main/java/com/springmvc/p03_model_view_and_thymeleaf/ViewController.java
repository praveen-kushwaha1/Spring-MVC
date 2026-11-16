package com.springmvc.p03_model_view_and_thymeleaf;

import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ViewController implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        //// Map /about directly to about.html.
        registry.addViewController("/about")
                .setViewName("about");
//   // Map /contact directly to contact.html.
        registry.addViewController("/contact")
                .setViewName("contact");
    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute User user, Model model) {

        // Spring automatically populates User properties.
        model.addAttribute("user", user);

        return "user-success";
    }
}