import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class Board {
    public static final int TILE_NUMBER = 16;
    public static final int GRID_WIDTH = 800 / TILE_NUMBER;
    public static final int GRID_HEIGHT = 800 / TILE_NUMBER;
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
        File file = new File("");

        if(Settings.isMap1()){
            file = new File("src/game/maps/maze1.txt");
        }
        if(Settings.isMap2()){
            file = new File("src/game/maps/maze2.txt");
        }
        if(Settings.isMap3()){
            file = new File("src/game/maps/maze3.txt");
        }else{
            file = new File("src/game/maps/default_maze.txt");
        }
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
                    // Draw walls with a dark blue gradient
                    Color darkBlueStart = new Color(10, 30, 70);
                    Color darkBlueEnd = new Color(25, 50, 100);
                    Paint wallPaint = new GradientPaint(tileX, tileY, darkBlueStart, tileX + GRID_WIDTH, tileY + GRID_HEIGHT, darkBlueEnd);
                    graphics2D.setPaint(wallPaint);
                    graphics2D.fillRect(tileX, tileY, GRID_WIDTH, GRID_HEIGHT);
                } else {
                    // Draw paths in a lighter color for contrast
                    Color lightPathColor = new Color(180, 200, 220); // Light blue-gray
                    graphics2D.setColor(lightPathColor);
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

    public boolean isRightFree(double x, double y) {
        int rightCol = (int) (x + GRID_WIDTH) / GRID_WIDTH;
        int topRow = (int) y / GRID_HEIGHT;
        int bottomRow = (int) (y + GRID_HEIGHT - 1) / GRID_HEIGHT;
        // Check if the tiles to the right are within bounds and are free paths
        return rightCol < TILE_NUMBER &&
                grid[topRow][rightCol] == 0 &&
                grid[bottomRow][rightCol] == 0;
    }

    public boolean isLeftFree(double x, double y) {
        // Calculate the grid indices for the tiles to the left of the pacman
        int leftCol = (int) (x - GRID_WIDTH) / GRID_WIDTH;
        int topRow = (int) y / GRID_HEIGHT;
        int bottomRow = (int) (y + GRID_HEIGHT - 1) / GRID_HEIGHT;
        // Check if the tiles to the left are within bounds and are free paths
        return leftCol >= 0 &&
                grid[topRow][leftCol] == 0 &&
                grid[bottomRow][leftCol] == 0;
    }

    public boolean isUpFree(double x, double y) {
        // Calculate the grid indices for the tiles above the pacman
        int leftCol = (int) x / GRID_WIDTH;
        int rightCol = (int) (x + GRID_WIDTH - 1) / GRID_WIDTH;
        int topRow = (int) (y - GRID_HEIGHT) / GRID_HEIGHT;
        // Check if the tiles above are within bounds and are free paths
        return topRow >= 0 &&
                grid[topRow][leftCol] == 0 &&
                grid[topRow][rightCol] == 0;
    }
    public boolean isDownFree(double x, double y) {
        // Calculate the grid indices for the tiles below the pacman
        int leftCol = (int) x / GRID_WIDTH;
        int rightCol = (int) (x + GRID_WIDTH - 1) / GRID_WIDTH;
        int bottomRow = (int) (y + GRID_HEIGHT) / GRID_HEIGHT;
        // Check if the tiles below are within bounds and are free paths
        return bottomRow < TILE_NUMBER &&
                grid[bottomRow][leftCol] == 0 &&
                grid[bottomRow][rightCol] == 0;
    }


}
