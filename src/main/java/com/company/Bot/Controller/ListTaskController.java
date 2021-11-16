package com.company.Bot.Controller;

import com.company.Bot.Model.Task;

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
    public void create(String name, String description, String category) {
        Task newTask = new Task(nextId++, name, description, category);
        taskList.add(newTask);
    }

    @Override
    public boolean delete(String name) {
        List<Task> possibleTasks = get(name);

        if (possibleTasks.size() != 1)
            return false;

        taskList.remove(possibleTasks.get(0));
        return true;
    }

    @Override
    public boolean delete(long id) {
        List<Task> possibleTasks = get(id);

        if (possibleTasks.size() != 1)
            return false;

        taskList.remove(possibleTasks.get(0));
        return true;
    }

    @Override
    public List<Task> get(String name) {
        ArrayList<Task> result = new ArrayList<>();

        for (Task task : taskList) {
            if (task.getName().equals(name)) {
                result.add(task);
            }
        }

        return result;
    }

    @Override
    public List<Task> get(long id) {
        ArrayList<Task> result = new ArrayList<>();

        for (Task task : taskList) {
            if (task.getId() == id) {
                result.add(task);
            }
        }

        return result;
    }

    @Override
    public List<Task> getAll() {
        return taskList;
    }

    @Override
    public List<Task> getAllInCategory(String category) {
        List<Task> result = new ArrayList<>();

        for (Task task : taskList) {
            if (Objects.equals(category, task.getCategory())) {
                result.add(task);
            }
        }

        return result;
    }
}
