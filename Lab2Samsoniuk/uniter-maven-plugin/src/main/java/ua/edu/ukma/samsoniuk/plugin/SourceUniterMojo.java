package ua.edu.ukma.samsoniuk.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Mojo(name = "unite")
public class SourceUniterMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project.build.sourceDirectory}")
    private File sourceDirectory;

    @Parameter(defaultValue = "${project.build.directory}/united-sources.txt")
    private File outputFile;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (sourceDirectory == null || !sourceDirectory.exists()) {
            getLog().warn("Source directory does not exist: " + sourceDirectory);
            return;
        }

        List<Path> javaFilesPath = findJavaFiles();
        if (javaFilesPath.isEmpty()) {
            getLog().warn("No Java files found in: " + sourceDirectory);
            return;
        }
        getLog().info("Found " + javaFilesPath.size() + " java files");

        if (outputFile.getParentFile() != null && !outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }

        StringBuilder unitedSources = new StringBuilder();
        for (Path javaFile : javaFilesPath) {
            try {
                unitedSources.append("//").append(javaFile.getFileName()).append("\n");
                String content = Files.readString(javaFile);
                unitedSources.append(content);
                unitedSources.append("\n\n");
            } catch (IOException e) {
                getLog().warn("Failed to read file: " + javaFile + " - " + e.getMessage());
            }
        }

        try (BufferedWriter writer = Files.newBufferedWriter(outputFile.toPath())) {
            writer.write(unitedSources.toString());
            getLog().info("Successfully united sources into: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            getLog().error("Failed to write output file: " + outputFile);
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
            getLog().warn("Cant walk source directory: " + sourceDirectory);
            return javaFiles;
        }
    }
}