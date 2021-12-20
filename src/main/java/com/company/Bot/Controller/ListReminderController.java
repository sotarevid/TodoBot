package com.company.Bot.Controller;

import com.company.Bot.Model.Reminder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ListReminderController implements ReminderController {

    private final List<Reminder> reminderList = new ArrayList<>();
    private long nextId = 1;

    @Override
    public void create(long userId, String text, Instant datetime) {
        Reminder reminder = new Reminder();
        reminder.setId(nextId++);
        reminder.setText(text);
        reminder.setDatetime(datetime);

        synchronized (reminderList) {
            reminderList.add(reminder);
        }
    }

    @Override
    public boolean delete(long userId, long id) {
        if (get(userId, id) != null) {
            synchronized (reminderList) {
                reminderList.remove(get(userId, id));
            }
            return true;
        }

        return false;
    }

    @Override
    public Reminder get(long userId, long id) {
        for (Reminder reminder : reminderList) {
            if (reminder.getId() == id)
                return reminder;
        }

        return null;
    }

    @Override
    public List<Reminder> getAll() {
        synchronized (reminderList) {
            return new ArrayList<>(reminderList);
        }
    }
}
