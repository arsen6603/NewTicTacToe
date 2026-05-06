public class SmartBot implements Player {
    private final Symbol symbol;
    private final Symbol opponent;

    public SmartBot(Symbol symbol) {
        this.symbol = symbol;
        this.opponent = (symbol == Symbol.X) ? Symbol.O : Symbol.X;
    }

    @Override
    public int makeMove(Board board) {
        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;

        for (int move : board.getAvailableMoves()) {
            Board copy = cloneBoard(board);
            copy.makeMove(move, symbol);

            int score = minimax(copy, false);

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        return bestMove;
    }

    private int minimax(Board board, boolean isMaximizing) {
        if (board.checkWin(symbol)) {
            return 1;
        }
        if (board.checkWin(opponent)) {
            return -1;
        }
        if (board.isFull()) {
            return 0;
        }

        if (isMaximizing) {
            int best = Integer.MIN_VALUE;
            for (int move : board.getAvailableMoves()) {
                Board copy = cloneBoard(board);
                copy.makeMove(move, symbol);
                best = Math.max(best, minimax(copy, false));
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int move : board.getAvailableMoves()) {
                Board copy = cloneBoard(board);
                copy.makeMove(move, opponent);
                best = Math.min(best, minimax(copy, true));
            }
            return best;
        }
    }

    private Board cloneBoard(Board board) {
        Board newBoard = new Board(board.getSize());
        Symbol[][] grid = board.getGrid();

        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                newBoard.getGrid()[i][j] = grid[i][j];
            }
        }

        return newBoard;
    }

    @Override
    public Symbol getSymbol() {
        return symbol;
    }
}