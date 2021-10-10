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
        Assertions.assertTrue(taskController.getAllTasks().isEmpty());

        taskController.createTask("name", "description", "category");

        Assertions.assertFalse(taskController.getAllTasks().isEmpty());
    }

    @Test
    public void shouldGet() {
        taskController.createTask("name", "description_one", "category");

        Assertions.assertFalse(taskController.getAllTasks().isEmpty());
    }

    @Test
    public void shouldGetOne() {
        taskController.createTask("name", "description", "category");

        List<Task> actual = taskController.getTask("name");

        Assertions.assertNotNull(actual);
        Assertions.assertFalse(actual.isEmpty());

        Task result = actual.get(0);

        Assertions.assertEquals("name", result.getName());
        Assertions.assertEquals("description", result.getDescription());
        Assertions.assertEquals("category", result.getCategory());
    }

    @Test
    public void shouldGetSameNames() {
        taskController.createTask("name", "description_one", "category");
        taskController.createTask("name", "description_two", "category");

        Assertions.assertEquals(2, taskController.getTask("name").size());
    }

    @Test
    public void shouldGetById() {
        taskController.createTask("name", "description_one", "category");
        taskController.createTask("name", "description_two", "category");

        Assertions.assertEquals(1, taskController.getTask(1).size());
        Assertions.assertEquals(1, taskController.getTask(2).size());
    }

    @Test
    public void shouldDelete() {
        taskController.createTask("name", "description", "category");
        boolean result = taskController.deleteTask("name");

        Assertions.assertTrue(result);
        Assertions.assertTrue(taskController.getAllTasks().isEmpty());
    }

    @Test
    public void shouldNotDeleteSameNames() {
        taskController.createTask("name", "description_one", "category");
        taskController.createTask("name", "description_two", "category");
        taskController.deleteTask("name");

        Assertions.assertEquals(2, taskController.getAllTasks().size());
    }

    @Test
    public void shouldDeleteById() {
        taskController.createTask("name", "description_one", "category");
        taskController.createTask("name", "description_two", "category");
        taskController.deleteTask(1);

        Assertions.assertEquals(1, taskController.getAllTasks().size());
    }

    @Test
    public void shouldGetCategory() {
        taskController.createTask("name", "description", "category_one");
        taskController.createTask("name", "description", "category_two");

        Assertions.assertEquals(1, taskController.getAllTasksInCategory("category_one").size());
        Assertions.assertEquals(1, taskController.getAllTasksInCategory("category_two").size());
    }



}