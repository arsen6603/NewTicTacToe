import java.util.Scanner;

public class TicTacToe {
    public void launch() {
        Scanner scanner = new Scanner(System.in);

        outer: while (true) {
            System.out.println("1: Human vs Human");
            System.out.println("2: Human vs Dumb Bot");
            System.out.println("3: Human vs Smart Bot");

            int choice = readChoice(scanner, 1, 3);

            Player p1 = new HumanPlayer(Symbol.X, scanner);
            Player p2;
            switch (choice) {
                case 1:
                    p2 = new HumanPlayer(Symbol.O, scanner);
                    break;
                case 2:
                    p2 = new DumbBot(Symbol.O);
                    break;
                default:
                    p2 = new SmartBot(Symbol.O);
                    break;
            }

            Player[] players = { p1, p2 };
            Symbol[] symbols = { p1.getSymbol(), p2.getSymbol() };

            while (true) {
                GameEngine engine = new GameEngine(15, symbols, 5);
                ConsoleUI.render(engine.getBoard());

                MoveResult moveResult = MoveResult.CONTINUE;
                while (moveResult == MoveResult.CONTINUE || moveResult == MoveResult.INVALID) {
                    Symbol currentSymbol = engine.getCurrentSymbol();
                    Player currentPlayer = findPlayer(players, currentSymbol);

                    System.out.println("Turn of " + currentSymbol + ":");
                    int move = currentPlayer.makeMove(engine.getBoard());
                    moveResult = engine.makeMove(move);

                    if (moveResult == MoveResult.INVALID) {
                        System.out.println("Invalid move!");
                    } else {
                        ConsoleUI.render(engine.getBoard());
                    }
                }

                if (moveResult == MoveResult.DRAW) {
                    System.out.println("Draw!");
                } else {
                    System.out.println("Winner is: " + engine.getCurrentSymbol() + " !");
                }

                System.out.println("1: Restart");
                System.out.println("2: Back to menu");
                System.out.println("3: Exit");

                switch (readChoice(scanner, 1, 3)) {
                    case 1:
                        break;
                    case 2:
                        continue outer;
                    case 3:
                        break outer;
                }
            }
        }

        scanner.close();
    }

    private Player findPlayer(Player[] players, Symbol symbol) {
        for (Player p : players) {
            if (p.getSymbol() == symbol)
                return p;
        }
        throw new IllegalStateException("No player for symbol: " + symbol);
    }

    private static int readChoice(Scanner scanner, int min, int max) {
        while (true) {
            System.out.print("> ");
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Invalid input! \nEnter a number between " + min + " and " + max + ".");
        }
    }
}
