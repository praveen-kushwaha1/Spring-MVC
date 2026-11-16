package com.springmvc.p02_request_handling;

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
