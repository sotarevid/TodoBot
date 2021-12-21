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
    public void execute(long userId) {
        sendMessage("Получить задачу по id или названию?", createReplyKeyBoard(true));
        String response = getNextMessage().toLowerCase();

        if (response.length() == 0 || (response.toCharArray()[0] != 'i' && response.toCharArray()[0] != 'n'))
            return;

        boolean deleteById = response.toCharArray()[0] == 'i';
        if (deleteById) {
            sendMessage("Введите id: ");
            List<Task> result = taskController.get(userId, Long.parseLong(getNextMessage()));
            if (result.size() == 1) {
                sendMessage(generateResponse(result.get(0)));
            }
            else
                sendMessage("Не нашёл такую задачу :(");
        }
        else {
            sendMessage("Введите название: ");
            List<Task> result = taskController.get(userId, getNextMessage());
            if (result.size() == 1) {
                sendMessage(generateResponse(result.get(0)));
            }
            else {
                if (result.size() > 0) {
                    StringBuilder builder = new StringBuilder();

                    for (Task task : result) {
                        builder.append(task.getId()).append(": ").append(task.getName());
                        if (task.getCategory() != null)
                            builder.append(" (").append(task.getCategory()).append(")");
                        builder.append(System.lineSeparator());
                    }

                    sendMessage(builder.toString());
                    sendMessage("Выберите нужную задачу по id: ");

                    long id = Long.parseLong(getNextMessage());
                    result = taskController.get(userId, id);
                    if (result.size() == 1) {
                        sendMessage(generateResponse(result.get(0)));
                    }
                    else {
                        sendMessage("Не нашёл такую задачу :(");
                    }
                }
                else {
                    sendMessage("Не нашёл такую задачу :(");
                }
            }
        }
        sendMessage("Жду новых указаний :)", createHelpKeyboard());
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
