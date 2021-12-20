package com.company.Bot.Controller;

import com.company.Bot.Model.Task;
import org.hibernate.criterion.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Реализация TaskController, хранящая все задачи в памяти
 */
public class ListTaskController implements TaskController {

    private final List<Task> taskList = new ArrayList<>();
    private long nextId = 1;

    @Override
    public void create(long userId, String name, String description, String category) {
        Task task = new Task();
        task.setUserId(userId);
        task.setName(name);
        task.setDescription(description);
        task.setCategory(category);

        taskList.add(task);
    }

    @Override
    public boolean delete(long userId, String name) {
        List<Task> possibleTasks = get(userId, name);

        if (possibleTasks.size() != 1)
            return false;

        taskList.remove(possibleTasks.get(0));
        return true;
    }

    @Override
    public boolean delete(long userId, long id) {
        List<Task> possibleTasks = get(userId, id);

        if (possibleTasks.size() != 1)
            return false;

        taskList.remove(possibleTasks.get(0));
        return true;
    }

    @Override
    public List<Task> get(long userId, String name) {
        ArrayList<Task> result = new ArrayList<>();

        for (Task task : taskList) {
            if (task.getName().equals(name)) {
                result.add(task);
            }
        }

        return result;
    }

    @Override
    public List<Task> get(long userId, long id) {
        ArrayList<Task> result = new ArrayList<>();

        for (Task task : taskList) {
            if (task.getId() == id) {
                result.add(task);
            }
        }

        return result;
    }

    @Override
    public List<Task> getAll(long userId) {
        return taskList;
    }

    @Override
    public List<Task> getAllInCategory(long userId, String category) {
        List<Task> result = new ArrayList<>();

        for (Task task : taskList) {
            if (Objects.equals(category, task.getCategory())) {
                result.add(task);
            }
        }

        return result;
    }
}
