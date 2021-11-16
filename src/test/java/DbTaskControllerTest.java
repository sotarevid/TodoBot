import com.company.Bot.Controller.DbTaskController;
import com.company.Bot.Controller.TaskController;
import com.company.Bot.Model.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.*;

import java.util.List;

/**
 * Тесты для интерфейса TaskController
 * С реализацией на базе данных
 */
public class DbTaskControllerTest {

    private TaskController taskController;
    private Session session;

    @BeforeEach
    public void setupTests() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        session = sessionFactory.openSession();

        taskController = new DbTaskController(session);
    }

    @AfterEach
    public void cleanUp() {
        session.beginTransaction();
        session.createQuery("DELETE FROM Task").executeUpdate();
        session.getTransaction().commit();
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
