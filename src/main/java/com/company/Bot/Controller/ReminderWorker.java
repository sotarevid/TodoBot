package com.company.Bot.Controller;

import com.company.Bot.Model.Reminder;

import java.time.Instant;
import java.util.List;

public class ReminderWorker {

    private final ReminderController reminderController;
    private final ClientController clientController;

    private final int deviation = 15 * 60 * 1000;

    public ReminderWorker(ReminderController reminderController, ClientController clientController) {
        this.reminderController = reminderController;
        this.clientController = clientController;
    }

    public void execute() {
        while (true) {
            List<Reminder> reminders = reminderController.getAll();

            if (reminders != null && reminders.size() > 0) {
                for (Reminder reminder : reminders) {
                    if (reminder.getDatetime().isBefore(Instant.now().plusMillis(deviation))) {
                        clientController.sendMessage(reminder.getUserId(), reminder.getText());
                        reminderController.delete(reminder.getUserId(), reminder.getId());
                    }
                }
            }

            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
