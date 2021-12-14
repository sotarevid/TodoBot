import com.company.Bot.Controller.ListTaskController;
import com.company.Bot.Controller.TaskController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClientControllerTest {

    private MockClientController clientController;

    @BeforeEach
    public void setupTest() {
        TaskController taskController = new ListTaskController();
        clientController = new MockClientController(taskController);
    }

    @Test
    public void shouldShowStart() {
        clientController.runCommand("/start");

        Assertions.assertEquals("Привет, я Todo-bot! Введи \"/help\", чтобы получить справку.",
                clientController.getLastOutput());
    }

    @Test
    public void shouldShowHelp() {
        clientController.runCommand("/help");

        Assertions.assertEquals("""
                /help - выводит это сообщение :)
                /create - создаёт новую задачу
                /list - показывает список всех задач
                /category - показывает список всех задач в категории
                /get - подробно показывает одну задачу
                /delete - удаляет задачу""", clientController.getLastOutput());
    }

    @Test
    public void shouldShowDefault() {
        clientController.runCommand("test");

        Assertions.assertEquals("Не понял. Может, стоит попробовать /help? :)",
                clientController.getLastOutput());
    }

    @Test
    public void shouldShowEmptyList() {
        clientController.runCommand("/list");

        Assertions.assertEquals("Ничего нет!", clientController.getLastOutput());
    }

    @Test
    public void shouldCreateNoCategory() {
        clientController.setInput("Name", "Description", "Н");
        clientController.runCommand("/create");

        clientController.runCommand("/list");
        Assertions.assertEquals("1: Name\r\n", clientController.getLastOutput());
    }

    @Test
    public void shouldCreateWithCategory() {
        clientController.setInput("Name", "Description", "Д", "Category");
        clientController.runCommand("/create");

        clientController.runCommand("/list");
        Assertions.assertEquals("1: Name (Category)\r\n", clientController.getLastOutput());
    }

    @Test
    public void shouldGetByName() {
        clientController.setInput("Name", "Description", "Н");
        clientController.runCommand("/create");

        clientController.setInput("Name", "Name");
        clientController.runCommand("/get");
        Assertions.assertEquals("""
                1: Name \r
                Description\r
                """, clientController.getLastOutput());
    }

    @Test
    public void shouldGetById() {
        clientController.setInput("Name", "Description", "Н");
        clientController.runCommand("/create");

        clientController.setInput("Id", "1");
        clientController.runCommand("/get");
        Assertions.assertEquals("""
                1: Name \r
                Description\r
                """, clientController.getLastOutput());
    }

    @Test
    public void shouldListCategory() {
        clientController.setInput("Name", "Description", "Д", "Category");
        clientController.runCommand("/create");

        clientController.setInput("Category");
        clientController.runCommand("/category");
        Assertions.assertEquals("""
                1: Name\r
                """, clientController.getLastOutput());
    }

    @Test
    public void shouldDeleteByName() {
        clientController.setInput("Name", "Description", "Н");
        clientController.runCommand("/create");

        clientController.setInput("Name", "Name");
        clientController.runCommand("/delete");

        clientController.runCommand("/list");
        Assertions.assertEquals("Ничего нет!", clientController.getLastOutput());
    }

    @Test
    public void shouldDeleteById() {
        clientController.setInput("Name", "Description", "Н");
        clientController.runCommand("/create");

        clientController.setInput("Id", "1");
        clientController.runCommand("/delete");

        clientController.runCommand("/list");
        Assertions.assertEquals("Ничего нет!", clientController.getLastOutput());
    }
}
