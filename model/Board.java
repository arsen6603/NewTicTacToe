import java.util.*;

public class Board {
    private final int size;
    private final Symbol[][] grid;
    private final int winCellCount;

    public Board(int size) {
        this.size = size;
        this.grid = new Symbol[size][size];
        this.winCellCount = 5;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = Symbol.EMPTY;
            }
        }
    }

    public Board(int size, int winCellCount) {
        this.size = size;
        this.grid = new Symbol[size][size];
        this.winCellCount = winCellCount;

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
        int cellCount;

        for (int i = 0; i < size; i++) {
            cellCount = 0;
            for (int j = 0; j < size; j++) {
                cellCount = grid[i][j] == symbol ? cellCount + 1 : 0;
                if (cellCount == winCellCount)
                    return true;
            }
        }

        for (int j = 0; j < size; j++) {
            cellCount = 0;
            for (int i = 0; i < size; i++) {
                cellCount = grid[i][j] == symbol ? cellCount + 1 : 0;
                if (cellCount == winCellCount)
                    return true;
            }
        }

        for (int c = 0; c <= size - winCellCount; c++) {
            cellCount = 0;
            for (int i = 0, j = c; i < size && j < size; i++, j++) {
                cellCount = grid[i][j] == symbol ? cellCount + 1 : 0;
                if (cellCount == winCellCount)
                    return true;
            }
        }

        for (int r = 1; r <= size - winCellCount; r++) {
            cellCount = 0;
            for (int i = r, j = 0; i < size && j < size; i++, j++) {
                cellCount = grid[i][j] == symbol ? cellCount + 1 : 0;
                if (cellCount == winCellCount)
                    return true;
            }
        }

        for (int c = winCellCount - 1; c < size; c++) {
            cellCount = 0;
            for (int i = 0, j = c; i < size && j >= 0; i++, j--) {
                cellCount = grid[i][j] == symbol ? cellCount + 1 : 0;
                if (cellCount == winCellCount)
                    return true;
            }
        }

        for (int r = 1; r <= size - winCellCount; r++) {
            cellCount = 0;
            for (int i = r, j = size - 1; i < size && j >= 0; i++, j--) {
                cellCount = grid[i][j] == symbol ? cellCount + 1 : 0;
                if (cellCount == winCellCount)
                    return true;
            }
        }

        return false;
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

    public int getWinCellCount() {
        return winCellCount;
    }

    private int rowOf(int position) {
        return (position - 1) / size;
    }

    private int colOf(int position) {
        return (position - 1) % size;
    }
}
