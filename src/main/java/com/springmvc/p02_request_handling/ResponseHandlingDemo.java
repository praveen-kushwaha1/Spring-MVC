package com.springmvc.p02_request_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * ============================================================
 * CLASS: ResponseHandlingDemo
 * ============================================================
 *
 * PURPOSE:
 * Demonstrates how Spring MVC sends responses to clients.
 *
 * CONCEPTS:
 * 1. @ResponseBody
 * 2. ResponseEntity
 * 3. HTTP status codes
 * 4. Custom response headers
 *
 * NOTE:
 * @RestController already includes @ResponseBody.
 */

@RestController
@RequestMapping("/api/response-demo")
public class ResponseHandlingDemo {

    // ========================================================
    // PART 1: RESPONSE BODY
    // ========================================================

    /*
     * @ResponseBody:
     *
     * Writes the returned value directly into the HTTP response.
     *
     * URL:
     * GET /api/response-demo/hello
     *
     * Output:
     * Hello from Spring MVC
     *
     * With @RestController, @ResponseBody is already applied.
     */
    @GetMapping("/hello")
    @ResponseBody
    public String hello() {

        return "Hello from Spring MVC";
    }

    /*
     * Returning a Java object.
     *
     * Spring uses an HttpMessageConverter to convert
     * StudentRequest into JSON.
     *
     * URL:
     * GET /api/response-demo/student
     */
    @GetMapping("/student")
    public StudentRequest getStudent() {

        return new StudentRequest(
                101,
                "Praveen",
                "praveen@gmail.com"
        );
    }

    /*
     * Returning multiple objects.
     *
     * Spring converts the Java List into a JSON array.
     */
    @GetMapping("/students")
    public List<StudentRequest> getStudents() {

        return List.of(
                new StudentRequest(
                        101,
                        "Praveen",
                        "praveen@gmail.com"
                ),
                new StudentRequest(
                        102,
                        "Rahul",
                        "rahul@gmail.com"
                )
        );
    }


    // ========================================================
    // PART 2: RESPONSE ENTITY
    // ========================================================

    /*
     * ResponseEntity:
     *
     * Gives complete control over the HTTP response.
     *
     * We can configure:
     * 1. HTTP status code
     * 2. Response headers
     * 3. Response body
     *
     * Returns:
     * HTTP 200 OK
     */
    @GetMapping("/success")
    public ResponseEntity<String> success() {

        return ResponseEntity.ok("Request Successful");
    }

    /*
     * HTTP 201 CREATED:
     *
     * Used when a new resource has been created successfully.
     *
     * In real applications, this normally follows a successful
     * database insert.
     */
    @PostMapping("/create")
    public ResponseEntity<StudentRequest> createStudent() {

        StudentRequest student = new StudentRequest(
                101,
                "Praveen",
                "praveen@gmail.com"
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(student);
    }

    /*
     * HTTP 404 NOT FOUND:
     *
     * Used when the requested resource does not exist.
     */
    @GetMapping("/not-found")
    public ResponseEntity<String> notFound() {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Student Not Found");
    }

    /*
     * Custom response headers:
     *
     * header() adds a custom HTTP response header.
     *
     * Client can read this header from the response.
     */
    @GetMapping("/headers")
    public ResponseEntity<String> getHeaders() {

        return ResponseEntity
                .ok()
                .header("Custom-Header", "Spring-MVC")
                .body("Custom Header Added");
    }

    /*
     * HTTP 400 BAD REQUEST:
     *
     * Used when the client sends invalid request data.
     */
    @GetMapping("/bad-request")
    public ResponseEntity<String> badRequest() {

        return ResponseEntity
                .badRequest()
                .body("Invalid Request");
    }

    /*
     * HTTP 204 NO CONTENT:
     *
     * Indicates successful processing without a response body.
     *
     * Commonly used for successful DELETE operations.
     *
     * This is only a demonstration; no database deletion occurs.
     */
    @DeleteMapping("/student/{id}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable int id) {

        return ResponseEntity.noContent().build();
    }

    /*
     * HTTP 500 INTERNAL SERVER ERROR:
     *
     * Represents an unexpected server-side failure.
     *
     * Demonstration only: real applications should use
     * centralized exception handling.
     */
    @GetMapping("/server-error")
    public ResponseEntity<String> serverError() {

        return ResponseEntity
                .internalServerError()
                .body("Internal Server Error");
    }
}
