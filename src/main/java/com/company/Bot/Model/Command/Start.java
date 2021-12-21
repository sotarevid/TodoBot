package com.company.Bot.Model.Command;

import com.company.Bot.Controller.ClientController;
import com.company.Bot.Controller.TaskController;

public class Start extends Command {

    public Start(TaskController taskController, ClientController clientController) {
        super(taskController, clientController);
    }

    @Override
    public void execute() {
        sendMessage("Привет, я Todo-bot! Вот мои команды:", createHelpKeyboard());
    }
}
