import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({TaskTests.class, TaskServiceTests.class})
@IncludeTags({"task", "task-service", "core", "file-io"})
public class AllTestsSuite {
}
