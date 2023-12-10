

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class GamePanel extends JComponent {
    private Graphics2D g2;
    private BufferedImage panel;
    private Board board;
    private int width;
    private int height;
    private Thread thread;
    private boolean start = true;
    private Pacman pacman;

    private UserInput userInput;

    private final int FPS = 60;
    private final int TARGET_TIME = 1000000000 / FPS;

    public void start(){
        width = 800;
        height = 800;
        panel = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        g2 = panel.createGraphics();
        thread = new Thread(() -> {
            while (start){
                long startTime = System.nanoTime();
                drawBackground();
                drawGame();
                render();
                pacman.move();
                long time = System.nanoTime() - startTime;
                if (time < TARGET_TIME){
                    long sleep = (TARGET_TIME - time) / 1000000;
                    sleep(sleep);
                    //System.out.println(sleep);
                }
            }
        });
        initObjGame();
        initUserInput();
        thread.start();
    }

    public void initObjGame(){
        pacman = Pacman.getInstance();
        board = new Board();
        //pacman = new Pacman();
        //pacman.changePosition(350, 350);
    }

    public void initUserInput(){
        userInput = new UserInput();
        requestFocus();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_A){
                    userInput.setKey_a(true);
                } else if (e.getKeyCode() == KeyEvent.VK_W){
                    userInput.setKey_w(true);
                } else if (e.getKeyCode() == KeyEvent.VK_D){
                    userInput.setKey_d(true);
                } else if (e.getKeyCode() == KeyEvent.VK_S){
                    userInput.setKey_s(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_A){
                    userInput.setKey_a(false);
                } else if (e.getKeyCode() == KeyEvent.VK_W){
                    userInput.setKey_w(false);
                } else if (e.getKeyCode() == KeyEvent.VK_D){
                    userInput.setKey_d(false);
                } else if (e.getKeyCode() == KeyEvent.VK_S){
                    userInput.setKey_s(false);
                }
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(start){
                    float angle = pacman.getAngle();
                    if (userInput.isKey_a()){
                        angle -= 90;
                    }
                    if (userInput.isKey_d()){
                        angle += 90;
                    }
                    pacman.changeAngle(angle);
                    sleep(85);
                }
            }
        }).start();
    }

    public void drawBackground(){
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(0, 0, width, height);
    }

    public void drawGame(){
       pacman.draw(g2);
       board.draw(g2);
    }

    public void render(){
        Graphics g = getGraphics();
        g.drawImage(panel, 0, 0, null);
        g.dispose();
    }

    private void sleep(long speed){
        try {
            Thread.sleep(speed);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }


}