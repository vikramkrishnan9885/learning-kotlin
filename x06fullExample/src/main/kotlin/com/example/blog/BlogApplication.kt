package com.example.blog

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

// @SpringBootApplication is a convenience annotation that adds all of the following:
//
// @Configuration: Tags the class as a source of bean definitions for the application context.
// @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath settings,
// other beans, and various property settings. For example, if spring-webmvc is on the classpath,
// this annotation flags the application as a web application and activates key behaviors,
// such as setting up a DispatcherServlet.
// @ComponentScan: Tells Spring to look for other components, configurations,
// and services in the com/example package, letting it find the controllers.
//
@SpringBootApplication
// Spring Boot offers many different options to pass parameters like these into an application.
// In this article, we choose to create an application.properties file with the parameters we need:
//
//	myapp.mail.enabled=true
//	myapp.mail.default-subject=This is a Test
//
// Within our application, we could now access the values of these properties by
// asking Spring’s Environment bean or by using the @Value annotation, among other things.
//
// However, there’s a more convenient and safer way to access those properties by creating a
// class annotated with @ConfigurationProperties:
//
// @ConfigurationProperties(prefix = "myapp.mail")
//
// The basic usage of @ConfigurationProperties is pretty straightforward:
// we provide a class with fields for each of the external properties we want to capture.
//
// Note the following:
// * The prefix defines which external properties will be bound to the fields of the class.
// * The classes’ property names must match the names of the external properties according to
// Spring Boot’s relaxed binding rules.
// * We can define a default values by simply initializing a field with a value.
// * The class itself can be package private.
// * The classes’ fields must have public setters.
//
// If we inject a bean of type MailModuleProperties into an other bean,
// this bean can now access the values of those external configuration parameters in a type-safe manner.
//
// However, we still have to make our @ConfigurationProperties class known to Spring so it will be
// loaded into the application context.
//
// For Spring Boot to create a bean of the MailModuleProperties class,
// we need to add it to the application context in one of several ways.
//
// First, we can simply let it be part of a component scan by adding the @Component annotation:
// @Component
// @ConfigurationProperties(prefix = "myapp.mail")
// class MailModuleProperties {
//  // ...
// }
// This obviously only works if the class in within a package that is scanned
// for Spring’s stereotype annotations via @ComponentScan, which by default is any class in the package
// structure below the main application class.
//
//
// We can achieve the same result using Spring’s Java Configuration feature:
// @Configuration
// class MailModuleConfiguration {
//
//   @Bean
//   public MailModuleProperties mailModuleProperties(){
//     return new MailModuleProperties();
//   }
// }
// As long as the MailModuleConfiguration class is scanned by the Spring Boot application,
// we’ll have access to a MailModuleProperties bean in the application context.
//
//
// Alternatively, we can use the @EnableConfigurationProperties annotation to make our class known to Spring Boot:
// @Configuration
// @EnableConfigurationProperties(MailModuleProperties.class)
// class MailModuleConfiguration {
//
// }
//
//
// Which is the Best Way to activate a @ConfigurationProperties Class?
//
// All of the above ways are equally valid. I would suggest, however,
// to modularize your application and have each module provide its own @ConfigurationProperties class
// with only the properties it needs as we have done for the mail module in the code above.
// This makes it easy to refactor properties in one module without affecting other modules.
//
// For this reason, I would not recommend to use @EnableConfigurationProperties on the application class itself,
// as is shown in many other tutorials, but instead on a module-specific @Configuration class which might
// also make use of package-private visibility to hide the properties from the rest of the application.
//
@EnableConfigurationProperties(BlogProperties::class)
class BlogApplication

fun main(args: Array<String>) {
	runApplication<BlogApplication>(*args){
		setBannerMode(Banner.Mode.OFF)
	}
}
