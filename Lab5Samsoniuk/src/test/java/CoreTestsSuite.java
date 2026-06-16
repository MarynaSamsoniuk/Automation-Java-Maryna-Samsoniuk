import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({TaskTests.class, TaskServiceTests.class})
@IncludeTags("core")
public class CoreTestsSuite {
}
