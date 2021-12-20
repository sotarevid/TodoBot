package com.company.Bot.Controller;

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
     * Отправляет сообщение
     */
    void sendMessage(String text);

    /**
     * Отправляет сообщение пользователю с указанным id
     */
    void sendMessage(long userId, String text);
}
