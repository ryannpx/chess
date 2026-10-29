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



try (var rs = ps.executeQuery()) {
        if (rs.next()) {                          // move to the first row; false if there are no rows
            String name = rs.getString("username");   // read a column by name
            // ...
        }
    }

    @Override
    public void createUser(UserData user) throws DataAccessException {
        String sql = "INSERT INTO user (username, password, email) VALUES (?,?,?)";
        try (var connection = DatabaseManager.getConnection();
             var ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.username()); // first ?
            ps.setString(2, user.password()); // second ?
            ps.setString(3, user.email()); // third ?
        }
        catch (SQLException ex) {
            throw new DataAccessException("Error, unable to create user", ex);
        }
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
