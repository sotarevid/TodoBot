package com.company.Bot.Model.Command;

import com.company.Bot.Controller.ClientController;
import com.company.Bot.Controller.TaskController;
import com.company.Bot.Model.Task;

import java.util.List;

public class FullList extends Command {

    public FullList(TaskController taskController, ClientController clientController) {
        super(taskController, clientController);
    }

    @Override
    public void execute() {
        List<Task> tasks = taskController.getAll(userId);
        StringBuilder builder = new StringBuilder();

        for (Task task : tasks) {
            builder.append(task.getId()).append(": ").append(task.getName());
            if (task.getCategory() != null)
                builder.append(" (").append(task.getCategory()).append(")");
            builder.append(System.lineSeparator());
        }

        if (builder.isEmpty())
            builder.append("Ничего нет!");

        sendMessage(builder.toString());
    }
}
