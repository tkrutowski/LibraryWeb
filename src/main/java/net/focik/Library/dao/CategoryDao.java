package net.focik.Library.dao;

import net.focik.Library.Util.ConnectionFactory;
import net.focik.Library.model.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    private static Logger logger = LoggerFactory.getLogger(CategoryDao.class);

    private final static String CREATE = "INSERT INTO categories(name) VALUES(?);";
    private final static String READ_ONE = "SELECT * FROM categories WHERE id_category = ?;";
    private final static String READ_ALL = "SELECT * FROM categories;";
    private final static String UPDATE = "UPDATE categories SET name=? WHERE id_category = ?;";
    private final static String DELETE = "DELETE FROM categories WHERE id_category=?;";

    public int create(Category category)  {
        int affectedRows=0;
        try (Connection connection = ConnectionFactory.getConnection()){
            PreparedStatement preparedStatement=connection.prepareStatement(CREATE);
            preparedStatement.setString(1,category.getName());

            affectedRows=preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Can't add category to db");
        }
        logger.info("Create new category: {} {}",category.getName());
        return affectedRows;
    }

    public Category readOne(int id)  {
        Category category=null;
        try (Connection connection=ConnectionFactory.getConnection()){
            PreparedStatement preparedStatement=connection.prepareStatement(READ_ONE);
            preparedStatement.setInt(1,id);

            ResultSet resultSet=preparedStatement.executeQuery();

            if(resultSet.next()) {
                category=new Category();
                category.setIdCategory((resultSet.getInt(1)));
                category.setName(resultSet.getString(2));

                logger.info("Readed category: {} {}",category.getIdCategory(),category.getName());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Can't read category from db");
            return null;
        }
        return category;
    }

    public List<Category> readAll()  {
        List<Category> categories=new ArrayList<>();
        try (Connection connection=ConnectionFactory.getConnection()){

            Statement statement=connection.createStatement();

            ResultSet resultSet=statement.executeQuery(READ_ALL);

            while (resultSet.next()){
                Category category = new Category();
                category.setIdCategory(resultSet.getInt(1));
                category.setName(resultSet.getString(2));

                categories.add(category);
                logger.info("Added to list {} {} {}",category.getIdCategory(),category.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Can't read categories from db");
            return null;
        }
        return categories;
    }

    public void update(Category category)  {
        try (Connection conn=ConnectionFactory.getConnection();
             PreparedStatement statement=conn.prepareStatement(UPDATE);){

            statement.setString(1,category.getName());
            statement.setInt(2,category.getIdCategory());

            statement.executeUpdate();
            logger.info("Update category {} {}",category.getIdCategory(),category.getName());

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Can't update category in db");
        }
    }

    public void delete(int id){
        try (Connection connection = ConnectionFactory.getConnection()){

            PreparedStatement statement=connection.prepareStatement(DELETE);
            statement.setInt(1,id);
            statement.executeUpdate();
            logger.info("Deleted category where id =  {}",id);

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Can't delete category in db");
        }
    }
}
