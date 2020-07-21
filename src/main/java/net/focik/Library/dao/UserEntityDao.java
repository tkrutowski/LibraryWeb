package net.focik.Library.dao;

import lombok.extern.log4j.Log4j;
import net.focik.Library.Util.ConnectionProvider;
import net.focik.Library.model.AuthorEntity;
import net.focik.Library.model.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class UserEntityDao {
    private static Logger logger = LoggerFactory.getLogger(AuthorEntityDao.class);
    ConnectionProvider provider = new ConnectionProvider();

    /*  public static void main(String[] args) {
          AuthorEntity userEntity = new AuthorEntity("Lee","Child");
          AuthorEntityDao dao=new AuthorEntityDao();
          dao.save(userEntity);
      }*/
    public int save(UserEntity userEntity) {

        int id = -1;
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            //początek tranzakcji
            entityManager.getTransaction().begin();
            //przechodzi do stanu persistent - zostaje nadane id
            entityManager.persist(userEntity);
            //pobieramy to id
            id = userEntity.getIdUser();
            //zapisujemy zmiany w bazie
            entityManager.getTransaction().commit();
            logger.info("Added to database: {}", userEntity.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public void update(UserEntity authorEntity) {
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            logger.info("User before update {}", authorEntity.toString());
            entityManager.getTransaction().begin();
            entityManager.merge(authorEntity);
            entityManager.getTransaction().commit();
            logger.info("User after update: {}", authorEntity.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(UserEntity userEntity) {
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            if (entityManager.contains(userEntity)) {
                entityManager.remove(userEntity);
            }
            entityManager.getTransaction().commit();
            logger.info("Remove user: {}", userEntity.toString());
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
            UserEntity userEntity = entityManager.find(UserEntity.class, id);
            if (userEntity != null) {
                entityManager.remove(userEntity);
                result = true;
            }
            entityManager.getTransaction().commit();
            if (userEntity != null)
                logger.info("Removed user by id: {}, {}", id, userEntity.toString());
            else
                logger.info("Removing user by id: {} ..... false", id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public UserEntity findById(int id) {
        UserEntity entity = null;
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            logger.info("Finding user by id: {}", id);
            entity = entityManager.find(UserEntity.class, id);
            if (entity != null) {
                logger.info("Found user by id: {}", entity.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    public List<UserEntity> getAll() {
        List<UserEntity> entities = new ArrayList<>();
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            logger.info("Finding all users");
            entities = entityManager.createQuery("FROM UserEntity", UserEntity.class).getResultList();
            logger.info("Found {} users", entities.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entities;
    }
}
