# 🌱 P01 – Spring MVC Basics: Revision Notes

A quick-reference guide covering the fundamentals of Spring MVC, request handling, parameter extraction, Thymeleaf views, and request lifecycle architecture.

---

## 1. Project Setup & Structure

### Required Dependencies (`pom.xml`)
* **`spring-boot-starter-web`**: Provides Spring MVC, `DispatcherServlet`, REST-style request handling, and embedded Tomcat.
* **`spring-boot-starter-thymeleaf`**: Provides the Thymeleaf server-side template engine for rendering HTML views.

### Standard Folder Layout
```text
src/main/
├── java/com/learning/springmvc/
│   ├── SpringMvcApplication.java       # Main entry point (keep in root package!)
│   └── p01_spring_mvc_basics/          # Subpackage scanned automatically
│       ├── HomeController.java
│       ├── HelloController.java
│       ├── RequestMappingDemo.java
│       └── ViewControllerDemo.java
└── resources/
    ├── static/css/                     # Static assets (CSS, JS, images)
    └── templates/                      # Dynamic HTML views (Thymeleaf)
        ├── home.html
        ├── hello.html
        └── welcome.html
```

> **Tip:** Always keep `SpringMvcApplication` in the parent/root package because `@SpringBootApplication` includes `@ComponentScan`, which scans the current package and all its subpackages.

---

## 2. Core Annotations Cheat Sheet

| Annotation | Target | Purpose |
| :--- | :--- | :--- |
| `@SpringBootApplication` | Main Class | Combines `@Configuration`, `@EnableAutoConfiguration`, and `@ComponentScan`. |
| `@Controller` | Class | Marks a class as a Spring MVC web controller (typically returns view names). |
| `@ResponseBody` | Class / Method | Tells Spring to write the return value directly to the HTTP response body (JSON/text) instead of resolving a view. |
| `@RestController` | Class | Convenience annotation combining `@Controller` + `@ResponseBody`. |
| `@RequestMapping` | Class / Method | Maps HTTP requests to handler methods (supports `GET`, `POST`, `PUT`, `DELETE`, `PATCH`). |
| `@GetMapping` | Method | Shortcut for `@RequestMapping(method = RequestMethod.GET)`. (Same applies for `@PostMapping`, `@PutMapping`, `@DeleteMapping`, `@PatchMapping`). |
| `@PathVariable` | Method Param | Extracts dynamic values directly from the **URL path** (e.g., `/hello/{name}`). |
| `@RequestParam` | Method Param | Extracts values from **query parameters** (e.g., `/greet?name=Praveen`). |

---

## 3. Extracting Data from URLs

### `@PathVariable` vs. `@RequestParam`

| Feature | `@PathVariable` | `@RequestParam` |
| :--- | :--- | :--- |
| **Location in URL** | URI Path segment | Query string (after `?`) |
| **Example URL** | `http://localhost:8080/hello/Praveen` | `http://localhost:8080/greet?name=Praveen` |
| **Code Syntax** | `@GetMapping("/hello/{name}")`<br>`public String greet(@PathVariable String name)` | `@GetMapping("/greet")`<br>`public String greet(@RequestParam String name)` |
| **Primary Use Case** | Identifying a specific resource (e.g., `/users/10`). | Filtering, sorting, or passing optional metadata. |

### Optional Query Parameters
You can make a `@RequestParam` optional and provide a fallback value using `defaultValue` (which automatically implies `required = false`):
```java
@GetMapping("/welcome")
public String welcome(@RequestParam(defaultValue = "Guest") String name) {
    return "Welcome " + name + "!";
}
// /welcome              -> "Welcome Guest!"
// /welcome?name=Praveen -> "Welcome Praveen!"
```

---

## 4. HTTP Methods & Request Mapping

While `@RequestMapping(value = "/api/info", method = RequestMethod.GET)` works, **modern Spring Boot projects prefer specialized annotations** for readability:

