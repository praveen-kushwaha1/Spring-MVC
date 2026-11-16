# P03 – Model, ModelAndView, Redirect, Forward, Thymeleaf & Form Handling

This section covers how Spring MVC sends data from the Controller to the View, how HTML pages display dynamic data, how forms submit data, and how redirects and forwards work.

We will understand each concept step by step, with simple Java examples, practical scenarios, and interview points.

> **Note:** No project setup or unnecessary files—only individual concepts and code examples.

---

## 1. First Understand the Complete Flow

Before learning individual classes, understand how Spring MVC connects the Controller and View.

### Spring MVC Request-Response Flow

1. **Browser** – User sends HTTP request
2. **DispatcherServlet** – Receives and routes the request
3. **Controller** – Processes request and prepares data
4. **Model** – Stores data to be displayed
5. **ViewResolver** – Finds the requested HTML template
6. **Thymeleaf View** – Combines HTML and dynamic data
7. **Browser** – Displays the final HTML response

**Example:**
A user visits `/user/profile`:
- Controller receives the request.
- Controller prepares user information.
- Model stores the information.
- Thymeleaf reads the information.
- Browser displays the user's profile.

---

## 2. `ModelDemo.java` – Understanding `Model`

### 2.1 What is `Model`?
`Model` is an interface provided by Spring MVC. It is used to transfer data from the Controller to the View. Think of `Model` as a temporary container or bag that carries data.

**Real-world example:**
Suppose you want to display:
- **Name:** Praveen
- **Age:** 26
- **City:** Bengaluru

The controller puts these values into the `Model`, and the HTML page reads them.

### 2.2 Important `Model` Methods

| Method | Purpose |
| :--- | :--- |
| `addAttribute()` | Adds data to Model |
| `addAllAttributes()` | Adds multiple attributes |
| `addAttribute("key", value)` | Adds data with a specific key |
| `mergeAttributes()` | Adds attributes without replacing existing ones |
| `containsAttribute()` | Checks whether an attribute exists |
| `getAttribute()` | *Not a standard Model interface method* |

### 2.3 Example: `ModelDemo.java`

```java
package com.learning.springmvc.p03;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ModelDemo {

    // Model transfers controller data to the HTML view.
    @GetMapping("/model-demo")
    public String showModel(Model model) {

        // Add a String attribute.
        model.addAttribute("name", "Praveen");

        // Add an Integer attribute.
        model.addAttribute("age", 26);

        // Add a String attribute.
        model.addAttribute("city", "Bengaluru");

        // Add a Boolean attribute.
        model.addAttribute("active", true);

        // Return the view name.
        return "model-demo";
    }
}
```

### 2.4 How Does `Model` Store Data?
For example:
```java
model.addAttribute("name", "Praveen");
```

Internally, you can understand it as key-value pairs:

| Key | Value |
| :--- | :--- |
| `name` | Praveen |
| `age` | 26 |
| `city` | Bengaluru |
| `active` | true |

### 2.5 Interview Questions

- **Q1. What is `Model` in Spring MVC?**
  `Model` is an interface used to transfer data from the Controller to the View.
- **Q2. Is `Model` a database?**
  No. It is used to carry request-related data to the view. It does not permanently store data.
- **Q3. Can `Model` store multiple attributes?**
  Yes. We can add multiple attributes using `addAttribute()`.

---

## 3. `ModelAndViewDemo.java` – Understanding `ModelAndView`

### 3.1 What is `ModelAndView`?
`ModelAndView` is a Spring MVC class that combines two things:
1. **Model** – Data to display.
2. **View** – Name of the page to display.

Think of it as one object containing both data and view information.

#### `Model` vs `ModelAndView`

| Feature | `Model` | `ModelAndView` |
| :--- | :--- | :--- |
| **Storage** | Stores data | Stores data and view |
| **View Handling** | Returns view name separately | View name is stored inside object |
| **Method Used** | `addAttribute()` | `addObject()` |
| **Return Type** | Controller returns `String` | Controller returns `ModelAndView` |

