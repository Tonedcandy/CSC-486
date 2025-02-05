package monishss;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {

    private JLabel connectionStatusLabel;
    private JLabel fileStatusLabel;
    private JLabel commandStatusLabel;

    public StatusPanel() {
        // Set background to black and use white text.
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);

        // Use a BoxLayout for vertical stacking.
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Create labels with default text and white color.
        connectionStatusLabel = createStatusLabel("Connection: Unknown");
        commandStatusLabel = createStatusLabel("Commands: None");

        // Add some padding by wrapping labels in a panel or using borders.
        connectionStatusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        commandStatusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Add labels to the StatusPanel.
        add(connectionStatusLabel);
        add(commandStatusLabel);
    }

    // Helper method to create a JLabel with white text.
    private JLabel createStatusLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    // Public methods to update the status text.
    public void setConnectionStatus(String status) {
        connectionStatusLabel.setText("Connection: " + status);
    }


    public void setCommandStatus(String status) {
        commandStatusLabel.setText("Commands: " + status);
    }
}