import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JComponent {
    private List<Ghost> ghosts;
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

    public void start() {
        width = 800;
        height = 800;
        panel = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        g2 = panel.createGraphics();

        thread = new Thread(() -> {
            long startTime = 0;
            while (start) {
                startTime = System.nanoTime();
                drawBackground();
                drawGame();
                render();

                pacman.move(board);
                for (Ghost ghost : ghosts) {
                    ghost.move(pacman);
                }
                long time = System.nanoTime() - startTime;
                if (time < TARGET_TIME) {
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

    public void initObjGame() {
        pacman = Pacman.getInstance();
        board = new Board(); // Initializes the maze layout
        ghosts = new ArrayList<>(); // Initialize the list of ghosts

        ghosts.add(new Ghost(board));
    }

    public void initUserInput() {
        userInput = new UserInput();
        requestFocus();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A:
                        pacman.changeAngle(180); // Left
                        break;
                    case KeyEvent.VK_W:
                        pacman.changeAngle(270); // Up
                        break;
                    case KeyEvent.VK_D:
                        pacman.changeAngle(0);   // Right
                        break;
                    case KeyEvent.VK_S:
                        pacman.changeAngle(90);  // Down
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A:
                        userInput.setKey_a(false);
                        break;
                    case KeyEvent.VK_W:
                        userInput.setKey_w(false);
                        break;
                    case KeyEvent.VK_D:
                        userInput.setKey_d(false);
                        break;
                    case KeyEvent.VK_S:
                        userInput.setKey_s(false);
                        break;
                }
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (start) {
                    float angle = pacman.getAngle();
                    if (userInput.isKey_a()) {
                        angle -= 90;
                    }
                    if (userInput.isKey_d()) {
                        angle += 90;
                    }
                    pacman.changeAngle(angle);
                    sleep(85);
                }
            }
        }).start();
    }

    public void drawBackground() {
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(0, 0, width, height);
    }

    public void drawGame() {
        board.draw(g2);
        pacman.draw(g2);
        // Draw each ghost
        for (Ghost ghost : ghosts) {
            ghost.draw(g2);
        }
    }

    public void render() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (panel != null) {
            g.drawImage(panel, 0, 0, this);
        }
    }

    private void sleep(long speed) {
        try {
            Thread.sleep(speed);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }
}
//Test