package dataaccess;


import model.UserData;
import java.sql.SQLException;

public class MySQLUserDAO implements UserDAO{

    public MySQLUserDAO() throws DataAccessException{
        DatabaseManager.createDatabase();
        String sql = """
            CREATE TABLE IF NOT EXISTS user (
        username VARCHAR(255) NOT NULL,
        password VARCHAR(255) NOT NULL,
        email VARCHAR(255) NOT NULL,
        PRIMARY KEY (username)
    )
    """;

        try (var connection = DatabaseManager.getConnection(); var prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.executeUpdate();
        }
        catch (SQLException ex){
            throw new DataAccessException("error: unable to configure database connection", ex);

        }
    }

    @Override
    public void createUser(UserData user) throws DataAccessException {
        throw new UnsupportedOperationException();
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() throws DataAccessException {
        throw new UnsupportedOperationException();
    }
}