* **GET** (`@GetMapping`): Retrieve data/resources.
* **POST** (`@PostMapping`): Create a new resource or submit form data.
* **PUT** (`@PutMapping`): Completely replace/update an existing resource.
* **PATCH** (`@PatchMapping`): Partially update an existing resource.
* **DELETE** (`@DeleteMapping`): Remove a resource.

---

## 5. Returning Views with Thymeleaf & `Model`

To return an HTML page instead of raw text:
1. Annotate the class with `@Controller` (**do not** use `@ResponseBody` or `@RestController`).
2. Return a `String` matching the HTML filename inside `src/main/resources/templates/` (without the `.html` extension).
3. Use Spring's `Model` interface to pass dynamic key-value pairs from the Controller to the HTML template.

### Controller Example
```java
@Controller
public class ViewControllerDemo {

    @GetMapping("/view/welcome")
    public String showWelcomePage(Model model) {
        model.addAttribute("name", "Praveen");
        model.addAttribute("course", "Spring MVC");
        return "welcome"; // Resolves to templates/welcome.html
    }
}
```

### Thymeleaf Template Example (`welcome.html`)
Include the Thymeleaf XML namespace (`xmlns:th="http://www.thymeleaf.org"`) and use `th:text="${attributeName}"` to render dynamic values:
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<body>
    <p>Name: <span th:text="${name}">Default Name</span></p>
    <p>Course: <span th:text="${course}">Default Course</span></p>
</body>
</html>
```

---

## 6. Spring MVC Request Flow (Interview Crucial!)

Understanding how an HTTP request travels through Spring MVC is a staple Java backend interview question:

```text
[1. Client / Browser] 
       │  (Sends HTTP Request)
       ▼
[2. DispatcherServlet] ◄── Front Controller (Intercepts all incoming requests)
       │
       ├──► [3. HandlerMapping] (Finds which Controller & method matches the URL)
       ▼
[4. Controller] (Executes business logic)
       │
       ├──► Path A (MVC View): Controller returns view name + Model
       │         └──► [5A. ViewResolver] locates HTML template (e.g., Thymeleaf)
       │
       └──► Path B (REST/Data): @ResponseBody / @RestController used
                 └──► [5B. HttpMessageConverter] serializes Java object to JSON/Text
       │
       ▼
[6. HTTP Response] ──► Sent back to [7. Client / Browser]
```

---

## 7. Quick Interview Q&A Summary

* **What is `DispatcherServlet`?** The Front Controller in Spring MVC that receives all incoming HTTP requests and coordinates routing, execution, and response rendering.
* **Difference between `@Controller` and `@RestController`?** `@Controller` is used in traditional MVC to return view names resolved by a `ViewResolver`. `@RestController` combines `@Controller` and `@ResponseBody` to return data (JSON/XML/text) directly in the HTTP response body.
* **What is the role of `HandlerMapping`?** It inspects the incoming request (URL, HTTP method) and maps it to the appropriate controller method.
* **What is the role of `ViewResolver`?** It maps the logical view name returned by a controller (e.g., `"home"`) to an actual physical template file (e.g., `/templates/home.html`).
* **What is `HttpMessageConverter`?** A strategy interface used when `@ResponseBody` is present to convert Java objects into HTTP response formats (like JSON via Jackson) and vice versa.

---

## 8. Self-Check Practice Tasks
- [ ] Create a `/about` endpoint using `@GetMapping`.
- [ ] Create `/user/{id}` using `@PathVariable`.
- [ ] Create `/search?keyword=java` using `@RequestParam`.
- [ ] Create a `POST` endpoint `/api/register`.
- [ ] Create a `PUT` endpoint `/api/users/{id}`.
- [ ] Create a `DELETE` endpoint `/api/users/{id}`.
- [ ] Pass `username` and `email` from a Controller to an HTML page using `Model`.
- [ ] Create a Thymeleaf page that displays the passed values using `th:text`.