package com.company;

import com.company.Bot.Controller.*;
import com.company.Bot.TelegramBot;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        StandardServiceRegistry registry;

        Map<String,String> jdbcUrlSettings = new HashMap<>();
        String jdbcDbUrl = System.getenv("JDBC_DATABASE_URL");
        if (null != jdbcDbUrl) {
            jdbcUrlSettings.put("hibernate.connection.url", System.getenv("JDBC_DATABASE_URL"));
        }

        registry = new StandardServiceRegistryBuilder().
                configure("hibernate.cfg.xml").
                applySettings(jdbcUrlSettings).
                build();


        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        TelegramBot bot = new TelegramBot(sessionFactory);
        bot.connect();
    }
}
