package com.company.Bot.Model.Command;

import com.company.Bot.Controller.ClientController;
import com.company.Bot.Controller.TaskController;
import com.company.Bot.Model.Task;

import java.util.List;

public class Get extends Command {

    public Get(TaskController taskController, ClientController clientController) {
        super(taskController, clientController);
    }

    @Override
    public void execute() {
        clientController.sendMessage("Получить задачу по id или названию? [I]d/[N]ame: ");
        String response = clientController.getNextMessage().toLowerCase();

        if (response.length() == 0 || (response.toCharArray()[0] != 'i' && response.toCharArray()[0] != 'n'))
            return;

        boolean usingId = response.toCharArray()[0] == 'i';
        if (usingId) {
            sendResponse("Введите id: ");
            List<Task> result = taskController.get(Long.parseLong(clientController.getNextMessage()));
            if (result.size() == 1) {
                sendResponse(generateResponse(result.get(0)));
            }
            else
                sendResponse("Не нашёл такую задачу :(");
        }
        else {
            sendResponse("Введите название: ");
            List<Task> result = taskController.get(clientController.getNextMessage());
            if (result.size() == 1) {
                sendResponse(generateResponse(result.get(0)));
            }
            else {
                if (result.size() > 0) {
                    StringBuilder builder = new StringBuilder();
                    int i = 0;

                    for (Task task : result) {
                        builder.append(task.getId()).append(": ").append(task.getName());
                        if (task.getCategory() != null)
                            builder.append(" (").append(task.getCategory()).append(")");
                        builder.append(System.lineSeparator());
                    }

                    sendResponse(builder.toString());
                    sendResponse("Выберите нужную задачу по id: ");

                    long id = Long.parseLong(clientController.getNextMessage());
                    result = taskController.get(id);
                    if (result.size() == 1) {
                        sendResponse(generateResponse(result.get(0)));
                    }
                    else {
                        sendResponse("Не нашёл такую задачу :(");
                    }
                }
                else {
                    sendResponse("Не нашёл такую задачу :(");
                }
            }
        }
    }

    private String generateResponse(Task task) {
        StringBuilder builder = new StringBuilder();

        builder.append(task.getId()).append(": ").append(task.getName()).append(" ");
        if (task.getCategory() != null)
            builder.append("(").append(task.getCategory()).append(")");
        builder.append(System.lineSeparator());
        builder.append(task.getDescription()).append(System.lineSeparator());

        return builder.toString();
    }
}
