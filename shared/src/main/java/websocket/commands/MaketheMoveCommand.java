package websocket.commands;
import chess.ChessMove; // ma chess moves written

public class MaketheMoveCommand extends UserGameCommand {
    private final ChessMove move;


    public MaketheMoveCommand(String authToken,Integer gameID, ChessMove move) {
        super(CommandType.MAKE_MOVE,authToken,gameID);
        this.move = move;
    }
}