### 3.2 Example: `ModelAndViewDemo.java`

```java
package com.learning.springmvc.p03;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ModelAndViewDemo {

    @GetMapping("/model-and-view")
    public ModelAndView showDetails() {

        // Create ModelAndView object.
        ModelAndView mav = new ModelAndView();

        // Set the view name.
        mav.setViewName("user-details");

        // Add data to the model.
        mav.addObject("name", "Praveen");
        mav.addObject("role", "Java Developer");
        mav.addObject("experience", 3);

        // Return both model and view together.
        return mav;
    }
}
```

### 3.3 Important `ModelAndView` Methods

| Method | Purpose |
| :--- | :--- |
| `setViewName()` | Sets view name |
| `addObject()` | Adds model attribute |
| `getViewName()` | Gets view name |
| `getModel()` | Gets model attributes |
| `setStatus()` | Sets HTTP response status |
| `setView()` | Sets a View object |

### 3.4 Another Way to Create `ModelAndView`

```java
@GetMapping("/user")
public ModelAndView getUser() {

    // Create ModelAndView with view name.
    ModelAndView mav = new ModelAndView("user-details");

    // Add attributes.
    mav.addObject("name", "Praveen");

    return mav;
}
```

**When should you use it?**
Use `ModelAndView` when you prefer to return the view and model together. In modern Spring MVC applications, returning a view-name `String` with `Model` is also common.

---

## 4. `RedirectForwardDemo.java` – Redirect vs Forward

This is a very important Spring MVC interview topic. Both redirect and forward transfer a request to another URL or resource, but their behaviour is different.

### 4.1 What is Redirect?
Redirect means sending the browser instructions to make a **new HTTP request** to another URL.

**Example:**
1. User visits `/old-page`
2. Spring returns a redirect response to `/new-page`
3. Browser makes another request to `/new-page`

**Redirect Flow:**
```text
Browser → /old-page
   ↓ (HTTP 302 redirect response)
Browser receives new URL /new-page
   ↓ (New HTTP request)
Controller handles /new-page
```

**Redirect Example:**
```java
package com.learning.springmvc.p03;

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
}
```

### 4.2 What is Forward?
Forward means **internally transferring the same request** to another URL/resource on the server. The browser does not know about this internal transfer, and the browser URL remains unchanged.

**Forward Flow:**
```text
Browser → /forward-demo
   ↓ (Internal server-side forward)
/welcome
   ↓ (Response returned to browser)
Browser URL remains /forward-demo
```

**Forward Example** *(add this method to `RedirectForwardDemo.java`)*:
```java
// Forward request internally.
@GetMapping("/forward-demo")
public String forwardDemo() {

    // Server forwards the same request.
    return "forward:/welcome";
}
```

### 4.3 Redirect vs Forward

| Feature | Redirect | Forward |
| :--- | :--- | :--- |
| **Request** | New request | Same request |
| **Browser URL** | Changes | Does not change |
| **HTTP Requests** | Two | One |
| **Server-side Transfer** | No, browser initiates new request | Yes |
| **Request Attributes** | Not automatically preserved | Preserved |
| **Typical Use** | After form submission | Internal navigation |

### 4.4 What is the PRG Pattern?
**PRG** stands for:
- **P** – Post
- **R** – Redirect
- **G** – Get

It is a common pattern used after successful form submission.

**Example:**
1. User submits registration form using `POST`.
2. Server saves the user.
3. Server redirects to `/success`.
4. Browser makes a `GET` request to `/success`.

**Why use PRG?**
It prevents accidental duplicate form submissions when the user refreshes the success page.

> **Interview Point:** Redirect is generally preferred after successful form submissions.

---

## 5. `ViewController.java` – Understanding View Controllers

### 5.1 What is a View?
A View is responsible for displaying the response to the user (e.g., HTML page, Thymeleaf template, JSP page).

