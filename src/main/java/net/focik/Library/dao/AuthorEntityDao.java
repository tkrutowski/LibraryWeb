package net.focik.Library.dao;

import net.focik.Library.Util.ConnectionProvider;
import net.focik.Library.model.AuthorEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;

public class AuthorEntityDao {
    private static Logger logger = LoggerFactory.getLogger(AuthorEntityDao.class);


    public int save(AuthorEntity authorEntity){

        int id = -1;
        try {
            EntityManager entityManager = ConnectionProvider.getEntityManagerFactory().createEntityManager();
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
            EntityManager entityManager =ConnectionProvider.getEntityManagerFactory().createEntityManager();
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
            EntityManager entityManager = ConnectionProvider.getEntityManagerFactory().createEntityManager();
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
}
