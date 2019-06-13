import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.1.5.RELEASE"
    id("io.spring.dependency-management") version "1.0.7.RELEASE"
    kotlin("jvm") version "1.2.71"
    kotlin("plugin.spring") version "1.2.71"
}

group = "fr.slickteam"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

val versionSpringBoot = "2.1.5.RELEASE"
val versionKeycloak = "6.0.1"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:$versionSpringBoot")
    implementation("org.springframework.boot:spring-boot-starter-security:$versionSpringBoot")

    implementation("org.keycloak:keycloak-spring-boot-starter:$versionKeycloak")
    implementation("org.keycloak.bom:keycloak-adapter-bom:$versionKeycloak")
//    implementation("org.keycloak:keycloak-spring-boot-adapter:$versionKeycloak")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("org.springframework.boot:spring-boot-starter-test:$versionSpringBoot")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
