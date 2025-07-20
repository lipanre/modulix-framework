import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    id("java-library")
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.lombok)
    alias(libs.plugins.spring.boot.dependencies)
}



dependencies {
    api(platform(libs.mybatis.plus.bom))

    api(project(":modulix-framework-common"))
    api(project(":modulix-framework-mybatis-plus:modulix-framework-mybatis-plus-api"))
    api(project(":modulix-framework-web:modulix-framework-web-api"))
    compileOnly(project(":modulix-framework-security:modulix-framework-security-api"))

    implementation(libs.jakarta.servlet.api)
    implementation(libs.jackson.databind)
    implementation(libs.spring.web)
    implementation(libs.jakarta.annotation.api)
    implementation(libs.mybatis.plus.starter)
    implementation(libs.mybatis.plus.jsqlparser)
    implementation(libs.mybatis.plus.extension)
    implementation(libs.spring.boot.starter.aop)

}

