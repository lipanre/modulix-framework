
plugins {
    id("base")
    alias(libs.plugins.maven.publish)
}

allprojects {
    group = "io.github.lipanre"
    version = "0.0.15"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "com.vanniktech.maven.publish")

    mavenPublishing {
        coordinates(project.group.toString(), project.name, project.version.toString())
        publishToMavenCentral(automaticRelease = true)
        signAllPublications()

        // Configure POM metadata for the published artifact
        pom {
            name.set("modulix framework")
            description.set("simple common business framework library")
            inceptionYear.set("2025")
            url.set("https://github.com/lipanre/modulix-framework")

            licenses {
                license {
                    name.set("MIT")
                    url.set("https://opensource.org/licenses/MIT")
                }
            }

            // Specify developers information
            developers {
                developer {
                    id.set("lipanre")
                    name.set("lipanre")
                    email.set("lipanre@gmail.com")
                }
            }

            // Specify SCM information
            scm {
                url.set("https://github.com/lipanre/modulix-framework")
            }
        }
    }
}