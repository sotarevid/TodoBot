package com.company.Bot.Model.Command;

import com.company.Bot.Controller.ClientController;
import com.company.Bot.Controller.TaskController;

public class Create extends Command {

    public Create(TaskController taskController, ClientController clientController) {
        super(taskController, clientController);
    }

    @Override
    public void execute() {
        clientController.sendMessage("Введите название задачи: ");
        String name = clientController.getNextMessage();
        clientController.sendMessage("Введите подробное описание задачи: ");
        String description = clientController.getNextMessage();
        clientController.sendMessage("Хотите указать категорию задачи? [Д]а/[Н]ет: ");
        String categoryResponse = clientController.getNextMessage().toLowerCase();
        String category = null;
        if (categoryResponse.length() >= 1 && categoryResponse.toCharArray()[0] == 'д') {
            clientController.sendMessage("Введите название категории: ");
            category = clientController.getNextMessage();
            if (category.equals(""))
                category = null;
        }

        taskController.create(name, description, category);
        sendResponse("Успешно!");
    }
}
