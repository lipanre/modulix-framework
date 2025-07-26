plugins {
    id("java-library")
    alias(libs.plugins.lombok)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.boot.dependencies)
}

dependencies {
    api(project(":modulix-framework-common"))
    api(project(":modulix-framework-web:modulix-framework-web-api"))
    api(project(":modulix-framework-security:modulix-framework-security-api"))

    api(libs.jjwt)
    api(libs.nanoid)
    api(libs.spring.boot.starter.security)
    api(libs.spring.boot.starter.validation)
    api(libs.jakarta.servlet.api)
    annotationProcessor(libs.spring.boot.configuration.processor)
}
