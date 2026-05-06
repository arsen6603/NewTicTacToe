import java.util.Scanner;

public class HumanPlayer implements Player {
    private final Symbol symbol;
    private final Scanner scanner;

    public HumanPlayer(Symbol symbol, Scanner scanner) {
        this.symbol = symbol;
        this.scanner = scanner;
    }

    @Override
    public int makeMove(Board board) {
        while (true) {
            System.out.print("> ");
            try {
                int move = Integer.parseInt(scanner.nextLine());
                if (board.getAvailableMoves().contains(move)) {
                    return move;
                }
            } catch (Exception ignored) {
            }

            System.out.println("Invalid number!");
        }
    }

    @Override
    public Symbol getSymbol() {
        return symbol;
    }
}