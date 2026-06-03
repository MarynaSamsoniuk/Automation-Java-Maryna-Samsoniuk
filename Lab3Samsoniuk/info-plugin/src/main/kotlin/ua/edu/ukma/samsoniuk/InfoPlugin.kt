package ua.edu.ukma.samsoniuk

import org.gradle.api.Plugin
import org.gradle.api.Project

class InfoPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("showProjectInfo") {
            group = "info"
            description = "Shows information about the project"
            doLast {
                println("Project name: ${project.name}")
                println("Group: ${project.group}")
                println("Version: ${project.version}")
                println("Project dir: ${project.projectDir}")
            }
        }

        project.tasks.register("validateFileNames") {
            group = "info"
            description = "Checks that all Java source files follow UpperCamelCase naming"
            doLast {
                val srcDir = project.file("src")
                if (!srcDir.exists()) {
                    println("Directory does not exist: ${srcDir.absolutePath}!")
                    return@doLast
                }

                val upperCamelCaseName = Regex("^[A-Z][a-zA-Z0-9]*$")
                var hasErrors = false

                srcDir.walkTopDown().forEach { file ->
                    if (file.isFile && file.extension == "java") {
                        val fileName = file.nameWithoutExtension
                        if (!upperCamelCaseName.matches(fileName)) {
                            println("${file.name} – filename does not match UpperCamelCase")
                            hasErrors = true
                        }
                    }
                }

                if (!hasErrors) {
                    println("All java files have correct names (UpperCamelCase)")
                }
            }
        }
    }
}