import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    id("java-library")
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.lombok)
    alias(libs.plugins.spring.boot.dependencies)
}


dependencies {
    api(libs.jackson.databind)
    compileOnly(libs.spring.expression)
}
