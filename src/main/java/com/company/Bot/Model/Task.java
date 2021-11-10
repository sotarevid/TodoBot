package com.company.Bot.Model;


import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id
                && name.equals(task.name)
                && description.equals(task.description)
                && Objects.equals(category, task.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
