package com.company.Bot.Controller;

import com.company.Bot.Model.Reminder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DbReminderController implements ReminderController {

    private final Session session;
    private final List<Reminder> allReminders = new ArrayList<>();

    public DbReminderController(SessionFactory sessionFactory) {
        this.session = sessionFactory.openSession();
        updateList();
    }

    private void updateList() {
        List<Reminder> result = session.createQuery("select r from Reminder r", Reminder.class).list();

        synchronized (allReminders) {
            allReminders.clear();
            allReminders.addAll(result);
        }
    }

    @Override
    public void create(long userId, String text, Instant datetime) {
        session.beginTransaction();

        Reminder reminder = new Reminder();
        reminder.setUserId(userId);
        reminder.setText(text);
        reminder.setDatetime(datetime);

        session.save(reminder);
        session.getTransaction().commit();
    }

    @Override
    public boolean delete(long userId, long id) {
        Reminder reminder = session.get(Reminder.class, id);

        if (reminder != null && reminder.getUserId() == userId) {
            session.beginTransaction();
            session.delete(reminder);
            session.getTransaction().commit();

            return true;
        }

        return false;
    }

    @Override
    public Reminder get(long userId, long id) {
        Reminder reminder = session.get(Reminder.class, id);

        if (reminder != null && reminder.getUserId() == userId) {
            return reminder;
        }

        return null;
    }

    @Override
    public List<Reminder> getAll() {
        updateList();
        synchronized (allReminders) {
            return new ArrayList<>(allReminders);
        }
    }
}
