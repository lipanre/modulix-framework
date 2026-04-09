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
    api(project(":modulix-framework-web:modulix-framework-web-api"))
    compileOnly(project(":modulix-framework-security:modulix-framework-security-api"))

    api(libs.spring.boot.starter.aop)
    api(libs.bundles.mybatis.plus)
}

