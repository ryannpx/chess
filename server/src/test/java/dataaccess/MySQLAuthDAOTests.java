package dataaccess;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import static org.junit.jupiter.api.Assertions.*;

public class MySQLAuthDAOTests {
    private MySQLAuthDAO authDAO;


    @BeforeEach
    public void setup() throws DataAccessException {
        authDAO = new MySQLAuthDAO();
        authDAO.clear();
    }



    //tests , auth success, auth dupe, get auth sucess, auth not found?, delete auth, delete auth wrong token
    //clear

    @Test
    public void createAuthSuccess() throws Exception {
        authDAO.createAuth(new AuthData("authToken", "steve"));

        AuthData found = authDAO.getAuth("authToken");
        assertNotNull(found);
        assertEquals("steve", found.username());
    }

    @Test
    public void createAuthDupe() throws Exception {
        authDAO.createAuth(new AuthData("authToken", "steve"));

        assertThrows(DataAccessException.class, () ->
                authDAO.createAuth(new AuthData("authToken", "steve")));



    }

    @Test
    public void getAuthSuccess() throws Exception {
        authDAO.createAuth(new AuthData("authToken", "steve"));

        AuthData auth = authDAO.getAuth("authToken");
        assertEquals("authToken", auth.authToken());
        assertEquals("steve", auth.username());


    }

    @Test
    public void getAuthNotFoundHaha() throws Exception {
        authDAO.getAuth("fakeTokenYay");
        AuthData found = authDAO.getAuth("fakeTokenYay");

        assertNull(found);


    }

    @Test
    public void deleteAuthSuccess() throws Exception {
        authDAO.createAuth(new AuthData("authToken", "steve"));
        authDAO.deleteAuth("authToken");

        assertNull(authDAO.getAuth("authToken"));


    }

    @Test
    public void deleteWrongTokenAuth() throws Exception {

        authDAO.createAuth(new AuthData("authToken", "steve"));
        authDAO.deleteAuth("fakeToken");

        assertNotNull(authDAO.getAuth("authToken"));

    }


    @Test
    public void clearSuccess() throws Exception {
        authDAO.createAuth(new AuthData("", ""));



        authDAO.clear();
        assertNull(authDAO.getAuth(""));
    }



}
