package net.focik.Library.dao;

import net.focik.Library.Util.ConnectionProvider;
import net.focik.Library.model.AuthorEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;

public class AuthorEntityDaoTest {
    private static Logger logger = LoggerFactory.getLogger(AuthorEntityDaoTest.class);
    AuthorEntityDao dao=new AuthorEntityDao();

    @Before
    public void setUp() throws Exception {
     /*   try (Connection connection = ConnectionProvider.getConnection()) {
            Statement statement = connection.createStatement();

            statement.executeUpdate("INSERT INTO authors(first_name, last_name) VALUES('Tom','Clancy' );");
//            logger.info("Created TOM CLANCY in authors");
            statement.executeUpdate("INSERT INTO authors(first_name, last_name) VALUES('Brandon','Sanderson' );");
//            logger.info("Created BRANDON SANDERSON in authors");
            statement.executeUpdate("INSERT INTO authors(first_name, last_name) VALUES('Remigiusz','Mróz' );");
//            logger.info("Created REMIGIUSZ MRÓZ in authors");
            statement.executeUpdate("INSERT INTO authors(first_name, last_name) VALUES('Lee','Child' );");
//            logger.info("Created LEE CHILD in authors");
            statement.executeUpdate("INSERT INTO authors(first_name, last_name) VALUES('Jo','Nesbo' );");
//            logger.info("Created JO NESBO in authors");
            logger.info("Created 5  authors");*/
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void save() {
        AuthorEntity authorEntity = new AuthorEntity(8,"Tom","Kru");

        int result = dao.save(authorEntity);

        assertNotEquals(-1,result);
        assertEquals(10,result);
    }

    @Test
    public void update() {
        //when
        AuthorEntity entity=dao.findById(10);
        entity.setFirstName("Tomek");
        dao.update(entity);

        //given
        AuthorEntity result = dao.findById(10);

        //then

        assertEquals("Tomek",result.getFirstName());
    }

    @Test
    public void deleteById_should_return_null() {
        //when

        //given
        boolean result = dao.deleteById(9999);

        //then

        assertFalse(result);
    }

    @Test
    public void deleteById() {

        //given
        boolean result = dao.deleteById(11);

        //then

        assertTrue(result);
    }

    @Test
    public void should_return_5_when_list_size_is_5() {

        //given
        List<AuthorEntity> result = dao.getAll();

        //then
        assertEquals(5,result.size());
    }
}