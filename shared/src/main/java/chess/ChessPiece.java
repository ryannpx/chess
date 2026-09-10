package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
        //throw new RuntimeException("Not implemented");

    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        //throw new RuntimeException("Not implemented");
        ChessPiece piece = board.getPiece(myPosition);
        if (piece.getPieceType() == PieceType.BISHOP) {
            Collection<ChessMove> moves = new ArrayList<>();

            //downleft, up left, up right
            //r-1 c-1     r +1c-1    r+1 c+1

            int row = myPosition.getRow();
            int col = myPosition.getColumn();
            //down, right
            int r = row -1;
            int c = col +1;
            while (r >= 1 && r <= 8 && c >= 1 && c <= 8) { //while its on the board
                moves.add(new ChessMove(myPosition, new ChessPosition(r,c) , null));
                r = r -1;
                c = c +1;
            }




            return moves;
            //return List.of(new ChessMove(new ChessPosition(5,4), new ChessPosition(1, 8), null));//hardcoded instead of empty list
        }
        return moves; // returns an empty list of object
    }
}
