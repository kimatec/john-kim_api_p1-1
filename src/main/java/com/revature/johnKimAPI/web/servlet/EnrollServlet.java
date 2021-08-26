package com.revature.johnKimAPI.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.johnKimAPI.pojos.Course;
import com.revature.johnKimAPI.pojos.Enrolled;
import com.revature.johnKimAPI.pojos.Student;
import com.revature.johnKimAPI.pojos.StudentPrincipal;
import com.revature.johnKimAPI.service.ValidationService;
import com.revature.johnKimAPI.util.exceptions.InvalidRequestException;
import com.revature.johnKimAPI.util.exceptions.ResourceNotFoundException;
import com.revature.johnKimAPI.util.exceptions.ResourcePersistenceException;
import com.revature.johnKimAPI.web.dtos.ErrorResponse;
import com.revature.johnKimAPI.web.dtos.Principal;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * The purpose of the CourseServlet is to interact with Faculty-created courses, allowing queries to choose how they
 * are Read, Created, Updated, or Deleted.
 */

public class EnrollServlet extends HttpServlet {

    private final ValidationService userService;
    private final ObjectMapper mapper;

    public EnrollServlet(ValidationService userService, ObjectMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");


        // Establish query parameters
        String opening = req.getParameter("open");
        String enrolledCourse = req.getParameter("enrolled");

        // Attempt to fulfill user request.
        try {
            if(!(opening == null)) {
                List<Course> openCourses = userService.getOpenClasses();
                respWriter.write(mapper.writeValueAsString(openCourses));

            } else if(!(enrolledCourse == null)) {
                List<Enrolled> enrolled = userService.getMyCourses();
                respWriter.write(mapper.writeValueAsString(enrolled));

            }

        } catch (ResourceNotFoundException rnfe) {
            resp.setStatus(404);
            ErrorResponse errResp = new ErrorResponse(404, "No course found!");
            respWriter.write(mapper.writeValueAsString(errResp));
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            ErrorResponse errResp = new ErrorResponse(500, e.getMessage());
            respWriter.write(mapper.writeValueAsString(errResp));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");


        String cancel = req.getParameter("cancel");
        String register = req.getParameter("register");

        try {
            Enrolled enrolled = mapper.readValue(req.getInputStream(), Enrolled.class);


            if(!(cancel == null)) {

                resp.getWriter().write("Canceling  " + enrolled.getUsername() + "...");
                userService.deregister(enrolled.getUsername());

                resp.getWriter().write("Course successfully canceled!");

            } else if(!(register == null)){

                Course registered = new Course(enrolled);

                Course registerCourse = userService.enroll(registered);

                resp.getWriter().write("Course successfully registered!" + registerCourse);
            }

        } catch(InvalidRequestException | MismatchedInputException e) {
            e.printStackTrace();
            resp.setStatus(400);
            ErrorResponse errResp = new ErrorResponse(400, e.getMessage());
            respWriter.write(mapper.writeValueAsString(errResp));
        } catch(ResourcePersistenceException rpe) {
            resp.setStatus(409);
            ErrorResponse errResp = new ErrorResponse(409, rpe.getMessage());
            respWriter.write(mapper.writeValueAsString(errResp));
        } catch(Exception e) { // For some reason, the execution failed.
            e.printStackTrace();
            resp.setStatus(500);
        }
    }
}
