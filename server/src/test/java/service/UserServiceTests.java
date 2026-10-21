package service;

import dataaccess.*;
import model.*;
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
        assertThrows(BadRequestException.class, () ->
                userService.register(new RegisterRequest("bob", null, "bob@mail.com"))); //missing password
    }

    @Test
    public void loginSuccess() throws Exception {
        userService.register(new RegisterRequest("bob", "pass", "bob@gmail.com")); //make a user to log in as

        LoginResult result = userService.login(new LoginRequest("bob", "pass")); // function call

        assertEquals("bob", result.username()); //the right user comes back
        assertNotNull(result.authToken()); //token exists
        assertNotNull(authDAO.getAuth(result.authToken())); //token was actually saved
    }

    @Test
    public void loginBadRequest() throws Exception {
        assertThrows(BadRequestException.class, () ->
                userService.login(new LoginRequest("bob", null))); //missing password
    }

    @Test
    public void loginWrongPassword() throws Exception {
        userService.register(new RegisterRequest("bob", "pass", "bob@gmail.com"));

        assertThrows(UnauthorizedException.class, () ->
                userService.login(new LoginRequest("bob", "wrongPass")));
    }

    @Test
    public void logoutSuccess() throws Exception {
        String token = userService.register(new RegisterRequest("bob", "pass", "bob@gmail.com")).authToken();

        userService.logout(token);

        assertNull(authDAO.getAuth(token)); //token is gone
    }

    @Test
    public void logoutBadToken() throws Exception {
        assertThrows(UnauthorizedException.class, () ->
                userService.logout("fakeToken"));
    }
}
