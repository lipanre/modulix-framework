plugins {
    id("java-library")
    alias(libs.plugins.lombok)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.boot.dependencies)
}

dependencies {
    api(project(":modulix-framework-web:modulix-framework-web-api"))
    api(project(":modulix-framework-common"))

    api(libs.spring.boot.starter.web)
}
