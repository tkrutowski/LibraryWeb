package Util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class ConnectionFactoryTest {


    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getConnection() throws SQLException {
        //when


        //given
        Connection result = ConnectionFactory.getConnection();

        //then
        assertNotNull(result);
    }
    @Test
    public void getTestConnection() throws SQLException {
        //when


        //given
        Connection result = ConnectionProvider.getConnection();

        //then
        assertNotNull(result);
    }
}