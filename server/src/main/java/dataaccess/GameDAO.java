package dataaccess;


import model.AuthData;
import model.UserData;

public interface GameDAO {
    int createGame(String gameName) throws DataAccessException;
    void GameData getGame(int gameID) throws DataAccessException;
    void updateGame(GameData game);
    void clear() throws DataAccessException;
}