import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
public class GamePanel extends JComponent {
    private BufferedImage offScreenBuffer;

    private List<Ghost> ghosts;
    private Graphics2D g2;
    private BufferedImage panel;
    private Board board;
    private Food food; // Add this line for the Food object

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

                // Update game state
                pacman.move(board);
                for (Ghost ghost : ghosts) {
                    ghost.move(pacman);
                }
                food.checkCollisionWithPacman(pacman); // Add this line here

                // Render the game
                drawBackground();
                drawGame();
                render();

                // Manage frame timing
                long time = System.nanoTime() - startTime;
                if (time < TARGET_TIME) {
                    long sleep = (TARGET_TIME - time) / 1000000;
                    sleep(sleep);
                }
            }
        });


        initObjGame();
        initUserInput();
        thread.start();
    }
    public void initObjGame() {
        pacman = Pacman.getInstance();
        board = new Board();
        ghosts = new ArrayList<>();
        food = new Food(board); // Initialize the food object


        int ghostCount = 5; // Number of ghosts we want to create
        int attempts = 0; // To prevent an infinite loop

        while (ghosts.size() < ghostCount && attempts < 100) {
            int x = (int) (Math.random() * Board.TILE_NUMBER) * Board.GRID_WIDTH;
            int y = (int) (Math.random() * Board.TILE_NUMBER) * Board.GRID_HEIGHT;

            if (board.isPath(x, y) && (x != pacman.getX() || y != pacman.getY())) {
                ghosts.add(new Ghost(board, x, y));
            }

            attempts++;
        }
    }
    public void initUserInput() {
        userInput = new UserInput();
        requestFocus();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    userInput.setKey_a(true);
                } else if (e.getKeyCode() == KeyEvent.VK_W) {
                    userInput.setKey_w(true);
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    userInput.setKey_d(true);
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    userInput.setKey_s(true);
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    userInput.setKey_a(false);
                } else if (e.getKeyCode() == KeyEvent.VK_W) {
                    userInput.setKey_w(false);
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    userInput.setKey_d(false);
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    userInput.setKey_s(false);
                }
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (start) {
                    float angle = pacman.getAngle();
                    if (userInput.isKey_a()) {
                        if (board.isLeftFree(pacman.getX(), pacman.getY())) {
                            angle = 180;
                        } else {
                            pacman.changeAngle(angle);
                        }
                    } else if (userInput.isKey_d()) {
                        if (board.isRightFree(pacman.getX(), pacman.getY())) {
                            angle = 0;
                            pacman.changeAngle(angle);
                        } else {
                            pacman.changeAngle(angle);
                        }
                    } else if (userInput.isKey_w()) {
                        if (board.isUpFree(pacman.getX(), pacman.getY())) {
                            angle = 270;
                            pacman.changeAngle(angle);
                        } else {
                            pacman.changeAngle(angle);
                        }
                    } else if (userInput.isKey_s()) {
                        if (board.isDownFree(pacman.getX(), pacman.getY())) {
                            angle = 90;
                            pacman.changeAngle(angle);
                        } else {
                            pacman.changeAngle(angle);
                        }
                    }
                    pacman.changeAngle(angle);
                    sleep(1);
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

        for (Ghost ghost : ghosts) {
            ghost.draw(g2);  // Draw each ghost
        }

        food.draw(g2); // Add this line to draw food
        food.drawScore(g2); // Add this line to draw score
    }
    public void render() {
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Initialize off-screen buffer if not already initialized
        if (offScreenBuffer == null) {
            offScreenBuffer = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        }

        // Use the buffer to draw
        Graphics bufferGraphics = offScreenBuffer.getGraphics();

        try {
            // Render everything to the off-screen buffer
            if (panel != null) {
                bufferGraphics.drawImage(panel, 0, 0, this);
            }

            // Draw the off-screen buffer to the screen
            g.drawImage(offScreenBuffer, 0, 0, this);
        } finally {
            // Ensure the Graphics object is always disposed
            bufferGraphics.dispose();
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
