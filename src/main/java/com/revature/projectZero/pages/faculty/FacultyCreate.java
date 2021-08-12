package com.revature.projectZero.pages.faculty;

import com.revature.projectZero.pages.Page;
import com.revature.projectZero.pojos.Course;
import com.revature.projectZero.service.ValidationService;
import com.revature.projectZero.util.PageRouter;

import java.io.BufferedReader;

public class FacultyCreate extends Page {

    private final ValidationService checker;

    public FacultyCreate(BufferedReader reader, PageRouter router, ValidationService checker) {
        super("FacultyCreate", "/facCreate", reader, router);
        this.checker = checker;
    }

    @Override
    public void render() throws Exception {

        System.out.print("\nWould you like to create a new class?"
                    + "\nY/N: ");
        String input = reader.readLine();

        if(input.equals("n") || input.equals("N")) {
            router.navigate("/f_dashboard");
            return;
        }

        System.out.print("\nOkay. Please enter the details of the new class:"
                    + "\nWhat is the (full) name of the course?"
                    + "\n> ");
        String name = reader.readLine();

        System.out.print("\nWhat is the abbreviated name/call-sign? (A good example of this is ENG101)."
                    + "\n> ");
        String id = reader.readLine();

        System.out.print("\nPlease provide a short description of the course you are teaching."
                    + "\n> ");
        String desc = reader.readLine();

        boolean notReady = true;
        boolean isOpen = true;
        while(notReady) {
        System.out.print("\nLastly, would you like this class to be open, or closed?"
                    + "\n> ");
        input = reader.readLine();


            switch (input) {
                case "open":
                case "Open":
                    isOpen = true;
                    notReady = false;
                    break;
                case "closed":
                case "Closed":
                    isOpen = false;
                    notReady = false;
                    break;
                default:
                    System.out.println("Sorry, we didn't quite understand that.");
            }
        }

        Course newCourse = new Course(name, id, desc, null, isOpen);
        checker.createCourse(newCourse);
        System.out.println("Course creation was successful! Sending you back to the Faculty dashboard...");
        router.navigate("/f_dashboard");
    }
}
