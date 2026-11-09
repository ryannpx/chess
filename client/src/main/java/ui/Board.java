package ui;

import chess.ChessBoard;
import chess.ChessGame;

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



        }

    }
}
