package com.company.Bot.Model.Command;

import com.company.Bot.Controller.ClientController;
import com.company.Bot.Controller.TaskController;

public class Create extends Command {

    public Create(TaskController taskController, ClientController clientController) {
        super(taskController, clientController);
    }

    @Override
    public void execute() {
        sendMessage("Введите название задачи: ");
        String name = getNextMessage();
        sendMessage("Введите подробное описание задачи: ");
        String description = getNextMessage();
        sendMessage("Хотите указать категорию задачи? [Д]а/[Н]ет: ");
        String categoryResponse = getNextMessage().toLowerCase();
        String category = null;
        if (categoryResponse.length() >= 1 && categoryResponse.toCharArray()[0] == 'д') {
            sendMessage("Введите название категории: ");
            category = getNextMessage();
            if (category.equals(""))
                category = null;
        }

        taskController.create(userId, name, description, category);
        sendMessage("Успешно!");
    }
}
