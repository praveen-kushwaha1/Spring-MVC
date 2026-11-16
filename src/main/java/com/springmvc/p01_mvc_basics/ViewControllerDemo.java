package com.springmvc.p01_mvc_basics;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
// Notice: We are NOT using @ResponseBody here.
//
// This is because we want Spring MVC to resolve
// the returned String as an HTML view name.

public class ViewControllerDemo {

    // Example 1: Return home.html.
    //
    // URL:
    // http://localhost:8080/view/home
    //
    // Return "home" tells Spring to locate:
    // src/main/resources/templates/home.html

    @GetMapping("/view/home")
    public String showHomePage() {

        return "home";
    }

    // Example 2: Passing data from Controller to HTML.
    //
    // Model is used to transfer data from the controller
    // to the view.
    //
    // The HTML page can access this data using Thymeleaf.

    @GetMapping("/view/hello")
    public String showHelloPage(Model model) {

        // Add an attribute named "message" to the model.
        model.addAttribute(
                "message",
                "Welcome to Spring MVC with Thymeleaf!"
        );

        // Spring resolves this as hello.html.
        return "hello";
    }

    // Example 3: Passing multiple values to HTML.

    @GetMapping("/view/welcome")
    public String showWelcomePage(Model model) {

        model.addAttribute("name", "Praveen");
        model.addAttribute("course", "Spring MVC");
        model.addAttribute("duration", "30 Days");

        return "welcome";
    }
}