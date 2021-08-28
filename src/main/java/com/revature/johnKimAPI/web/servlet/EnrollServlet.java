package com.revature.johnKimAPI.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.johnKimAPI.pojos.Course;
import com.revature.johnKimAPI.pojos.Enrolled;
import com.revature.johnKimAPI.pojos.EnrolledCourse;
import com.revature.johnKimAPI.service.ValidationService;
import com.revature.johnKimAPI.util.exceptions.InvalidRequestException;
import com.revature.johnKimAPI.util.exceptions.ResourceNotFoundException;
import com.revature.johnKimAPI.util.exceptions.ResourcePersistenceException;
import com.revature.johnKimAPI.web.dtos.ErrorResponse;
import com.revature.johnKimAPI.web.dtos.Principal;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


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

        // Get the session from the request
        //Principal requestingUser = (Principal)req.getAttribute("principal");

        String enrolledCourse = req.getParameter("enrolled");

        try {
            if(enrolledCourse != null) {
                Enrolled enrolled = userService.getMyCourses(enrolledCourse);
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
       // String register = req.getParameter("register");

        try {
            Enrolled enrolled = mapper.readValue(req.getInputStream(), Enrolled.class);

            if(cancel != null) {

                userService.deregister(enrolled.getClassID(), enrolled.getUsername());

                ErrorResponse errInfo = new ErrorResponse(200, "Course canceled!");
                resp.getWriter().write(mapper.writeValueAsString(errInfo));

            } else{

                EnrolledCourse newEnrolled = mapper.readValue(req.getInputStream(), EnrolledCourse.class);
                Enrolled newEnroll = new Enrolled(newEnrolled);

                Enrolled newCourses = userService.enroll(newEnroll);
                String payload = mapper.writeValueAsString(newCourses);
                resp.getWriter().write(payload);

                //ErrorResponse errInfo = new ErrorResponse(200, "Course registered!");
               // resp.getWriter().write(mapper.writeValueAsString(errInfo));
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
        } catch(Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
        }
    }
}
