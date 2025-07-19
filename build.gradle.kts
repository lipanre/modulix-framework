plugins {
    id("base")
}

allprojects {
    group = "io.github.lipanre"
    version = "0.0.1"

    repositories {
        maven {setUrl("https://maven.aliyun.com/repository/public/")}
        maven {setUrl("https://maven.aliyun.com/repository/spring/")}
        mavenCentral()
    }
}