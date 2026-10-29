package dataaccess;

import model.AuthData;

import java.sql.SQLException;

public class MySQLAuthDAO implements AuthDAO{

    public MySQLAuthDAO() throws DataAccessException{
        DatabaseManager.createDatabase();
        String sql = """
                CREATE TABLE IF NOT EXISTS auth (
             authToken VARCHAR(255) NOT NULL,
             username VARCHAR(255) NOT NULL,
             PRIMARY KEY (authtoken)
           
                
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
    public void createAuth(AuthData auth) throws DataAccessException {
        String sql = "INSERT INTO auth (authToken, username) VALUES (?,?)";
        try (var connection = DatabaseManager.getConnection();
             var ps = connection.prepareStatement(sql)) {
            ps.setString(1, auth.authToken()); // first ?
            ps.setString(2, auth.username()); // second ?
            ps.executeUpdate();
        }
        catch (SQLException ex) {
            throw new DataAccessException("Error, unable to create user", ex);
        }
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        String sql = "SELECT username, password, email FROM auth WHERE authToken = ?";
        try (var connection = DatabaseManager.getConnection();
             var ps = connection.prepareStatement(sql)) {
            // set the ? to token
            ps.setString(1, authToken);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new AuthData(rs.getString("authToken"), rs.getString("username"));
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
        public void deleteAuth(String authToken) throws DataAccessException {
            String sql = "DELETE FROM auth WHERE authToken = ?"; //sql command
            try (var connection = DatabaseManager.getConnection();
                 var ps = connection.prepareStatement(sql)) {
                ps.setString(1, authToken);
                ps.executeUpdate();
            } catch (SQLException ex) {
                throw new DataAccessException("Error, unable to clear users", ex);
            }
        }
            @Override
            public void clear() throws DataAccessException {
                String sql = "TRUNCATE auth"; //sql command
                try (var connection = DatabaseManager.getConnection();
                     var ps = connection.prepareStatement(sql)) {
                    ps.executeUpdate();
                }
                catch (SQLException ex) {
                    throw new DataAccessException("Error, unable to clear authToken", ex);
                }

            }

        }





}
