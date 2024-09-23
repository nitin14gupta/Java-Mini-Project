import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SmartParkingSystemGUI extends JFrame {
    private static final int TOTAL_SLOTS = 12; // Increased number of slots
    private final JButton[] parkingSlots = new JButton[TOTAL_SLOTS];
    private final JLabel statusLabel = new JLabel("Welcome to Smart Parking System");

    public SmartParkingSystemGUI() {
        setTitle("Smart Parking System");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(0x34495e)); // Darker Blue Background
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Parking Slots Panel
        JPanel slotPanel = new JPanel(new GridLayout(3, 4, 20, 20)); // 3 rows, 4 columns with spacing
        slotPanel.setBackground(new Color(0xecf0f1)); // Light Gray Background
        for (int i = 0; i < TOTAL_SLOTS; i++) {
            parkingSlots[i] = new JButton("Slot " + (i + 1));
            styleButton(parkingSlots[i], Color.WHITE, new Color(0x27ae60), Color.BLACK); // White Button with Green Border
            parkingSlots[i].setFont(new Font("Arial", Font.BOLD, 14));
            parkingSlots[i].setPreferredSize(new Dimension(100, 100)); // Set button size
            parkingSlots[i].addActionListener(new SlotButtonListener());
            slotPanel.add(parkingSlots[i]);
        }

        // Footer
        JLabel footerLabel = new JLabel("Smart Parking System © 2024 Developed by [❤️Nitin Gupta ❤️]");
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footerLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
        footerLabel.setForeground(new Color(0x7f8c8d)); // Gray Text
        footerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Adding components to the frame
        add(statusLabel, BorderLayout.NORTH);
        add(slotPanel, BorderLayout.CENTER);
        add(footerLabel, BorderLayout.SOUTH);
    }

    private void styleButton(JButton button, Color backgroundColor, Color borderColor, Color textColor) {
        button.setBackground(backgroundColor);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(borderColor, 3));
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor on hover
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBorderPainted(true);
    }

    private class SlotButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            Color endColor;
            if (clickedButton.getBackground() == Color.WHITE) {
                endColor = new Color(0xe74c3c); // Red color for occupied
                statusLabel.setText(clickedButton.getText() + " is now occupied.");
            } else {
                endColor = Color.WHITE; // White color for available
                statusLabel.setText(clickedButton.getText() + " is now available.");
            }
            animateButton(clickedButton, endColor);
        }

        private void animateButton(JButton button, Color endColor) {
            new Thread(() -> {
                try {
                    Color startColor = button.getBackground();
                    for (int i = 0; i < 255; i += 5) {
                        int r = (startColor.getRed() * (255 - i) + endColor.getRed() * i) / 255;
                        int g = (startColor.getGreen() * (255 - i) + endColor.getGreen() * i) / 255;
                        int b = (startColor.getBlue() * (255 - i) + endColor.getBlue() * i) / 255;
                        button.setBackground(new Color(r, g, b));
                        Thread.sleep(10);
                    }
                    // Ensure the final color is set correctly after the animation
                    button.setBackground(endColor);
                    if (endColor == Color.WHITE) {
                        button.setBorder(BorderFactory.createLineBorder(new Color(0x27ae60), 3)); // Green Border when available
                    } else {
                        button.setBorder(BorderFactory.createLineBorder(new Color(0xc0392b), 3)); // Red Border when occupied
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }).start();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SmartParkingSystemGUI frame = new SmartParkingSystemGUI();
            frame.setVisible(true);
        });
    }
}
