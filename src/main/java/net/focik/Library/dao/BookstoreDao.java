package net.focik.Library.dao;

import net.focik.Library.Util.ConnectionFactory;
import net.focik.Library.model.Bookstore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookstoreDao {
    private static Logger logger = LoggerFactory.getLogger(BookstoreDao.class);

    private final static String CREATE = "INSERT INTO bookstores(name, www) VALUES(?, ?);";
    private final static String READ_ONE = "SELECT * FROM bookstores WHERE id_bookstore = ?;";
    private final static String READ_ALL = "SELECT * FROM bookstores;";
    private final static String UPDATE = "UPDATE bookstores SET name=?, www=? WHERE id_bookstore = ?;";
    private final static String DELETE = "DELETE FROM bookstores WHERE id_bookstore=?;";

    public int create(Bookstore bookstore)  {
        int affectedRows=0;
        try (Connection connection = ConnectionFactory.getConnection()){
            PreparedStatement preparedStatement=connection.prepareStatement(CREATE);
            preparedStatement.setString(1,bookstore.getName());
            preparedStatement.setString(2,bookstore.getWww());

            affectedRows=preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Can't add bookstore to db");
        }
        logger.info("Create new bookstore: {} {}",bookstore.getName(),bookstore.getWww());
        return affectedRows;
    }

    public Bookstore readOne(int id)  {
        Bookstore bookstore=null;
        try (Connection connection=ConnectionFactory.getConnection()){
            PreparedStatement preparedStatement=connection.prepareStatement(READ_ONE);
            preparedStatement.setInt(1,id);

            ResultSet resultSet=preparedStatement.executeQuery();

            if(resultSet.next()) {
                bookstore=new Bookstore();
                bookstore.setIdBookStore(resultSet.getInt(1));
                bookstore.setName(resultSet.getString(2));
                bookstore.setWww(resultSet.getString(3));

                logger.info("Readed bookstore: {} {} {}",bookstore.getIdBookStore(),bookstore.getName(),bookstore.getWww());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Can't read bookstore from db");
            return null;
        }
        return bookstore;
    }

    public List<Bookstore> readAll()  {
        List<Bookstore> bookstores=new ArrayList<>();
        try (Connection connection=ConnectionFactory.getConnection()){

            Statement statement=connection.createStatement();

            ResultSet resultSet=statement.executeQuery(READ_ALL);

            while (resultSet.next()){
                Bookstore bookstore = new Bookstore();
                bookstore.setIdBookStore(resultSet.getInt(1));
                bookstore.setName(resultSet.getString(2));
                bookstore.setWww(resultSet.getString(3));

                bookstores.add(bookstore);
                logger.info("Added to list {} {} {}",bookstore.getIdBookStore(),bookstore.getName(),bookstore.getWww());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Can't read bookstores from db");
            return null;
        }
        return bookstores;
    }

    public void update(Bookstore bookstore)  {
        try (Connection conn=ConnectionFactory.getConnection();
             PreparedStatement statement=conn.prepareStatement(UPDATE);){

            statement.setString(1,bookstore.getName());
            statement.setString(2,bookstore.getWww());
            statement.setInt(3,bookstore.getIdBookStore());

            statement.executeUpdate();
            logger.info("Update bookstore {} {} {}",bookstore.getIdBookStore(),bookstore.getName(),bookstore.getWww());

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Can't update bookstore in db");
        }
    }

    public void delete(int id){
        try (Connection connection = ConnectionFactory.getConnection()){

            PreparedStatement statement=connection.prepareStatement(DELETE);
            statement.setInt(1,id);
            statement.executeUpdate();
            logger.info("Deleted bookstore where id =  {}",id);

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Can't delete bookstore in db");
        }
    }
}
