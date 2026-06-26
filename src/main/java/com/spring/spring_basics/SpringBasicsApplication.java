package com.spring.spring_basics;

import com.spring.spring_basics.course.AiCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.context.WebApplicationContext;

@SpringBootApplication
public class SpringBasicsApplication implements CommandLineRunner {
    @Autowired
    WebApplicationContext webApplicationContext;

    public static void main(String[] args) {
        SpringApplication.run(SpringBasicsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        AiCourse aiCourse = webApplicationContext.getBean(AiCourse.class);
        aiCourse.aiCourseDetails();
    }
}
