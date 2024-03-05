//Name: Shaolong Xu Username: SHAOLONGX Student ID: 1067946
package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.awt.Cursor;
import javax.swing.border.TitledBorder;
import javax.swing.JPanel;


public class ClientUI extends Thread {

	private JFrame frame;
	private JTextField textText;
	private JTextField textMeaning;
	static JTextArea textDictionary;
	static JTextArea textMessage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientUI window = new ClientUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					new ClientUI().displayErrorMessage("An error occurred while starting the application.");
	            }
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientUI() {
		initialize();
	}

	private void displayErrorMessage(String message) {
		JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		frame.getContentPane().setBackground(SystemColor.window);
		frame.setResizable(false);
		frame.setBounds(100, 100, 572, 311);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblDictionary = new JLabel("Dictionary");
		lblDictionary.setBounds(0, 0, 572, 49);
		lblDictionary.setFont(new Font("Silom", Font.BOLD, 30));
		lblDictionary.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblDictionary);

		JLabel lblText = new JLabel("Text:");
		lblText.setBounds(64, 56, 32, 16);
		frame.getContentPane().add(lblText);

		JLabel lblMeaning = new JLabel("Meaning:");
		lblMeaning.setBounds(42, 103, 61, 16);
		frame.getContentPane().add(lblMeaning);

		textText = new JTextField();
		textText.setBounds(106, 44, 391, 40);
		frame.getContentPane().add(textText);
		textText.setColumns(10);

		textMeaning = new JTextField();
		textMeaning.setBounds(106, 91, 391, 40);
		frame.getContentPane().add(textMeaning);
		textMeaning.setColumns(10);

		textDictionary = new JTextArea();
		textDictionary.setBackground(new Color(255, 255, 255));
		textDictionary.setBorder(new TitledBorder(null, "Explanation", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		textDictionary.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		textDictionary.setBounds(150, 161, 379, 103);
		frame.getContentPane().add(textDictionary);

		JButton btnQuery = new JButton("Query");
		btnQuery.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnQuery.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textDictionary.setText(null);
				textMessage.setText(null);

				String text = textText.getText();
				if (text.isEmpty()) {
					JOptionPane.showMessageDialog(btnQuery, "Please ensure the text is filled in before proceeding.");
					return;
				}
				String request = "QUERY"+"/"+text+"\n";
				try{DictionaryClient.writer.write(request);
					DictionaryClient.writer.flush();
				}catch (IOException error) {
					displayErrorMessage("Failed to send the query request to the server.");
					System.err.println("Error: " + error.getMessage());
				}

			}
		});
		btnQuery.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		btnQuery.setBounds(83, 209, 55, 55);
		frame.getContentPane().add(btnQuery);

		JButton btnAdd = new JButton("Add");
		btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textDictionary.setText(null);
				textMessage.setText(null);

				String text = textText.getText();
				String meaning = textMeaning.getText();
				if (text.isEmpty() || meaning.isEmpty()) {
					JOptionPane.showMessageDialog(btnAdd, "Please ensure both the text and its meaning are filled in before proceeding.");
					return;
				}
				String request = "ADD" + "/" + text + "/" + meaning + "\n";
				try{DictionaryClient.writer.write(request);
					DictionaryClient.writer.flush();
				}catch (IOException error) {
					displayErrorMessage("Failed to send the add request to the server.");
					System.err.println("Error: " + error.getMessage());
				}

			}
		});
		btnAdd.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		btnAdd.setBounds(29, 155, 55, 55);
		frame.getContentPane().add(btnAdd);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textDictionary.setText(null);
				textMessage.setText(null);

				String text = textText.getText();

				if (text.isEmpty()) {
					JOptionPane.showMessageDialog(btnDelete, "Please ensure the text is filled in before proceeding.");
					return;
				}
				String request = "DELETE"+"/"+text+"\n";
				try{DictionaryClient.writer.write(request);
					DictionaryClient.writer.flush();
				}catch (IOException error) {
					displayErrorMessage("Failed to send the delete request to the server.");
					System.err.println("Error: " + error.getMessage());
				}

			}
		});
		btnDelete.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		btnDelete.setBounds(83, 155, 55, 55);
		frame.getContentPane().add(btnDelete);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textDictionary.setText(null);
				textMessage.setText(null);

				String text = textText.getText();
				String meaning = textMeaning.getText();
				if (text.isEmpty() || meaning.isEmpty()) {
					JOptionPane.showMessageDialog(btnUpdate, "Please ensure both the text and its meaning are filled in before proceeding.");
					return;
				}
				String request = "UPDATE"+"/"+text+"/"+meaning+"\n";
				try{DictionaryClient.writer.write(request);
					DictionaryClient.writer.flush();
				}catch (IOException error) {
					displayErrorMessage("Failed to send the update request to the server.");
					System.err.println("Error: " + error.getMessage());
				}

			}
		});
		btnUpdate.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		btnUpdate.setBounds(29, 209, 55, 55);
		frame.getContentPane().add(btnUpdate);

		textMessage = new JTextArea();
		textMessage.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		textMessage.setEditable(false);
		textMessage.setForeground(Color.RED);
		textMessage.setBackground(SystemColor.window);
		textMessage.setBounds(28, 131, 512, 25);
		frame.getContentPane().add(textMessage);
		frame.setVisible(true);
	}
}
