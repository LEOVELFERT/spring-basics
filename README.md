# Spring Basics - Spring Boot Autoconfiguration Guide

This project demonstrates the core concepts of **Spring Boot Autoconfiguration**, showcasing how Spring Boot automatically configures beans and components based on classpath conditions and annotations.

## 📋 Table of Contents

- [Project Overview](#project-overview)
- [What is Spring Boot Autoconfiguration?](#what-is-spring-boot-autoconfiguration)
- [Project Structure](#project-structure)
- [Key Concepts](#key-concepts)
- [How This Project Works](#how-this-project-works)
- [Running the Application](#running-the-application)
- [Configuration Files](#configuration-files)
- [Dependencies](#dependencies)
- [Understanding the Code](#understanding-the-code)

---

## 🎯 Project Overview

**Spring Basics** is a learning project that demonstrates Spring Boot's autoconfiguration mechanism. It showcases how to:

- Use `@AutoConfiguration` to define auto-configurable components
- Apply conditional configuration with `@ConditionalOnClass`
- Automatically register beans without explicit XML configuration
- Use the Spring Boot configuration import mechanism

The example uses an **AI Course** system where course details are automatically configured and displayed when the application starts.

---

## 🚀 What is Spring Boot Autoconfiguration?

### Concept

Spring Boot autoconfiguration is a powerful feature that automatically configures Spring applications based on:
1. **Jar dependencies** present on the classpath
2. **Annotations** used in your code
3. **Properties** defined in configuration files
4. **Conditional logic** that determines when configurations should apply

### How It Works

1. Spring Boot scans the classpath for `AutoConfiguration` classes
2. It evaluates conditional annotations (e.g., `@ConditionalOnClass`, `@ConditionalOnProperty`)
3. If conditions are met, it automatically registers the beans
4. This eliminates boilerplate configuration code

### Benefits

✅ **Reduced Boilerplate** - No need for explicit bean definitions  
✅ **Convention Over Configuration** - Sensible defaults out of the box  
✅ **Flexibility** - Easy to override defaults through properties  
✅ **Modularity** - External libraries can provide their own auto-configurations  

---

## 📁 Project Structure

```
spring-basics/
├── src/
│   ├── main/
│   │   ├── java/com/spring/spring_basics/
│   │   │   ├── SpringBasicsApplication.java       # Main application entry point
│   │   │   ├── config/
│   │   │   │   └── CourseConfig.java              # Auto-configuration class
│   │   │   ├── course/
│   │   │   │   └── AiCourse.java                  # Business logic class (bean)
│   │   │   └── dto/
│   │   │       └── Student.java                   # DTO (conditional trigger)
│   │   └── resources/
│   │       ├── application.properties             # Application configuration
│   │       └── META-INF/spring/
│   │           └── org.springframework.boot.autoconfigure.AutoConfiguration.imports
│   └── test/
│       └── java/com/spring/spring_basics/
│           └── SpringBasicsApplicationTests.java
├── pom.xml                                        # Maven configuration
└── README.md                                      # This file
```

---

## 🔑 Key Concepts

### 1. @SpringBootApplication

```java
@SpringBootApplication
public class SpringBasicsApplication implements CommandLineRunner {
    // ...
}
```

**What it does:**
- Meta-annotation that combines `@Configuration`, `@ComponentScan`, and `@EnableAutoConfiguration`
- Marks the main entry point of the application
- Enables component scanning in the package and subpackages
- Triggers Spring Boot's autoconfiguration mechanism

### 2. @AutoConfiguration

```java
@AutoConfiguration
public class CourseConfig {
    // ...
}
```

**What it does:**
- Marks a class as a candidate for auto-configuration
- Must be registered in the `AutoConfiguration.imports` file
- Configurations are processed in a specific order (can be controlled with `@AutoConfigureBefore`, `@AutoConfigureAfter`)

### 3. @ConditionalOnClass

```java
@ConditionalOnClass(Student.class)
@AutoConfiguration
public class CourseConfig {
    // ...
}
```

**What it does:**
- The configuration is only applied if the specified class (Student.class) is present on the classpath
- Prevents `ClassNotFoundException` if dependencies are missing
- Allows conditional bean registration

**Other Conditional Annotations:**
- `@ConditionalOnBean` - If a specific bean exists
- `@ConditionalOnProperty` - If a property has a specific value
- `@ConditionalOnClass` - If a class is on the classpath
- `@ConditionalOnMissingClass` - If a class is NOT on the classpath
- `@ConditionalOnWebApplication` - If running in a web environment

### 4. @Bean

```java
@Bean
AiCourse aiCourse() {
    return new AiCourse();
}
```

**What it does:**
- Marks a method that returns an object to be registered as a Spring bean
- The returned object is managed by the Spring container
- Method name becomes the bean name (or use `@Bean(name="customName")`)

---

## 💡 How This Project Works

### Step-by-Step Execution Flow

1. **Application Start**
   ```
   SpringApplication.run(SpringBasicsApplication.class, args)
   ```

2. **Classpath Scanning**
   - Spring Boot scans for autoconfiguration classes
   - Finds `CourseConfig` registered in `AutoConfiguration.imports`

3. **Conditional Check**
   - Evaluates `@ConditionalOnClass(Student.class)`
   - Student.class is on the classpath ✓
   - Condition is satisfied

4. **Bean Creation**
   - `CourseConfig` configuration is activated
   - `aiCourse()` bean method is invoked
   - `AiCourse` instance is created and registered in the Spring container

5. **CommandLineRunner Execution**
   - Application implements `CommandLineRunner`
   - `run()` method is called after context is initialized
   - Retrieves `AiCourse` bean from the context
   - Calls `aiCourseDetails()` to display course information

6. **Output**
   ```
   ======================================
   Ai Key Compoenents
   Prompt Engineering
   =======================================
   Ai thing auto configuration
   ```

---

## 🏃 Running the Application

### Prerequisites

- **Java 25** or higher (as specified in pom.xml)
- **Maven 3.6+** or use the provided `mvnw`/`mvnw.cmd`

### Build and Run

```bash
# Using Maven wrapper (Windows)
mvnw.cmd clean install
mvnw.cmd spring-boot:run

# Or using Maven directly
mvn clean install
mvn spring-boot:run

# Or build JAR and run
mvnw.cmd clean package
java -jar target/spring-basics-0.0.1-SNAPSHOT.jar
```

### Expected Output

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v4.1.0)

2026-06-26 12:00:00.000  INFO 1234 --- [           main] ...
======================================
Ai Key Compoenents
Prompt Engineering
=======================================
Ai thing auto configuration
```

---

## ⚙️ Configuration Files

### 1. pom.xml - Maven Dependencies

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webmvc</artifactId>
</dependency>
```

**Key Dependencies:**
- `spring-boot-starter-webmvc` - Web MVC starter (includes core, logging, embedded Tomcat)
- `lombok` - Code generation (optional, marked for exclusion in plugin)

### 2. AutoConfiguration.imports

Location: `src/main/resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`

This file registers all auto-configuration classes:

```
com.spring.spring_basics.config.CourseConfig
```

**Why is this needed?**
- Spring Boot needs to know which classes are auto-configurations
- Without this file, `@AutoConfiguration` annotations are ignored
- This is Spring Boot 3.0+ format (replaces old `spring.factories` file)

### 3. application.properties

```properties
spring.application.name=spring-basics
```

**Common Properties:**
- `spring.application.name` - Application name for logging
- `server.port` - Port for embedded server (default: 8080)
- `logging.level.*` - Log levels for packages
- `spring.mvc.static-path-pattern` - Static resources path

---

## 📦 Dependencies

| Dependency | Version | Purpose |
|------------|---------|---------|
| spring-boot-starter-parent | 4.1.0 | Parent POM with Spring Boot 4.1.0 |
| spring-boot-starter-webmvc | 4.1.0 | Web MVC autoconfiguration |
| spring-boot-starter-webmvc-test | 4.1.0 | Testing support |
| lombok | Latest | Code generation (optional) |
| Java | 25 | Language version |

---

## 🔍 Understanding the Code

### CourseConfig.java - The Heart of Autoconfiguration

```java
@ConditionalOnClass(Student.class)      // Only if Student.class exists
@AutoConfiguration                       // This is an autoconfiguration
public class CourseConfig {
    
    @Bean                                // Define a bean
    AiCourse aiCourse() {
        return new AiCourse();
    }
}
```

**Key Points:**
1. The class will only be processed if `Student.class` is available
2. Without `@AutoConfiguration`, Spring wouldn't load this class
3. Without `AutoConfiguration.imports` entry, even `@AutoConfiguration` wouldn't work
4. The `@Bean` method produces the actual bean instance

### SpringBasicsApplication.java - Entry Point

```java
@SpringBootApplication
public class SpringBasicsApplication implements CommandLineRunner {
    
    @Autowired
    WebApplicationContext webApplicationContext;
    
    @Override
    public void run(String... args) throws Exception {
        AiCourse aiCourse = webApplicationContext.getBean(AiCourse.class);
        aiCourse.aiCourseDetails();
    }
}
```

**Key Points:**
1. `@SpringBootApplication` enables autoconfiguration scanning
2. `CommandLineRunner` interface ensures code runs after application startup
3. `WebApplicationContext` is injected (auto-wired) by Spring
4. The `AiCourse` bean created by `CourseConfig` is retrieved and used

---

## 🎓 Learning Outcomes

After studying this project, you should understand:

✅ What Spring Boot autoconfiguration is and why it's useful  
✅ How `@AutoConfiguration` works  
✅ The role of `AutoConfiguration.imports` file  
✅ Conditional bean registration with `@ConditionalOnClass`  
✅ Bean creation with `@Bean` annotation  
✅ How Spring Boot discovers and registers beans  
✅ The `CommandLineRunner` pattern for startup logic  
✅ Best practices for creating reusable auto-configurations  

---

## 📚 Additional Resources

- [Spring Boot Autoconfiguration Documentation](https://spring.io/guides/gs/spring-boot/)
- [Spring Boot Reference Guide - Autoconfiguration](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.developing-auto-configuration)
- [Conditional Annotations](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.developing-auto-configuration.condition-annotations)

---

## 🚀 Next Steps

To extend this project:

1. **Add More Beans** - Create additional auto-configurable beans
2. **Use Properties** - Add `@ConditionalOnProperty` conditions
3. **Add Configuration Properties** - Use `@ConfigurationProperties`
4. **Create Multiple Conditions** - Combine multiple conditional annotations
5. **Build Custom Starter** - Package as a reusable Spring Boot starter library

---

## 📝 Notes

- This project uses **Spring Boot 4.1.0** (latest as of creation date)
- Java version is set to **25** in pom.xml
- The `.gitignore` file properly excludes `target/` and IDE-specific files from Git

---

**Happy Learning! 🎯**

