package com.company.Bot.Model;


/**
 * Класс, описывающий задачу.
 */
public class Task {

    private final long id;
    private final String name;
    private final String description;
    private final String category;

    public Task(long id, String name, String description, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
    }

    /**
     * @return id задачи, записанной в объекте
     */
    public long getId() {
        return id;
    }

    /**
     * @return название задачи, записанной в объекте
     */
    public String getName() {
        return name;
    }

    /**
     * @return подробный текст задачи, записанной в объекте
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return категория, к которой принадлежит задача
     */
    public String getCategory() {
        return category;
    }
}