In Spring MVC, a Controller can return a view name:
```java
@GetMapping("/home")
public String home() {
    return "home";
}
```
Spring looks for the corresponding template using the configured `ViewResolver`. With Spring Boot Thymeleaf's default configuration, this resolves to:
`templates/home.html`

### 5.2 What is `ViewControllerRegistry`?
Spring MVC provides `ViewControllerRegistry` to configure simple URL-to-view mappings without writing a controller method for every page.

**Initial Concept Example:**
```java
package com.learning.springmvc.p03;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ViewController {

    // Configure simple view mappings.
    public void addViewControllers(ViewControllerRegistry registry) {

        // Map /about directly to about.html.
        registry.addViewController("/about")
                .setViewName("about");

        // Map /contact directly to contact.html.
        registry.addViewController("/contact")
                .setViewName("contact");
    }
}
```

> **Important:** This class must implement `WebMvcConfigurer` and override its method.

**Corrected Complete Implementation:**
```java
@Configuration
public class ViewController implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/about")
                .setViewName("about");

        registry.addViewController("/contact")
                .setViewName("contact");
    }
}
```

**When should you use it?**
- **Use** a view controller when you only need to display a static page, no business logic is required, and no model data needs to be prepared (e.g., About Us, Contact Us, Help pages).
- **Do not use** it when you need to process data or handle business logic.

---

## 6. `FormController.java` – Understanding Form Handling

Form handling is one of the most important concepts in Spring MVC. A form allows the user to submit information to the server (e.g., Login, Registration, Contact, Employee, or Product forms).

### 6.1 How Does Form Submission Work?

```text
HTML Form (User enters name and email)
   ↓
HTTP POST Request (Form data is submitted)
   ↓
Spring Controller (Receives submitted data)
   ↓
Processing (Validates and processes data)
   ↓
Response (Success page or error page)
```

### 6.2 Important Annotations

| Annotation | Purpose |
| :--- | :--- |
| `@GetMapping` | Displays the form |
| `@PostMapping` | Handles form submission |
| `@RequestParam` | Reads individual form parameters |
| `@ModelAttribute` | Binds form data to Java object |
| `@Valid` | Triggers validation |
| `@RequestBody` | Reads request body, commonly JSON |
| `@PathVariable` | Reads values from URL path |

### 6.3 Example: Using `@RequestParam`

```java
package com.learning.springmvc.p03;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FormController {

    // Display registration form.
    @GetMapping("/register")
    public String showForm() {
        return "register";
    }

    // Process submitted form data.
    @PostMapping("/register")
    public String registerUser(
            // Read name from HTML input.
            @RequestParam("name") String name,
            // Read email from HTML input.
            @RequestParam("email") String email,
            Model model) {

        // Add submitted data to Model.
        model.addAttribute("name", name);
        model.addAttribute("email", email);

        // Display success page.
        return "register-success";
    }
}
```

#### Understanding `@RequestParam`
Suppose your HTML contains:
```html
<input type="text" name="name">
<input type="email" name="email">
```
Spring reads values using the `name` attribute:
```java
@RequestParam("name") String name
```
If the user enters `Praveen`, the variable `name` contains `"Praveen"`.

### 6.4 Required and Optional Parameters

```java
@GetMapping("/search")
public String search(
        // Required parameter.
        @RequestParam("keyword") String keyword,

        // Optional parameter with default value.
        @RequestParam(value = "page", defaultValue = "1") int page) {

    return "search";
}
```

### 6.5 What is `@ModelAttribute`?
`@ModelAttribute` binds multiple request parameters to a Java object. Suppose the user submits Name, Email, and Age. Instead of writing three separate `@RequestParam` arguments, we can use a Java object.

**Model Class:**
```java
public class User {

    private String name;
    private String email;
    private int age;

    // Generate getters and setters.
}
```

**Controller:**
```java
@PostMapping("/save-user")
public String saveUser(@ModelAttribute User user, Model model) {

    // Spring automatically populates User properties.
    model.addAttribute("user", user);

    return "user-success";
}
```

> **Important:** Spring uses JavaBean property accessors (setters/getters) or suitable binding mechanisms to populate the object.

