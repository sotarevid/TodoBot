package com.company.Bot;

import com.company.Bot.Controller.ClientController;
import com.company.Bot.Controller.DbTaskController;
import com.company.Bot.Controller.TaskController;
import com.company.Bot.Controller.TelegramClientController;
import org.hibernate.SessionFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class TelegramBot extends TelegramLongPollingBot {

    private final String token = "5027949483:AAHDKIejFNZ-UFPvbBxgYl8zomqQwiHrerE";
    private final String username = "oop_todo_bot";

    private final Map<Long, Queue<String>> messageQueue = new HashMap<>();

    private final SessionFactory sessionFactory;

    public TelegramBot(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public String getMessage(long userId) {
        synchronized (messageQueue) {
            if (messageQueue.containsKey(userId)) {
                return messageQueue.get(userId).poll();
            }
        }
        throw new IllegalStateException();
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message.hasText()) {
            long chatId = message.getChatId();

            if (messageQueue.containsKey(chatId)) {
                messageQueue.get(chatId).offer(message.getText());
            }
            else {
                messageQueue.put(chatId, new ArrayDeque<>());
                new Thread(() -> {
                    new TelegramClientController(chatId, new DbTaskController(sessionFactory), this)
                            .runCommand(message.getText());

                    synchronized (messageQueue) {
                        messageQueue.remove(chatId);
                    }
                }).start();
            }
        }
    }

    public void sendMessage(Long userId, String message) {
        SendMessage response = new SendMessage();
        response.setChatId(userId.toString());
        response.setText(message);

        try {
            execute(response);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        TelegramBotsApi telegramBotsApi;
        try {
            telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
            System.out.println("Connected!");
        } catch (TelegramApiException e) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            connect();
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
