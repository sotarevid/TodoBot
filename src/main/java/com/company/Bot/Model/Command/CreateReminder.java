package com.company.Bot.Model.Command;

import com.company.Bot.Controller.ClientController;
import com.company.Bot.Controller.ReminderController;
import com.company.Bot.Model.Reminder;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class CreateReminder extends Command {

    public CreateReminder(ReminderController reminderController, ClientController clientController) {
        super(reminderController, clientController);
    }

    @Override
    public void execute(long userId) {
        sendMessage("Введите текст напоминания: ");
        String text = getNextMessage();

        sendMessage("Введите дату и время для напоминания в формате DD.MM.YYYY HH:MM в часовом поясе UTC: ");

        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd.MM.yyy HH:mm")
                .withZone(ZoneId.from(ZoneOffset.UTC));

        Instant instant = Instant.from(formatter.parse(getNextMessage()));

        reminderController.create(userId, text, instant);

        sendMessage("Запомнил!");
    }
}
