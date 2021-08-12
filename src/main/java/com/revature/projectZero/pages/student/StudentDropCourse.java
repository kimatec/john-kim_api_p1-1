package com.revature.projectZero.pages.student;

import com.revature.projectZero.pages.Page;
import com.revature.projectZero.service.ValidationService;
import com.revature.projectZero.util.PageRouter;

import java.io.BufferedReader;

public class StudentDropCourse extends Page {

    ValidationService checker;

    public StudentDropCourse(BufferedReader reader, PageRouter router, ValidationService checker) {
        super("MyClasses", "/dropcourse", reader, router);
        this.checker = checker;
    }

    @Override
    public void render() throws Exception {

        System.out.print("\nWould you like to drop a course?"
                + "\nY/N: ");

        String input = reader.readLine();
        if(input.equals("n") || input.equals("N")) {
            router.navigate("/s_dashboard");
        }

        System.out.print("\nPlease enter the ID/call-sign of the class you would like to delete (A good example of this is ENG101)"
                + "\n> ");
        String id = reader.readLine();

        boolean success = checker.deregister(id);

        // This is a pass/fail marker.
        if (success) {
            System.out.println("You have successfully dropped the class!");
        } else {
            System.out.println("We could not find that class!");
        }

        // Query the user for their intention.
        System.out.print("\nWould you like to drop another course?"
                + "\nY/N: ");
        input = reader.readLine();

        if (input.equals("n")||input.equals("N")) {
            System.out.println("Sending you back to the Dashboard...");
            router.navigate("/s_dashboard");
        }
    }
}
