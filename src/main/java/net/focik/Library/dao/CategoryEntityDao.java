package net.focik.Library.dao;

import net.focik.Library.Util.ConnectionProvider;
import net.focik.Library.model.AuthorEntity;
import net.focik.Library.model.CategoryEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class CategoryEntityDao {
    private static Logger logger = LoggerFactory.getLogger(CategoryEntityDao.class);

    ConnectionProvider provider = new ConnectionProvider();

    /*  public static void main(String[] args) {
          AuthorEntity categoryEntity = new AuthorEntity("Lee","Child");
          AuthorEntityDao dao=new AuthorEntityDao();
          dao.save(categoryEntity);
      }*/
    public int create(CategoryEntity categoryEntity) {

        int id = -1;
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            //początek tranzakcji
            entityManager.getTransaction().begin();
            //przechodzi do stanu persistent - zostaje nadane id
            entityManager.persist(categoryEntity);
            //pobieramy to id
            id = categoryEntity.getIdCategory();
            //zapisujemy zmiany w bazie
            entityManager.getTransaction().commit();
            logger.info("Added to database: {}", categoryEntity.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public void update(CategoryEntity categoryEntity) {
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            logger.info("Category before update {}", categoryEntity.toString());
            entityManager.getTransaction().begin();
            entityManager.merge(categoryEntity);
            entityManager.getTransaction().commit();
            logger.info("Category after update: {}", categoryEntity.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(CategoryEntity categoryEntity) {
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            if (entityManager.contains(categoryEntity)) {
                entityManager.remove(categoryEntity);
            }
            entityManager.getTransaction().commit();
            logger.info("Remove category: {}", categoryEntity.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean deleteById(int id) {
        boolean result = false;
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            logger.info("Removing  category by id: {} ....", id);
            entityManager.getTransaction().begin();
            CategoryEntity categoryEntity = entityManager.find(CategoryEntity.class, id);
            if (categoryEntity != null) {
                entityManager.remove(categoryEntity);
                result = true;
            }
            entityManager.getTransaction().commit();
            if (categoryEntity != null)
                logger.info("Removed category by id: {}, {}", id, categoryEntity.toString());
            else
                logger.info("Removing category by id: {} ..... false", id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public CategoryEntity findById(int id) {
        CategoryEntity entity = null;
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            logger.info("Finding category by id: {}", id);
            entity = entityManager.find(CategoryEntity.class, id);
            if (entity != null) {
                logger.info("Found category by id: {}", entity.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    public List<CategoryEntity> getAll() {
        List<CategoryEntity> entities = new ArrayList<>();
        try {
            EntityManager entityManager = provider.getEntityManagerFactory().createEntityManager();
            logger.info("Finding all cetegories");
            entities = entityManager.createQuery("FROM CategoryEntity", CategoryEntity.class).getResultList();
            logger.info("Found {} categories", entities.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entities;
    }
}
