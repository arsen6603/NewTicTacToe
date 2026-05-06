import java.util.*;

public class DumbBot implements Player {
    private final Symbol symbol;
    private final Random random = new Random();

    public DumbBot(Symbol symbol) {
        this.symbol = symbol;
    }

    @Override
    public int makeMove(Board board) {
        List<Integer> moves = board.getAvailableMoves();
        return moves.get(random.nextInt(moves.size()));
    }

    @Override
    public Symbol getSymbol() {
        return symbol;
    }
}