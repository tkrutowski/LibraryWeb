package Util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class ConnectionFactoryTest {
    ConnectionFactory connectionFactory;
    @Before
    public void setUp() throws Exception {
        connectionFactory=new ConnectionFactory();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getConnection() throws SQLException {
        //when


        //given
        Connection result = connectionFactory.getConnection();

        //then
        assertNotNull(result);
    }
}