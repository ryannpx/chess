package websocket.commands;
import chess.ChessMove; // ma chess moves written

public class MakeTheMoveCommand extends UserGameCommand {
    private final ChessMove move;


    public MakeTheMoveCommand(String authToken,Integer gameID, ChessMove move) {
        super(CommandType.MAKE_MOVE,authToken,gameID);
        this.move = move; // tells it to actually make the move
    }


    public ChessMove getMove() {
        return move; //pulls move
    }



}
