plugins {
    java
    id("com.github.johnrengelman.shadow")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:3.5.0")
    runtimeOnly("org.slf4j:slf4j-simple:1.7.26")
}

tasks {
    jar {
        manifest {
            attributes("Main-Class" to "App")
        }
    }
}