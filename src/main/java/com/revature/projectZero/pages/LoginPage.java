package com.revature.projectZero.pages;

import com.revature.projectZero.pojos.Faculty;
import com.revature.projectZero.pojos.Student;
import com.revature.projectZero.service.ValidationService;
import com.revature.projectZero.util.PageRouter;
import java.io.BufferedReader;

public class LoginPage extends Page {

    public LoginPage(BufferedReader reader, PageRouter router, ValidationService checker){
        super("LoginPage", "/login", reader, router);
        this.checker = checker;
    }

    public final ValidationService checker;

    @Override
    public void render() throws Exception {
        String userInput;
        String username;
        String password;

        System.out.print("\nWelcome to the Login portal!\n"
                    + "\nAre you a Student, or a Faculty member?"
                    + "\n1) Student"
                    + "\n2) Faculty"
                    + "\n3) Register a new Student"
                    + "\n4) Go back to the Welcome page"
                    + "\n> ");

        userInput = reader.readLine();

        // This is a long switching statement that checks user input against a variety of potential inputs
        // for the best experience
        boolean isUserFaculty = false;
        switch(userInput) {
            case "1":
            case "Student":
            case "student":
                break;
            case "2":
            case "Faculty":
            case "faculty":
                isUserFaculty = true;
                break;
            case "3":
            case "Register":
            case "New Student":
            case "new":
                System.out.println("Great! Redirecting you to the Registry");
                router.navigate("/register");
                return;
            case "4":
            case "back":
            case "Back":
                System.out.println("Sending you back to the Welcome page...");
                router.navigate("/welcome");
                return;
            default:
                System.out.println("Sorry, we didn't understand that!");
                router.navigate("/login");
                return;
        }

        // Checks whether or not the user is Faculty or a Student.
        if(isUserFaculty) {
            System.out.println("Welcome to the Faculty log-in portal!");

            // This is a for loop that will track how many times a user attempts to log into a Faculty account.
            for(int i=0; i<3; i++){
                System.out.print("\n\nPlease enter your Username: ");
                username = reader.readLine();
                System.out.print("\nPlease enter your Password: ");
                password = reader.readLine();
                int hashPass = password.hashCode();

                // Attempts to check the user-input data against the Faculty database.
                Faculty authFac = checker.facLogin(username, hashPass);

                if(authFac != null){
                    System.out.println("Login successful! Welcome back, " + authFac.getFirstName() + "!");
                    router.navigate("/f_dashboard");
                    return;
                }
            }
            // This is the "game over" message that displays if you fail to validate as
            // a Faculty member three times.
            System.out.println("You have input an invalid username and password 3 times!\n"
                            + "If you are a Student, please press '2' when prompted!");
            router.navigate("/login");

        } else {
            // This is the student log-in portion.
            System.out.println("Welcome to the Student log-in portal!");
            boolean wantsToBeHere = true;
            int tracker = 0;
            String input;

            // This is a loop that will keep a student inside of this panel for as long as they feel necessary.
            while(wantsToBeHere) {
                for(int i = 0; i<5; i++){
                    System.out.print("\n\nPlease enter your Username: ");
                    username = reader.readLine();
                    System.out.print("\nPlease enter your Password: ");
                    password = reader.readLine();
                    int hashPass = password.hashCode();

                    // Attempts to check the user-input data against the Student database.
                    Student authStudent = checker.login(username, hashPass);

                    if (authStudent != null) {
                        System.out.println("Login Successful! Welcome back, " + authStudent.getFirstName() + "!");
                        router.navigate("/s_dashboard");
                        return;
                    }

                    // This is a simple counter, added as a sort of sobriety test for
                    // the Student's wishes. It is also a way to get out of logging in, rather
                    // than being stuck in a recursive loop.
                    tracker++;
                }
                System.out.println("You have failed " + tracker
                        + " times. Are you sure you want to try again?\n"
                        + "Y/N: ");
                input = reader.readLine();
                // checks user input and learns whether or not the user wishes to be on this login screen.
                if(input.equals("n") || input.equals("N")){
                    wantsToBeHere = false;
                }
            }
        }
    }
}