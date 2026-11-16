# Spring MVC: Request & Response Handling Notes

Beginner-friendly guide with detailed comments explaining **what**, **why**, and **how** each annotation works, along with a complete API testing reference and interview notes.

---

## 1. Updated Folder Structure

Create all classes inside the package: `com.learning.springmvc.p02_request_handling`

```text
p02_request_handling/
│
├── RequestMappingAndParametersDemo.java
│
├── RequestDataHandlingDemo.java
│
└── ResponseHandlingDemo.java
```

*(Note: Also includes `StudentRequest.java` as a reusable DTO inside the same package).*

### Concept Division Across Classes

| Class | Concepts Covered |
| :--- | :--- |
| `RequestMappingAndParametersDemo` | `@RequestMapping`, `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`, `@PatchMapping`, `@RequestParam`, `@PathVariable` |
| `RequestDataHandlingDemo` | `@RequestHeader`, `@CookieValue`, `@RequestBody`, `@ModelAttribute` |
| `ResponseHandlingDemo` | `@ResponseBody`, `ResponseEntity`, HTTP status codes, Response headers, Response body |

---

## 2. `RequestMappingAndParametersDemo.java`

### Concepts Covered
* `@RequestMapping`
* `@GetMapping`
* `@PostMapping`
* `@PutMapping`
* `@DeleteMapping`
* `@PatchMapping`
* `@RequestParam`
* `@PathVariable`

### Code Implementation

```java
package com.learning.springmvc.p02_request_handling;

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
```

---

## 3. `RequestDataHandlingDemo.java`

### Concepts Covered
* `@RequestHeader`
* `@CookieValue`
* `@RequestBody`
* `@ModelAttribute`

### Prerequisite: `StudentRequest.java` (Common DTO Class)
Keep this class in the same package (`com.learning.springmvc.p02_request_handling`). It is a reusable Data Transfer Object (DTO) for our examples.

```java
package com.learning.springmvc.p02_request_handling;

/*
 * DTO = Data Transfer Object.
 *
 * PURPOSE:
 * Carries student information between client and server.
 *
 * Spring uses this class to bind incoming JSON or form data
 * to Java object properties.
 */

public class StudentRequest {

    private int id;
    private String name;
    private String email;

    // Default constructor used by typical Jackson deserialization.
    public StudentRequest() {
    }

    // Parameterized constructor for creating objects manually.
    public StudentRequest(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getters and setters allow Spring to read and populate properties.

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
```

### Code Implementation (`RequestDataHandlingDemo.java`)

```java
package com.learning.springmvc.p02_request_handling;

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
```

---

## 4. `ResponseHandlingDemo.java`

### Concepts Covered
* `@ResponseBody`
* `ResponseEntity`
* HTTP status codes
* Response headers
* Response body

### Code Implementation

```java
package com.learning.springmvc.p02_request_handling;

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
```

---

## 5. Deep Dive: `@Controller` vs `@RestController` & Request Processing

In traditional Spring MVC, we generally use `@Controller`. In the previous examples, `@RestController` was used because we were returning text and JSON responses directly instead of rendering HTML views.

### 1. `@Controller` vs `@RestController`

* **`@Controller` – Traditional Spring MVC**
  * View-based application.
  * Used when returning HTML pages such as JSP, Thymeleaf, etc.
  ```java
  @Controller
  public class HomeController {

      @GetMapping("/home")
      public String home() {
          return "home";
      }
  }
  ```
  * Here, `"home"` is interpreted as a **view name**, not response text.

* **`@RestController` – REST API**
  * REST-based application.
  * Used when returning JSON, text, or other response data directly.
  ```java
  @RestController
  public class StudentController {

      @GetMapping("/student")
      public String student() {
          return "Praveen";
      }
  }
  ```
  * Here, `"Praveen"` is returned **directly in the HTTP response body**.

### 2. Internal Difference

`@RestController` is a combination of two annotations:
* `@Controller`
* `@ResponseBody`

So:
```java
@RestController
public class StudentController {
}
```
is equivalent to:
```java
@Controller
@ResponseBody
public class StudentController {
}
```
The difference lies in how Spring handles the return value.

### 3. Practical Examples Using Both

#### A. Using `@Controller` (View Rendering)
```java
package com.learning.springmvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Handles GET /home.
    @GetMapping("/home")
    public String home(Model model) {

        // Adds data to the model for the HTML view.
        model.addAttribute("name", "Praveen");

        // Returns the view name.
        // Spring searches for home.html or another configured view.
        return "home";
    }
}
```
For this example, with Thymeleaf configured, create `src/main/resources/templates/home.html`:
```html
<!DOCTYPE html>
<html>
<head>
    <title>Spring MVC</title>
</head>
<body>
    <h1>Welcome to Spring MVC</h1>
    <h2 th:text="${name}"></h2>
</body>
</html>
```
* **Expected browser output:**
  ```text
  Welcome to Spring MVC
  Praveen
  ```
  *(Rendered HTML page)*

#### B. Using `@Controller` with `@ResponseBody`
```java
@Controller
public class StudentController {

    // @ResponseBody tells Spring to write the returned value
    // directly into the HTTP response body.
    @GetMapping("/student")
    @ResponseBody
    public String student() {

        return "Welcome Praveen";
    }
}
```
* **Output:** `Welcome Praveen`

#### C. Using `@RestController`
```java
@RestController
public class StudentRestController {

    // @RestController automatically applies @ResponseBody.
    @GetMapping("/api/student")
    public String student() {

        return "Welcome Praveen";
    }
}
```
* **Output:** `Welcome Praveen` *(Both B and C produce the same kind of response).*

### 4. Spring MVC Request Processing Flow

