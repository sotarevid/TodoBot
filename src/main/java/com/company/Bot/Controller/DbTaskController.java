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

        Task task = new Task();
        task.setUserId(userId);
        task.setName(name);
        task.setDescription(description);
        task.setCategory(category);

        session.save(task);
        session.getTransaction().commit();
    }

    @Override
    public boolean delete(long userId, String name) {
        Task task = new Task();
        task.setUserId(userId);
        task.setName(name);

        Example example = Example.create(task);

        Criteria criteria = session.createCriteria(Task.class).add(example);
        List<Task> result = criteria.list();

        return result.size() == 1 && delete(userId, result.get(0).getId());
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
        Task task = new Task();
        task.setUserId(userId);
        task.setName(name);

        Example example = Example.create(task);

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
        Task task = new Task();
        task.setUserId(userId);

        Example example = Example.create(task);

        Criteria criteria = session.createCriteria(Task.class).add(example);
        List<Task> result = criteria.list();


        return result;
    }

    @Override
    public List<Task> getAllInCategory(long userId, String category) {
        Task task = new Task();
        task.setUserId(userId);
        task.setCategory(category);

        Example example = Example.create(task);

        Criteria criteria = session.createCriteria(Task.class).add(example);
        List<Task> result = criteria.list();

        return result;
    }
}
