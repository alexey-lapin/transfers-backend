plugins {
    java
    application
    id("com.github.johnrengelman.shadow")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:3.5.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.10.0.pr1")
    implementation("com.google.inject:guice:4.2.2")
//    implementation("org.javamoney:moneta:1.3")
    runtimeOnly("org.slf4j:slf4j-simple:1.7.26")

    testImplementation("io.rest-assured:rest-assured:4.1.2")
    testImplementation("org.mockito:mockito-core:3.1.0")
    testImplementation("org.assertj:assertj-core:3.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.1")
}

tasks {
    compileJava {
        sourceCompatibility = JavaVersion.VERSION_1_8.majorVersion
        targetCompatibility = JavaVersion.VERSION_1_8.majorVersion
    }

    compileTestJava {
        sourceCompatibility = JavaVersion.VERSION_11.majorVersion
        targetCompatibility = JavaVersion.VERSION_11.majorVersion
    }

    jar {
        manifest {
            attributes("Main-Class" to "com.github.al.transfers.App")
        }
    }

    test {
        useJUnitPlatform()
    }
}

application {
    mainClassName = "com.github.al.transfers.App"
}