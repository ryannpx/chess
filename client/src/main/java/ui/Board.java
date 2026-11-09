package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import static ui.EscapeSequences.*;

public class Board {
    public static String print(ChessBoard board, ChessGame.TeamColor perspective) {
        var output = new StringBuilder();

        boolean whiteView = perspective == ChessGame.TeamColor.WHITE;

        int rowStart = whiteView ? 8 : 1;
        int rowEnd = whiteView ? 1 : 8;
        int rowStep = whiteView ? -1 : 1;
        int colStart = whiteView ? 1 : 8;
        int colEnd = whiteView ? 1 : 8;
        int colStep = whiteView ? 1 : -1;


        output.append(header(colStart,colEnd,colStep));

        for (int row = rowStart; whiteView ? row >= rowEnd : row <= rowEnd; row += rowStep) {
            output.append(""),append(row).append("");

            for (int col = colStart; whiteView ? col <= colEnd : col >= colEnd; col += colStep) {
                boolean white = (row + col) % 2 !0;
                String background = white ? SET_BG_COLOR_LIGHT_GREY : SET_BG_COLOR_DARK_GREY;
                ChessPiece piece = board.getPiece(new ChessPosition(row,col));
                output.append(background).append(pieceString(piece)).append(RESET_BG_COLOR);
            }
            output.append(" ").append(row).append("\n");
        }
        output.append(header(colStart, colEnd, colStep));
        output.append(RESET_TEXT_COLOR);
        return output.toString();


        }
    private static String header(int colStart, int colEnd, int colStep) {
        var line = new StringBuilder("    ");
        for (int col = colStart; colStep > 0 ? col <= colEnd : col >= colEnd; col += colStep) {
            line.append(" ").append((char) ('a' + col - 1)).append(" ");
        }
        return line.append("\n").toString();
    }



    private static String pieceString(ChessPiece piece) {
        if (piece == null) {
            return EMPTY;
        }


        boolean white = piece.getTeamColor() == ChessGame.TeamColor.WHITE;
        return switch (piece.getPieceType()) {

            case KING -> white ? WHITE_KING : BLACK_KING;
            case QUEEN -> white ? WHITE_QUEEN : BLACK_QUEEN;
            case BISHOP -> white ? WHITE_BISHOP : BLACK_BISHOP;
            case KNIGHT -> white ? WHITE_KNIGHT : BLACK_KNIGHT;
            case ROOK -> white ? WHITE_ROOK : BLACK_ROOK;
            case PAWN -> white ? WHITE_PAWN : BLACK_PAWN;
        };
    }
}
