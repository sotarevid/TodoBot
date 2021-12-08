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
    private long userId;

    private String name;
    private String description;
    private String category;

    public Task() { }

    /**
     * @return id задачи, записанной в объекте
     */
    public long getId() {
        return id;
    }

    public Task setId(long id) {
        this.id = id;
        return this;
    }

    /**
     * @return id пользователя, которому присвоена задача
     */
    public long getUserId() {
        return userId;
    }

    public Task setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    /**
     * @return название задачи, записанной в объекте
     */
    public String getName() {
        return name;
    }

    public Task setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return подробный текст задачи, записанной в объекте
     */
    public String getDescription() {
        return description;
    }

    public Task setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * @return категория, к которой принадлежит задача
     */
    public String getCategory() {
        return category;
    }

    public Task setCategory(String category) {
        this.category = category;
        return this;
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
