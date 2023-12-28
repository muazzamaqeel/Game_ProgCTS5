import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Objects;

public class Pacman {
    //image for pacman
    private final Image pacman_close_mouth;
    private static Pacman pacman;

    //coordinates
    private double x,y;
    private float speed = 2f;

    //rotation angle
    private float angle = 0f;

    public static final double PLAYER_SIZE = 50;

    public Pacman() {
        this.pacman_close_mouth = new ImageIcon(Objects.requireNonNull(getClass().getResource("game/images/pixil-frame-0-4.png"))).getImage();
        Image pacman_open_mouth = new ImageIcon(Objects.requireNonNull(getClass().getResource("game/images/pixil-frame-0-5.png"))).getImage();

        this.x = (double) Board.TILE_NUMBER / 2 * Board.GRID_WIDTH;
        this.y = (Board.TILE_NUMBER - 2) * Board.GRID_HEIGHT;
    }

    public void move(Board board) {
        double nextX = x + speed * Math.cos(Math.toRadians(angle));
        double nextY = y + speed * Math.sin(Math.toRadians(angle));

        // Check if the next position is within a path
        if (board.isPath(nextX, nextY)) {
            x = nextX;
            y = nextY;
        }
    }


    public void changePosition(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void changeAngle(float angle){
        this.angle = angle;

    }

    public static Pacman getInstance(){
        if (pacman == null)
            pacman = new Pacman();
        return Pacman.pacman;
    }

    public void draw(Graphics2D graphics2D){
        AffineTransform oldTransformation = graphics2D.getTransform();
        graphics2D.translate(x, y);
        AffineTransform rotation = new AffineTransform();
        rotation.rotate(Math.toRadians(angle), PLAYER_SIZE/2, PLAYER_SIZE/2);
        graphics2D.drawImage(pacman_close_mouth, rotation,null);
        graphics2D.setTransform(oldTransformation);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public float getAngle() {
        return angle;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}