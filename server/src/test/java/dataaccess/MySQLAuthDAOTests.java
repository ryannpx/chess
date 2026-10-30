package dataaccess;
import model.AuthData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

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



    }

    @Test
    public void createAuthSuccess() throws Exception {



    }

    @Test
    public void createAuthSuccess() throws Exception {



    }

    @Test
    public void createAuthSuccess() throws Exception {



    }

    @Test
    public void createAuthSuccess() throws Exception {



    }

    @Test
    public void createAuthSuccess() throws Exception {



    }



}
