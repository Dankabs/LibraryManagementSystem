import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CoverPage extends JPanel {
    private MainApp mainApp;

    public CoverPage(MainApp mainApp) {
        // Set background color
        setBackground(new Color(0, 0, 0));
        this.mainApp = mainApp;
        // Use null layout for absolute positioning
        setLayout(null);
        
        // Create and configure the label to display an image
        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setBounds(364, 0, 453, 600); // Set position and size
        add(lblNewLabel);

        // Load and set the image icon for the label
        ImageIcon image = new ImageIcon("focus.jpg");
        lblNewLabel.setIcon(image);
        
        // Create and configure the "explore" button
        JButton btnNewButton = new JButton("explore");
        btnNewButton.setFont(new Font("Tw Cen MT", Font.BOLD, 27));
        btnNewButton.setBounds(153, 479, 117, 43); // Set position and size
        btnNewButton.setFocusable(false); // Prevent the button from being focusable
        add(btnNewButton);
        
        // Create and configure the "KNOWLEDGE" label
        JLabel lblNewLabel_1 = new JLabel("KNOWLEDGE");
        lblNewLabel_1.setFont(new Font("Sylfaen", Font.BOLD, 45));
        lblNewLabel_1.setForeground(new Color(255, 255, 255)); // White color
        lblNewLabel_1.setBounds(22, 166, 332, 61); // Set position and size
        add(lblNewLabel_1);
        
        // Create and configure the "OASIS" label
        JLabel lblNewLabel_2 = new JLabel("OASIS");
        lblNewLabel_2.setFont(new Font("Sylfaen", Font.BOLD, 35));
        lblNewLabel_2.setForeground(new Color(255, 255, 255)); // White color
        lblNewLabel_2.setBounds(74, 216, 183, 49); // Set position and size
        add(lblNewLabel_2);
        
        // Add an ActionListener to the button to switch to the MainGUI panel
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainApp.showMainGUI(); // Call the method to switch panels
            }
        });
    }
}
