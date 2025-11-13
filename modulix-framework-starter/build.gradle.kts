plugins {
    id("java-library")
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.lombok)
    alias(libs.plugins.spring.boot.dependencies)
}

dependencies {
    api(project(":modulix-framework-mybatis-plus:modulix-framework-mybatis-plus-starter"))
    api(project(":modulix-framework-validation"))
    api(project(":modulix-framework-security:modulix-framework-security-satoken-starter"))
    api(project(":modulix-framework-web:modulix-framework-web-starter"))
    api(libs.mapstruct.plus.spring.boot.starter)
    api(libs.spring.boot.starter.validation)
    api(libs.mybatis.plus.join.starter)
    api(libs.sa.token.starter)
}