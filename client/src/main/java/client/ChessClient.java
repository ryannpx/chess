package client;

//import;

import java.util.Arrays;

public class ChessClient {
    private final ServerFacade facade;
    private State state = State.SIGNEDOUT;
    private String authToken = null;
    private String username = null;

    private final java.util.ArrayList<Integer> gameIDs = new java.util.ArrayList<>();

    public ChessClient(int port) {
        facade = new ServerFacade(port);



    }

    public State getState() {
        return state;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split("\\s+");
            var command = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);



            return switch (state) {
                case SIGNEDOUT -> switch (command) {
                    case "register" -> register(params);
                    case "login" -> login(params);
                    case "quit" -> "quit";
                    default -> help();
                }; //create list play observe
                case SIGNEDIN -> switch (command) {
                    case "logout" -> logout();
                    case "create" -> createGame(params);
                    case "list" -> listGames();
                    case "play" -> playGame(params);
                    case "quit" -> "quit";
                    default -> help();
                };
            };


        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

        private String help () {
            if (state == State.SIGNEDOUT) {
                return """
                        register <USERNAME> <PASSWORD> <EMAIL> - create an account
                        login <USERNAME> <PASSWORD> - sign in
                        help - show this help
                        quit - exit the program
                        """;
            }
            return """
                    logout - sign out
                    help - show this help
                    quit - exit the program
                    """;
        }
        private String register (String[]params) throws ResponseException {
            if (params.length != 3) {
                return "Expected: register <USERNAME> <PASSWORD> <EMAIL>\n";
            }


            var result = facade.register(params[0], params[1], params[2]);

            state = State.SIGNEDIN;
            authToken = result.authToken();
            username = result.username();


            return "Registered + logged in as " + username + "\n";

        }
        private String login (String[]params) throws ResponseException {
            if (params.length != 2) {
                return "Expected: login <USERNAME> <PASSWORD>\n";
            }


            var result = facade.login(params[0], params[1]);

            state = State.SIGNEDIN;
            authToken = result.authToken();
            username = result.username();


            return "Logged in as " + username + "\n";
        }
        private String logout () throws ResponseException {
            facade.logout(authToken);
            state = State.SIGNEDOUT;

            authToken = null;
            username = null;


            return "Logged out\n";
        }


    private String createGame(String[] params) throws ResponseException {
        if (params.length != 1) {
            return "Expected: create <NAME>\n";
        }

        facade.createGame(authToken, params[0]);


        return "Created game: " + params[0] + "\n";
    }
    private String listGames() throws ResponseException {
        var result = facade.listGames(authToken);

        gameIDs.clear();

        var output = new StringBuilder();
        int i = 1;


        for (var game : result.games()) {
            gameIDs.add(game.gameID());
            String white = game.whiteUsername() != null ? game.whiteUsername() : "(empty)";
            String black = game.blackUsername() != null ? game.blackUsername() : "(empty)";
            output.append(i).append(". ").append(game.gameName())
                    .append(" | White: ").append(white)
                    .append(" | Black: ").append(black).append("\n");
            i++;
        }
        if (i == 1) {
            return "No games\n";
        }

        return output.toString();
    }




// run server first, then client



}
