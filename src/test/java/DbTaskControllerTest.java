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

    private final long userId = Long.MAX_VALUE;
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
        Assertions.assertTrue(taskController.getAll(userId).isEmpty());

        taskController.create(userId, "name", "description", "category");

        Assertions.assertFalse(taskController.getAll(userId).isEmpty());
    }

    @Test
    public void shouldGet() {
        taskController.create(userId, "name", "description_one", "category");

        Assertions.assertFalse(taskController.getAll(userId).isEmpty());
    }

    @Test
    public void shouldGetOne() {
        taskController.create(userId, "name", "description", "category");

        List<Task> actual = taskController.get(userId, "name");

        Assertions.assertNotNull(actual);
        Assertions.assertFalse(actual.isEmpty());

        Task result = actual.get(0);

        Assertions.assertEquals("name", result.getName());
        Assertions.assertEquals("description", result.getDescription());
        Assertions.assertEquals("category", result.getCategory());
    }

    @Test
    public void shouldGetSameNames() {
        taskController.create(userId, "name", "description_one", "category");
        taskController.create(userId, "name", "description_two", "category");

        Assertions.assertEquals(2, taskController.get(userId, "name").size());
    }

    @Test
    public void shouldGetById() {
        taskController.create(userId, "name", "description_one", "category");
        taskController.create(userId, "name", "description_two", "category");

        Assertions.assertEquals(1, taskController.get(userId, 1).size());
        Assertions.assertEquals(1, taskController.get(userId, 2).size());
    }

    @Test
    public void shouldDelete() {
        taskController.create(userId,"name", "description", "category");
        boolean result = taskController.delete(userId, "name");

        Assertions.assertTrue(result);
        Assertions.assertTrue(taskController.getAll(userId).isEmpty());
    }

    @Test
    public void shouldNotDeleteSameNames() {
        taskController.create(userId, "name", "description_one", "category");
        taskController.create(userId, "name", "description_two", "category");
        taskController.delete(userId, "name");

        Assertions.assertEquals(2, taskController.getAll(userId).size());
    }

    @Test
    public void shouldDeleteById() {
        taskController.create(userId, "name", "description_one", "category");
        taskController.create(userId, "name", "description_two", "category");
        taskController.delete(userId, 1);

        Assertions.assertEquals(1, taskController.getAll(userId).size());
    }

    @Test
    public void shouldGetCategory() {
        taskController.create(userId, "name", "description", "category_one");
        taskController.create(userId, "name", "description", "category_two");

        Assertions.assertEquals(1, taskController.getAllInCategory(userId, "category_one").size());
        Assertions.assertEquals(1, taskController.getAllInCategory(userId, "category_two").size());
    }

    @Test
    public void shouldNotGetForeign() {
        taskController.create(0, "name", "description_one", "category");
        taskController.create(1, "name", "description_two", "category");

        Assertions.assertEquals(0, taskController.get(0, 2).size());
        Assertions.assertEquals(0, taskController.get(1, 1).size());
    }

}
