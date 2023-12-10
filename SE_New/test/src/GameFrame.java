import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameFrame extends JFrame {
    private void init(){
        setTitle("Project PacMan");
        setSize(800,800);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        GamePanel panel = new GamePanel();
        add(panel, BorderLayout.CENTER);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                panel.start();
            }
        });

    }
    public GameFrame(){
        init();
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            var ex = new HomeScreen();
            ex.setVisible(true);
        });
    }
}
