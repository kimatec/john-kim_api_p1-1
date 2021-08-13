package com.revature.projectZero.web.util;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.revature.projectZero.repositories.SchoolRepository;
import com.revature.projectZero.service.ValidationService;
import com.revature.projectZero.util.GetMongoClient;
import com.revature.projectZero.web.servlet.UserServlet;
// TODO: Find out what this is!
import com.sun.xml.internal.ws.developer.MemberSubmissionAddressing;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

public class ContextLoaderListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        MongoClient mongoClient = GetMongoClient.generate().getConnection();
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

        SchoolRepository userRepo = new SchoolRepository(mongoClient);
        ValidationService userService = new ValidationService(userRepo);

        // TODO: Add all of your servlets to here!
//        UserServlet userServlet = new UserServlet(userService, mapper);
//        AuthServlet authServlet = new AuthServlet(userService, mapper);
//
        ServletContext servletContext = sce.getServletContext();
//        servletContext.addServlet("UserServlet", userServlet).addMapping("/users/*");
//        servletContext.addServlet("AuthServlet", authServlet).addMapping("/auth");

        configureLogback(servletContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        GetMongoClient.generate().cleanUp();
    }

    private void configureLogback(ServletContext servletContext) {

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator logbackConfig = new JoranConfigurator();
        logbackConfig.setContext(loggerContext);
        loggerContext.reset();

        String logbackConfigFilePath = servletContext.getRealPath("") + File.separator + servletContext.getInitParameter("logback-config");

        try {
            logbackConfig.doConfigure(logbackConfigFilePath);
        } catch (JoranException e) {
            e.printStackTrace();
            System.out.println("An unexpected exception occurred. Unable to configure Logback.");
        }

    }

}