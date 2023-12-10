import java.awt.*;

public class Board {
    private static final int TILE_NUMBER = 8;
    private static final int GRID_WIDTH = 100;
    private static final int GRID_HEIGHT = 100;
    public static final int BOARD_WIDTH = TILE_NUMBER * GRID_WIDTH;
    public static final int BOARD_HEIGHT = TILE_NUMBER * GRID_HEIGHT;

    private int[][] grid;

    public Board() {
        Pacman pacman = Pacman.getInstance();
        pacman.changePosition(350, 350);
        // Initialize the grid
        grid = new int[TILE_NUMBER][TILE_NUMBER];
        // You can initialize the grid with specific values if needed
        // For example, grid[0][0] = 1; // This represents a specific type of tile
    }

    public void draw(Graphics2D graphics2D) {
        // Draw the grid
        for (int row = 0; row < TILE_NUMBER; row++) {
            for (int col = 0; col < TILE_NUMBER; col++) {
                int tileX = col * GRID_WIDTH;
                int tileY = row * GRID_HEIGHT;

                // Draw the grid cell
                graphics2D.setColor(Color.BLACK);
                graphics2D.drawRect(tileX, tileY, GRID_WIDTH, GRID_HEIGHT);

                // Optionally, you can fill the cells with colors based on their values
                // For example, if (grid[row][col] == 1) graphics2D.fillRect(tileX, tileY, GRID_WIDTH, GRID_HEIGHT);
            }
        }
    }

    // Other methods for modifying the grid if needed

    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }
}
