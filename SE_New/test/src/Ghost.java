import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Ghost {
    // Ghost's position
    private int x, y;
    // Ghost's speed
    private static int speed;
    private static Ghost ghost;
    private Board board;

    public Ghost(Board board) {
        this.board = board;
        // Adjust the speed here
        if(Settings.isGhostSpeedChanged()){
            this.speed = Settings.getNewGhostSpeed();
        } else {
            this.speed = 1;
        }
        initializePosition();
    }

    public Ghost(Board board, int startX, int startY) {
        this.board = board;
        if(Settings.isGhostSpeedChanged()){
            this.speed = Settings.getNewGhostSpeed();
        } else {
            this.speed = 1;
        }
        this.x = startX;
        this.y = startY;
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
    public void draw(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, Board.GRID_WIDTH / 2, Board.GRID_HEIGHT / 2); // Adjust the size
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
    public static void setSpeed(int Speed) {
        speed = Speed;
    }
}
