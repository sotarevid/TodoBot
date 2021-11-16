package com.company.Bot.Controller;

import com.company.Bot.Model.Task;
import org.hibernate.Session;

import java.util.List;

public class DbTaskController implements TaskController {

    private final Session session;

    public DbTaskController(Session session) {
        this.session = session;
    }

    @Override
    public void create(String name, String description, String category) {
        session.beginTransaction();
        session.save(new Task(name, description, category));
        session.getTransaction().commit();
    }

    @Override
    public boolean delete(String name) {
        List<Task> result = session.createQuery("FROM Task WHERE name=\'"+name+"\'", Task.class).list();
        if (result.size() == 1) {
            return delete(result.get(0).getId());
        }

        return false;
    }

    @Override
    public boolean delete(long id) {
        List<Task> result = session.createQuery("FROM Task WHERE id=\'"+id+"\'", Task.class).list();
        if (result.size() == 1) {
            session.beginTransaction();
            session.createQuery(String.format("DELETE FROM Task WHERE id=%d", id)).executeUpdate();
            session.getTransaction().commit();
            return true;
        }

        return false;
    }

    @Override
    public List<Task> get(String name) {
        List<Task> result = session.createQuery("FROM Task WHERE name=\'"+name+"\'", Task.class).list();

        return result;
    }

    @Override
    public List<Task> get(long id) {
        List<Task> result = session.createQuery("FROM Task WHERE id=\'"+id+"\'", Task.class).list();

        return result;
    }

    @Override
    public List<Task> getAll() {
        List<Task> result = session.createQuery("FROM Task", Task.class).list();

        return result;
    }

    @Override
    public List<Task> getAllInCategory(String category) {
        List<Task> result = session.createQuery("FROM Task WHERE category=\'"+category+"\'", Task.class).list();

        return result;
    }
}
