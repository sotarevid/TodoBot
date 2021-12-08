import com.company.Bot.Controller.TaskController;
import com.company.Bot.Controller.TaskControllerImpl;
import com.company.Bot.Model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Тесты для интерфейса TaskController
 */
public class TaskControllerTest {

    private TaskController taskController;

    @BeforeEach
    public void setupTests() {
        taskController = new TaskControllerImpl();
    }

    @Test
    public void shouldCreate() {
        Assertions.assertTrue(taskController.getAll().isEmpty());

        taskController.create("name", "description", "category");

        Assertions.assertFalse(taskController.getAll().isEmpty());
    }

    @Test
    public void shouldGet() {
        taskController.create("name", "description_one", "category");

        Assertions.assertFalse(taskController.getAll().isEmpty());
    }

    @Test
    public void shouldGetOne() {
        taskController.create("name", "description", "category");

        List<Task> actual = taskController.get("name");

        Assertions.assertNotNull(actual);
        Assertions.assertFalse(actual.isEmpty());

        Task result = actual.get(0);

        Assertions.assertEquals("name", result.getName());
        Assertions.assertEquals("description", result.getDescription());
        Assertions.assertEquals("category", result.getCategory());
    }

    @Test
    public void shouldGetSameNames() {
        taskController.create("name", "description_one", "category");
        taskController.create("name", "description_two", "category");

        Assertions.assertEquals(2, taskController.get("name").size());
    }

    @Test
    public void shouldGetById() {
        taskController.create("name", "description_one", "category");
        taskController.create("name", "description_two", "category");

        Assertions.assertEquals(1, taskController.get(1).size());
        Assertions.assertEquals(1, taskController.get(2).size());
    }

    @Test
    public void shouldDelete() {
        taskController.create("name", "description", "category");
        boolean result = taskController.delete("name");

        Assertions.assertTrue(result);
        Assertions.assertTrue(taskController.getAll().isEmpty());
    }

    @Test
    public void shouldNotDeleteSameNames() {
        taskController.create("name", "description_one", "category");
        taskController.create("name", "description_two", "category");
        taskController.delete("name");

        Assertions.assertEquals(2, taskController.getAll().size());
    }

    @Test
    public void shouldDeleteById() {
        taskController.create("name", "description_one", "category");
        taskController.create("name", "description_two", "category");
        taskController.delete(1);

        Assertions.assertEquals(1, taskController.getAll().size());
    }

    @Test
    public void shouldGetCategory() {
        taskController.create("name", "description", "category_one");
        taskController.create("name", "description", "category_two");

        Assertions.assertEquals(1, taskController.getAllInCategory("category_one").size());
        Assertions.assertEquals(1, taskController.getAllInCategory("category_two").size());
    }



}
