import java.awt.*;

public class Ghost {
    // Ghost's position
    private int x, y;
    // Ghost's speed
    private int speed;
    // Reference to the board for collision detection
    private Board board;

    public Ghost(Board board) {
        this.board = board;
        // Use Board.GRID_WIDTH to access the constant from the Board class
        this.speed = Board.GRID_WIDTH; // Assuming the ghost moves one tile at a time
        initializePosition();
    }
    // Set the initial position of the ghost
    private void initializePosition() {
        // Place the ghost in the upper left corner as an example
        this.x = Board.GRID_WIDTH; // Avoid placing directly in the corner
        this.y = Board.GRID_HEIGHT;
    }

    // Move the ghost in a random direction
    public void move() {
        // Example of a simplified move in one direction for debugging
        int newX = x + speed; // Try to move right
        int newY = y;         // Y remains the same
        int speed = Board.GRID_WIDTH / 99999;

        // For debugging: print out current and new position
        System.out.println("Current X: " + x + ", Y: " + y);
        System.out.println("Trying to move to X: " + newX + ", Y: " + newY);

        // Check for collisions with the walls
        if (board.isPath(newX, newY)) {
            // For debugging: print out success
            System.out.println("Move successful");
            x = newX;
            y = newY;
        } else {
            // For debugging: print out failure
            System.out.println("Move failed: Path blocked");
        }
    }




    // Draw the ghost on the board
    public void draw(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, Board.GRID_WIDTH, Board.GRID_HEIGHT);
    }

    // Getters and setters for the ghost's position
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
