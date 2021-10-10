package com.company;

import com.company.Bot.Controller.TaskControllerImpl;
import com.company.Bot.Model.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Dialogue {
    public static void execute() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        TaskControllerImpl taskController = new TaskControllerImpl();
        System.out.println("""
                Привет, я ToDoBot и вот что я умею:\s
                1. Записывать задачи, о которых тебе важно помнить\s
                Подразделять их на категории, добавлять им описания\s
                2. Удалять уже имеющиеся записи
                3. Показывать все задачи в категории
                4. Показывать полный список записей
                5. Показывать подробное описание одной задачи
                Для выполнения команды введи ее номер, для выхода - exit""");
        boolean condition = true;
        while (condition) {
            String input = br.readLine();
            switch (input) {
                case ("1") -> {
                    System.out.println("Введи название задания");
                    String name = br.readLine();
                    System.out.println("Введи описание");
                    String description = br.readLine();
                    System.out.println("К какой категории его отнести?");
                    String category = br.readLine();
                    taskController.createTask(name, description, category);
                }
                case ("2") -> {
                    System.out.println("Введи название задачи, которую нужно удалить");
                    String name = br.readLine();
                    List<Task> tasksToDel = taskController.getTask(name);
                    if (tasksToDel.size() == 0) System.out.println("Не было найдено задач с подобным именем");
                    else if (tasksToDel.size() == 1) {
                        taskController.deleteTask(tasksToDel.get(0).getName());
                        System.out.println("Успешно удалено");
                    } else {
                        System.out.println("Было найдено более одной задачи с этим именем\n" +
                                "Введите ID той, которую вы хотите удалить или 0 чтобы удалить все");
                        printTasks(tasksToDel, true);
                        long id = Long.parseLong(br.readLine());
                        if (id == 0) {
                            for (Task task : tasksToDel) {
                                taskController.deleteTask(task.getId());
                            }
                        } else if (taskController.getTask(id).get(0).getName().equals(name)) {
                            taskController.deleteTask(id);

                        } else System.out.println("Неверно введён ID");
                        System.out.println("Успешно удалено");

                    }
                }
                case ("3") -> {
                    System.out.println("Введи категорию, которую хочешь посмотреть");
                    String category = br.readLine();
                    List<Task> tasks = taskController.getAllTasksInCategory(category);
                    if (tasks.size() == 0) System.out.println("Не было найдено задач в данной категории");
                    else {
                        System.out.println("Вот список всех задач в категории: \n");
                        for (Task task : tasks) {
                            System.out.println("Название: " + task.getName());
                            System.out.println("Описание: " + task.getDescription());
                            System.out.println("Категория: " + task.getCategory() + "\n");
                        }
                    }
                }
                case ("4") -> {
                    System.out.println("Вот список всех задач:");
                    List<Task> tasks = taskController.getAllTasks();
                    System.out.println("Вот список всех задач в категории: \n");
                    for (Task task : tasks) {
                        System.out.println("Название: " + task.getName());
                        System.out.println("Описание: " + task.getDescription());
                        System.out.println("Категория: " + task.getCategory() + "\n");
                    }
                }
                case ("5") -> {
                    System.out.println("Введи название задачи");
                    String name = br.readLine();
                    List<Task> tasksDescription = taskController.getTask(name);
                    if (tasksDescription.size() == 1) {
                        System.out.println("Название: " + tasksDescription.get(0).getName());
                        System.out.println("Описание: " + tasksDescription.get(0).getDescription());
                        System.out.println("Категория: " + tasksDescription.get(0).getCategory());
                    } else {
                        System.out.println("Было найдено более одной задачи с этим именем\n" +
                                "Вот их список:");
                        printTasks(tasksDescription, false);
                    }

                }
                case ("exit") -> condition = false;
            }
        }
    }

    private static void printTasks(List<Task> tasksDescription, boolean id) {
        if (id) {
            for (Task task : tasksDescription) {
                System.out.println("ID: " + task.getId());
                System.out.println("Название: " + task.getName());
                System.out.println("Описание: " + task.getDescription());
                System.out.println("Категория: " + task.getCategory() + "\n");
            }
        } else {
            for (Task task : tasksDescription) {
                System.out.println("Название: " + task.getName());
                System.out.println("Описание: " + task.getDescription());
                System.out.println("Категория: " + task.getCategory() + "\n");
            }
        }
    }
}
