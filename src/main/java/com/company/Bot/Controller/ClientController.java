package com.company.Bot.Controller;

public interface ClientController {

    String getNextMessage();

    void runCommand(String message);

    void sendMessage(String text);
}
