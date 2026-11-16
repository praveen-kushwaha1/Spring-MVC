package com.springmvc.p01_mvc_basics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
// Class-level @ResponseBody applies to all handler methods.
// Therefore, we don't need to write it on every method.

public class HelloController {

    // Example 1: Simple GET request.
    //
    // URL:
    // http://localhost:8080/hello

    @GetMapping("/hello")
    public String sayHello() {

        return "Hello! Welcome to Spring MVC.";
    }

    // Example 2: Using @PathVariable.
    //
    // URL:
    // http://localhost:8080/hello/Praveen
    //
    // {name} is a variable part of the URL.
    // @PathVariable extracts its value and passes it to the method.

    @GetMapping("/hello/{name}")
    public String greetUser(@PathVariable String name) {

        return "Hello " + name + "! Welcome to Spring MVC.";
    }

    // Example 3: Using @RequestParam.
    //
    // URL:
    // http://localhost:8080/greet?name=Praveen
    //
    // @RequestParam extracts query parameters from the URL.
    // Here, name=Praveen is extracted and passed to the method.

    @GetMapping("/greet")
    public String greetWithParameter(
            @RequestParam String name) {

        return "Good Morning " + name + "!";
    }

    // Example 4: Optional request parameter with default value.
    //
    // URL:
    // http://localhost:8080/welcome
    //
    // URL:
    // http://localhost:8080/welcome?name=Praveen
    //
    // required=false means the parameter is optional.
    // defaultValue provides a fallback value when missing.

    @GetMapping("/welcome")
    public String welcome(
            @RequestParam(defaultValue = "Guest") String name) {

        return "Welcome " + name + "!";
    }
}