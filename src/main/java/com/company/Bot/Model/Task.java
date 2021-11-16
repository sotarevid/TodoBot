package com.company.Bot.Model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Класс, описывающий задачу.
 */
@Entity
public class Task {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private long id;

    private String name;
    private String description;
    private String category;

    public Task(String name, String description, String category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public Task(long id, String name, String description, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
    }

    protected Task() { }

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
