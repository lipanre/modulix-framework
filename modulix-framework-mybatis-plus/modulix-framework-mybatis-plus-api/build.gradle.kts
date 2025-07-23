
plugins {
    id("java-library")
    alias(libs.plugins.lombok)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.boot.dependencies)
}


dependencies {
    api(project(":modulix-framework-common"))
    api(platform(libs.mybatis.plus.bom))

    api(libs.jackson.databind)
    api(libs.spring.tx)
    api(libs.jakarta.annotation.api)
    api(libs.mybatis.plus.spring)
    api(libs.mybatis.plus.extension)
    api(libs.mybatis.plus.annotation)
    api(libs.mybatis.plus.core)
    api(libs.mybatis.plus.jsqlparser)
    api(libs.mybatis.plus.join.core)
    api(libs.mybatis.plus.join.extension)
    api(libs.mapstruct.plus)
}
