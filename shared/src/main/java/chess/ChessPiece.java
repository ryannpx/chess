package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
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

            //down left, up left, up right
            //r-1 c-1     r +1c-1    r+1 c+1

            int row = myPosition.getRow();
            int col = myPosition.getColumn();
            //down, right
            int r = row -1;
            int c = col +1;
            while (r >= 1 && r <= 8 && c >= 1 && c <= 8) { //while its on the board
//                moves.add(new ChessMove(myPosition, new ChessPosition(r,c) , null));
//                r = r -1;
//                c = c +1;
                ChessPiece occupant = board.getPiece(new ChessPosition(r ,c));

                if (occupant == null) { //emtpy check
                    moves.add(new ChessMove(myPosition, new ChessPosition(r,c) , null));
                }
                else if (occupant.getTeamColor() != piece.getTeamColor()){ //enemy check
                    moves.add(new ChessMove(myPosition, new ChessPosition(r,c) , null));
                    break;
                }
                else { //friend check
                    break;
                }
                r = r - 1;
                c = c + 1;
            }
            // down-left
            r = row - 1;
            c = col - 1;
            while (r >= 1 && r <= 8 && c >= 1 && c <= 8) {
                //moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
                ChessPiece occupant = board.getPiece(new ChessPosition(r, c));

                if (occupant == null) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r,c) , null));
                }
                else if (occupant.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r,c) , null));
                    break;
                }
                else {
                    break;
                }
                r = r - 1;
                c = c - 1;
            }
            //up left
            r = row + 1;
            c = col -1;
            while (r>=1 && r<= 8 && c>= 1 && c <=8) {
                //moves.add(new ChessMove(myPosition, new ChessPosition(r,c) , null));
                ChessPiece occupant = board.getPiece(new ChessPosition(r, c));
                if (occupant == null) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r,c) , null));
                }
                else if (occupant.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r,c) , null));
                    break;
                }
                else {
                    break;
                }
                r = r +1;
                c = c -1;
            }
            //up right
            r = row +1;
            c = col +1;
            while (r>=1 && r<=8 && c>=1 && c<=8){
                //moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
                ChessPiece occupant = board.getPiece(new ChessPosition(r ,c));
                if (occupant == null) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r,c) , null));
                }
                else if (occupant.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r,c) , null));
                    break;
                }
                else {
                    break;
                }
                r = r +1;
                c = c+1;
            }




            return moves;
            //return List.of(new ChessMove(new ChessPosition(5,4), new ChessPosition(1, 8), null));//hardcoded instead of empty list
        }

        if (piece.getPieceType() == PieceType.ROOK) {
            // right, left, up, down
            Collection<ChessMove> moves = new ArrayList<>();
            int row = myPosition.getRow();
            int col = myPosition.getColumn();
            // right
            int r = row;
            int c = col +1;
            while (r>=1 && r<=8 && c>=1 && c<=8) {
                ChessPiece occupant = board.getPiece(new ChessPosition(r, c));
                if (occupant == null) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r,c ), null));
                }
                else if (occupant.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
                    break;
                }
                else {
                    break;
                }
                c = c +1;
            }
            //left c -1
            r = row;
            c = col - 1;
            while (r>=1 && r<=8 && c>=1 && c<=8) {
                ChessPiece occupant = board.getPiece(new ChessPosition(r, c));
                if (occupant == null) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r,c ), null));
                }
                else if (occupant.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
                    break;
                }
                else {
                    break;
                }
                c = c -1;
            }
            //up r + 1
            r = row +1;
            c = col;
            while (r>=1 && r<=8 && c>=1 && c<=8) {
                ChessPiece occupant = board.getPiece(new ChessPosition(r, c));
                if (occupant == null) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r,c ), null));
                }
                else if (occupant.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
                    break;
                }
                else {
                    break;
                }
                r = r +1;

            }
            //down r-1
            r = row -1;
            c = col;
            while (r>=1 && r<=8 && c>=1 && c<=8) {
                ChessPiece occupant = board.getPiece(new ChessPosition(r, c));
                if (occupant == null) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r,c ), null));
                }
                else if (occupant.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
                    break;
                }
                else {
                    break;
                }
                r = r-1;

            }

            return moves;
        }

        if (piece.getPieceType() == PieceType.QUEEN){
            Collection<ChessMove> moves = new ArrayList<>();
            int row = myPosition.getRow();
            int col = myPosition.getColumn();
            // right
            int r = row;
            int c = col +1;
            while (r>=1 && r<=8 && c>=1 && c<=8) {
                ChessPiece occupant = board.getPiece(new ChessPosition(r, c));
                if (occupant == null) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r,c ), null));
                }
                else if (occupant.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
                    break;
                }
                else {
                    break;
                }
                c = c +1;
            }
            //left c -1
            r = row;
            c = col - 1;
            while (r>=1 && r<=8 && c>=1 && c<=8) {
                ChessPiece occupant = board.getPiece(new ChessPosition(r, c));
                if (occupant == null) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r,c ), null));
                }
                else if (occupant.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
                    break;
                }
                else {
                    break;
                }
                c = c -1;
            }
            //up r + 1
            r = row +1;
            c = col;
            while (r>=1 && r<=8 && c>=1 && c<=8) {
                ChessPiece occupant = board.getPiece(new ChessPosition(r, c));
                if (occupant == null) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r,c ), null));
                }
                else if (occupant.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
                    break;
                }
                else {
                    break;
                }
                r = r +1;

            }
            //down r-1
            r = row -1;
            c = col;
            while (r>=1 && r<=8 && c>=1 && c<=8) {
                ChessPiece occupant = board.getPiece(new ChessPosition(r, c));
                if (occupant == null) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r,c ), null));
                }
                else if (occupant.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
                    break;
                }
                else {
                    break;
                }
                r = r-1;

            }

            //down, right
            r = row - 1;
            c = col + 1;
            while (r >= 1 && r <= 8 && c >= 1 && c <= 8) { //while its on the board
//                moves.add(new ChessMove(myPosition, new ChessPosition(r,c) , null));
//                r = r -1;
//                c = c +1;
                ChessPiece occupant = board.getPiece(new ChessPosition(r ,c));

                if (occupant == null) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r,c) , null));
                }
                else if (occupant.getTeamColor() != piece.getTeamColor()){
                    moves.add(new ChessMove(myPosition, new ChessPosition(r,c) , null));
                    break;
                }
                else {
                    break;
                }
                r = r - 1;
                c = c + 1;
            }
            // down-left
            r = row - 1;
            c = col - 1;
            while (r >= 1 && r <= 8 && c >= 1 && c <= 8) {
                //moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
                ChessPiece occupant = board.getPiece(new ChessPosition(r, c));

                if (occupant == null) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r,c) , null));
                }
                else if (occupant.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r,c) , null));
                    break;
                }
                else {
                    break;
                }
                r = r - 1;
                c = c - 1;
            }
            //up left
            r = row + 1;
            c = col -1;
            while (r>=1 && r<= 8 && c>= 1 && c <=8) {
                //moves.add(new ChessMove(myPosition, new ChessPosition(r,c) , null));
                ChessPiece occupant = board.getPiece(new ChessPosition(r, c));
                if (occupant == null) { //empty
                    moves.add(new ChessMove(myPosition, new ChessPosition(r,c) , null));
                }
                else if (occupant.getTeamColor() != piece.getTeamColor()) { //enemy
                    moves.add(new ChessMove(myPosition, new ChessPosition(r,c) , null));
                    break;
                }
                else { //friend
                    break;
                }
                r = r +1;
                c = c -1;
            }
            //up right
            r = row +1;
            c = col +1;
            while (r>=1 && r<=8 && c>=1 && c<=8){
                //moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
                ChessPiece occupant = board.getPiece(new ChessPosition(r ,c));
                if (occupant == null) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r,c) , null));
                }
                else if (occupant.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r,c) , null));
                    break;
                }
                else {
                    break;
                }
                r = r +1;
                c = c+1;
            }

            return moves;
        }

        if (getPieceType() == PieceType.KNIGHT) {
            Collection<ChessMove> moves= new ArrayList<>();

            int row = myPosition.getRow();
            int col = myPosition.getColumn();

            int[][] jumps = { //all possible combos of 1,-1,2,-2
                    {2, 1},
                    {2, -1},
                    {-2, 1},
                    {-2, -1},
                    {1, 2},
                    {1, -2},
                    {-1, 2},
                    {-1, -2}
            };

            for (int[] jump : jumps) {// loop that checks as long as its on the board
                int r = row + jump[0];
                int c = col + jump[1];

                if (r < 1 || r > 8 || c < 1 || c > 8) {
                    continue;
                }

                ChessPiece occupant = board.getPiece(new ChessPosition(r, c));
                if (occupant == null) { //empty
                    moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
                } else if (occupant.getTeamColor() != piece.getTeamColor()) { //enemy
                    moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
                }
            }

            return moves;
        }

        if (piece.getPieceType() == PieceType.KING) {
            Collection<ChessMove> moves= new ArrayList<>();
            int row = myPosition.getRow();
            int col = myPosition.getColumn();

            int[][] steps = { //1 square in each direction for 0 1 -1
                    {1, 0},
                    {-1, 0},
                    {0, 1},
                    {0, -1},
                    {1, 1},
                    {1, -1},
                    {-1, 1},
                    {-1, -1}
            };
            for (int[] step : steps) {
                int r = row + step[0];
                int c = col + step[1];
                if (r < 1 || r > 8 || c < 1 || c > 8) {
                    continue;
                }
                ChessPiece occupant = board.getPiece(new ChessPosition(r, c));
                if (occupant == null) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
                } else if (occupant.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
                }
            }
            return moves;
        }

        if (getPieceType() == PieceType.PAWN) {
            Collection<ChessMove> moves = new ArrayList<>();
            int row = myPosition.getRow();
            int col = myPosition.getColumn();

            int direction;
            int startRow;
            int promoRow;
            if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                direction = 1;
                startRow = 2;
                promoRow = 8;
            } else {
                direction = -1;
                startRow = 7;
                promoRow = 1;
            }

            int oneRow = row + direction;
            if (oneRow >= 1 && oneRow <= 8) {
                ChessPosition oneAhead = new ChessPosition(oneRow, col);
                if (board.getPiece(oneAhead) == null) {
                    addPawnMove(moves, myPosition, oneAhead, promoRow);

                    int twoRow = row + 2 * direction;
                    if (row == startRow && twoRow >= 1 && twoRow <= 8) {
                        ChessPosition twoAhead = new ChessPosition(twoRow, col);
                        if (board.getPiece(twoAhead) == null) {
                            addPawnMove(moves, myPosition, twoAhead, promoRow);
                        }
                    }
                }
            }

            int[] captureCols = {col - 1, col + 1};
            for (int captureCol : captureCols) {
                if (oneRow < 1 || oneRow > 8 || captureCol < 1 || captureCol > 8) {
                    continue;
                }
                ChessPosition capturePos = new ChessPosition(oneRow, captureCol);
                ChessPiece occupant = board.getPiece(capturePos);
                if (occupant != null && occupant.getTeamColor() != piece.getTeamColor()) {
                    addPawnMove(moves, myPosition, capturePos, promoRow);
                }
            }

            return moves;
        }



        return new ArrayList<>(); // returns an empty list of object
    }


    private void addPawnMove(Collection<ChessMove> moves, ChessPosition start,
                             ChessPosition end, int promoRow) {
        if (end.getRow() == promoRow) {
            moves.add(new ChessMove(start, end, PieceType.QUEEN));
            moves.add(new ChessMove(start, end, PieceType.BISHOP));
            moves.add(new ChessMove(start, end, PieceType.ROOK));
            moves.add(new ChessMove(start, end, PieceType.KNIGHT));
        } else {
            moves.add(new ChessMove(start, end, null));
        }
    }
}
