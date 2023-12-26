import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Ghost {
    // Ghost's position
    private int x, y;
    // Ghost's speed
    private int speed;
    private Board board;

    public Ghost(Board board) {
        this.board = board;
        // Adjust the speed here
        this.speed = 1; // Example speed
        initializePosition();
    }
    private void initializePosition() {
        this.x = Board.GRID_WIDTH;
        this.y = Board.GRID_HEIGHT;
    }

    public void move(Pacman pacman) {
        int pacmanX = (int) pacman.getX();
        int pacmanY = (int) pacman.getY();
        int newX = x + ((pacmanX > x) ? speed : (pacmanX < x) ? -speed : 0);
        int newY = y + ((pacmanY > y) ? speed : (pacmanY < y) ? -speed : 0);
        if (board.isPath(newX, newY)) {
            x = newX;
            y = newY;
        } else {
            if (board.isPath(newX, y)) {
                x = newX;
            } else if (board.isPath(x, newY)) {
                y = newY;
            }
        }
    }

    // Draw the ghost on the board
    public void draw(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, Board.GRID_WIDTH, Board.GRID_HEIGHT);
    }

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
