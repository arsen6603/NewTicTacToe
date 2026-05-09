import java.util.ArrayList;
import java.util.List;

public class SmartBot implements Player {
    private final Symbol symbol;
    private final Symbol opponent;
    private static final int MAX_DEPTH = 3;
    private static final int WIN_SCORE = 1_000_000;

    public SmartBot(Symbol symbol) {
        this.symbol = symbol;
        this.opponent = (symbol == Symbol.X) ? Symbol.O : Symbol.X;
    }

    @Override
    public int makeMove(Board board) {
        List<Integer> candidates = getCandidateMoves(board);
        if (candidates.isEmpty()) {
            int mid = board.getSize() / 2;
            return mid * board.getSize() + mid + 1;
        }

        int bestScore = Integer.MIN_VALUE;
        int bestMove = candidates.get(0);

        for (int move : candidates) {
            Board copy = cloneBoard(board);
            copy.makeMove(move, symbol);
            int score = minimax(copy, MAX_DEPTH - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        return bestMove;
    }

    private int minimax(Board board, int depth, int alpha, int beta, boolean isMaximizing) {
        if (board.checkWin(symbol)) return WIN_SCORE + depth;
        if (board.checkWin(opponent)) return -(WIN_SCORE + depth);
        if (board.isFull() || depth == 0) return evaluate(board);

        List<Integer> candidates = getCandidateMoves(board);
        if (candidates.isEmpty()) return evaluate(board);

        if (isMaximizing) {
            int best = Integer.MIN_VALUE;
            for (int move : candidates) {
                Board copy = cloneBoard(board);
                copy.makeMove(move, symbol);
                best = Math.max(best, minimax(copy, depth - 1, alpha, beta, false));
                alpha = Math.max(alpha, best);
                if (beta <= alpha) break;
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int move : candidates) {
                Board copy = cloneBoard(board);
                copy.makeMove(move, opponent);
                best = Math.min(best, minimax(copy, depth - 1, alpha, beta, true));
                beta = Math.min(beta, best);
                if (beta <= alpha) break;
            }
            return best;
        }
    }

    private List<Integer> getCandidateMoves(Board board) {
        int size = board.getSize();
        Symbol[][] grid = board.getGrid();
        boolean[][] marked = new boolean[size][size];
        List<Integer> candidates = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == Symbol.EMPTY) continue;
                for (int di = -2; di <= 2; di++) {
                    for (int dj = -2; dj <= 2; dj++) {
                        int ni = i + di, nj = j + dj;
                        if (ni >= 0 && ni < size && nj >= 0 && nj < size
                                && grid[ni][nj] == Symbol.EMPTY && !marked[ni][nj]) {
                            marked[ni][nj] = true;
                            candidates.add(ni * size + nj + 1);
                        }
                    }
                }
            }
        }
        return candidates;
    }

    private int evaluate(Board board) {
        return scoreFor(board, symbol) - scoreFor(board, opponent);
    }

    private int scoreFor(Board board, Symbol sym) {
        int size = board.getSize();
        int W = board.getWinCellCount();
        Symbol[][] g = board.getGrid();
        Symbol opp = (sym == Symbol.X) ? Symbol.O : Symbol.X;
        int score = 0;

        int[][] dirs = {{0, 1}, {1, 0}, {1, 1}, {1, -1}};
        for (int[] d : dirs) {
            for (int r = 0; r < size; r++) {
                for (int c = 0; c < size; c++) {
                    int er = r + d[0] * (W - 1);
                    int ec = c + d[1] * (W - 1);
                    if (er < 0 || er >= size || ec < 0 || ec >= size) continue;
                    int myCount = 0, opCount = 0;
                    for (int k = 0; k < W; k++) {
                        Symbol s = g[r + d[0] * k][c + d[1] * k];
                        if (s == sym) myCount++;
                        else if (s == opp) opCount++;
                    }
                    if (opCount == 0) score += scoreCount(myCount, W);
                }
            }
        }
        return score;
    }

    private int scoreCount(int count, int W) {
        if (count == W)     return WIN_SCORE;
        if (count == W - 1) return 10000;
        if (count == W - 2) return 100;
        if (count == W - 3) return 10;
        return 1;
    }

    private Board cloneBoard(Board board) {
        Board copy = new Board(board.getSize(), board.getWinCellCount());
        Symbol[][] grid = board.getGrid();
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                copy.getGrid()[i][j] = grid[i][j];
            }
        }
        return copy;
    }

    @Override
    public Symbol getSymbol() {
        return symbol;
    }
}
