package com.company.Bot.Controller;

import com.company.Bot.Model.Reminder;

import java.time.Instant;
import java.util.List;

public interface ReminderController {

    /**
     * Создаёт новое напоминание с переданными параметрами
     * @param text текст напоминания
     * @param datetime объект Instant, описывающий время, на которое назначено напоминание
     */
    void create(long userId, String text, Instant datetime);

    /**
     * Удаляет напоминание по его id
     * @param id id напоминания для удаления
     * @return true, если удаление произошло успешно, false иначе
     */
    boolean delete(long userId, long id);

    /**
     * Получает напоминание по его id
     * @param id id напоминание
     * @return напоминание с соответствующим id
     */
    Reminder get(long userId, long id);

    /**
     * @return все сохранённые напоминания
     */
    List<Reminder> getAll();
}
