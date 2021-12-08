package com.company;

import com.company.Bot.Controller.ClientController;
import com.company.Bot.Controller.ConsoleClientController;
import com.company.Bot.Controller.TaskControllerImpl;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        ConsoleClientController bot = new ConsoleClientController(new TaskControllerImpl());
        bot.listen();
    }
}
