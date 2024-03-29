import com.company.Bot.Controller.ClientController;
import com.company.Bot.Controller.ReminderController;
import com.company.Bot.Controller.ReminderWorker;
import com.company.Bot.Controller.TaskController;
import com.company.Bot.Model.Command.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class MockClientController implements ClientController {

    private Queue<String> inputQueue = new ArrayDeque<>();
    private String lastOutput;

    private final TaskController taskController;
    private final ReminderController reminderController;
    private final Map<String, Command> commands = new HashMap<>();
    private Command defaultCommand;
    private long userID = Long.MAX_VALUE;

    public MockClientController(TaskController taskController, ReminderController reminderController) {
        this.taskController = taskController;
        this.reminderController = reminderController;

        ReminderWorker reminderWorker = new ReminderWorker(reminderController, this);
        new Thread(reminderWorker::execute).start();

        setupCommands();
    }

    public void setInput(String... messages) {
        for (String message : messages) {
            inputQueue.offer(message);
        }
    }

    public String getLastOutput() {
        return lastOutput;
    }

    @Override
    public String getNextMessage() {
        return inputQueue.poll();
    }

    private void setupCommands() {
        commands.put("/start", new Start(taskController, this));
        commands.put("/help", new Help(taskController, this));
        commands.put("/create", new CreateTask(taskController, this));
        commands.put("/list", new FullList(taskController, this));
        commands.put("/category", new Category(taskController, this));
        commands.put("/get", new Get(taskController, this));
        commands.put("/delete", new Delete(taskController, this));

        commands.put("/remind", new CreateReminder(reminderController, this));

        defaultCommand = new DefaultCommand(taskController, this);
    }

    @Override
    public void runCommand(String message) {
        if (commands.containsKey(message))
            commands.get(message).execute(userID);
        else
            defaultCommand.execute(userID);
    }

    @Override
    public void sendMessage(String text) {
        lastOutput = text;
    }

    @Override
    public void sendMessage(long userId, String text) {
        lastOutput = text;
    }

    @Override
    public void sendMessage(String text, ReplyKeyboardMarkup replyKeyboardMarkup) {
        lastOutput = text;
    }
}
