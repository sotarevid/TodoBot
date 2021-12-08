package com.company.Bot.Model.Command;

import com.company.Bot.Controller.ClientController;
import com.company.Bot.Controller.TaskController;
import com.company.Bot.Model.Task;

import java.util.List;

public class Category extends Command {

    public Category(TaskController taskController, ClientController clientController) {
        super(taskController, clientController);
    }

    @Override
    public void execute() {
        clientController.sendMessage("Введите название категории (или нажмите Enter для отображения задач без категории): ");
        String response = clientController.getNextMessage();
        String category = response.equals("") ? null : response;
        List<Task> tasks = taskController.getAllInCategory(category);
        StringBuilder builder = new StringBuilder();

        if (category == null)
            category = "Без категории";
        sendResponse(category + ":" + System.lineSeparator());

        for (Task task : tasks) {
            builder.append(task.getId()).append(": ").append(task.getName()).append(System.lineSeparator());
        }

        sendResponse(builder.toString());
    }
}
