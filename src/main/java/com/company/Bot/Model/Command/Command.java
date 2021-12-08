package com.company.Bot.Model.Command;

import com.company.Bot.Controller.ClientController;
import com.company.Bot.Controller.TaskController;

public abstract class Command {

    protected final TaskController taskController;
    protected final ClientController clientController;

    public Command(TaskController taskController, ClientController clientController) {
        this.taskController = taskController;
        this.clientController = clientController;
    }

    public abstract void execute();

    public void sendResponse(String text) {
        clientController.sendMessage(text);
    };
}
