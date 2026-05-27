package ua.edu.ukma.samsoniuk.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Mojo(name = "readme")
public class ReadmeCreatorMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project.build.sourceDirectory}")
    private File sourceDirectory;

    @Parameter(defaultValue = "${project.build.directory}/README.md")
    private File outputReadmeFile;

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (sourceDirectory == null || !sourceDirectory.exists()) {
            getLog().warn("Source directory does not exist: " + sourceDirectory);
            return;
        }

        List<Path> javaFiles = findJavaFiles();
        if (javaFiles.isEmpty()) {
            getLog().warn("No Java files found in: " + sourceDirectory);
            return;
        }

        getLog().info("Found " + javaFiles.size() + " Java files");

        int numberOfLines = 0;
        int numberOfMethods = 0;
        List<String> methodNames = new ArrayList<>();

        for (Path javaFile : javaFiles) {
            try {
                List<String> lines = Files.readAllLines(javaFile);
                numberOfLines += lines.size();

                for (String line : lines) {
                    if (isMethodDeclaration(line)) {
                        numberOfMethods++;
                        String methodName = extractMethodName(line);
                        if (methodName != null && !methodName.isEmpty()) {
                            String className = javaFile.getFileName().toString().replace(".java", "");
                            methodNames.add(className + "." + methodName);
                        }
                    }
                }
            } catch (IOException e) {
                getLog().warn("Failed to read file: " + javaFile + " - " + e.getMessage());
            }
        }

        String projectName = project.getName() != null ? project.getName() : project.getArtifactId();
        String projectGroupId = project.getGroupId();
        String projectArtifactId = project.getArtifactId();
        String projectVersion = project.getVersion();

        StringBuilder readmeFile = new StringBuilder();
        readmeFile.append("# Project: ").append(projectName).append("\n\n");
        readmeFile.append("## Basic Information\n");
        readmeFile.append("- **GroupId:** ").append(projectGroupId).append("\n");
        readmeFile.append("- **ArtifactId:** ").append(projectArtifactId).append("\n");
        readmeFile.append("- **Version:** ").append(projectVersion).append("\n\n");
        readmeFile.append("## Code Statistics\n");
        readmeFile.append("- **Java files:** ").append(javaFiles.size()).append("\n");
        readmeFile.append("- **Total lines:** ").append(numberOfLines).append("\n");
        readmeFile.append("- **Methods found:** ").append(numberOfMethods).append("\n\n");
        readmeFile.append("## Methods List\n");
        if (methodNames.isEmpty()) {
            readmeFile.append("No methods found.\n");
        } else {
            for (String method : methodNames) {
                readmeFile.append("- ").append(method).append("\n");
            }
        }

        if (outputReadmeFile.getParentFile() != null && !outputReadmeFile.getParentFile().exists()) {
            outputReadmeFile.getParentFile().mkdirs();
        }

        try (BufferedWriter writer = Files.newBufferedWriter(outputReadmeFile.toPath())) {
            writer.write(readmeFile.toString());
            getLog().info("Successfully united sources into: " + outputReadmeFile.getAbsolutePath());
        } catch (IOException e) {
            getLog().error("Failed to write README file: " + outputReadmeFile);
        }
    }

    private List<Path> findJavaFiles() {
        List<Path> javaFiles = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(sourceDirectory.toPath())) {
            paths.filter(Files::isRegularFile)
                    .filter(Files::isReadable)
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(javaFiles::add);
            return javaFiles;
        } catch (IOException e) {
            getLog().warn("Cannot walk source directory: " + sourceDirectory);
            return javaFiles;
        }
    }

    private boolean isMethodDeclaration(String line) {
        String trimmed_line = line.trim();
        if (trimmed_line.startsWith("//") || trimmed_line.startsWith("/*") || trimmed_line.startsWith("*")) {
            return false;
        }
        boolean hasModifier = trimmed_line.contains("public") || trimmed_line.contains("protected") || trimmed_line.contains("private");
        boolean hasParen = trimmed_line.contains("(") && trimmed_line.contains(")");
        return hasModifier && hasParen;
    }

    private String extractMethodName(String line) {
        int parenIndex = line.indexOf('(');
        if (parenIndex == -1) return null;
        String beforeParen = line.substring(0, parenIndex).trim();
        int lastSpace = beforeParen.lastIndexOf(' ');
        if (lastSpace != -1) {
            return beforeParen.substring(lastSpace + 1);
        }
        return beforeParen;
    }
}