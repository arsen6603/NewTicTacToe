import java.util.*;

public class Board {
    private final int size;
    private final Symbol[][] grid;

    public Board(int size) {
        this.size = size;
        this.grid = new Symbol[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = Symbol.EMPTY;
            }
        }
    }

    public boolean makeMove(int position, Symbol symbol) {
        if (position < 1 || position > size * size) {
            return false;
        }

        if (grid[rowOf(position)][colOf(position)] != Symbol.EMPTY) {
            return false;
        }

        grid[rowOf(position)][colOf(position)] = symbol;
        return true;
    }

    public List<Integer> getAvailableMoves() {
        List<Integer> moves = new ArrayList<>();
        for (int pos = 1; pos <= size * size; pos++) {
            if (grid[rowOf(pos)][colOf(pos)] == Symbol.EMPTY) {
                moves.add(pos);
            }
        }
        return moves;
    }

    public boolean checkWin(Symbol symbol) {
        for (int i = 0; i < size; i++) {
            boolean win = true;
            for (int j = 0; j < size; j++) {
                if (grid[i][j] != symbol) {
                    win = false;
                    break;
                }
            }
            if (win) {
                return true;
            }
        }

        for (int j = 0; j < size; j++) {
            boolean win = true;
            for (int i = 0; i < size; i++) {
                if (grid[i][j] != symbol) {
                    win = false;
                    break;
                }
            }
            if (win) {
                return true;
            }
        }

        boolean diag = true;
        for (int i = 0; i < size; i++) {
            if (grid[i][i] != symbol) {
                diag = false;
                break;
            }
        }
        if (diag) {
            return true;
        }

        diag = true;
        for (int i = 0; i < size; i++) {
            if (grid[i][size - i - 1] != symbol) {
                diag = false;
                break;
            }
        }
        return diag;
    }

    public boolean isFull() {
        return getAvailableMoves().isEmpty();
    }

    public int getSize() {
        return size;
    }

    public Symbol[][] getGrid() {
        return grid;
    }

    private int rowOf(int position) {
        return (position - 1) / size;
    }

    private int colOf(int position) {
        return (position - 1) % size;
    }
}
