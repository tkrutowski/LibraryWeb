package net.focik.Library.dao;

import net.focik.Library.Util.ConnectionProvider;
import net.focik.Library.model.Author;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

public class AuthorDaoTest {

    private static Logger logger = LoggerFactory.getLogger(AuthorDaoTest.class);
    AuthorDao authorDao= new AuthorDao();
    Author author=new Author();


    @Before
    public  void setUp() throws Exception {
        //ConnectionFactory("database_test.properties");
        //authorDao = new AuthorDao();
        // = new Author();
        try (Connection connection = ConnectionProvider.getConnection()) {
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
            logger.info("Created 5  authors");
        }
    }

    @After
    public  void tearDown() throws Exception {
        try (Connection connection = ConnectionProvider.getConnection()) {
            Statement statement = connection.createStatement();

            statement.executeUpdate("TRUNCATE authors;");
            logger.info("Deleted table authors");
        }
    }

    /*
    * return number of affected rows
    * */
    @Test
    public void test1_should_return_1_when_author_is_added() throws SQLException {
        //when
        logger.info("Test1_should_return_1_when_author_is_added...start");
        author.setId(0);
        author.setFirstName("Julian");
        author.setLastName("Tuwim");

        //given
        int result = authorDao.create(author);

        //then
        assertNotEquals(0,result);
        assertNotEquals(-1,result);
        logger.info("Test1_should_return_1_when_author_is_added...end");
    }

    @Test
    public void should_return_notNull_when_readOne() throws SQLException {
        logger.info("should_return_notNull_when_readOne....start");
        //when

        //given
        Author result = authorDao.readOne(3);

        //then
        assertNotNull(result);
        logger.info("should_return_notNull_when_readOne.....end");

    }

    @Test
    public void should_return_author_when_readOne() throws SQLException {
        logger.info("should_return_wonted_author_when_readOne.......start");
        //when

        //given
        Author result = authorDao.readOne(4);

        //then
        assertEquals("Lee",result.getFirstName());
        assertEquals("Child",result.getLastName());

        logger.info("should_return_wonted_author_when_readOne......end");

    }

    @Test
    public void Test2_should_return_6_when_added_one__readAll() throws SQLException {
        logger.info("Test2_should_return_6_when_readAll.......start");
        //when
        author.setId(0);
        author.setFirstName("Julian");
        author.setLastName("Tuwim");
        authorDao.create(author);

        //given
        int result = authorDao.readAll().size();

        //then
        assertEquals(6,result);
        logger.info("Test2_should_return_6_when_added_one__readAll.....end");

    }
//
@Test
public void Test3_should_return_4_when_delete_one_and_readAll() throws SQLException {
    logger.info("Test3_should_return_5_when_delete_one_and_readAll.....start");
    //when
    authorDao.delete(3);
    //given
    int result = authorDao.readAll().size();

    //then
    assertEquals(4,result);
    logger.info("Test3_should_return_4_when_delete_one_and_readAll......end");

}

    @Test
    public void should_return_changed_value_when_update() throws SQLException {
        //when
        Author a = authorDao.readOne(4);
        a.setFirstName("Adam");
        a.setLastName("Mickiewicz");
        authorDao.update(a);
        //given

        Author result = authorDao.readOne(4);

        //then
        assertEquals("Adam",result.getFirstName());
        assertEquals("Mickiewicz",result.getLastName());

        logger.info("should_return_changed_value_when_update......end");
    }


}