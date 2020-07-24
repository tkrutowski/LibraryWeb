package net.focik.Library.dao;

import net.focik.Library.Util.ConnectionFactory;
import net.focik.Library.Util.ConnectionProvider;
import net.focik.Library.model.AuthorEntity;
import net.focik.Library.model.BookstoreEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookstoreEntityDao {
    private static Logger logger = LoggerFactory.getLogger(BookstoreEntityDao.class);

    ConnectionProvider provider = new ConnectionProvider();

    /*  public static void main(String[] args) {
          BookstoreEntity bookstoreEntity = new AuthorEntity("Lee","Child");
          BookstoreEntity dao=new AuthorEntityDao();
          dao.save(bookstoreEntity);
      }*/
    public int create(BookstoreEntity bookstoreEntity) {
        int id = -1;
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            //początek tranzakcji
            entityManager.getTransaction().begin();
            //przechodzi do stanu persistent - zostaje nadane id
            entityManager.persist(bookstoreEntity);
            //pobieramy to id
            id = bookstoreEntity.getIdBookstore();
            //zapisujemy zmiany w bazie
            entityManager.getTransaction().commit();
            logger.info("Added to database: {}", bookstoreEntity.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public void update(BookstoreEntity bookstoreEntity) {
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            logger.info("Bookstore before update {}", bookstoreEntity.toString());
            entityManager.getTransaction().begin();
            entityManager.merge(bookstoreEntity);
            entityManager.getTransaction().commit();
            logger.info("Bookstore after update: {}", bookstoreEntity.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(BookstoreEntity bookstoreEntity) {
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            if (entityManager.contains(bookstoreEntity)) {
                entityManager.remove(bookstoreEntity);
            }
            entityManager.getTransaction().commit();
            logger.info("Remove bookstore: {}", bookstoreEntity.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean deleteById(int id) {
        boolean result = false;
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            logger.info("Removing  bookstore by id: {} ....", id);
            entityManager.getTransaction().begin();
            BookstoreEntity bookstoreEntity = entityManager.find(BookstoreEntity.class, id);
            if (bookstoreEntity != null) {
                entityManager.remove(bookstoreEntity);
                result = true;
            }
            entityManager.getTransaction().commit();
            if (bookstoreEntity != null)
                logger.info("Removed bookstore by id: {}, {}", id, bookstoreEntity.toString());
            else
                logger.info("Removing bookstore by id: {} ..... false", id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public BookstoreEntity findById(int id) {
        BookstoreEntity entity = null;
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            logger.info("Finding bookstore by id: {}", id);
            entity = entityManager.find(BookstoreEntity.class, id);
            if (entity != null) {
                logger.info("Found bookstore by id: {}", entity.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    public List<BookstoreEntity> getAll() {
        List<BookstoreEntity> entities = new ArrayList<>();
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            logger.info("Finding all bookstores");
            entities = entityManager.createQuery("FROM BookstoreEntity", BookstoreEntity.class).getResultList();
            logger.info("Found {} bookstores", entities.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entities;
    }
}
