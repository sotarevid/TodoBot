package com.company.Bot.Model.Command;

import com.company.Bot.Controller.ClientController;
import com.company.Bot.Controller.TaskController;

public class DefaultCommand extends Command {

    public DefaultCommand(TaskController taskController, ClientController clientController) {
        super(taskController, clientController);
    }

    @Override
    public void execute(long userId) {
        sendMessage("Не понял. Может, стоит попробовать /help? :)");
    }
}
