import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Objects;

public class Pacman {
    //image for pacman
    private final Image pacman_close_mouth;
    static Pacman pacman;

    //coordinates
    private int x,y;
    private float speed;

    //rotation angle
    private float angle = 0f;

    public static final double PLAYER_SIZE = 50;
    private float targetX, targetY;

    public Pacman() {
        this.pacman_close_mouth = new ImageIcon(Objects.requireNonNull(getClass().getResource("game/images/pixil-frame-0-4.png"))).getImage();
        Image pacman_open_mouth = new ImageIcon(Objects.requireNonNull(getClass().getResource("game/images/pixil-frame-0-5.png"))).getImage();
        if(Settings.isPacmanSpeedChanged()){
            this.speed = (float)Settings.getNewPacmanSpeed();
        }else{
            this.speed = 2f;
        }
        this.x =  Board.TILE_NUMBER / 2 * Board.GRID_WIDTH;
        this.y = (Board.TILE_NUMBER - 2) * Board.GRID_HEIGHT;
    }


    public void moveTowardsTarget() {
        x += (targetX - x) * speed;
        y += (targetY - y) * speed;
    }

    public void move(Board board) {
        //int out = (int) (Math.round(in / 50.0) * 50);
        double nextX = x + speed * Math.cos(Math.toRadians(angle));
        double nextY = y + speed * Math.sin(Math.toRadians(angle));

        // Check if the next position is within a path
        if (board.isPath((int)Math.round(nextX / 2)*2, (int)Math.round(nextY / 2)*2)) {
            x = (int)Math.round(nextX / 2)*2;
            y = (int)Math.round(nextY / 2)*2;;
        }
    }

    public void speedUp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long t = System.currentTimeMillis();
                long end = t + 2000;

                while (System.currentTimeMillis() < end) {
                    // do something
                    changeSpeed(4f);
                    // pause to avoid churning
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // After 2 seconds, revert to normal speed
                changeSpeed(2f);
            }
        }).start();
    }

    public void changeSpeed(float speed){
        this.speed = speed;
    }

    public void changePosition(int x, int y){
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}