package com.revature.projectZero.pages.student;

import com.revature.projectZero.pages.Page;
import com.revature.projectZero.pojos.Course;
import com.revature.projectZero.service.ValidationService;
import com.revature.projectZero.util.PageRouter;

import java.io.BufferedReader;
import java.util.List;

public class StudentEnrollment extends Page {

    ValidationService checker;

    public StudentEnrollment(BufferedReader reader, PageRouter router, ValidationService checker) {
        super("StudentDashboard", "/enrollment", reader, router);
        this.checker = checker;
    }

    @Override
    public void render() throws Exception {

        System.out.println("The current existing classes are:");
        List<Course> courses = checker.getOpenClasses();

        if(courses != null) {
            for (Course cours : courses) {
                System.out.println("\nName: " + cours.getName()
                        + "\nID/Call-Sign: " + cours.getClassID()
                        + "\nDescription: " + cours.getDesc()
                        + "\nTeacher: " + cours.getTeacher()
                        + "\nOpen: " + cours.isOpen());
            }
        } else {
            System.out.println("There are no open courses!");
        }

        System.out.print("\nWould you like to enroll in an existing class?"
                + "\nY/N: ");
        String input = reader.readLine();

        if(input.equals("n") || input.equals("N")) {
            router.navigate("/s_dashboard");
            return;
        }

        System.out.print("\nPlease input the call-sign/ID of the class you would like to Enroll in (e.g. ENG101)"
                + "\n> ");
        String id = reader.readLine();

        Course thisCourse = checker.getCourseByID(id);

        if(thisCourse != null) {
            System.out.println("Course found!"
                    + "\nName: " + thisCourse.getName()
                    + "\nID/Call-Sign: " + thisCourse.getClassID()
                    + "\nDescription: " + thisCourse.getDesc()
                    + "\nTeacher: " + thisCourse.getTeacher()
                    + "\nOpen: " + thisCourse.isOpen());
        } else {
            System.out.println("Sorry! We could not find a course with that ID!");
            System.out.println("Sending you back to the dashboard...");
            router.navigate("/s_dashboard");
            return;
        }

        System.out.println("Would you like to enroll in this course?");
        System.out.print("\nY/N: ");
        input = reader.readLine();
        if(input.equals("n")||input.equals("N")) {
            router.navigate("/s_dashboard");
        }

        checker.enroll(thisCourse);

        System.out.println("You have successfully been enrolled into " + thisCourse.getClassID() + "!");
        System.out.println("Sending you back to the dashboard...");
        router.navigate("/s_dashboard");
    }
}
