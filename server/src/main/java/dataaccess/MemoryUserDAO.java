package dataaccess;

import model.UserData;
import java.util.HashMap;

public class MemoryUserDAO implements UserDao {
    private final HashMap<String, UserData> users = new HashMap<>();


    //points to the throws in the userdao interface
    @Override
    public void createUser(UserData user)  {
        users.put(user.username(),user);
    }

    @Override
    public UserData getUser(String username)  {
        return users.get(username); // null if not found
    }

    @Override
    public void clear()  {
        users.clear();
    }
}
