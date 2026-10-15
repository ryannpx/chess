package dataaccess;

import model.AuthData;
import model.UserData;
import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO {
    private final HashMap<String, AuthData> users = new HashMap<>();


    //points to the throws in the authdao interface
    @Override
    public void createAuth(AuthData auth)  {
        users.put(auth.authToken(), auth));
    }

    @Override
    public AuthData getAuth(String authToken)  {
        return users.get(authToken); // null if not found
    }

    @Override
    public void deleteAuth(String authToken)  {
        users.delete();
    }

    @Override
    public void clear()  {
        users.clear();
    }
}
