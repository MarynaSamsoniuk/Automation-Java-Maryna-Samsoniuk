plugins {
    id("java")
    id("ua.edu.ukma.samsoniuk.info-plugin")
}

group = "ua.edu.ukma.samsoniuk"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.register<Copy>("backupSourceCode") {
    group = "info"
    description = "Copies source code to backup"
    dependsOn("deleteEmptyJavaFiles")
    from("src/main/java")
    into("build/backup")
    include("**/*.java")
    doLast {
        println("Backup created in derictory build/backup")
    }
}

tasks.test {
    useJUnitPlatform()
}