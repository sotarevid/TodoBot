package com.company.Bot.Model.Command;

import com.company.Bot.Controller.ClientController;
import com.company.Bot.Controller.TaskController;

public class Default extends Command {

    public Default(TaskController taskController, ClientController clientController) {
        super(taskController, clientController);
    }

    @Override
    public void execute() {
        sendMessage("Не понял. Вот список команд: :)", createHelpKeyboard());
    }
}
