import com.company.Bot.Controller.ListReminderController;
import com.company.Bot.Controller.ListTaskController;
import com.company.Bot.Controller.ReminderController;
import com.company.Bot.Controller.TaskController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class ClientControllerTest {

    private MockClientController clientController;

    @BeforeEach
    public void setupTest() {
        TaskController taskController = new ListTaskController();
        ReminderController reminderController = new ListReminderController();
        clientController = new MockClientController(taskController, reminderController);
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
                /delete - удаляет задачу
                /remind - создаёт напоминание""", clientController.getLastOutput());
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

    @Test
    public void shouldSendReminder() {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd.MM.yyy HH:mm")
                .withZone(ZoneId.from(ZoneOffset.UTC));

        String time = formatter.format(Instant.now().plusMillis(10 * 1000));

        clientController.setInput("Проверить работу напоминаний", time);
        clientController.runCommand("/remind");

        clientController.setInput("Проверить работу напоминаний", time);
        clientController.runCommand("/remind");

        try {
            Thread.sleep(20 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assertions.assertEquals("Проверить работу напоминаний", clientController.getLastOutput());
    }
}
