package net.focik.Library.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionProvider {
    private static Logger logger = LoggerFactory.getLogger(ConnectionProvider.class);
    private  EntityManagerFactory entityManagerFactory;

    public ConnectionProvider() {
        entityManagerFactory = Persistence.createEntityManagerFactory("net.focik.Library.model");
    }

    public  EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public  void close() {
        entityManagerFactory.close();
    }
}
