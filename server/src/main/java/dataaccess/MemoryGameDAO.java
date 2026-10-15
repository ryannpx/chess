package dataaccess;

import model.GameData;
import java.util.HashMap;


public class MemoryGameDAO implements GameDAO {
    private final HashMap<String, GameData> users = new HashMap<>();


    @Override
    public int createGame(String gameName) throws DataAccessException {
        return 0;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        games.values();
    }

    @Override
    public void clear() throws DataAccessException {

    }

    @Override
    public void updateGame(GameData game) {

    }
}
