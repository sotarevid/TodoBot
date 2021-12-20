package com.company.Bot.Model.Command;

import com.company.Bot.Controller.ClientController;
import com.company.Bot.Controller.ReminderController;
import com.company.Bot.Controller.TaskController;

public abstract class Command {

    protected TaskController taskController;
    protected ClientController clientController;
    protected ReminderController reminderController;

    public Command(TaskController taskController, ClientController clientController) {
        this.taskController = taskController;
        this.clientController = clientController;
    }

    public Command(ReminderController reminderController, ClientController clientController) {
        this.reminderController = reminderController;
        this.clientController = clientController;
    }

    public abstract void execute(long userId);

    /**
     * Передаёт сообщение контроллеру для последующей отправки
     */
    public void sendMessage(String text) {
        clientController.sendMessage(text);
    }

    /**
     * Запрашивает у контроллера последнее полученное сообщение
     */
    public String getNextMessage() {
        return clientController.getNextMessage();
    }
}
