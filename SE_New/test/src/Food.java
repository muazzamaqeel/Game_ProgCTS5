import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Food {
    private List<Point> foodPositions;
    private Board board;
    private int score;
    private float glowRadius = 10; // Initial glow radius
    private boolean increasingGlow = true;

    public Food(Board board) {
        this.board = board;
        initializeFoodPositions();
        score = 0;
    }

    private void initializeFoodPositions() {
        foodPositions = new ArrayList<>();
        int[][] grid = board.getGrid();
        for (int row = 0; row < Board.TILE_NUMBER; row++) {
            for (int col = 0; col < Board.TILE_NUMBER; col++) {
                if (grid[row][col] == 0) {
                    int xCenter = (col * Board.GRID_WIDTH) + (Board.GRID_WIDTH / 2);
                    int yCenter = (row * Board.GRID_HEIGHT) + (Board.GRID_HEIGHT / 2);
                    foodPositions.add(new Point(xCenter, yCenter));
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        updateGlowEffect();

        for (Point point : foodPositions) {
            drawGlowingBall(g2, point, glowRadius);
        }
    }

    private void drawGlowingBall(Graphics2D g2, Point center, float radius) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        RadialGradientPaint paint = new RadialGradientPaint(
                new Point2D.Float(center.x, center.y),
                radius,
                new float[] {0.0f, 0.5f, 1.0f},
                new Color[] {
                        new Color(255, 255, 224, 255),  // bright center
                        new Color(255, 215, 0, 230),     // gold middle
                        new Color(255, 215, 0, 180)      // darker edge
                }
        );
        g2.setPaint(paint);
        g2.fillOval((int) (center.x - radius), (int) (center.y - radius), (int) (radius * 2), (int) (radius * 2));

        paint = new RadialGradientPaint(
                new Point2D.Float(center.x - radius * 0.2f, center.y - radius * 0.2f),
                radius * 0.5f,
                new float[] {0.0f, 1.0f},
                new Color[] {new Color(255, 255, 255, 200), new Color(255, 255, 255, 0)}
        );
        g2.setPaint(paint);
        g2.fillOval((int) (center.x - radius), (int) (center.y - radius), (int) (radius * 2), (int) (radius * 2));
        paint = new RadialGradientPaint(
                center,
                radius * 1.5f,
                new float[] {0.0f, 1.0f},
                new Color[] {new Color(255, 255, 100, 50), new Color(255, 255, 0, 0)}
        );
        g2.setPaint(paint);
        g2.fillOval((int) (center.x - radius * 1.5f), (int) (center.y - radius * 1.5f), (int) (radius * 3f), (int) (radius * 3f));
    }


    private void updateGlowEffect() {
        if (increasingGlow) {
            glowRadius += 0.2;
            if (glowRadius > 15) {
                increasingGlow = false;
            }
        } else {
            glowRadius -= 0.2;
            if (glowRadius < 10) {
                increasingGlow = true;
            }
        }
    }

    // Rest of your class...


    public void checkCollisionWithPacman(Pacman pacman) {
        Rectangle2D pacmanBounds = new Rectangle2D.Double(pacman.getX(), pacman.getY(), Pacman.PLAYER_SIZE, Pacman.PLAYER_SIZE);
        foodPositions.removeIf(point -> {
            Rectangle2D foodBounds = new Rectangle2D.Double(point.x, point.y, Board.GRID_WIDTH, Board.GRID_HEIGHT);
            if (pacmanBounds.intersects(foodBounds)) {
                score += 10; // Increment score for each food eaten
                return true; // Remove the food from the list
            }
            return false;
        });
    }

    public void drawScore(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString("Score: " + score, 650, 30);
    }

    // Getters and setters
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
