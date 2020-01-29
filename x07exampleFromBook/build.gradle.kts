import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.2.4.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm") version "1.3.61"
	kotlin("plugin.spring") version "1.3.61"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

//
// Spring Context will provide our application with the following:
// * Core support for dependency injection Core support for transaction management Core support for web applications
// * Core support for data access
// * Core support for messaging
// * Core support for testing
// * Miscellaneous
//
//Spring AOP will provide us with support for aspect-oriented programming.
//
// Spring Actuator is a sub-project of Spring Boot.
// It adds several important services to your Spring application.
// When the Actuator is configured in your Spring Boot application,
// you can perform interaction and monitor your application behavior by executing misc
// HTTP endpoints exposed by Spring Boot Actuator.
// Actuator offers the following out-of-the-box endpoints:
// * Application health
// * Bean details
// * Version details
// * Configurations
// * Logger details, and many more
//

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	implementation("org.springframework:spring-context")
	implementation("org.springframework:spring-aop")
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework:spring-web")
	implementation("org.springframework:spring-webmvc")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}
