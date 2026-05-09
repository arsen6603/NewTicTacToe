public class ConsoleUI {

    public static void render(Board board) {
        Symbol[][] grid = board.getGrid();
        int size = board.getSize();
        System.out.println("====================================");

        int maxPos = size * size;
        int width = String.valueOf(maxPos).length();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Symbol s = grid[i][j];
                int pos = i * size + j + 1;
                String cell = s == Symbol.EMPTY ? String.valueOf(pos) : s.toString();
                while (cell.length() < width) {
                    cell = cell + " ";
                }
                System.out.print(cell + " | ");
            }
            ;
            System.out.println();
        }
        System.out.println("====================================");
    }
}