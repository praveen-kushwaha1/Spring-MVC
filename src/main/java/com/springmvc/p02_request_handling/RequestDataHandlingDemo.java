package com.springmvc.p02_request_handling;

import org.springframework.web.bind.annotation.*;

/*
 * ============================================================
 * CLASS: RequestDataHandlingDemo
 * ============================================================
 *
 * PURPOSE:
 * Demonstrates how Spring MVC reads different types of
 * information from an HTTP request.
 *
 * CONCEPTS:
 * 1. @RequestHeader
 * 2. @CookieValue
 * 3. @RequestBody
 * 4. @ModelAttribute
 */

@RestController
@RequestMapping("/api/request-data")
public class RequestDataHandlingDemo {

    // ========================================================
    // PART 1: REQUEST HEADER
    // ========================================================

    /*
     * @RequestHeader:
     * Reads HTTP headers sent by the client.
     *
     * URL:
     * GET /api/request-data/user-agent
     *
     * User-Agent:
     * Identifies the client making the request.
     *
     * Example:
     * PostmanRuntime/7.x
     */
    @GetMapping("/user-agent")
    public String getUserAgent(
            @RequestHeader("User-Agent") String userAgent) {

        return "User Agent: " + userAgent;
    }

    /*
     * Reads the Authorization header.
     *
     * Authorization headers are commonly used to send
     * authentication credentials such as Bearer tokens.
     *
     * required=false:
     * Allows the request even when the header is missing.
     */
    @GetMapping("/authorization")
    public String getAuthorization(
            @RequestHeader(
                    value = "Authorization",
                    required = false
            ) String authorization) {

        return "Authorization: " +
                (authorization == null
                        ? "Not Provided"
                        : authorization);
    }

    /*
     * defaultValue:
     * Uses "en" when Accept-Language is missing.
     */
    @GetMapping("/language")
    public String getLanguage(
            @RequestHeader(
                    value = "Accept-Language",
                    defaultValue = "en"
            ) String language) {

        return "Preferred Language: " + language;
    }


    // ========================================================
    // PART 2: COOKIE VALUE
    // ========================================================

    /*
     * @CookieValue:
     * Reads a specific cookie sent by the browser.
     *
     * Request Cookie:
     * username=Praveen
     *
     * URL:
     * GET /api/request-data/username
     */
    @GetMapping("/username")
    public String getUsername(
            @CookieValue("username") String username) {

        return "Username Cookie: " + username;
    }

    /*
     * Optional cookie.
     *
     * If sessionId cookie is missing, the method still executes.
     */
    @GetMapping("/session")
    public String getSession(
            @CookieValue(
                    value = "sessionId",
                    required = false
            ) String sessionId) {

        return "Session ID: " +
                (sessionId == null ? "Not Found" : sessionId);
    }

    /*
     * Cookie with a default value.
     *
     * If theme cookie is missing, Spring uses "light".
     */
    @GetMapping("/theme")
    public String getTheme(
            @CookieValue(
                    value = "theme",
                    defaultValue = "light"
            ) String theme) {

        return "Selected Theme: " + theme;
    }


    // ========================================================
    // PART 3: REQUEST BODY
    // ========================================================

    /*
     * @RequestBody:
     * Reads JSON data from the HTTP request body.
     *
     * Spring uses HttpMessageConverter to convert JSON
     * into a Java object.
     *
     * Jackson is the commonly used JSON converter in Spring Boot.
     *
     * POST URL:
     * /api/request-data/student
     *
     * JSON:
     * {
     *   "id": 101,
     *   "name": "Praveen",
     *   "email": "praveen@gmail.com"
     * }
     */
    @PostMapping("/student")
    public String createStudent(
            @RequestBody StudentRequest student) {

        return "Student Created: ID = " + student.getId()
                + ", Name = " + student.getName()
                + ", Email = " + student.getEmail();
    }

    /*
     * Demonstrates updating a student using JSON.
     *
     * PUT URL:
     * /api/request-data/student
     *
     * Spring converts the JSON request body into StudentRequest.
     *
     * This is only a demonstration; no database update occurs.
     */
    @PutMapping("/student")
    public StudentRequest updateStudent(
            @RequestBody StudentRequest student) {

        return student;
    }


    // ========================================================
    // PART 4: MODEL ATTRIBUTE
    // ========================================================

    /*
     * @ModelAttribute:
     * Binds request parameters to Java object properties.
     *
     * GET URL:
     * /api/request-data/student-form
     * ?id=101&name=Praveen&email=test@gmail.com
     *
     * Spring creates a StudentRequest object and populates
     * its properties using the incoming request parameters.
     */
    @GetMapping("/student-form")
    public StudentRequest getStudentForm(
            @ModelAttribute StudentRequest student) {

        return student;
    }

    /*
     * Form submission using @ModelAttribute.
     *
     * POST URL:
     * /api/request-data/register
     *
     * Postman:
     * Body -> x-www-form-urlencoded
     *
     * id    = 101
     * name  = Praveen
     * email = test@gmail.com
     *
     * IMPORTANT:
     * This is form-data binding, not JSON binding.
     */
    @PostMapping("/register")
    public String registerStudent(
            @ModelAttribute StudentRequest student) {

        return "Registered Student: "
                + student.getId() + ", "
                + student.getName() + ", "
                + student.getEmail();
    }

    /*
     * Method-level @ModelAttribute:
     *
     * Adds a common attribute to the model before the
     * controller request-handling method executes.
     *
     * Useful in traditional Spring MVC applications.
     */
    @ModelAttribute("applicationName")
    public String applicationName() {

        return "Spring MVC Learning Application";
    }
}
