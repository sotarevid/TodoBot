package com.company.Bot.Controller;

public interface ClientController {

    long getUserId();

    String getNextMessage();

    void runCommand(String message);

    void sendMessage(long userId, String text);
}
