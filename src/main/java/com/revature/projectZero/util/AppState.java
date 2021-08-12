package com.revature.projectZero.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.revature.projectZero.pages.*;
import com.revature.projectZero.pages.faculty.*;
import com.revature.projectZero.pages.student.*;
import com.revature.projectZero.repositories.SchoolRepository;
import com.revature.projectZero.service.ValidationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The beating heart of the application. So long as appRunning is true,
 * the application will continue trying to render. This is where the Graceful shutdown occurs.
 */

public class AppState {
    // Set up the appRunning boolean for closing the app, and the router, linked to the PageRouter class.
    private static boolean appRunning = true;
    private final PageRouter router;

    // This should be the only instantiation needed of a Buffered Reader, which will be injected to each Page.
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    // This is AppState, the constructor for this class.
    public AppState() {
        router = new PageRouter();

        // make the app components (certain dependencies to get injected)
        SchoolRepository groupRepo = new SchoolRepository();
        ValidationService checker = new ValidationService(groupRepo);

        // This is a list of all pages that are added to the pageset.
        // Use the addPage method and give it the necessary features.
        router.addPage(new WelcomePage(reader, router, this))
                .addPage(new LoginPage(reader, router, checker))
                .addPage(new StudentRegisterPage(reader, router, checker))
                .addPage(new StudentDashboard(reader, router, checker))
                .addPage(new StudentMyClasses(reader, router, checker))
                .addPage(new StudentEnrollment(reader, router, checker))
                .addPage(new StudentDropCourse(reader, router, checker))
                .addPage(new FacultyDashboard(reader, router, checker))
                .addPage(new FacultyCreate(reader, router, checker))
                .addPage(new FacultyRead(reader, router, checker))
                .addPage(new FacultyUpdate(reader, router, checker))
                .addPage(new FacultyDelete(reader, router, checker));
    }

    Logger logger = LogManager.getLogger(AppState.class);
    public void startApp() {
        router.navigate("/welcome");



        while(appRunning) {
            try {
                router.getCurrentPage().render();
            } catch (Exception e) {
                logger.error("Page not found. Stack trace follows: ", e);
            }
        }
    }

    // the application shutdown method, safely (and effectively) closes the app.
    public static void closeApp() { appRunning = false; }
}
