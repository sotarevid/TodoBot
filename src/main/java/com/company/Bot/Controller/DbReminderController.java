package com.company.Bot.Controller;

import com.company.Bot.Model.Reminder;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import java.time.Instant;
import java.util.List;

public class DbReminderController implements ReminderController {

    private final Session session;
    private List<Reminder> allReminders;

    public DbReminderController(SessionFactory sessionFactory) {
        this.session = sessionFactory.openSession();
    }

    private void updateList() {
        Reminder reminder = new Reminder();

        Example example = Example.create(reminder);

        Criteria criteria = session.createCriteria(Reminder.class).add(example);
        allReminders = criteria.list();
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

        updateList();
    }

    @Override
    public boolean delete(long userId, long id) {
        Reminder reminder = session.get(Reminder.class, id);

        if (reminder != null && reminder.getUserId() == userId) {
            session.beginTransaction();
            session.delete(reminder);
            session.getTransaction().commit();

            updateList();

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
        return allReminders;
    }
}
