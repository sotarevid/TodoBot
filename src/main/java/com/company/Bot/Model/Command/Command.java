package com.company.Bot.Model.Command;

import com.company.Bot.Controller.ClientController;
import com.company.Bot.Controller.ReminderController;
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

    protected TaskController taskController;
    protected ClientController clientController;
    protected ReminderController reminderController;

    public Command(TaskController taskController, ClientController clientController) {
        this.taskController = taskController;
        this.clientController = clientController;
    }

    public Command(ReminderController reminderController, ClientController clientController) {
        this.reminderController = reminderController;
        this.clientController = clientController;
    }

    public abstract void execute(long userId);

    /**
     * Передаёт сообщение контроллеру для последующей отправки
     */
    public void sendMessage(String text) {
        clientController.sendMessage(text);
    }

    public void sendMessage(String text, ReplyKeyboardMarkup keyboardMarkup) {
        clientController.sendMessage(text, keyboardMarkup);
    }

    /**
     * Запрашивает у контроллера последнее полученное сообщение
     */
    public String getNextMessage() {
        return clientController.getNextMessage();
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
        String[] commands = new String[]{
                "\u2712 Создать задачу", "\uD83D\uDCD9 Список задач",
                "\uD83D\uDCDA Список задач по категории", "\uD83D\uDC40 Посмотреть задачу",
                "\u274C Удалить задачу", "\u23F0 Создать напоминание"};
        ArrayList<KeyboardRow> keyboardRows= new ArrayList<>();
        for (String command : commands) {
            KeyboardRow row = new KeyboardRow();
            row.add(new KeyboardButton(command));
            keyboardRows.add(row);
        }
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }
}
