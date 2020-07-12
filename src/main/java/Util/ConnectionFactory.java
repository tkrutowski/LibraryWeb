package Util;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);

    private final Properties properties;
    private final String FILE_NAME="database.properties";

    public ConnectionFactory() {
        this.properties = getDataBaseProperties(FILE_NAME);
    }

    private Properties getDataBaseProperties(String filename) {
        Properties properties = new Properties();
        try {
            /**
             * Pobieramy zawartość pliku za pomocą classloadera, plik musi znajdować się w katalogu ustawionym w CLASSPATH
             */
            ClassLoader classLoader = this.getClass().getClassLoader();
            InputStream propertiesStream = classLoader.getResourceAsStream(filename);
            if (propertiesStream == null) {
                throw new IllegalArgumentException("Can't find file: " + filename);
            }
            /**
             * Pobieramy dane z pliku i umieszczamy w obiekcie klasy Properties
             */
            properties.load(propertiesStream);
        } catch (IOException e) {
            logger.error("Error during fetching properties for database", e);
            return null;
        }

        return properties;
    }

    public Connection getConnection() throws SQLException {
        MysqlDataSource dataSource = null;
        try {
            dataSource = new MysqlDataSource();
            dataSource.setServerName(properties.getProperty("jdbc.mysql.server"));
            dataSource.setDatabaseName(properties.getProperty("jdbc.mysql.name"));
            dataSource.setUser(properties.getProperty("jdbc.mysql.user"));
            dataSource.setPassword(properties.getProperty("jdbc.mysql.password"));
            dataSource.setPort(Integer.parseInt(properties.getProperty("jdbc.mysql.port")));
            dataSource.setAllowMultiQueries(true);
            dataSource.setServerTimezone("Europe/Warsaw");
            dataSource.setUseSSL(false);
            dataSource.setCharacterEncoding("UTF-8");
        } catch (SQLException e) {
            logger.error("Error during creating MysqlDataSource", e);
        }
        logger.info("Connecting to a selected database...");
        return dataSource.getConnection();
    }
}
