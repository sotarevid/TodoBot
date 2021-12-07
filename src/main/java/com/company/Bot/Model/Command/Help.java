package com.company.Bot.Model.Command;

import com.company.Bot.Controller.ClientController;
import com.company.Bot.Controller.TaskController;

public class Help extends Command {

    public Help(TaskController taskController, ClientController clientController) {
        super(taskController, clientController);
    }

    @Override
    public void execute() {
        sendMessage("""
                /help - выводит это сообщение :)
                /create - создаёт новую задачу
                /list - показывает список всех задач
                /category - показывает список всех задач в категории
                /get - подробно показывает одну задачу
                /delete - удаляет задачу""");
    }
}
