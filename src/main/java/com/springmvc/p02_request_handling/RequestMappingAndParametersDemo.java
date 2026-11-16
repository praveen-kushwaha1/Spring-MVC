package com.springmvc.p02_request_handling;

import org.springframework.web.bind.annotation.*;

/*
 * ============================================================
 * CLASS: RequestMappingAndParametersDemo
 * ============================================================
 *
 * PURPOSE:
 * This class demonstrates how Spring MVC maps incoming HTTP
 * requests to controller methods and extracts values from URLs.
 *
 * CONCEPTS:
 * 1. @RequestMapping
 * 2. HTTP method mappings
 * 3. @RequestParam
 * 4. @PathVariable
 *
 * @RestController:
 * Combines @Controller and @ResponseBody.
 * Every returned value is written directly into the HTTP response.
 *
 * @RequestMapping:
 * Defines a common URL prefix for all methods in this controller.
 */

@RestController
@RequestMapping("/api/request-demo")
public class RequestMappingAndParametersDemo {

    // ========================================================
    // PART 1: REQUEST MAPPING
    // ========================================================

    /*
     * @GetMapping:
     * Handles HTTP GET requests.
     *
     * URL:
     * GET /api/request-demo
     *
     * USE:
     * Fetching or reading data from the server.
     */
    @GetMapping
    public String getRequest() {
        return "GET request handled successfully";
    }

    /*
     * @PostMapping:
     * Handles HTTP POST requests.
     *
     * URL:
     * POST /api/request-demo
     *
     * USE:
     * Creating new resources.
     */
    @PostMapping
    public String postRequest() {
        return "POST request handled successfully";
    }

    /*
     * @PutMapping:
     * Handles HTTP PUT requests.
     *
     * USE:
     * Replacing or updating an existing resource.
     */
    @PutMapping
    public String putRequest() {
        return "PUT request handled successfully";
    }

    /*
     * @PatchMapping:
     * Handles HTTP PATCH requests.
     *
     * USE:
     * Partially updating an existing resource.
     */
    @PatchMapping
    public String patchRequest() {
        return "PATCH request handled successfully";
    }

    /*
     * @DeleteMapping:
     * Handles HTTP DELETE requests.
     *
     * USE:
     * Deleting a resource.
     */
    @DeleteMapping
    public String deleteRequest() {
        return "DELETE request handled successfully";
    }

    /*
     * @RequestMapping:
     * General-purpose request mapping annotation.
     *
     * Here we explicitly specify the HTTP method.
     *
     * URL:
     * GET /api/request-demo/custom
     */
    @RequestMapping(
            value = "/custom",
            method = RequestMethod.GET
    )
    public String customMapping() {
        return "Custom RequestMapping executed";
    }


    // ========================================================
    // PART 2: REQUEST PARAMETER
    // ========================================================

    /*
     * @RequestParam:
     * Extracts query parameters from the URL.
     *
     * URL:
     * GET /api/request-demo/student?name=Praveen
     *
     * name=Praveen is a query parameter.
     *
     * Spring automatically assigns its value to the name variable.
     */
    @GetMapping("/student")
    public String getStudent(
            @RequestParam String name) {

        return "Student Name: " + name;
    }

    /*
     * Multiple @RequestParam values.
     *
     * URL:
     * /api/request-demo/details?name=Praveen&age=26
     *
     * Spring automatically converts age from String to int.
     */
    @GetMapping("/details")
    public String getStudentDetails(
            @RequestParam String name,
            @RequestParam int age) {

        return "Name: " + name + ", Age: " + age;
    }

    /*
     * required=false:
     * Makes the query parameter optional.
     *
     * Both URLs are valid:
     * /api/request-demo/city
     * /api/request-demo/city?city=Kanpur
     */
    @GetMapping("/city")
    public String getCity(
            @RequestParam(required = false) String city) {

        return "City: " +
                (city == null ? "Not Provided" : city);
    }

    /*
     * defaultValue:
     * Provides a fallback value when the parameter is missing
     * or empty.
     *
     * URL:
     * /api/request-demo/country
     *
     * Output:
     * Country: India
     */
    @GetMapping("/country")
    public String getCountry(
            @RequestParam(defaultValue = "India") String country) {

        return "Country: " + country;
    }

    /*
     * Mapping a URL parameter to a differently named variable.
     *
     * URL:
     * /api/request-demo/search?student_name=Praveen
     */
    @GetMapping("/search")
    public String searchStudent(
            @RequestParam("student_name") String name) {

        return "Searching Student: " + name;
    }


    // ========================================================
    // PART 3: PATH VARIABLE
    // ========================================================

    /*
     * @PathVariable:
     * Extracts values directly from the URL path.
     *
     * URL:
     * /api/request-demo/student/101
     *
     * 101 is a path variable.
     *
     * DIFFERENCE:
     * RequestParam  -> ?id=101
     * PathVariable  -> /student/101
     */
    @GetMapping("/student/{id}")
    public String getStudentById(
            @PathVariable int id) {

        return "Student ID: " + id;
    }

    /*
     * Multiple path variables.
     *
     * URL:
     * /api/request-demo/student-details/101/Praveen
     */
    @GetMapping("/student-details/{id}/{name}")
    public String getStudentByIdAndName(
            @PathVariable int id,
            @PathVariable String name) {

        return "Student ID: " + id +
                ", Student Name: " + name;
    }

    /*
     * Explicitly mapping the path variable name.
     *
     * Useful when URL variable and Java variable names differ.
     *
     * URL:
     * /api/request-demo/employee/501
     */
    @GetMapping("/employee/{employeeId}")
    public String getEmployee(
            @PathVariable("employeeId") int id) {

        return "Employee ID: " + id;
    }
}
