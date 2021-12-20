package com.company.Bot.Model.Command;

import com.company.Bot.Controller.ClientController;
import com.company.Bot.Controller.TaskController;

public class Start extends Command {

    public Start(TaskController taskController, ClientController clientController) {
        super(taskController, clientController);
    }

    @Override
    public void execute(long userId) {
        sendMessage("Привет, я Todo-bot! Введи \"/help\", чтобы получить справку.");
    }
}
