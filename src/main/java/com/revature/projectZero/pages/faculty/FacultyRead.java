package com.revature.projectZero.pages.faculty;

import com.revature.projectZero.pages.Page;
import com.revature.projectZero.pojos.Course;
import com.revature.projectZero.service.ValidationService;
import com.revature.projectZero.util.PageRouter;

import java.io.BufferedReader;
import java.util.List;

public class FacultyRead extends Page {

    private final ValidationService checker;

    public FacultyRead(BufferedReader reader, PageRouter router, ValidationService checker) {
        super("FacultyRead", "/facRead", reader, router);
        this.checker = checker;
    }

    @Override
    public void render() throws Exception {

        List<Course> courses = checker.getTeacherClasses();

        if(courses != null) {
            for (Course course : courses) {
                System.out.println("\nName: " + course.getName()
                        + "\nID/Call-Sign: " + course.getClassID()
                        + "\nDescription: " + course.getDesc()
                        + "\nTeacher: " + course.getTeacher()
                        + "\nOpen: " + course.isOpen());
            }
        } else {
            System.out.println("You have no courses!");
        }

        // Everything before this is skipped.
        System.out.print("\nWhat would you like to do?"
                    + "\n1) Delete a class"
                    + "\n2) Update a class"
                    + "\n3) Go back to the dashboard"
                    + "\n> ");
        String input = reader.readLine();

        switch(input) {
            case "1":
            case "delete":
            case "Delete":
                System.out.println("Sending you to Course Deletion services...");
                router.navigate("/facDelete");
                break;
            case "2":
            case "update":
            case "Update":
                System.out.println("Sending you to Course Update services...");
                router.navigate("/facUpdate");
                break;
            case "3":
            case "back":
            case "Back":
            default:
                System.out.println("Sending you back to the Dashboard...");
                router.navigate("/f_dashboard");
        }
    }
}
