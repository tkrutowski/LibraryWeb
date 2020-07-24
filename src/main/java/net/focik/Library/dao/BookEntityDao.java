package net.focik.Library.dao;

import net.focik.Library.Util.ConnectionProvider;
import net.focik.Library.model.BookEntity;
import net.focik.Library.model.BookstoreEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class BookEntityDao {
    private static Logger logger = LoggerFactory.getLogger(BookEntityDao.class);

    ConnectionProvider provider = new ConnectionProvider();

    /*  public static void main(String[] args) {
          BookstoreEntity bookEntity = new AuthorEntity("Lee","Child");
          BookstoreEntity dao=new AuthorEntityDao();
          dao.save(bookEntity);
      }*/
    public int save(BookEntity bookEntity) {
        int id = -1;
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            //początek tranzakcji
            entityManager.getTransaction().begin();
            //przechodzi do stanu persistent - zostaje nadane id
            entityManager.persist(bookEntity);
            //pobieramy to id
            id = bookEntity.getIdBook();
            //zapisujemy zmiany w bazie
            entityManager.getTransaction().commit();
            logger.info("Added to database: {}", bookEntity.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public void update(BookEntity bookEntity) {
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            logger.info("Book before update {}", bookEntity.toString());
            entityManager.getTransaction().begin();
            entityManager.merge(bookEntity);
            entityManager.getTransaction().commit();
            logger.info("Book after update: {}", bookEntity.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean deleteById(int id) {
        boolean result = false;
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            logger.info("Removing  book by id: {} ....", id);
            entityManager.getTransaction().begin();
            BookEntity bookEntity = entityManager.find(BookEntity.class, id);
            if (bookEntity != null) {
                entityManager.remove(bookEntity);
                result = true;
            }
            entityManager.getTransaction().commit();
            if (bookEntity != null)
                logger.info("Removed book by id: {}, {}", id, bookEntity.toString());
            else
                logger.info("Removing book by id: {} ..... false", id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public BookEntity findById(int id) {
        BookEntity entity = null;
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            logger.info("Finding book by id: {}", id);
            entity = entityManager.find(BookEntity.class, id);
            if (entity != null) {
                logger.info("Found book by id: {}", entity.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    public List<BookEntity> getAll() {
        List<BookEntity> entities = new ArrayList<>();
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            logger.info("Finding all books");
            entities = entityManager.createQuery("FROM BookEntity", BookEntity.class).getResultList();
            logger.info("Found {} books", entities.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entities;
    }
}
