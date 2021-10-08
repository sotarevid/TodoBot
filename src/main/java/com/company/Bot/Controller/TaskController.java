package com.company.Bot.Controller;

import com.company.Bot.Model.Task;

import java.util.List;

/**
 * Интерфейс, описывающий класс-контроллер, управляющий задачами
 */
public interface TaskController {

    /**
     * Создаёт новую задачу с переданными параметрами
     * @param name название задачи
     * @param description подробный текст задачи
     * @param category категория, к которой принадлежит задача
     */
    void createTask(String name, String description, String category);

    /**
     * Удаляет задачу по её названию
     * @param name название задачи для удаления
     * @return true, если удаление произошло успешно, false иначе (такой задачи нет или их несколько)
     */
    boolean deleteTask(String name);

    /**
     * Удаляет задачу по её id
     * @param id название задачи для удаления
     * @return true, если удаление произошло успешно, false иначе (такой задачи нет)
     */
    boolean deleteTask(long id);

    /**
     * Получает задачу по её имени
     * @param name имя задачи
     * @return объект - искомая задача
     */
    Task getTask(String name);

    /**
     * Получает задачу по её id
     * @param id имя задачи
     * @return объект - искомая задача
     */
    Task getTask(long id);

    /**
     * Получает список всех задач
     * @return List<Task>, хранящий все имеющиеся задачи
     */
    List<Task> getAllTasks();

    /**
     * Получает список всех задач в категории
     * @param category категория, задачи из которой надо получить
     * @return List<Task>, хранящий все имеющиеся задачи в этой категории
     */
    List<Task> getAllTasksInCategory(String category);
}
