import java.awt.*;

public class Board {
    public static final int TILE_NUMBER = 8;
    public static final int GRID_WIDTH = 100;
    public static final int GRID_HEIGHT = 100;
    public static final int BOARD_WIDTH = TILE_NUMBER * GRID_WIDTH;
    public static final int BOARD_HEIGHT = TILE_NUMBER * GRID_HEIGHT;

    private int[][] grid;

    public Board() {
        // Initialize the grid
        grid = new int[TILE_NUMBER][TILE_NUMBER];
        initializeMaze();

        // Create the Pacman instance and set its initial position
        Pacman pacman = Pacman.getInstance();
        // Set Pacman's position to a valid path location, away from the walls
        pacman.changePosition(TILE_NUMBER / 2 * GRID_WIDTH, (TILE_NUMBER - 2) * GRID_HEIGHT);
    }

    public boolean isPath(double x, double y) {
        int leftCol = (int) x / GRID_WIDTH;
        int rightCol = (int) (x + GRID_WIDTH - 1) / GRID_WIDTH;
        int topRow = (int) y / GRID_HEIGHT;
        int bottomRow = (int) (y + GRID_HEIGHT - 1) / GRID_HEIGHT;

        // Check if any of the corners of the ghost's tile are not a path
        return leftCol >= 0 && rightCol < TILE_NUMBER &&
                topRow >= 0 && bottomRow < TILE_NUMBER &&
                grid[topRow][leftCol] == 0 &&
                grid[topRow][rightCol] == 0 &&
                grid[bottomRow][leftCol] == 0 &&
                grid[bottomRow][rightCol] == 0;
    }

    private void initializeMaze() {
        // Set the entire grid as paths initially
        for (int row = 0; row < TILE_NUMBER; row++) {
            for (int col = 0; col < TILE_NUMBER; col++) {
                grid[row][col] = 0; // Path
            }
        }

        // Top and bottom borders
        for (int col = 0; col < TILE_NUMBER; col++) {
            grid[0][col] = 1;
            grid[TILE_NUMBER - 1][col] = 1;
        }

        // Left and right borders
        for (int row = 1; row < TILE_NUMBER - 1; row++) {
            grid[row][0] = 1;
            grid[row][TILE_NUMBER - 1] = 1;
        }

        // Add internal walls
        for (int col = 2; col < TILE_NUMBER - 2; col += 2) {
            for (int row = 2; row < TILE_NUMBER - 2; row++) {
                grid[row][col] = 1;
            }
        }

        // Create a central box
        for (int row = TILE_NUMBER / 2 - 1; row <= TILE_NUMBER / 2 + 1; row++) {
            for (int col = TILE_NUMBER / 2 - 1; col <= TILE_NUMBER / 2 + 1; col++) {
                grid[row][col] = 1;
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        // Draw the grid
        for (int row = 0; row < TILE_NUMBER; row++) {
            for (int col = 0; col < TILE_NUMBER; col++) {
                int tileX = col * GRID_WIDTH;
                int tileY = row * GRID_HEIGHT;

                if (grid[row][col] == 1) {
                    // Draw walls
                    graphics2D.setColor(Color.WHITE); // Wall color
                    graphics2D.fillRect(tileX, tileY, GRID_WIDTH, GRID_HEIGHT);
                } else {
                    // Optionally draw paths with a different color or leave them as is
                }
            }
        }
    }

    // Getters and setters
    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }
}
