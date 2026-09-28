package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard board;
    private TeamColor teamTurn;


    public ChessGame() {
        board = new ChessBoard();
        board.resetBoard();
        teamTurn = TeamColor.WHITE;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
        //throw new RuntimeException("Not implemented"); //IDKKKKKKKKKKKKKKKKK
    }

    /**
     * Sets which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets all valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
       // throw new RuntimeException("Not implemented");
        ChessPiece piece = board.getPiece(startPosition);
        if (piece == null){
            return null;
        }
        Collection<ChessMove> isLegal = new ArrayList<>();
        for (ChessMove move : piece.pieceMoves(board,startPosition)){
            ChessBoard original = board;
            board = copyBoard();
            ChessPiece.PieceType promo = move.getPromotionPiece();
            if (promo != null) {
                board.addPiece(move.getEndPosition(), new ChessPiece(piece.getTeamColor(), promo));
            } else {
                board.addPiece(move.getEndPosition(), piece);
            }
            board.addPiece(move.getStartPosition(), null);
            boolean kingInCheck = isInCheck(piece.getTeamColor());
            board = original;
            if (!kingInCheck) {
                isLegal.add(move);
            }

        }
        return isLegal;
    }

    /**
     * Makes a move in the chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
       // throw new RuntimeException("Not implemented");

        ChessPiece piece = board.getPiece(move.getStartPosition());
        if (piece == null){
            throw new InvalidMoveException(); //up there
        }
        if (piece.getTeamColor() != teamTurn) {
            throw new InvalidMoveException();
        }
        Collection<ChessMove> legal = validMoves(move.getStartPosition());
        if (!legal.contains(move)) {
            throw new InvalidMoveException();
        }
        // same put down and pick up as validMove but on  real board
        ChessPiece.PieceType promo = move.getPromotionPiece();
        if (promo != null) {
            board.addPiece(move.getEndPosition(), new ChessPiece(piece.getTeamColor(), promo));
        } else {
            board.addPiece(move.getEndPosition(), piece);
        }
        board.addPiece(move.getStartPosition(), null);
        if (teamTurn == TeamColor.WHITE) {
            teamTurn = TeamColor.BLACK;
        } else {
            teamTurn = TeamColor.WHITE;
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPosition = null;

        // find the king
        for (int row =1; row <=8; row++ ) {
            for (int col = 1; col <= 8; col++) {

                ChessPosition pos = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(pos);

                if (piece != null && piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == teamColor) {
                    kingPosition = pos;
                }

            }
        }
        // see if any enemy can get the king
        for (int row =1; row <=8; row++ ){
            for (int col = 1; col <= 8; col++) {

                ChessPosition pos = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(pos);

                if (piece == null){
                    continue;
                }
                if (piece.getTeamColor() == teamColor){
                    continue;
                }
                Collection<ChessMove> moves = piece.pieceMoves(board, pos);
                for (ChessMove move : moves) {
                    if (move.getEndPosition().equals(kingPosition)){
                        return true;
                    }
                }
            }
        }
        return false;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard to a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board= board;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
        //throw new RuntimeException("Not implemented");
    }

//makes a copy so the piece isnt in a fake position
    private ChessBoard copyBoard() {
        ChessBoard copy = new ChessBoard();
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition pos = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(pos);
                if (piece != null) {
                    copy.addPiece(pos, piece);
                }
            }
        }
        return copy;
    }
}
