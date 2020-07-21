package net.focik.Library.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionProvider {
    private static Logger logger = LoggerFactory.getLogger(ConnectionProvider.class);
    private static EntityManagerFactory entityManagerFactory;

    public ConnectionProvider() {
        entityManagerFactory = Persistence.createEntityManagerFactory("org.example.jpa.starter.lifecycle");
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public static void close() {
        entityManagerFactory.close();
    }
}
