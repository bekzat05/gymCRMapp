package com.bekzat.gym;

import com.bekzat.gym.config.AppConfig;
import com.bekzat.gym.model.Trainee;
import com.bekzat.gym.model.Trainer;
import com.bekzat.gym.model.Training;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.File;

@Slf4j
public class Main {
    public static void main(String[] args) throws LifecycleException {
        log.info("Starting Tomcat server...");
        Tomcat tomcat = new Tomcat();

        tomcat.setPort(Integer.parseInt(System.getProperty("server.port", "8081")));
        Context context = tomcat.addWebapp("", new File("src/main/webapp").getAbsolutePath());
        tomcat.getConnector();

        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(AppConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(appContext);
        Tomcat.addServlet(context, "dispatcherServlet", dispatcherServlet).setLoadOnStartup(1);
        context.addServletMappingDecoded("/*", "dispatcherServlet");

        tomcat.start();
        tomcat.getServer().await();
    }
}
