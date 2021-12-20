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
    public void execute(long userId) {
        sendMessage("Введите название категории (или нажмите Enter для отображения задач без категории): ");
        String response = getNextMessage();
        String category = response.equals("") ? null : response;
        List<Task> tasks = taskController.getAllInCategory(userId, category);
        StringBuilder builder = new StringBuilder();

        if (category == null)
            category = "Без категории";
        sendMessage(category + ":" + System.lineSeparator());

        for (Task task : tasks) {
            builder.append(task.getId()).append(": ").append(task.getName()).append(System.lineSeparator());
        }

        if (builder.isEmpty())
            builder.append("Ничего нет!");

        sendMessage(builder.toString());
    }
}
