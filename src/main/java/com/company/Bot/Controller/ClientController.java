package com.company.Bot.Controller;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public interface ClientController {

    /**
     * Возвращает последнее сообщение, полученное контроллером
     */
    String getNextMessage();

    /**
     * Обрабатывает сообщение и исполняет соответствующую команду
     */
    void runCommand(String message);

    /**
     * Метод отправки сообщения пользователю.
     * Для использования в классах, реализующих общение с пользователем без использования Telegram API
     */
    default void sendMessage(long userId, String text) {
        return;
    }

    /**
     * Метод отправки сообщения пользователю.
     * Для использования в классах, реализующих общение с пользователем с использованием кнопок TelegramAPI
     */
    default void sendMessage(String text, ReplyKeyboardMarkup keyboardMarkup) {
        return;
    }

    void sendMessage(String text);
}
