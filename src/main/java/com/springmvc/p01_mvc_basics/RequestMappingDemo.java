package com.springmvc.p01_mvc_basics;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody

public class RequestMappingDemo {

    // Example 1: Basic @RequestMapping.
    //
    // URL:
    // http://localhost:8080/api/info
    //
    // By default, @RequestMapping supports multiple HTTP methods.
    // Use method to restrict it to a specific HTTP method.

    @RequestMapping(
            value = "/api/info",
            method = RequestMethod.GET
    )
    public String getInfo() {

        return "This is GET request using @RequestMapping.";
    }

    // Example 2: POST request.
    //
    // POST is commonly used to create resources or submit data.
    //
    // We can test this using Postman.

    @RequestMapping(
            value = "/api/create",
            method = RequestMethod.POST
    )
    public String createResource() {

        return "Resource created successfully!";
    }

    // Example 3: PUT request.
    //
    // PUT is commonly used to replace/update a resource.

    @RequestMapping(
            value = "/api/update",
            method = RequestMethod.PUT
    )
    public String updateResource() {

        return "Resource updated successfully!";
    }

    // Example 4: DELETE request.
    //
    // DELETE is used to remove a resource.

    @RequestMapping(
            value = "/api/delete",
            method = RequestMethod.DELETE
    )
    public String deleteResource() {

        return "Resource deleted successfully!";
    }

    // Example 5: PATCH request.
    //
    // PATCH is commonly used for partial updates.

    @RequestMapping(
            value = "/api/patch",
            method = RequestMethod.PATCH
    )
    public String patchResource() {

        return "Resource partially updated successfully!";
    }
}