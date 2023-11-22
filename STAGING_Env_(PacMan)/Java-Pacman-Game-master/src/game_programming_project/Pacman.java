package game_programming_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Pacman extends JFrame {

    public Pacman() {
        initUI();
    }

    private void initUI() {
        setTitle("PACMAN REMASTERED");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        // Create panel to hold buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Play button
        JButton playBtn = new JButton("Play");
        playBtn.setPreferredSize(new Dimension(100, 40));
        playBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        playBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(playBtn, gbc);

        // Settings button
        JButton settingsBtn = new JButton("Settings");
        settingsBtn.setPreferredSize(new Dimension(100, 40));
        settingsBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        settingsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSettings();
            }
        });
        gbc.gridy = 1;
        panel.add(settingsBtn, gbc);

        // Quit button
        JButton quitBtn = new JButton("Quit");
        quitBtn.setPreferredSize(new Dimension(100, 40));
        quitBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        quitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        gbc.gridy = 2;
        panel.add(quitBtn, gbc);

        add(panel);
    }

    private void startGame() {
        // Start the game by creating a new Board and setting it visible
        Board board = new Board();
        getContentPane().removeAll();
        getContentPane().add(board);
        revalidate();
        board.requestFocus();
    }

    private void openSettings() {
        // Open settings window
        Settings settings = new Settings();
        settings.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var ex = new Pacman();
            ex.setVisible(true);
        });
    }
}
