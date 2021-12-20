package com.company.Bot.Controller;

import com.company.Bot.Model.Command.*;
import com.company.Bot.TelegramBot;

import java.util.HashMap;
import java.util.Map;

public class TelegramClientController implements ClientController {

    private final long userId;
    private final TaskController taskController;
    private final ReminderController reminderController;
    private final TelegramBot bot;
    private final Map<String, Command> commands = new HashMap<>();
    private Command defaultCommand;

    public TelegramClientController(long userId, TaskController taskController, ReminderController reminderController, TelegramBot bot) {
        this.userId = userId;
        this.taskController = taskController;
        this.reminderController = reminderController;
        this.bot = bot;

        ReminderWorker reminderWorker = new ReminderWorker(reminderController, this);
        new Thread(reminderWorker::execute).start();

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
}
