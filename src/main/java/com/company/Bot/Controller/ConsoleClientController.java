package com.company.Bot.Controller;

import com.company.Bot.Model.Command.*;


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleClientController implements ClientController {

    private final TaskController taskController;
    private final ReminderController reminderController;
    private final Map<String, Command> commands = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);
    private Command defaultCommand;
    private long userId = Long.MAX_VALUE;

    public ConsoleClientController(TaskController taskController, ReminderController reminderController) {
        this.taskController = taskController;
        this.reminderController = reminderController;

        ReminderWorker reminderWorker = new ReminderWorker(reminderController, this);
        new Thread(reminderWorker::execute).start();

        setupCommands();
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
    public String getNextMessage() {
        return scanner.nextLine();
    }

    /**
     * Запускает контроллер для обработки всех поступающих сообщений в цикле
     */
    public void listen() {
        while (true) {
            runCommand(getNextMessage());
            sendMessage(System.lineSeparator() + """
                    ---------------------------------
                    Жду команду:\040""");
        }
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
        System.out.print(text);
    }

    @Override
    public void sendMessage(long userId, String text) {
        System.out.print(text);
    }
}
