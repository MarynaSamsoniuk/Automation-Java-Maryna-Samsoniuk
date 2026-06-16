plugins {
    id("java")
}

group = "ua.edu.ukma.samsoniuk"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:6.1.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:6.1.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:6.1.0")
    testImplementation("org.junit.platform:junit-platform-suite:6.1.0")
}

tasks.test {
    useJUnitPlatform()
}