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
        String sql = "INSERT INTO user (username, password, email) VALUES (?,?,?)";
        try (var connection = DatabaseManager.getConnection();
             var ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.username()); // first ?
            ps.setString(2, user.password()); // second ?
            ps.setString(3, user.email()); // third ?
            ps.executeUpdate();
        }
        catch (SQLException ex) {
            throw new DataAccessException("Error, unable to create user", ex);
        }
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        String sql = "SELECT username, password, email FROM user WHERE username = ?";
        try (var connection = DatabaseManager.getConnection();
             var ps = connection.prepareStatement(sql)) {
            // set the ? to username
           ps.setString(1, username);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    // return a new UserData built from rs.getString("username"), "password", and "email"
                    return new UserData(rs.getString("username"), rs.getString("password"), rs.getString("email"));
                }
                //  no row was found, so return null
                else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Error: unable to get user", ex);
        }
    }

    @Override
    public void clear() throws DataAccessException {
        String sql = "TRUNCATE user"; //sql command
        try (var connection = DatabaseManager.getConnection();
             var ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        }
        catch (SQLException ex) {
            throw new DataAccessException("Error, unable to clear users", ex);
        }

    }
}
