package com.company.Bot.Controller;

import com.company.Bot.Model.Command.*;
import com.company.Bot.Model.Reminder;
import com.company.Bot.TelegramBot;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.HashMap;
import java.util.Map;

public class TelegramClientController implements ClientController {

    private long userId;
    private final TaskController taskController;
    private final ReminderController reminderController;
    private final TelegramBot bot;
    private final Map<String, Command> commands = new HashMap<>();
    private Command defaultCommand;

    public TelegramClientController(TaskController taskController, ReminderController reminderController, TelegramBot bot) {
        this.taskController = taskController;
        this.reminderController = reminderController;
        this.bot = bot;

        setupCommands();
    }

    public TelegramClientController(long userId, TaskController taskController, ReminderController reminderController, TelegramBot bot) {
        this.userId = userId;
        this.taskController = taskController;
        this.reminderController = reminderController;
        this.bot = bot;

        setupCommands();
    }

    @Override
    public String getNextMessage() {
        String result = null;
        while (result == null) {
            result = bot.getMessage(userId);
        }

        return result;
    }

    /**
     * Сохраняет команды для дальнейшей обработки сообщений
     */
    private void setupCommands() {
        commands.put("/start", new Start(taskController, this));
        commands.put("/help", new Help(taskController, this));
        commands.put("/create", new CreateTask(taskController, this));
        commands.put("/list", new FullList(taskController, this));
        commands.put("/category", new Category(taskController, this));
        commands.put("/get", new Get(taskController, this));
        commands.put("/delete", new Delete(taskController, this));
        commands.put("/remind", new CreateReminder(reminderController, this));

        commands.put("\u2712 Создать задачу", new CreateTask(taskController, this));
        commands.put("\uD83D\uDCD9 Список задач", new FullList(taskController, this));
        commands.put("\uD83D\uDCDA Список задач по категории", new Category(taskController, this));
        commands.put("\uD83D\uDC40 Посмотреть задачу", new Get(taskController, this));
        commands.put("\u274C Удалить задачу", new Delete(taskController, this));
        commands.put("\u23F0 Создать напоминание", new CreateReminder(reminderController, this));

        defaultCommand = new DefaultCommand(taskController, this);
    }

    @Override
    public void runCommand(String message) {
        if (commands.containsKey(message))
            commands.get(message).execute(userId);
        else
            defaultCommand.execute(userId);
    }

    @Override
    public void sendMessage(String text) {
        bot.sendMessage(this.userId, text);
    }

    @Override
    public void sendMessage(long userId, String text) {
        bot.sendMessage(userId, text);
    }

    @Override
    public void sendMessage(String text, ReplyKeyboardMarkup replyKeyboardMarkup) {
        bot.sendMessage(userId, text, replyKeyboardMarkup);
    }
}
