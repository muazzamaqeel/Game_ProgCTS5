
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class HomeScreen extends JFrame{
    private JPanel currentMap; // Added currentMap variable

    public HomeScreen() {
        initUI();
    }

    public void closeWindow() {
        this.dispose();
    }

    private void initUI() {
        setTitle("PACMAN REMASTERED");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 800);
        setResizable(false); // Set window not resizable
        setLocationRelativeTo(null);

        // Create panel to hold buttons, image, and text
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.BLACK); // Set background color to black

        GridBagConstraints gbc = new GridBagConstraints();

        // Text label
        JLabel textLabel = new JLabel("PAC-MAN!");
        textLabel.setFont(new Font("Arial", Font.BOLD, 40)); // Set the desired font and size
        textLabel.setForeground(Color.YELLOW); // Set text color to yellow
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 20, 0); // Add space top and bottom
        panel.add(textLabel, gbc);

        // Image label
        ImageIcon icon = new ImageIcon("src/resources/images/Main_Window_Wallpaper.jpg"); // Replace with your image path
        JLabel imageLabel = new JLabel(icon);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 50, 0); // Add space after the image
        panel.add(imageLabel, gbc);

        // Reset gridwidth for buttons
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add horizontal space before the buttons
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(Box.createHorizontalStrut(200), gbc); // Adjust the strut size as needed

        // Play button
        JButton playBtn = new JButton("Play");
        playBtn.setPreferredSize(new Dimension(100, 40));
        playBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        playBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameFrame d = new GameFrame();
                d.setVisible(true);

                // Close the current window
                HomeScreen.this.closeWindow();
            }
        });
        gbc.gridx = 1;
        panel.add(playBtn, gbc);

        // Settings button
        JButton settingsBtn = new JButton("Settings");
        settingsBtn.setPreferredSize(new Dimension(100, 40));
        settingsBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        settingsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //openSettings();
            }
        });
        gbc.gridx = 2;
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
        gbc.gridx = 3;
        panel.add(quitBtn, gbc);

        // Add horizontal space after the buttons
        gbc.gridx = 4;
        panel.add(Box.createHorizontalStrut(200), gbc); // Adjust the strut size as needed

        add(panel);
    }

}
