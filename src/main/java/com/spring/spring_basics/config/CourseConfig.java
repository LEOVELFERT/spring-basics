package com.spring.spring_basics.config;

import com.spring.spring_basics.course.AiCourse;
import com.spring.spring_basics.dto.Student;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
@ConditionalOnClass(Student.class)
@AutoConfiguration
public class CourseConfig {

    @Bean
    AiCourse aiCourse() {
        return new AiCourse();
    }
}
