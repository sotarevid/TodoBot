package com.company.Bot.Controller;

import com.company.Bot.Model.Task;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import java.util.ArrayList;
import java.util.List;

public class DbTaskController implements TaskController {

    private final Session session;

    public DbTaskController(SessionFactory sessionFactory) {
        this.session = sessionFactory.openSession();
    }

    @Override
    public void create(long userId, String name, String description, String category) {
        session.beginTransaction();
        session.save(new Task()
                .setUserId(userId)
                .setName(name)
                .setDescription(description)
                .setCategory(category));
        session.getTransaction().commit();
    }

    @Override
    public boolean delete(long userId, String name) {
        Example example = Example.create(new Task()
                .setUserId(userId)
                .setName(name));

        Criteria criteria = session.createCriteria(Task.class).add(example);
        List<Task> result = criteria.list();

        if (result.size() == 1) {
            return delete(userId, result.get(0).getId());
        }

        return false;
    }

    @Override
    public boolean delete(long userId, long id) {
        Task task = session.get(Task.class, id);

        if (task != null && task.getUserId() == userId) {
            session.beginTransaction();
            session.delete(task);
            session.getTransaction().commit();
            return true;
        }

        return false;
    }

    @Override
    public List<Task> get(long userId, String name) {
        Example example = Example.create(new Task()
                .setUserId(userId)
                .setName(name));

        Criteria criteria = session.createCriteria(Task.class).add(example);
        List<Task> result = criteria.list();

        return result;
    }

    @Override
    public List<Task> get(long userId, long id) {
        Task task = session.get(Task.class, id);
        List<Task> result = new ArrayList<>();

        if (task != null && task.getUserId() == userId) {
            result.add(task);
        }

        return result;
    }

    @Override
    public List<Task> getAll(long userId) {
        Example example = Example.create(new Task()
                .setUserId(userId));

        Criteria criteria = session.createCriteria(Task.class).add(example);
        List<Task> result = criteria.list();


        return result;
    }

    @Override
    public List<Task> getAllInCategory(long userId, String category) {
        Example example = Example.create(new Task()
                .setUserId(userId)
                .setCategory(category));

        Criteria criteria = session.createCriteria(Task.class).add(example);
        List<Task> result = criteria.list();

        return result;
    }
}
