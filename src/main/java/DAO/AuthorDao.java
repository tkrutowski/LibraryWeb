package DAO;

import Util.ConnectionFactory;
import net.focik.Library.model.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao {

    private static Logger logger = LoggerFactory.getLogger(AuthorDao.class);

    private final static String CREATE = "INSERT INTO authors(first_name, last_name) VALUES(?, ?);";
    private final static String READ_ONE = "SELECT * FROM authors WHERE id_author = ?;";
    private final static String READ_ALL = "SELECT * FROM authors;";
    private final static String UPDATE = "UPDATE authors SET first_name=?, last_name=? WHERE id_author = ?;";
    private final static String DELETE = "DELETE FROM authors WHERE id_author=?;";

    public int create(Author author)  {
        int affectedRows=0;
        try (Connection connection = ConnectionFactory.getConnection()){
            PreparedStatement preparedStatement=connection.prepareStatement(CREATE);
            preparedStatement.setString(1,author.getFirstName());
            preparedStatement.setString(2,author.getLastName());

            affectedRows=preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Can't add author to db");
        }
        logger.info("Create new author: {} {}",author.getFirstName(),author.getLastName());
        return affectedRows;
    }

    public Author readOne(int id)  {
        Author author=null;
        try (Connection connection=ConnectionFactory.getConnection()){
            PreparedStatement preparedStatement=connection.prepareStatement(READ_ONE);
            preparedStatement.setInt(1,id);

            ResultSet resultSet=preparedStatement.executeQuery();

            if(resultSet.next()) {
                author=new Author();
                author.setId(resultSet.getInt(1));
                author.setFirstName(resultSet.getString(2));
                author.setLastName(resultSet.getString(3));

                logger.info("Readed author: {} {} {}",author.getId(),author.getFirstName(),author.getLastName());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Can't read author from db");
            return null;
        }
        return author;
    }

    public List<Author> readAll()  {
        List<Author> authors=new ArrayList<>();
        try (Connection connection=ConnectionFactory.getConnection()){

            Statement statement=connection.createStatement();

            ResultSet resultSet=statement.executeQuery(READ_ALL);

            while (resultSet.next()){
                Author author = new Author();
                author.setId(resultSet.getInt(1));
                author.setFirstName(resultSet.getString(2));
                author.setLastName(resultSet.getString(3));

                authors.add(author);
                logger.info("Added to list {} {} {}",author.getId(),author.getFirstName(),author.getLastName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Can't read authors from db");
            return null;
        }
        return authors;
    }

    public void update(Author author)  {
        try (Connection conn=ConnectionFactory.getConnection();
             PreparedStatement statement=conn.prepareStatement(UPDATE);){

            statement.setString(1,author.getFirstName());
            statement.setString(2,author.getLastName());
            statement.setInt(3,author.getId());

            statement.executeUpdate();
            logger.info("Update author {} {} {}",author.getId(),author.getFirstName(),author.getLastName());

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Can't update author in db");
        }
    }

    public void delete(int id){
        try (Connection connection = ConnectionFactory.getConnection()){

            PreparedStatement statement=connection.prepareStatement(DELETE);
            statement.setInt(1,id);
            statement.executeUpdate();
            logger.info("Deleted author where id =  {}",id);

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Can't delete author in db");
        }
    }
}
