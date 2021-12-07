package com.company.Bot.Controller;

import com.company.Bot.Model.Command.*;


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleClientController implements ClientController {

    private final long userId = Long.MAX_VALUE;
    private final TaskController taskController;
    private final Map<String, Command> commands = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleClientController(TaskController taskController) {
        this.taskController = taskController;

        setupCommands();
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
    }

    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public String getNextMessage() {
        return scanner.nextLine();
    }

    public void listen() {
        while (true) {
            runCommand(getNextMessage());
            sendMessage(userId,System.lineSeparator() + """
                    ---------------------------------
                    Жду команду:\040""");
        }
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
        System.out.print(text);
    }
}
