import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Ghost {
    // Ghost's position
    private int x, y;
    private int prevX, prevY;
    // Ghost's speed
    private static int speed;
    private Board board;
    private static int amount_of_ghosts = 0;
    private Image ghost_image;
    private Random random = new Random();

    public Ghost(Board board, int startX, int startY) {
        if(amount_of_ghosts == 0){
            ghost_image = new ImageIcon(Objects.requireNonNull(getClass().getResource("game/images/ghost/Ghost1.png"))).getImage();
            amount_of_ghosts++;
        }
        else if(amount_of_ghosts == 1){
            ghost_image = new ImageIcon(Objects.requireNonNull(getClass().getResource("game/images/ghost/Ghost2.png"))).getImage();
            amount_of_ghosts++;
        }
        else if(amount_of_ghosts == 2){
            ghost_image = new ImageIcon(Objects.requireNonNull(getClass().getResource("game/images/ghost/Ghost3.png"))).getImage();
            amount_of_ghosts++;
        }
        else if(amount_of_ghosts == 3){
            ghost_image = new ImageIcon(Objects.requireNonNull(getClass().getResource("game/images/ghost/Ghost4.png"))).getImage();
            amount_of_ghosts++;
        }
        else {
            ghost_image = new ImageIcon(Objects.requireNonNull(getClass().getResource("game/images/ghost/Ghost1.png"))).getImage();
            amount_of_ghosts = 0;
        }

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


//    public void move(List<Ghost> ghosts ,Pacman pacman) {
//        int pacmanX = (int) pacman.getX();
//        int pacmanY = (int) pacman.getY();
//        int newX = x + ((pacmanX > x) ? speed : (pacmanX < x) ? -speed : 0);
//        int newY = y + ((pacmanY > y) ? speed : (pacmanY < y) ? -speed : 0);
//        if (board.isPath(newX, newY)) {
//            x = newX;
//            y = newY;
//        } else {
//            if (board.isPath(newX, y)) {
//                x = newX;
//            } else if (board.isPath(x, newY)) {
//                y = newY;
//            }
//        }
//    }
public void move(List<Ghost> ghosts, Pacman pacman) {
    int pacmanX = (int) pacman.getX();
    int pacmanY = (int) pacman.getY();
    int newX = x + ((pacmanX > x) ? speed : (pacmanX < x) ? -speed : 0);
    int newY = y + ((pacmanY > y) ? speed : (pacmanY < y) ? -speed : 0);

    // Check for collisions with other ghosts
    for (Ghost otherGhost : ghosts) {
        if (this != otherGhost && collidesWith(otherGhost, newX, newY)) {
            // Handle collision by moving ghosts back to their previous positions
            moveBackToPreviousPosition();
            otherGhost.moveBackToPreviousPosition();
            return; // Exit the method if collision occurred
        }
    }

    // Save the current position before moving
    prevX = x;
    prevY = y;

    // Move the ghost if there are no collisions
    if (board.isPath(newX, newY)) {
        x = newX;
        y = newY;
    } else {
        // If collision occurs, try to move along the other axis
        if (board.isPath(newX, y)) {
            x = newX;
        } else if (board.isPath(x, newY)) {
            y = newY;
        }
        // If both axes are blocked, do not move (ghost stays in the same position)
    }
}

    private void moveBackToPreviousPosition() {
        x = prevX;
        y = prevY;
    }

    private boolean collidesWith(Ghost otherGhost, int newX, int newY) {
        Rectangle thisGhostBounds = new Rectangle(newX, newY, Board.GRID_WIDTH, Board.GRID_HEIGHT);
        Rectangle otherGhostBounds = new Rectangle(otherGhost.getX(), otherGhost.getY(), Board.GRID_WIDTH, Board.GRID_HEIGHT);

        return thisGhostBounds.intersects(otherGhostBounds);
    }





    public void draw(Graphics2D graphics2D) {
        int ghostWidth = Board.GRID_WIDTH ;  // Adjust the width
        int ghostHeight = Board.GRID_HEIGHT;  // Adjust the height

        graphics2D.setColor(Color.RED);  // Set the color (you can adjust this based on your ghost image)

        // Draw the ghost image with adjusted size
        graphics2D.drawImage(ghost_image, x, y, ghostWidth, ghostHeight, null);
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