#### `@RequestParam` vs `@ModelAttribute`

| Feature | `@RequestParam` | `@ModelAttribute` |
| :--- | :--- | :--- |
| **Binding** | Reads individual parameter | Binds multiple parameters |
| **Best For** | Simple values | Form objects |
| **Data Types** | `String`, `int`, etc. | Java object |
| **Example** | `name`, `email` | `User` |

---

## 7. `UserViewController.java` – Understanding Dynamic User Views

Now combine the concepts learned earlier to send user information to Thymeleaf and display it dynamically.

### 7.1 What is Thymeleaf?
Thymeleaf is a server-side Java template engine that allows us to combine HTML with dynamic data from Spring MVC.

- **Java Controller sends:**
  ```java
  model.addAttribute("name", "Praveen");
  ```
- **Thymeleaf displays:**
  ```html
  <h2 th:text="${name}"></h2>
  ```
- **Browser displays:**
  `Praveen`

### 7.2 Important Thymeleaf Attributes

| Attribute | Purpose |
| :--- | :--- |
| `th:text` | Displays text |
| `th:utext` | Displays unescaped HTML |
| `th:if` | Conditional rendering |
| `th:unless` | Reverse conditional rendering |
| `th:each` | Loops over collections |
| `th:value` | Sets input value |
| `th:href` | Dynamically creates links |
| `th:src` | Dynamically sets image source |
| `th:action` | Sets form action |
| `th:object` | Binds form to an object |
| `th:field` | Binds an input to an object property |
| `th:class` | Dynamically sets CSS class |

### 7.3 `UserViewController.java`

```java
package com.learning.springmvc.p03;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class UserViewController {

    @GetMapping("/user/profile")
    public String userProfile(Model model) {

        // Add user information.
        model.addAttribute("name", "Praveen");
        model.addAttribute("email", "praveen@example.com");
        model.addAttribute("age", 26);
        model.addAttribute("active", true);

        // Create a list of skills.
        List<String> skills = Arrays.asList(
                "Java",
                "Spring Boot",
                "Microservices",
                "SQL"
        );

        // Send list to Thymeleaf.
        model.addAttribute("skills", skills);

        return "user-profile";
    }
}
```

### 7.4 Thymeleaf – Display Dynamic Text

```html
<h2 th:text="${name}">Default Name</h2>
<p th:text="${email}">Default Email</p>
<p th:text="${age}">Default Age</p>
```
`${name}` reads the `name` attribute from the `Model`.

### 7.5 Thymeleaf – Conditional Rendering

```html
<p th:if="${active}">
    User is Active
</p>

<p th:unless="${active}">
    User is Inactive
</p>
```
- `th:if` displays the element when the condition is `true`.
- `th:unless` displays the element when the condition is `false`.

### 7.6 Thymeleaf – Loop Using `th:each`

```html
<h3>My Skills</h3>

<ul>
    <li th:each="skill : ${skills}"
        th:text="${skill}">
    </li>
</ul>
```

**Output:**
- Java
- Spring Boot
- Microservices
- SQL

### 7.7 Thymeleaf – Dynamic Links

```html
<a th:href="@{/user/profile}">
    View Profile
</a>
```
`@{...}` is Thymeleaf URL expression syntax.

---

## 8. Thymeleaf Form Binding – `th:object` and `th:field`

Instead of manually reading every input parameter, bind the complete HTML form to a Java object.

### Java Object
```java
public class User {

    private String name;
    private String email;

    // Getters and setters.
}
```

### Controller
```java
@GetMapping("/user-form")
public String showUserForm(Model model) {

    // Create empty User object.
    model.addAttribute("user", new User());

    return "user-form";
}

@PostMapping("/user-form")
public String submitUserForm(@ModelAttribute("user") User user,
                             Model model) {

    // Add submitted User object.
    model.addAttribute("user", user);

    return "user-success";
}
```

