package net.focik.Library.dao;

import net.focik.Library.Util.ConnectionFactory;
import net.focik.Library.Util.ConnectionProvider;
import net.focik.Library.model.AuthorEntity;
import net.focik.Library.model.SeriesEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeriesEntityDao {
    private static Logger logger = LoggerFactory.getLogger(SeriesEntityDao.class);

    ConnectionProvider provider = new ConnectionProvider();

    /*  public static void main(String[] args) {
          AuthorEntity seriesEntity = new AuthorEntity("Lee","Child");
          AuthorEntityDao dao=new AuthorEntityDao();
          dao.save(seriesEntity);
      }*/
    public int save(SeriesEntity seriesEntity) {

        int id = -1;
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            //początek tranzakcji
            entityManager.getTransaction().begin();
            //przechodzi do stanu persistent - zostaje nadane id
            entityManager.persist(seriesEntity);
            //pobieramy to id
            id = seriesEntity.getIdSeries();
            //zapisujemy zmiany w bazie
            entityManager.getTransaction().commit();
            logger.info("Added to database: {}", seriesEntity.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public void update(SeriesEntity seriesEntity) {
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            logger.info("Series before update {}", seriesEntity.toString());
            entityManager.getTransaction().begin();
            entityManager.merge(seriesEntity);
            entityManager.getTransaction().commit();
            logger.info("Series after update: {}", seriesEntity.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(SeriesEntity seriesEntity) {
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            if (entityManager.contains(seriesEntity)) {
                entityManager.remove(seriesEntity);
            }
            entityManager.getTransaction().commit();
            logger.info("Remove series: {}", seriesEntity.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean deleteById(int id) {
        boolean result = false;
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            logger.info("Removing  series by id: {} ....", id);
            entityManager.getTransaction().begin();
            SeriesEntity seriesEntity = entityManager.find(SeriesEntity.class, id);
            if (seriesEntity != null) {
                entityManager.remove(seriesEntity);
                result = true;
            }
            entityManager.getTransaction().commit();
            if (seriesEntity != null)
                logger.info("Removed series by id: {}, {}", id, seriesEntity.toString());
            else
                logger.info("Removing author by id: {} ..... false", id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public SeriesEntity findById(int id) {
        SeriesEntity entity = null;
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            logger.info("Finding series by id: {}", id);
            entity = entityManager.find(SeriesEntity.class, id);
            if (entity != null) {
                logger.info("Found series by id: {}", entity.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    public List<SeriesEntity> getAll() {
        List<SeriesEntity> entities = new ArrayList<>();
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            logger.info("Finding all series");
            entities = entityManager.createQuery("FROM SeriesEntity", SeriesEntity.class).getResultList();
            logger.info("Found {} series", entities.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entities;
    }
}
