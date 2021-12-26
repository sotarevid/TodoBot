package com.company.Bot.Model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public class Reminder {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private long id;
    private long userId;
    private String text;
    private Instant datetime;

    public Reminder() { }

    public Reminder(String text, Instant datetime) {
        this.text = text;
        this.datetime = datetime;
    }

    /**
     * @return id задачи
     */
    public long getId() {
        return id;
    }

    /**
     * Устанавливает новое значение id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return userId пользователя, которому принадлежит напоминание
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Устанавливает новое значение userId
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * @return текст напоминания
     */
    public String getText() {
        return text;
    }

    /**
     * Устанавливает новое значение text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return объект Instant, на время которого записано напоминание
     */
    public Instant getDatetime() {
        return datetime;
    }

    /**
     * Устанавливает новое значение datetime
     */
    public void setDatetime(Instant datetime) {
        this.datetime = datetime;
    }
}
