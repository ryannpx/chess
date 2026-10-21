package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.*;

public class GameService {
    private final GameDAO gameDAO;
    private final AuthDAO authDAO;
    public GameService(GameDAO gameDAO, AuthDAO authDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    private AuthData authorize(String authToken)
        throws UnauthorizedException, DataAccessException{
        AuthData auth = authDAO.getAuth(authToken);
        if (auth == null){
            throw new UnauthorizedException("Error: unauthorized");
        }
        return auth;
    }


    public ListGamesResult listGames(String authToken) throws UnauthorizedException, DataAccessException {
        authorize(authToken);
        return new ListGamesResult(gameDAO.listGames());
    }



    public CreateGameResult createGame(String authToken, CreateGameRequest request)
            throws BadRequestException, UnauthorizedException, DataAccessException {
        authorize(authToken);
        if (request.gameName() == null) {
            throw new BadRequestException("Error: bad request");
        }
        int id = gameDAO.createGame(request.gameName());
        return new CreateGameResult(id);
    }

    public void joinGame(String authToken, JoinGameRequest request)
            throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {
        AuthData auth = authorize(authToken);
        String color = request.playerColor();
        if (!"WHITE".equals(color) && !"BLACK".equals(color)) {
            throw new BadRequestException("Error: bad request");
        }
        if (request.gameID() == null) {
            throw new BadRequestException("Error: bad request");
        }
        GameData game = gameDAO.getGame(request.gameID());
        if (game == null) {
            throw new BadRequestException("Error: bad request");
        }

        GameData updated;
        if (color.equals("WHITE")) {
            if (game.whiteUsername() != null) {
                throw new AlreadyTakenException("Error: already taken");
            }
            updated = new GameData(game.gameID(), auth.username(), game.blackUsername(), game.gameName(), game.game());
        } else {
            if (game.blackUsername() != null) {
                throw new AlreadyTakenException("Error: already taken");
            }
            updated = new GameData(game.gameID(), game.whiteUsername(), auth.username(), game.gameName(), game.game());
        }
        gameDAO.updateGame(updated);
    }
}
