package com.company.Bot.Model.Command;

import com.company.Bot.Controller.ClientController;
import com.company.Bot.Controller.TaskController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public abstract class Command {

    protected final TaskController taskController;
    protected final ClientController clientController;
    protected final long userId;

    public Command(TaskController taskController, ClientController clientController) {
        this.taskController = taskController;
        this.clientController = clientController;
        this.userId = clientController.getUserId();
    }

    public abstract void execute();

    public void sendMessage(String text) {
        clientController.sendMessage(userId, text);
    }

    public String getNextMessage() {
        return clientController.getNextMessage();
    }

    public void sendMessage(String text, ReplyKeyboardMarkup keyboardMarkup) {
        clientController.sendMessage(userId, text, keyboardMarkup);
    }

    public static ReplyKeyboardMarkup createReplyKeyBoard(boolean id) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        KeyboardRow keyboardRow = new KeyboardRow();
        if (id) {
            keyboardRow.add(new KeyboardButton("Id"));
            keyboardRow.add(new KeyboardButton("Name"));
        } else {
            keyboardRow.add(new KeyboardButton("Да"));
            keyboardRow.add(new KeyboardButton("Нет"));
        }
        ArrayList<KeyboardRow> keyboardRows= new ArrayList<>();
        keyboardRows.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup createHelpKeyboard(){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        String[] commands = new String[]{"Создать задачу", "Список задач",
                "Список задач по категории", "Посмотреть задачу", "Удалить задачу"};
        ArrayList<KeyboardRow> keyboardRows= new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            KeyboardRow row = new KeyboardRow();
            row.add(new KeyboardButton(commands[i]));
            keyboardRows.add(row);
        }
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }
}
