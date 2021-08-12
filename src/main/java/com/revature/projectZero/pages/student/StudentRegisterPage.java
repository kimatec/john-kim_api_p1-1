package com.revature.projectZero.pages.student;

import com.revature.projectZero.pages.Page;
import com.revature.projectZero.pojos.Student;
import com.revature.projectZero.service.ValidationService;
import com.revature.projectZero.util.PageRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.BufferedReader;

public class StudentRegisterPage extends Page {

    public StudentRegisterPage(BufferedReader reader, PageRouter router, ValidationService checker) {
        super("RegisterPage", "/register", reader, router);
        this.checker = checker;
    }

    public final ValidationService checker;

    @Override
    public void render() throws Exception{
        Logger logger = LogManager.getLogger(StudentRegisterPage.class);

        // This is a simple check to see if the user actually intends to make a new student account.
        // Prior to this, one would have to make a null user, just to get an exception.
        System.out.print("\nWould you like to register for a new Student Account?"
                    + "\nY/N: ");
        String input = reader.readLine();

        if(input.equals("N") || input.equals("n")) {
            System.out.println("Directing you back to the Welcome page...");
            router.navigate("/welcome");
            return;
        }

        System.out.print("First name: ");
        String firstname = reader.readLine();

        System.out.print("\nLast name: ");
        String lastname = reader.readLine();

        System.out.print("\nEmail: ");
        String email = reader.readLine();
        while(!checker.isEmailUnique(email)) {
            System.out.print("\nSorry! That Email already belongs to an account!"
                        + "\nPlease enter another one."
                        + "\nEmail: ");
            email = reader.readLine();
        }

        // The 'is user unique' while loop functions nominally.
        System.out.print("\nUsername: ");
        String username = reader.readLine();
        while (!checker.isUserUnique(username)) {
            System.out.print("\nSorry! That username is taken. Please enter another one."
                        + "\nUsername: ");
            username = reader.readLine();
        }

        System.out.print("\nPassword: ");
        String password = reader.readLine();
        int hashPass = password.hashCode();

        Student newStudent = new Student(username, hashPass, firstname, lastname, email);


        try {
            checker.register(newStudent);
            logger.info("New user successfully registered! " + newStudent);
            System.out.println("Great! Sending you to the student dashboard...");
            checker.login(newStudent.getUsername(), newStudent.getHashPass());
            router.navigate("/s_dashboard");
        } catch(Exception e) {
            logger.error(e.getMessage());
            logger.debug("User not registered!");
            router.navigate("/welcome");
        }
    }
}
