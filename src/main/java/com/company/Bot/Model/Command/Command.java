package com.company.Bot.Model.Command;

import com.company.Bot.Controller.ClientController;
import com.company.Bot.Controller.TaskController;

public abstract class Command {

    protected final TaskController taskController;
    protected final ClientController clientController;
    protected final long userId;

    public Command(TaskController taskController, ClientController clientController) {
        this.taskController = taskController;
        this.clientController = clientController;
        this.userId = clientController.getUserId();
    }

    public abstract void execute();

    public void sendMessage(String text) {
        clientController.sendMessage(userId, text);
    }

    public String getNextMessage() {
        return clientController.getNextMessage();
    }
}
