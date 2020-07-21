package net.focik.Library.dao;

import net.focik.Library.Util.ConnectionFactory;
import net.focik.Library.model.Series;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeriesDao {
    private static Logger logger = LoggerFactory.getLogger(SeriesDao.class);

    private final static String CREATE = "INSERT INTO series(title, description) VALUES(?, ?);";
    private final static String READ_ONE = "SELECT * FROM series WHERE id_series = ?;";
    private final static String READ_ALL = "SELECT * FROM series;";
    private final static String UPDATE = "UPDATE series SET title=?, description=? WHERE id_series = ?;";
    private final static String DELETE = "DELETE FROM series WHERE id_series=?;";

    public int create(Series series)  {
        int affectedRows=0;
        try (Connection connection = ConnectionFactory.getConnection()){
            PreparedStatement preparedStatement=connection.prepareStatement(CREATE);
            preparedStatement.setString(1,series.getTitle());
            preparedStatement.setString(2,series.getDescription());

            affectedRows=preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Can't add series to db");
        }
        logger.info("Create new series: {} {}",series.getTitle(),series.getDescription());
        return affectedRows;
    }

    public Series readOne(int id)  {
        Series series=null;
        try (Connection connection=ConnectionFactory.getConnection()){
            PreparedStatement preparedStatement=connection.prepareStatement(READ_ONE);
            preparedStatement.setInt(1,id);

            ResultSet resultSet=preparedStatement.executeQuery();

            if(resultSet.next()) {
                series=new Series();
                series.setIdSeries(resultSet.getInt(1));
                series.setTitle(resultSet.getString(2));
                series.setDescription(resultSet.getString(3));

                logger.info("Readed series: {} {} {}",series.getIdSeries(),series.getTitle(),series.getDescription());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Can't read series from db");
            return null;
        }
        return series;
    }

    public List<Series> readAll()  {
        List<Series> seriesList=new ArrayList<>();
        try (Connection connection=ConnectionFactory.getConnection()){

            Statement statement=connection.createStatement();

            ResultSet resultSet=statement.executeQuery(READ_ALL);

            while (resultSet.next()){
                Series series = new Series();
                series.setIdSeries(resultSet.getInt(1));
                series.setTitle(resultSet.getString(2));
                series.setDescription(resultSet.getString(3));

                seriesList.add(series);
                logger.info("Added to list {} {} {}",series.getIdSeries(),series.getTitle(),series.getDescription());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Can't read seriesList from db");
            return null;
        }
        return seriesList;
    }

    public void update(Series series)  {
        try (Connection conn=ConnectionFactory.getConnection();
             PreparedStatement statement=conn.prepareStatement(UPDATE);){

            statement.setString(1,series.getTitle());
            statement.setString(2,series.getDescription());
            statement.setInt(3,series.getIdSeries());

            statement.executeUpdate();
            logger.info("Update series {} {} {}",series.getIdSeries(),series.getTitle(),series.getDescription());

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Can't update series in db");
        }
    }

    public void delete(int id){
        try (Connection connection = ConnectionFactory.getConnection()){

            PreparedStatement statement=connection.prepareStatement(DELETE);
            statement.setInt(1,id);
            statement.executeUpdate();
            logger.info("Deleted series where id =  {}",id);

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Can't delete series in db");
        }
    }
}
