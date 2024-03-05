//Name: Shaolong Xu Username: SHAOLONGX Student ID: 1067946
package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import javax.swing.JOptionPane;

public class DictionaryClient {
	
	static Socket client = null;
	static BufferedReader reader;
	static BufferedWriter writer;
	static int portNumber = 1234; // Port number can be changed here.
	
	public static void main(String[] args) {
		ClientUI cui = new ClientUI();
		cui.start();

		// Attempt to connect to the server
		try {
			client = new Socket("localhost", portNumber);
		}catch (UnknownHostException e) {
			handleError("Unable to resolve host.", "Connection Error");
			return;
		} catch (IOException e) {
			handleError("Unable to establish a connection to the server. Check if the server is running and the port number is correct.", "Connection Error");
			return;
		} catch (Exception e) {
			handleError("Unexpected error occurred while connecting to the server: " + e.getMessage(), "Connection Error");
			return;
		}
		
		// Attempt to read/write data from/to the server
		try {
			reader = new BufferedReader(new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8));
			writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), StandardCharsets.UTF_8));
			
			while(true) {
			    String receive = reader.readLine();
			    if (receive == null) {
			        // Notify the user with a popup window that the server has closed the connection.
			        JOptionPane.showMessageDialog(null, 
			                "The server has closed the connection. Please close the client.", 
			                "Server Closed", 
			                JOptionPane.ERROR_MESSAGE);
			        System.out.println("Server has closed the connection.");
			        break; // Exit the loop
			    }
				
				System.out.println("Received from server: " + receive);

				String queryResponsePrefix = "QUERY_RESPONSE_PREFIX";
				// Check if the received message is a query response; display it in the appropriate UI component based on its type.
				if (receive.startsWith(queryResponsePrefix)) {
					String[] arrayRec = receive.split("/");
					ClientUI.textDictionary.setText(arrayRec[1]);
				} else {
					ClientUI.textMessage.setText(receive);
				}
			}
		} catch (IOException e) {
			System.out.println("Error communicating with the server: " + e.getMessage());
		}catch (Exception e) {
			handleError("Unexpected error occurred while communicating with the server: " + e.getMessage(), "Communication Error");
		}finally {
			// Close resources
			try {
				if (reader != null) reader.close();
				if (writer != null) writer.close();
				if (client != null) client.close();
			} catch (IOException e) {
				System.out.println("Error closing resources: " + e.getMessage());
			}
		}
	}
	private static void handleError(String logMessage, String title) {
		System.err.println(logMessage);
		JOptionPane.showMessageDialog(null, logMessage, title, JOptionPane.ERROR_MESSAGE);
	}
}
