//Name: Shaolong Xu Username: SHAOLONGX Student ID: 1067946
package server;

import javax.swing.*;
import java.awt.*;

public class ServerGUI extends JFrame {
    private JTextArea statusArea;
    private JLabel clientCountLabel;
    private int clientCount = 0;

    public ServerGUI() {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }

        setTitle("Server Status");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.LIGHT_GRAY); 

        statusArea = new JTextArea();
        statusArea.setEditable(false);
        statusArea.setFont(new Font("Arial", Font.PLAIN, 14)); 
        statusArea.setBorder(BorderFactory.createTitledBorder("Server Logs")); 
        JScrollPane scrollPane = new JScrollPane(statusArea);

        clientCountLabel = new JLabel("Connected Clients: 0");
        clientCountLabel.setFont(new Font("Arial", Font.BOLD, 16)); 
        clientCountLabel.setHorizontalAlignment(JLabel.CENTER);
        

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(null);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(clientCountLabel, BorderLayout.SOUTH);
        setContentPane(contentPanel);

        setVisible(true);
    }

    public void updateStatus(String status) {
        statusArea.append(status + "\n");
    }

    public void clientConnected() {
        clientCount++;
        updateClientCount();
        updateStatus("A client has connected.");
    }

    public void clientDisconnected() {
        clientCount--;
        updateClientCount();
        updateStatus("A client has disconnected.");
    }

    private void updateClientCount() {
        clientCountLabel.setText("Connected Clients: " + clientCount);
    }
}
