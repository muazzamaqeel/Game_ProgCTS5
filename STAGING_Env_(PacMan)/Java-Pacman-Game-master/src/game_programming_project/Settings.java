package game_programming_project;

import javax.swing.*;
import java.awt.*;

public class Settings extends JFrame {

    public Settings() {
        initUI();
    }

    private void initUI() {
        setTitle("Settings");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel label = new JLabel("Settings will be here");
        panel.add(label);

        add(panel);
    }
}
