package com.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// @Controller tells Spring that this class handles web requests.
//
// Spring detects this class during component scanning
// and registers it as a Spring-managed bean.
//
// Interview:
// Difference between @Controller and @RestController?
// @Controller is generally used for MVC views.
// @RestController returns response data directly.

@Controller
public class HomeController {

    // @GetMapping maps HTTP GET requests to this method.
    //
    // When the browser requests:
    // http://localhost:8080/home
    //
    // Spring executes this method.

    @GetMapping("/home")
    @ResponseBody

    // @ResponseBody tells Spring to write the returned value
    // directly into the HTTP response body.
    //
    // Without @ResponseBody, Spring MVC generally treats
    // the returned String as a view name.

    public String home() {

        // This text is returned directly to the browser.
        return "Welcome to Spring MVC - Home Controller!";
    }
}