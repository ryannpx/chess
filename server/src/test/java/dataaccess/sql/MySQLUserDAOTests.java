package dataaccess.sql;

import dataaccess.DataAccessException;
import model.UserData;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class MySQLUserDAOTests {
    private MySQLUserDAO userDAO;

    @BeforeEach //sets up a new
    public void setup() throws DataAccessException {
        userDAO = new MySQLUserDAO();
        userDAO.clear();
    }


// tests to cover the user sql daos i wrote
    @Test
    public void createUserSuccess() throws Exception { //sucessful user
        userDAO.createUser(new UserData("steve", "password", "steve@gmail.com"));

        UserData found = userDAO.getUser("steve");
        assertNotNull(found);
        assertEquals("steve@gmail.com", found.email());
    }

    @Test
    public void createUserDuplicate() throws Exception {
        userDAO.createUser(new UserData("steve", "password", "steve@gmail.com"));

        assertThrows(DataAccessException.class, () ->
                userDAO.createUser(new UserData("steve", "password", "steve@gmail.com")));

    }

    @Test
    public void getUserSuccess() throws Exception { //can retrieve user
        userDAO.createUser(new UserData("steve", "password", "steve@gmail.com"));

        UserData user = userDAO.getUser("steve");
        assertEquals("steve", user.username());
        assertEquals("password", user.password()); //one per line
        assertEquals("steve@gmail.com", user.email());    }


    @Test
    public void getUserNotFound() throws Exception {
        userDAO.getUser("nobody");
        UserData found = userDAO.getUser("nobody");
        assertNull(found);




    }
    @Test
    public void clearSuccess() throws Exception {
        userDAO.createUser(new UserData("steve", "password", "steve@gmail.com"));



        userDAO.clear();
        assertNull(userDAO.getUser("steve"));
    }


}