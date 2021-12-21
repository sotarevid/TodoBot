package com.company.Bot;

import com.company.Bot.Controller.*;
import org.hibernate.SessionFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.*;

public class TelegramBot extends TelegramLongPollingBot {

    private final String token = System.getenv("TODO_BOT_TOKEN");
    private final String username = System.getenv("TODO_BOT_USERNAME");

    private final Map<Long, Queue<String>> messageQueue = new HashMap<>();

    private final SessionFactory sessionFactory;

    public TelegramBot(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;

        startReminderWorker();
    }

    private void startReminderWorker() {
        DbReminderController workerReminderController = new DbReminderController(sessionFactory);

        TelegramClientController workerClientController = new TelegramClientController(
                new DbTaskController(sessionFactory),
                workerReminderController,
                this
        );

        ReminderWorker reminderWorker = new ReminderWorker(workerReminderController, workerClientController);
        new Thread(reminderWorker::execute).start();
    }

    /**
     * Получает следующее сообщение от пользователя по userId
     */
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
                    new TelegramClientController(chatId, new DbTaskController(sessionFactory), new DbReminderController(sessionFactory), this)
                            .runCommand(message.getText());

                    synchronized (messageQueue) {
                        messageQueue.remove(chatId);
                    }
                }).start();
            }
        }
    }

    /**
     * Отправляет сообщение пользователю по userId
     */
    public void sendMessage(Long userId, String message) {
        SendMessage response = new SendMessage();
        response.setChatId(userId.toString());
        response.setText(message);
        response.setReplyMarkup(new ReplyKeyboardRemove(true));
        try {
            execute(response);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Long userId, String message, ReplyKeyboardMarkup replyKeyboardMarkup) {
        SendMessage response = new SendMessage();
        response.setChatId(userId.toString());
        response.setText(message);
        response.setReplyMarkup(replyKeyboardMarkup);
        try {
            execute(response);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Устанавливает соединение с TelegramAPI и начинает слушать обновления
     */
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
