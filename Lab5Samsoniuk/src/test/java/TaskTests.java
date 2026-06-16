import org.junit.jupiter.api.*;
import ua.edu.ukma.samsoniuk.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class TaskTests {

    @Tag("task")
    @Tag("core")
    @Test
    @DisplayName("Initial isCompleted is false")
    void testsInitialBoolean() {
        Task task = new Task(1, "Buy book", "by Milan Kundera");
        assertFalse(task.isCompleted());
    }

    @Tag("task")
    @Tag("file-io")
    @TestFactory
    @DisplayName("Dynamic tests from file")
    Stream<DynamicTest> testTasksFromFile() throws IOException {
        Path filePath = Paths.get("src/test/resources/tasks.txt");
        assumeTrue(Files.exists(filePath));
        List<String> linesFromFile = Files.readAllLines(filePath);

        return linesFromFile.stream()
                .map(line -> {
                    String[] parts = line.split(",");
                    String name = parts[0].trim();
                    String description = parts[1].trim();

                    return DynamicTest.dynamicTest(
                            "Test add task (" + name + ", " + description + ")",
                            () -> {
                                Task task = new Task(1, name, description);
                                assertEquals(name, task.getName());
                                assertEquals(description, task.getDescription());
                                assertFalse(task.isCompleted());
                            }
                    );
                });
    }
}
