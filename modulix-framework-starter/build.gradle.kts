plugins {
    id("java-library")
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.lombok)
    alias(libs.plugins.spring.boot.dependencies)
}

dependencies {
    api(project(":modulix-framework-mybatis-plus:modulix-framework-mybatis-plus-starter"))
    api(project(":modulix-framework-security:modulix-framework-security-starter"))
    api(project(":modulix-framework-web:modulix-framework-web-starter"))
    api(project(":modulix-framework-validation"))
    api(libs.mapstruct.plus.spring.boot.starter)
    api(libs.mybatis.plus.join.starter)
}