### Thymeleaf HTML Form
```html
<form th:action="@{/user-form}"
      th:object="${user}"
      method="post">

    <label>Name:</label>
    <input type="text" th:field="*{name}">

    <label>Email:</label>
    <input type="email" th:field="*{email}">

    <button type="submit">Submit</button>

</form>
```

### Understand the Syntax

| Syntax | Meaning |
| :--- | :--- |
| `${user}` | Access `User` object from Model |
| `th:object` | Binds form to `User` object |
| `*{name}` | Access `name` property of bound object |
| `th:field` | Binds HTML input to Java property |
| `th:action` | Defines submission URL |

**Difference between `${}` and `*{}`:**
- `${}` accesses a model variable.
- `*{}` accesses a property relative to the currently selected `th:object`.

---

## 9. Important Additional Concepts

### 9.1 Flash Attributes
Flash attributes transfer data across a redirect. They are useful for showing success or error messages after form submission.

```java
@PostMapping("/save")
public String save(RedirectAttributes attributes) {

    attributes.addFlashAttribute(
            "message",
            "User saved successfully!"
    );

    return "redirect:/success";
}
```

**Destination:**
```java
@GetMapping("/success")
public String success() {
    return "success";
}
```

**Thymeleaf:**
```html
<p th:text="${message}"></p>
```
Flash attributes are generally available for the next request and then removed.

### 9.2 `RedirectAttributes`
`RedirectAttributes` is used to pass data during a redirect.

| Method | Purpose |
| :--- | :--- |
| `addAttribute()` | Adds URL query parameters |
| `addFlashAttribute()` | Adds temporary flash data |

**Example:**
```java
attributes.addAttribute("id", 101);
```
**Redirect URL:** `/success?id=101`

### 9.3 `ViewResolver`
`ViewResolver` is responsible for resolving a logical view name into an actual view.

For example, when a controller returns:
```java
return "user-profile";
```
Thymeleaf's view resolver finds `templates/user-profile.html`. Spring Boot normally configures this automatically when the Thymeleaf starter is available.

### 9.4 `Model` vs `ModelMap` vs `ModelAndView`

| Feature | `Model` | `ModelMap` | `ModelAndView` |
| :--- | :--- | :--- | :--- |
| **Type** | Interface | Class | Class |
| **Add Data** | Yes | Yes | Yes |
| **Set View** | No | No | Yes |
| **Main Method** | `addAttribute()` | `addAttribute()` | `addObject()` |
| **Common Use** | MVC controller data | MVC controller data | Model + View together |

---

## 10. Important Interview Questions – P03

Try answering these without looking at the explanations above:

1. **Q1.** What is `Model` in Spring MVC?
2. **Q2.** What is `ModelAndView`?
3. **Q3.** What is the difference between redirect and forward?
4. **Q4.** What is the PRG pattern?
5. **Q5.** What is `ViewResolver`?
6. **Q6.** What is Thymeleaf?
7. **Q7.** What is `@RequestParam`?
8. **Q8.** What is `@ModelAttribute`?
9. **Q9.** What is `th:each`?
10. **Q10.** What is `th:object`?
11. **Q11.** What is `th:field`?
12. **Q12.** What is `RedirectAttributes`?

---

## 11. Recommended Learning Sequence

Follow this order to understand the concepts properly:

| Step | Topic | Priority |
| :--- | :--- | :--- |
| 1 | `Model` and `addAttribute()` | Must learn |
| 2 | `ModelAndView` | Must learn |
| 3 | Redirect vs Forward | Must learn |
| 4 | `ViewResolver` | Must learn |
| 5 | `ViewControllerRegistry` | Important |
| 6 | `@RequestParam` | Must learn |
| 7 | `@ModelAttribute` | Must learn |
| 8 | Thymeleaf expressions | Must learn |
| 9 | Form binding | Must learn |
| 10 | Flash attributes and PRG | Important |

> **Recommendation:** Understand `Model`, `ModelAndView`, Redirect/Forward, and `@ModelAttribute` first. These concepts are particularly useful for Spring MVC interviews and for understanding server-side web applications.