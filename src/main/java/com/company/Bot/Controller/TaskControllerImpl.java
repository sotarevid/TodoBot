package com.company.Bot.Controller;

import com.company.Bot.Model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Реализация TaskController, хранящая все задачи в памяти
 */
public class TaskControllerImpl implements TaskController {

    private final List<Task> taskList;

    public TaskControllerImpl() {
        taskList = new ArrayList<>();
    }

    @Override
    public void create(String name, String description, String category) {
        Task newTask = new Task(taskList.size() + 1, name, description, category);
        taskList.add(newTask);
    }

    @Override
    public boolean delete(String name) {
        int count = 0;
        int index = 0;

        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getName().equals(name)) {
                count++;
                index = i;
            }
        }

        if (count == 1)
            taskList.remove(index);
        else return false;

        return true;
    }

    @Override
    public boolean delete(long id) {
        int count = 0;
        int index = 0;

        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getId() == id) {
                count++;
                index = i;
            }
        }

        if (count == 1)
            taskList.remove(index);
        else return false;

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
                return result;
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
            if (task.getCategory().equals(category)) {
                result.add(task);
            }
        }

        return result;
    }
}
