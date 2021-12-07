package com.company.Bot.Model.Command;

import com.company.Bot.Controller.ClientController;
import com.company.Bot.Controller.TaskController;
import com.company.Bot.Model.Task;

import java.util.List;

public class Delete extends Command {

    public Delete(TaskController taskController, ClientController clientController) {
        super(taskController, clientController);
    }

    @Override
    public void execute() {
        sendMessage("Удалить задачу по id или названию? [I]d/[N]ame: ");
        String response = getNextMessage().toLowerCase();

        if (response.length() == 0 || (response.toCharArray()[0] != 'i' && response.toCharArray()[0] != 'n'))
            return;

        boolean usingId = response.toCharArray()[0] == 'i';
        if (usingId) {
            sendMessage("Введите id: ");
            if (taskController.delete(userId, Long.parseLong(getNextMessage())))
                sendMessage("Удалил!");
            else
                sendMessage("Не нашёл такую задачу :(");
        }
        else {
            sendMessage("Введите имя:");
            response = getNextMessage();
            if (taskController.delete(userId, response))
                sendMessage("Удалил!");
            else {
                List<Task> tasks = taskController.get(userId, response);
                if (tasks.size() > 0) {
                    StringBuilder builder = new StringBuilder();

                    for (Task task : tasks) {
                        builder.append(task.getId()).append(": ").append(task.getName());
                        if (task.getCategory() != null)
                            builder.append(" (").append(task.getCategory()).append(")");
                        builder.append(System.lineSeparator());
                    }

                    sendMessage(builder.toString());
                    sendMessage("Выберите нужную задачу по id: ");

                    if (taskController.delete(userId, Long.parseLong(getNextMessage())))
                        sendMessage("Удалил!");
                    else
                        sendMessage("Не нашёл такую задачу :(");
                }
                else {
                    sendMessage("Не нашёл такую задачу :(");
                }
            }
        }
    }
}
