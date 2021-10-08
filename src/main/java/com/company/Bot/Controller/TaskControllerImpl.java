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
    public void createTask(String name, String description, String category) {
        Task newTask = new Task(taskList.size() + 1, name, description, category);
        taskList.add(newTask);
    }

    @Override
    public boolean deleteTask(String name) {
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
    public boolean deleteTask(long id) {
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
    public Task getTask(String name) {
        int count = 0;
        Task result = null;

        for (Task task : taskList) {
            if (task.getName().equals(name)) {
                count++;
                result = task;
            }
        }

        if (count == 1) return result;
        else return null;
    }

    @Override
    public Task getTask(long id) {
        int count = 0;
        Task result = null;

        for (Task task : taskList) {
            if (task.getId() == id) {
                count++;
                result = task;
            }
        }

        if (count == 1) return result;
        else return null;
    }

    @Override
    public List<Task> getAllTasks() {
        return taskList;
    }

    @Override
    public List<Task> getAllTasksInCategory(String category) {
        List<Task> result = new ArrayList<>();

        for (Task task : taskList) {
            if (task.getCategory().equals(category)) {
                result.add(task);
            }
        }

        return result;
    }
}
