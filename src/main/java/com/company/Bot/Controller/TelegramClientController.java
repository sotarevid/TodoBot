package com.company.Bot.Controller;

import com.company.Bot.Model.Command.*;
import com.company.Bot.TelegramBot;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.HashMap;
import java.util.Map;

public class TelegramClientController implements ClientController {

    private final long userId;
    private final TaskController taskController;
    private final TelegramBot bot;
    private final Map<String, Command> commands = new HashMap<>();

    public TelegramClientController(long userId, TaskController taskController, TelegramBot bot) {
        this.userId = userId;
        this.taskController = taskController;
        this.bot = bot;
        setupCommands();
    }

    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public String getNextMessage() {
        String result = null;
        while (result == null) {
            result = bot.getMessage(userId);
        }

        return result;
    }

    private void setupCommands() {
        commands.put("/start", new Start(taskController, this));
        commands.put("/help", new Help(taskController, this));
        commands.put("/create", new Create(taskController, this));
        commands.put("/list", new FullList(taskController, this));
        commands.put("/category", new Category(taskController, this));
        commands.put("/get", new Get(taskController, this));
        commands.put("/delete", new Delete(taskController, this));
        commands.put("default", new Default(taskController, this));
        commands.put("Создать задачу", new Create(taskController, this));
        commands.put("Список задач", new FullList(taskController, this));
        commands.put("Список задач по категории", new Category(taskController, this));
        commands.put("Посмотреть задачу", new Get(taskController, this));
        commands.put("Удалить задачу", new Delete(taskController, this));
    }

    @Override
    public void runCommand(String message) {
        if (commands.containsKey(message))
            commands.get(message).execute();
        else
            commands.get("default").execute();
    }

    @Override
    public void sendMessage(long userId, String text) {
        bot.sendMessage(userId, text);
    }

    @Override
    public void sendMessage(long userId, String text, ReplyKeyboardMarkup replyKeyboardMarkup) {
        bot.sendMessage(userId, text, replyKeyboardMarkup);
    }
}
