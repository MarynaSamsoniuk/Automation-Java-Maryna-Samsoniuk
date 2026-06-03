plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("infoPlugin") {
            id = "ua.edu.ukma.samsoniuk.info-plugin"
            implementationClass = "ua.edu.ukma.samsoniuk.InfoPlugin"
        }
    }
}