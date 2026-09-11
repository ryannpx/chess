package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
private ChessPiece[][] board = new ChessPiece[8][8];
    private ChessPosition position;
    private ChessPiece piece;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Arrays.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    //ChessPiece[][] = new ChessPiece[8][8];
    public ChessBoard() {
        
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        this.position = position;
        this.piece = piece;
        //throw new RuntimeException("Not implemented");
        board[position.getRow()-1][position.getColumn()-1] = piece;
        //squares[position.getRow()-1][position.getColumn()-1] = piece;
        // -1 takes from a 0 based index so instead of 0-7 you have 1-8
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return
     */
    public ChessPiece getPiece(ChessPosition position) {
        //throw new RuntimeException("Not implemented");
        return board[position.getRow()-1][position.getColumn()-1];
        //return squares[position.getRow()-1][position.getColumn()-1] = piece;
        // -1 takes from a 0 based index so instead of 0-7 you have 1-8

    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        //this is where i build the board
        board = new ChessPiece[8][8];

        ChessPiece.PieceType[] backRank = { //board setup along the bottom
                ChessPiece.PieceType.ROOK,
                ChessPiece.PieceType.KNIGHT,
                ChessPiece.PieceType.BISHOP,
                ChessPiece.PieceType.QUEEN,
                ChessPiece.PieceType.KING,
                ChessPiece.PieceType.BISHOP,
                ChessPiece.PieceType.KNIGHT,
                ChessPiece.PieceType.ROOK,
        };


//        8  r n b q k b n r     black
//        7  p p p p p p p p
//        6  (empty)
//        5  (empty)
//        4  (empty)
//        3  (empty)
//        2  P P P P P P P P
//        1  R N B Q K B N R     white
//        1 2 3 4 5 6 7 8

        for (int col = 1; col <=8; col++) { //the one and 2 are the two bottom rows, 7 and 8 and the top ones so black
            addPiece(new ChessPosition(1,col),new ChessPiece(ChessGame.TeamColor.WHITE, backRank[col -1])); //places the white backrank
            addPiece(new ChessPosition(2,col),new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN)); //puts white pawns on the next row
            addPiece(new ChessPosition(8,col),new ChessPiece(ChessGame.TeamColor.BLACK, backRank[col -1]));
            addPiece(new ChessPosition(7,col),new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));

        }

        //throw new RuntimeException("Not implemented");
    }
}
