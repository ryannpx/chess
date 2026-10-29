package service;
import org.mindrot.jbcrypt.BCrypt;

import dataaccess.*;
import model.*;
import java.util.UUID;

public class UserService {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public UserService(UserDAO userDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }

    public RegisterResult register(RegisterRequest request)
            throws BadRequestException, AlreadyTakenException, DataAccessException {
        if (request.username() == null || request.password() == null || request.email() == null) {
            throw new BadRequestException("Error: bad request");
        }
        if (userDAO.getUser(request.username()) != null) {
            throw new AlreadyTakenException("Error: already taken");
        }
        String hashed = BCrypt.hashpw(request.password(), BCrypt.gensalt());
        userDAO.createUser(new UserData(request.username(), hashed, request.email()));


        String token = createToken(request.username());

        return new RegisterResult(request.username(), token);
    }

    public LoginResult login(LoginRequest request)
            throws BadRequestException, DataAccessException, UnauthorizedException {
        if (request.username() == null || request.password() == null) {
            throw new BadRequestException("Error: bad request");
        }
        UserData user = userDAO.getUser(request.username());
        if (user == null || !BCrypt.checkpw(request.password(), user.password())) {
            throw new UnauthorizedException("Error: unauthorized");
        }

        String token = createToken(request.username());

        return new LoginResult(request.username(), token);
    }

    public void logout(String authToken)
        throws UnauthorizedException,DataAccessException{
        if (authDAO.getAuth(authToken) == null) {
            throw new UnauthorizedException("Error: unauthorized");
        }
        authDAO.deleteAuth(authToken);
    }

    private String createToken(String username) throws DataAccessException {
        String token = UUID.randomUUID().toString();
        authDAO.createAuth(new AuthData(token, username));
        return token;
    }

}