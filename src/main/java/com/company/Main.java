package com.company;

import com.company.Bot.Controller.ConsoleClientController;
import com.company.Bot.Controller.DbTaskController;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Main {

    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

        Session session = sessionFactory.openSession();
        DbTaskController controller = new DbTaskController(session);
        ConsoleClientController bot = new ConsoleClientController(controller);
        bot.listen();

        session.close();
    }
}
