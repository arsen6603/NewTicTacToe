public class ConsoleUI {

    public static void render(Board board) {
        Symbol[][] grid = board.getGrid();
        int size = board.getSize();
        System.out.println("============");

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Symbol s = grid[i][j];
                int pos = i * size + j + 1;
                System.out.print(s == Symbol.EMPTY ? pos : s);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("============");
    }
}