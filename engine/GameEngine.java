public class GameEngine {
    private final Board board;
    private final Symbol[] players;
    private int currentPlayer = 0;

    public GameEngine(int size, Symbol[] players, int winCellCount) {
        this.board = new Board(size, winCellCount);
        this.players = players;
    }

    public MoveResult makeMove(int move) {
        Symbol symbol = players[currentPlayer];

        if (!board.makeMove(move, symbol)) {
            return MoveResult.INVALID;
        }

        if (board.checkWin(symbol)) {
            return MoveResult.WIN;
        }

        if (board.isFull()) {
            return MoveResult.DRAW;
        }

        currentPlayer = currentPlayer == 0 ? 1 : 0;
        return MoveResult.CONTINUE;
    }

    public Symbol getCurrentSymbol() {
        return players[currentPlayer];
    }

    public Board getBoard() {
        return board;
    }
}
