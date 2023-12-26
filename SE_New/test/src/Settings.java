import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class Settings extends JFrame {
    private final MapSelectionListener listener;
    protected boolean map1;
    protected boolean map2;
    protected boolean map3;

    public Settings(MapSelectionListener listener) {
        this.listener = listener;
        this.initUI();
    }

    private void initUI() {
        setTitle("Select Map");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        JButton map1Button = new JButton("Map 1");
        map1Button.addActionListener((e) -> this.listener.onMapSelected(1));

        JButton map2Button = new JButton("Map 2");
        map2Button.addActionListener((e) -> this.listener.onMapSelected(2));

        JButton map3Button = new JButton("Map 3");
        map3Button.addActionListener((e) -> this.listener.onMapSelected(3));

        this.add(map1Button);
        this.add(map2Button);
        this.add(map3Button);
    }

    interface MapSelectionListener {
        void onMapSelected(int var1);
    }

    public void openSettings() {
        // Create a new JFrame for settings
        JFrame settingsFrame = new JFrame("Settings");
        settingsFrame.setSize(400, 300);
        settingsFrame.setLocationRelativeTo(null);
        settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a panel for settings components
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridLayout(3, 2, 10, 10));

        // Add components for a Map option
        JLabel mapLabel = new JLabel("Map:");

        //This stuff adjusts the border between the screen edge and text (Gaping)
        mapLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        JComboBox<String> mapOptions = new JComboBox<>(new String[]{"Map 1", "Map 2", "Map 3"});
        settingsPanel.add(mapLabel);
        settingsPanel.add(mapOptions);

        // Add components for a Pacman Speed option
        JLabel pacmanSpeedLabel = new JLabel("Pacman Speed:");

        //This stuff adjusts the border between the screen edge and text (Gaping)
        pacmanSpeedLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        JSlider pacmanSpeedSlider = new JSlider(1, 10, 5);
        configureSlider(pacmanSpeedSlider);
        settingsPanel.add(pacmanSpeedLabel);
        settingsPanel.add(pacmanSpeedSlider);

        // Add components for a Ghost Speed option
        JLabel ghostSpeedLabel = new JLabel("Ghost Speed:");

        //This stuff adjusts the border between the screen edge and text (Gaping)
        ghostSpeedLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        JSlider ghostSpeedSlider = new JSlider(1, 10, 5);
        configureSlider(ghostSpeedSlider);
        settingsPanel.add(ghostSpeedLabel);
        settingsPanel.add(ghostSpeedSlider);

        settingsFrame.add(settingsPanel);
        settingsFrame.setVisible(true);

        // Update Pacman speed
        pacmanSpeedSlider.addChangeListener(e -> {
            int newPacmanSpeed = ((JSlider) e.getSource()).getValue();
            System.out.println("New Pacman Speed: " + newPacmanSpeed);
            // Logic how to actually change is to be implemented
//            Pacman.getInstance(). // change speed
        });

        // Update Ghost speed
        ghostSpeedSlider.addChangeListener(e -> {
            int newGhostSpeed = ((JSlider) e.getSource()).getValue();
            System.out.println("New Ghost Speed: " + newGhostSpeed);
            // Logic how to actually change is to be implemented
            //same here
        });

        // Update the map
        mapOptions.addActionListener(e -> {
            String selectedMap = (String) mapOptions.getSelectedItem();
            System.out.println("Selected Map: " + selectedMap);

            if ((int)mapOptions.getSelectedItem() == 1)
                map1 = true;
            if ((int)mapOptions.getSelectedItem() == 2)
                map2 = true;
            if ((int)mapOptions.getSelectedItem() == 3)
                map3 = true;
        });
    }

    private void configureSlider(JSlider slider) {
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        for (int i = slider.getMinimum(); i <= slider.getMaximum(); i++) {
            labelTable.put(i, new JLabel(Integer.toString(i)));
        }
        slider.setLabelTable(labelTable);
    }

    public boolean isMap1() {
        return map1;
    }

    public boolean isMap2() {
        return map2;
    }

    public boolean isMap3() {
        return map3;
    }
}

