package com.revature.projectZero.pages.student;

import com.revature.projectZero.pages.Page;
import com.revature.projectZero.pojos.Enrolled;
import com.revature.projectZero.service.ValidationService;
import com.revature.projectZero.util.PageRouter;

import java.io.BufferedReader;
import java.util.List;

public class StudentMyClasses  extends Page {

    ValidationService checker;

    public StudentMyClasses(BufferedReader reader, PageRouter router, ValidationService checker) {
        super("MyClasses", "/myclasses", reader, router);
        this.checker = checker;
    }

    @Override
    public void render() throws Exception {
        List<Enrolled> courses = checker.getMyCourses();

        if(courses != null) {
            for (Enrolled cours : courses) {
                System.out.println("\nName: " + cours.getName()
                        + "\nID/Call-Sign: " + cours.getClassID()
                        + "\nDescription: " + cours.getDesc()
                        + "\nTeacher: " + cours.getTeacher());
            }
        } else {
            System.out.println("You have no courses!");
        }

        System.out.print("\nWhat would you like to do?"
                + "\n1) Enroll in a class"
                + "\n2) Drop a class"
                + "\n3) Go back to the dashboard"
                + "\n> ");
        String input = reader.readLine();

        switch(input) {
            case "1":
            case "enroll":
            case "Enroll":
                System.out.println("Sending you to Course Enrollment services...");
                router.navigate("/enrollment");
                break;
            case "2":
            case "drop":
            case "Drop":
                System.out.println("Sending you to Course Drop services...");
                router.navigate("/dropcourse");
                break;
            case "3":
            case "back":
            case "Back":
            default:
                System.out.println("Sending you back to the Dashboard...");
                router.navigate("/s_dashboard");
        }
    }
}
