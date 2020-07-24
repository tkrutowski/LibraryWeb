package net.focik.Library.dao;

import net.focik.Library.Util.ConnectionProvider;
import net.focik.Library.model.AuthorEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class AuthorEntityDao {
    private static Logger logger = LoggerFactory.getLogger(AuthorEntityDao.class);
    ConnectionProvider provider = new ConnectionProvider();

    /*  public static void main(String[] args) {
          AuthorEntity authorEntity = new AuthorEntity("Lee","Child");
          AuthorEntityDao dao=new AuthorEntityDao();
          dao.save(authorEntity);
      }*/
    public int create(AuthorEntity authorEntity) {

        int id = -1;
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            //początek tranzakcji
            entityManager.getTransaction().begin();
            //przechodzi do stanu persistent - zostaje nadane id
            entityManager.persist(authorEntity);
            //pobieramy to id
            id = authorEntity.getId();
            //zapisujemy zmiany w bazie
            entityManager.getTransaction().commit();
            logger.info("Added to database: {}", authorEntity.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public void update(AuthorEntity authorEntity) {
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            logger.info("Author before update {}", authorEntity.toString());
            entityManager.getTransaction().begin();
            entityManager.merge(authorEntity);
            entityManager.getTransaction().commit();
            logger.info("Author after update: {}", authorEntity.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(AuthorEntity authorEntity) {
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            if (entityManager.contains(authorEntity)) {
                entityManager.remove(authorEntity);
            }
            entityManager.getTransaction().commit();
            logger.info("Remove author: {}", authorEntity.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean deleteById(int id) {
    boolean result = false;
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            logger.info("Removing  author by id: {} ....", id);
            entityManager.getTransaction().begin();
            AuthorEntity authorEntity = entityManager.find(AuthorEntity.class, id);
            if (authorEntity != null) {
                entityManager.remove(authorEntity);
                result = true;
            }
            entityManager.getTransaction().commit();
            if (authorEntity != null)
                logger.info("Removed author by id: {}, {}", id, authorEntity.toString());
            else
                logger.info("Removing author by id: {} ..... false", id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public AuthorEntity findById(int id) {
        AuthorEntity entity = null;
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            logger.info("Finding author by id: {}", id);
            entity = entityManager.find(AuthorEntity.class, id);
            if (entity != null) {
                logger.info("Found author by id: {}", entity.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    public List<AuthorEntity> getAll() {
        List<AuthorEntity> entities = new ArrayList<>();
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            logger.info("Finding all authors");
            entities = entityManager.createQuery("FROM AuthorEntity", AuthorEntity.class).getResultList();
            logger.info("Found {} authors", entities.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entities;
    }
}
