package vttp.batch5.sdf.task02;

public class Game {

    static class Move {
        int row, col;
    }

    static char player = 'X', opponent = 'O';

    private char[][] board = new char[3][3];

    public Game(char[][] board) {
        this.board = board;
    }

    public void drawTheField() {
        System.out.println(
                "Board:" + "\n" +
                        board[0][0] + " " + board[0][1] + " " + board[0][2] + "\n" +
                        board[1][0] + " " + board[1][1] + " " + board[1][2] + "\n" +
                        board[2][0] + " " + board[2][1] + " " + board[2][2] + "\n");
    }

    public Boolean isLegal(char board[][]) {
        int numOfX = 0;
        int numOfO = 0;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == 'X')
                    numOfX++;
                else if (board[i][j] == 'O')
                    numOfO++;

        if (numOfX <= numOfO)
            return true;

        return false;
    }

    static Boolean isMovesLeft(char board[][]) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == '.')
                    return true;
        return false;
    }

    static int evaluate(char b[][]) {
        // Checking for Rows for X or O victory.
        for (int row = 0; row < 3; row++) {
            if (b[row][0] == b[row][1] &&
                    b[row][1] == b[row][2]) {
                if (b[row][0] == player)
                    return +1;
                else if (b[row][0] == opponent)
                    return -1;
            }
        }

        // Checking for Columns for X or O victory.
        for (int col = 0; col < 3; col++) {
            if (b[0][col] == b[1][col] &&
                    b[1][col] == b[2][col]) {
                if (b[0][col] == player)
                    return +1;

                else if (b[0][col] == opponent)
                    return -1;
            }
        }

        // Checking for Diagonals for X or O victory.
        if (b[0][0] == b[1][1] && b[1][1] == b[2][2]) {
            if (b[0][0] == player)
                return +1;
            else if (b[0][0] == opponent)
                return -1;
        }

        if (b[0][2] == b[1][1] && b[1][1] == b[2][0]) {
            if (b[0][2] == player)
                return +1;
            else if (b[0][2] == opponent)
                return -1;
        }

        // Else if none of them have won then return 0
        return 0;
    }

    static int minimax(char board[][], int depth, Boolean isMax) {
        int score = evaluate(board);

        if (score == 1)
            return score;

        if (score == -1)
            return score;

        if (isMovesLeft(board) == false)
            return 0;

        if (isMax) {
            int best = -100;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '.') {
                        board[i][j] = player;

                        best = Math.max(best, minimax(board, depth + 1, !isMax));

                        board[i][j] = '.';
                    }
                }
            }
            return best;
        }

        else {
            int best = 100;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '.') {
                        board[i][j] = opponent;

                        best = Math.min(best, minimax(board, depth + 1, !isMax));

                        board[i][j] = '.';
                    }
                }
            }
            return best;
        }
    }

    public void listPossibleMoves(char board[][]) {
        Move possibleMove = new Move();
        possibleMove.row = -1;
        possibleMove.col = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '.') {
                    System.out.printf("y=%d, x=%d, utility=", i, j);
                    board[i][j] = player;

                    int moveVal = minimax(board, 0, false);
                    System.out.println(moveVal);

                    board[i][j] = '.';
                }
            }
        }
    }
}
