package com.company.Bot.Model.Command;

import com.company.Bot.Controller.ClientController;
import com.company.Bot.Controller.TaskController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class CreateTask extends Command {

    public CreateTask(TaskController taskController, ClientController clientController) {
        super(taskController, clientController);
    }

    @Override
    public void execute(long userId) {
        sendMessage("Введите название задачи: ");
        String name = getNextMessage();
        sendMessage("Введите подробное описание задачи: ");
        String description = getNextMessage();
        sendMessage("Хотите указать категорию задачи?", createReplyKeyBoard(false));
        String categoryResponse = getNextMessage().toLowerCase();
        String category = null;
        if (categoryResponse.length() >= 1 && categoryResponse.toCharArray()[0] == 'д') {
            sendMessage("Введите название категории: ");
            category = getNextMessage();
            if (category.equals(""))
                category = null;
        }

        taskController.create(userId, name, description, category);
        sendMessage("Успешно!", createHelpKeyboard());
    }
}
