import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootWar
import org.springframework.boot.gradle.tasks.bundling.BootJar

/**
 * @author: ourfor <https://blog.ourfor.top>
 * @date: Feb 13, 2020
 */

repositories {
	jcenter()
}

plugins {
	val kotlinVersion = "1.3.61"

	java
	war
    application
	kotlin("jvm") version kotlinVersion
	kotlin("plugin.allopen") version kotlinVersion
	// kotlin("plugin.jpa") version kotlinVersion
	kotlin("plugin.spring") version kotlinVersion
	id("org.springframework.boot") version "2.2.3.RELEASE"
	id("io.spring.dependency-management") version "1.0.8.RELEASE"
}

apply {
	plugin("war")
	plugin("java")
	plugin("idea")
	plugin("kotlin")
	plugin("org.springframework.boot")
	plugin("io.spring.dependency-management")
}

allprojects {
	ext {
		set("spring.version","5.2.1.RELEASE")
	}
}

buildscript {
    var kotlinVersion = "1.3.61"
    repositories {
		mavenCentral()
		jcenter()
	}
	dependencies {
		classpath(kotlin("gradle-plugin", version = "1.3.61"))
		classpath("org.springframework.boot:spring-boot-gradle-plugin:2.2.3.RELEASE")
	}
}



the<DependencyManagementExtension>().apply {
	imports {
		mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
	}
}

repositories {
	jcenter()
	mavenCentral()
}

dependencies {
	implementation(kotlin("stdlib-jdk8"))
	implementation(kotlin("reflect"))
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")

	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-test")
    // implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-log4j2")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")


	runtimeOnly("com.microsoft.sqlserver:mssql-jdbc")
	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

configurations {
	all {
		// 排除默认日志, 换做Log4j2
		exclude("org.springframework.boot","spring-boot-starter-logging")
	}
}

allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.Embeddable")
	annotation("javax.persistence.MappedSuperclass")
}

java {
	sourceCompatibility = JavaVersion.VERSION_1_8
	targetCompatibility = JavaVersion.VERSION_1_8
}


application {
	mainClassName = "server.MainKt"
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

open class GreetingTask : DefaultTask() {
	@get: Input
	var greeting = "Hi, the jar and war will generate"

	@TaskAction
	fun greet() {
		println(greeting)
	}
}


val taskPackage by tasks.register<GreetingTask>("package") {
	dependsOn("bootJar")
	dependsOn("bootWar")

}


val bootJar: BootJar by tasks
bootJar.archiveName = "pocket.jar"

val bootWar: BootWar by tasks
bootWar.archiveName = "pocket.war"


tasks.getByName<BootJar>("bootJar") {
	classifier = "boot"
	mainClassName = "server.MainKt"
	manifest {
		attributes("Start-Class" to "server.MainKt")
		attributes("Main-Class" to "org.springframework.boot.loader.PropertiesLauncher")
	}
	launchScript()
}


tasks.getByName<BootWar>("bootWar") {
	classifier = "boot"
	mainClassName = "server.MainKt"
	manifest {
		attributes("Start-Class" to "server.MainKt")
		attributes("Main-Class" to "org.springframework.boot.loader.PropertiesLauncher")
	}
}

