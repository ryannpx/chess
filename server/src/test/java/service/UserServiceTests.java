package service;

import dataaccess.*;
import model.RegisterRequest;
import model.RegisterResult;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTests {
    private UserService userService;
    private MemoryUserDAO userDAO;
    private MemoryAuthDAO authDAO;

    @BeforeEach
    public void setup() {
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        userService = new UserService(userDAO, authDAO);
    }


    @Test
    public void successfulRegister() throws Exception {

        RegisterRequest request = new RegisterRequest("bob", "pass", "bob@gmail.com"); //passes in fake things
        RegisterResult result = userService.register(request); // function call

        assertEquals("bob", result.username()); //the right user comes back
        assertNotNull(result.authToken()); //token exists
        assertNotNull(userDAO.getUser("bob")); //user was actually saved
    }

    @Test
    public void registerAlreadyTaken() throws Exception {
        userService.register(new RegisterRequest("bob", "pass", "bob@mail.com"));

        assertThrows(AlreadyTakenException.class, () ->
                userService.register(new RegisterRequest("bob", "other", "x@mail.com")));
    }

    @Test
    public void registerBadRequest() throws Exception {
        assertThrows(BadRequestException.class,assertNotNull());
    }

    @Test
    public void loginSuccess() throws Exception {
        RegisterRequest request = new RegisterRequest("bob", "pass", "bob@gmail.com"); //passes in fake things
        RegisterResult result = userService.register(request); // function call

        assertEquals("bob", result.username()); //the right user comes back
        assertNotNull(result.authToken()); //token exists


    }
    @Test
    public void loginBadPassword() throws Exception {
        RegisterRequest request = new RegisterRequest("bob", "pass", "bob@gmail.com"); //passes in fake things
        RegisterResult result = userService.register(request); // function call

        assertThrows(BadRequestException.class,assertNotNull());

    }
    @Test
    public void logoutSuccess() throws Exception {

    }
    @Test
    public void loginWrongPassword() throws Exception {

    }
    @Test
    public void logoutBadToken() throws Exception {

    }
}
