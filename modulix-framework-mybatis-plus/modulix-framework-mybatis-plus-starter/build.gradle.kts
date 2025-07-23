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

    api(libs.jakarta.servlet.api)
    api(libs.jackson.databind)
    api(libs.spring.web)
    api(libs.jakarta.annotation.api)
    api(libs.mybatis.plus.starter)
    api(libs.mybatis.plus.jsqlparser)
    api(libs.mybatis.plus.extension)
    api(libs.spring.boot.starter.aop)

}

