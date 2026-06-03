plugins {
    id("java")
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

subprojects {
    tasks.register("deleteEmptyJavaFiles") {
        group = "info"
        description = "Deletes all empty java files in src directory"
        doLast {
            val srcDir = project.file("src")
            if (!srcDir.exists()) {
                println("Directory does not exist: ${srcDir.absolutePath}!")
                return@doLast
            }

            var deletedFilesCount = 0
            fileTree(srcDir).filter { it.extension == "java" }.forEach { file ->
                if (file.readText().trim().isEmpty()) {
                    if (file.delete()) {
                        println("Deleted empty file - ${file.relativeTo(rootDir)}")
                        deletedFilesCount++
                    } else {
                        println("Failed to delete empty file - ${file.relativeTo(rootDir)}")
                    }
                }
            }
            println("Deleted $deletedFilesCount empty Java files")
        }
    }
}

tasks.test {
    useJUnitPlatform()
}