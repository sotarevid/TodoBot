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
        clientController.sendMessage("Удалить задачу по id или названию? [I]d/[N]ame: ");
        String response = clientController.getNextMessage().toLowerCase();

        if (response.length() == 0 || (response.toCharArray()[0] != 'i' && response.toCharArray()[0] != 'n'))
            return;

        boolean usingId = response.toCharArray()[0] == 'i';
        if (usingId) {
            sendResponse("Введите id: ");
            if (taskController.delete(Long.parseLong(clientController.getNextMessage())))
                sendResponse("Удалил!");
            else
                sendResponse("Не нашёл такую задачу :(");
        }
        else {
            sendResponse("Введите имя:");
            response = clientController.getNextMessage();
            if (taskController.delete(response))
                sendResponse("Удалил!");
            else {
                List<Task> tasks = taskController.get(response);
                if (tasks.size() > 0) {
                    StringBuilder builder = new StringBuilder();
                    int i = 0;

                    for (Task task : tasks) {
                        builder.append(task.getId()).append(": ").append(task.getName());
                        if (task.getCategory() != null)
                            builder.append(" (").append(task.getCategory()).append(")");
                        builder.append(System.lineSeparator());
                    }

                    sendResponse(builder.toString());
                    sendResponse("Выберите нужную задачу по id: ");

                    if (taskController.delete(Long.parseLong(clientController.getNextMessage())))
                        sendResponse("Удалил!");
                    else
                        sendResponse("Не нашёл такую задачу :(");
                }
                else {
                    sendResponse("Не нашёл такую задачу :(");
                }
            }
        }
    }
}
