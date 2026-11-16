package com.springmvc.p03_model_view_and_thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectForwardDemo {

    // Redirect to another URL.
    @GetMapping("/redirect-demo")
    public String redirectDemo() {

        // Browser makes a new request to /welcome.
        return "redirect:/welcome";
    }

    // Destination of redirect.
    @GetMapping("/welcome")
    public String welcome() {

        return "welcome";
    }

    // Forward request internally.
    @GetMapping("/forward-demo")
    public String forwardDemo() {

        // Server forwards the same request.
        return "forward:/welcome";
    }
}