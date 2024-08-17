import javax.swing.*;
import java.awt.*;

public class MainApp extends JFrame {
    private JPanel cardPanel; // Panel to switch between different screens

    public MainApp() {
        // Set up the main frame
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Initialize the card panel with CardLayout
        cardPanel = new JPanel(new CardLayout());
        add(cardPanel);

        // Add the CoverPage panel
        CoverPage coverPage = new CoverPage(this);
        cardPanel.add(coverPage, "CoverPage");

        // Add the MainGUI panel
        MainGUI mainGui = new MainGUI();
        cardPanel.add(mainGui, "MainGUI");

        // Show the CoverPage initially
        CardLayout cl = (CardLayout) (cardPanel.getLayout());
        cl.show(cardPanel, "CoverPage");
    }

    // Method to switch to the MainGUI panel
    public void showMainGUI() {
        CardLayout cl = (CardLayout) (cardPanel.getLayout());
        cl.show(cardPanel, "MainGUI");
    }

    public static void main(String[] args) {
        // Run the application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new MainApp().setVisible(true);
        });
    }
}
