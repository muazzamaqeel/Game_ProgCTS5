import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class Board {
    public static final int TILE_NUMBER = 8;
    public static final int GRID_WIDTH = 100;
    public static final int GRID_HEIGHT = 100;
    public static final int BOARD_WIDTH = TILE_NUMBER * GRID_WIDTH;
    public static final int BOARD_HEIGHT = TILE_NUMBER * GRID_HEIGHT;

    private int[][] grid;

    public Board() {
        // Initialize the grid...
        grid = new int[TILE_NUMBER][TILE_NUMBER];

        try {
            initializeMaze();
        } catch (FileNotFoundException e) {
            System.err.println("Maze file not found.");
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Pacman pacman = Pacman.getInstance();
        pacman.changePosition((double) TILE_NUMBER / 2 * GRID_WIDTH, (TILE_NUMBER - 2) * GRID_HEIGHT);
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

    private void initializeMaze() throws IOException {
        File file = new File("src/game/maps/maze1.txt");
        if (!file.exists()) {
            throw new IOException("File not found: src/game/maps/maze.txt");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null && row < TILE_NUMBER) {
                for (int col = 0; col < TILE_NUMBER && col < line.length(); col++) {
                    grid[row][col] = Character.getNumericValue(line.charAt(col));
                }
                row++;
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
                    // Draw walls in black
                    graphics2D.setColor(Color.BLACK); // Changed wall color to black
                    graphics2D.fillRect(tileX, tileY, GRID_WIDTH, GRID_HEIGHT);
                } else {
                    // Draw paths - optional; could be left as is for a white background
                    graphics2D.setColor(Color.WHITE); // Path color
                    graphics2D.fillRect(tileX, tileY, GRID_WIDTH, GRID_HEIGHT);
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


//Test
//Test