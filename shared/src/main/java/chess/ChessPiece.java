package chess;

import java.util.ArrayList;
import java.util.Collection;
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

    // Sliding pieces keep going in a direction until they hit the edge or a piece.
    // Queen = bishop diagonals + rook rows/columns.
    private static final int[][] BISHOP_DIRS = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
    private static final int[][] ROOK_DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    // Stepping pieces try each offset once (they do not keep sliding).
    private static final int[][] KNIGHT_JUMPS = {
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
    };
    private static final int[][] KING_STEPS = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
    };

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
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        if (type == PieceType.BISHOP) {
            return slide(board, myPosition, BISHOP_DIRS);
        }
        if (type == PieceType.ROOK) {
            return slide(board, myPosition, ROOK_DIRS);
        }
        if (type == PieceType.QUEEN) {
            Collection<ChessMove> moves = slide(board, myPosition, BISHOP_DIRS);
            moves.addAll(slide(board, myPosition, ROOK_DIRS));
            return moves;
        }
        if (type == PieceType.KNIGHT) {
            return step(board, myPosition, KNIGHT_JUMPS);
        }
        if (type == PieceType.KING) {
            return step(board, myPosition, KING_STEPS);
        }
        if (type == PieceType.PAWN) {
            return pawnMoves(board, myPosition);
        }
        return new ArrayList<>();
    }

    /**
     * Keep moving in each direction: empty = add and continue, enemy = add and stop, friend = stop.
     */
    private Collection<ChessMove> slide(ChessBoard board, ChessPosition start, int[][] dirs) {
        Collection<ChessMove> moves = new ArrayList<>();
        for (int[] dir : dirs) {
            int row = start.getRow() + dir[0];
            int col = start.getColumn() + dir[1];
            while (onBoard(row, col)) {
                ChessPosition end = new ChessPosition(row, col);
                ChessPiece occupant = board.getPiece(end);
                if (occupant == null) {
                    moves.add(new ChessMove(start, end, null));
                } else {
                    if (occupant.getTeamColor() != pieceColor) {
                        moves.add(new ChessMove(start, end, null));
                    }
                    break;
                }
                row += dir[0];
                col += dir[1];
            }
        }
        return moves;
    }

    /**
     * Try each offset once: empty or enemy = add, friend or off-board = skip.
     */
    private Collection<ChessMove> step(ChessBoard board, ChessPosition start, int[][] offsets) {
        Collection<ChessMove> moves = new ArrayList<>();
        for (int[] offset : offsets) {
            int row = start.getRow() + offset[0];
            int col = start.getColumn() + offset[1];
            if (!onBoard(row, col)) {
                continue;
            }
            ChessPosition end = new ChessPosition(row, col);
            ChessPiece occupant = board.getPiece(end);
            if (occupant == null || occupant.getTeamColor() != pieceColor) {
                moves.add(new ChessMove(start, end, null));
            }
        }
        return moves;
    }

    private Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition start) {
        Collection<ChessMove> moves = new ArrayList<>();
        int row = start.getRow();
        int col = start.getColumn();

        int direction;
        int startRow;
        int promoRow;
        if (pieceColor == ChessGame.TeamColor.WHITE) {
            direction = 1;
            startRow = 2;
            promoRow = 8;
        } else {
            direction = -1;
            startRow = 7;
            promoRow = 1;
        }

        int oneRow = row + direction;
        if (onBoard(oneRow, col) && board.getPiece(new ChessPosition(oneRow, col)) == null) {
            addPawnMove(moves, start, new ChessPosition(oneRow, col), promoRow);

            int twoRow = row + 2 * direction;
            if (row == startRow && onBoard(twoRow, col)
                    && board.getPiece(new ChessPosition(twoRow, col)) == null) {
                addPawnMove(moves, start, new ChessPosition(twoRow, col), promoRow);
            }
        }

        int[] captureCols = {col - 1, col + 1};
        for (int captureCol : captureCols) {
            if (!onBoard(oneRow, captureCol)) {
                continue;
            }
            ChessPosition capturePos = new ChessPosition(oneRow, captureCol);
            ChessPiece occupant = board.getPiece(capturePos);
            if (occupant != null && occupant.getTeamColor() != pieceColor) {
                addPawnMove(moves, start, capturePos, promoRow);
            }
        }

        return moves;
    }

    private boolean onBoard(int row, int col) {
        return row >= 1 && row <= 8 && col >= 1 && col <= 8;
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
