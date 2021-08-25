package com.revature.johnKimAPI.web.dtos;

import com.revature.johnKimAPI.pojos.Course;
import com.revature.johnKimAPI.pojos.Faculty;
import com.revature.johnKimAPI.pojos.Student;
import io.jsonwebtoken.Claims;

import java.util.Objects;

public class Principal {

    private String id;
    private String username;
    private String lastName;

    public Principal() { super(); }

    public Principal(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public Principal(Student student) {
        this.id = student.getStudentID();
        this.username = student.getUsername();
    }

    public Principal(Faculty faculty) {
        this.id = faculty.getTeacherID();
        this.username = faculty.getUsername();
        this.lastName = faculty.getLastName();
    }

    public Principal(Claims jwtClaims) {
        this.id = jwtClaims.getId();
        this.username = jwtClaims.getSubject();
    }

    public Principal(Course course){
        this.id = course.getClassID();
        this.username = course.getName();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Principal principal = (Principal) o;
        return Objects.equals(id, principal.id) && Objects.equals(username, principal.username) && Objects.equals(lastName, principal.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, lastName);
    }

    @Override
    public String toString() {
        return "Principal{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
