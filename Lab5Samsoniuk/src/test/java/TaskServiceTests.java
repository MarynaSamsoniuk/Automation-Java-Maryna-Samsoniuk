import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import ua.edu.ukma.samsoniuk.TaskService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;


public class TaskServiceTests {
    TaskService taskService = new TaskService();

    @Tag("task-service")
    @Tag("core")
    @ParameterizedTest(name = "Task name change to {0}")
    @ValueSource(strings = {"abcd", "abc", "ab", "a"})
    @DisplayName("Don't change name if it's too short")
    void changeNameTooShort(String newName) {
        taskService.addTask("Old Name", "Description");
        assertFalse(taskService.changeName(0, newName));
    }

    @Tag("task-service")
    @Tag("core")
    @ParameterizedTest(name = "addTask(name = {0}, description = {1}) = {2}", quoteTextArguments = false)
    @CsvSource(textBlock = """
			Good name,  Good description, true
			Name,  Good description, false
			Interesting Name, Fall, false
			Yes, No, false
			""")
    @DisplayName("Don't create task if name or description is too short")
    void addTaskTooShortNameOrDescription(String name, String description, boolean expectedResult) {
        assertEquals(expectedResult, taskService.addTask(name, description));
    }
}