Understanding how a request travels through Spring MVC:

1. **Incoming Request:** Client sends an HTTP request to the server.
2. **Front Controller (`DispatcherServlet`):** Intercepts all incoming requests and acts as the central traffic coordinator.
3. **Handler Mapping:** `DispatcherServlet` consults `HandlerMapping` to find which Controller method matches the requested URL and HTTP method.
4. **Controller Execution:** `DispatcherServlet` invokes the matched Controller method via `HandlerAdapter`.
5. **Return Value Handling (The Fork):**
   * **If `@Controller` (without `@ResponseBody`):**
     * Returns a **View Name** (e.g., `"home"`) + `Model` data.
     * `ViewResolver` maps the logical view name to an actual template file (e.g., `/templates/home.html`).
     * View engine (Thymeleaf/JSP) renders the HTML using the `Model` data and sends it back to the client.
   * **If `@RestController` (or `@ResponseBody`):**
     * Bypasses `ViewResolver` completely.
     * `HttpMessageConverter` (e.g., Jackson) converts the returned Java object/String directly into JSON/text.
     * Writes the output straight into the HTTP response body and returns it to the client.

```text
                        ┌───────────────────┐
                        │  Incoming Request │
                        └─────────┬─────────┘
                                  ▼
                        ┌───────────────────┐
                        │ DispatcherServlet │
                        └─────────┬─────────┘
                                  ▼
                        ┌───────────────────┐
                        │  HandlerMapping   │
                        └─────────┬─────────┘
                                  ▼
                        ┌───────────────────┐
                        │    Controller     │
                        └─────────┬─────────┘
                                  │
                 ┌────────────────┴────────────────┐
                 ▼ (@Controller)                   ▼ (@RestController / @ResponseBody)
      ┌─────────────────────┐           ┌──────────────────────┐
      │    ViewResolver     │           │ HttpMessageConverter │
      └──────────┬──────────┘           └──────────┬───────────┘
                 ▼                                 │
      ┌─────────────────────┐                      │
      │ Render View (HTML)  │                      │
      └──────────┬──────────┘                      │
                 └────────────────┬────────────────┘
                                  ▼
                        ┌───────────────────┐
                        │   HTTP Response   │
                        └───────────────────┘
```

---

## 6. Complete API Testing Reference

Once your Spring Boot application is running on port `8080`, test these endpoints using Postman, cURL, or a browser.

### Class 1 – `RequestMappingAndParametersDemo`

| Method | URL |
| :--- | :--- |
| `GET` | `/api/request-demo` |
| `POST` | `/api/request-demo` |
| `PUT` | `/api/request-demo` |
| `PATCH` | `/api/request-demo` |
| `DELETE` | `/api/request-demo` |
| `GET` | `/api/request-demo/custom` |
| `GET` | `/api/request-demo/student?name=Praveen` |
| `GET` | `/api/request-demo/details?name=Praveen&age=26` |
| `GET` | `/api/request-demo/city?city=Kanpur` |
| `GET` | `/api/request-demo/country` |
| `GET` | `/api/request-demo/student/101` |
| `GET` | `/api/request-demo/student-details/101/Praveen` |

*(Additional endpoints defined in code: `GET /api/request-demo/search?student_name=Praveen` and `GET /api/request-demo/employee/501`)*

### Class 2 – `RequestDataHandlingDemo`

| Method | URL |
| :--- | :--- |
| `GET` | `/api/request-data/user-agent` |
| `GET` | `/api/request-data/authorization` |
| `GET` | `/api/request-data/language` |
| `GET` | `/api/request-data/username` |
| `GET` | `/api/request-data/session` |
| `GET` | `/api/request-data/theme` |
| `POST` | `/api/request-data/student` |
| `PUT` | `/api/request-data/student` |
| `GET` | `/api/request-data/student-form?id=101&name=Praveen&email=test@gmail.com` |
| `POST` | `/api/request-data/register` |

### Class 3 – `ResponseHandlingDemo`

| Method | URL |
| :--- | :--- |
| `GET` | `/api/response-demo/hello` |
| `GET` | `/api/response-demo/student` |
| `GET` | `/api/response-demo/students` |
| `GET` | `/api/response-demo/success` |
| `POST` | `/api/response-demo/create` |
| `GET` | `/api/response-demo/not-found` |
| `GET` | `/api/response-demo/headers` |
| `GET` | `/api/response-demo/bad-request` |
| `DELETE` | `/api/response-demo/student/101` |
| `GET` | `/api/response-demo/server-error` |

---

## 7. Important Interview Revision

| Comparison | Key Difference |
| :--- | :--- |
| **`@RequestParam` vs `@PathVariable`** | Query parameter (`?id=101`) vs URL path value (`/student/101`) |
| **`@RequestBody` vs `@ModelAttribute`** | JSON/body binding vs request/form parameter binding |
| **`@RequestHeader` vs `@CookieValue`** | HTTP header vs cookie |
| **`@ResponseBody` vs `ResponseEntity`** | Direct response body vs complete response control (status, headers, body) |
| **`@Controller` vs `@RestController`** | View-oriented controller vs REST response controller (`@Controller` + `@ResponseBody`) |
| **`@PostMapping` vs `@PutMapping`** | Create resource vs replace/update resource |
| **`@PutMapping` vs `@PatchMapping`** | Full replacement vs partial update |

> **Important Architectural Point:** In actual enterprise Spring Boot applications, controllers should generally delegate business logic to service classes rather than implementing business logic directly. For this concept lab, keeping everything inside controller methods makes it easier to understand Spring MVC annotations independently.

**Next Step:** Once you complete these classes, build **P03 – Controller & View Handling** using the same three-class learning approach, with detailed comments and runnable examples.