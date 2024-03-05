//Name: Shaolong Xu Username: SHAOLONGX Student ID: 1067946
package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JOptionPane;

public class DictionaryServer {
	
	// Hash map to store the dictionary	
	static ConcurrentHashMap<String, String> dict = new ConcurrentHashMap<String, String>();
	static File file = new File("dictionary.txt");
	
	static ServerGUI serverGUI;
	static int portNumber = 1234; // Port number can be changed here.
	
	public static void main(String[] args) {

		// Start server socket	
		ServerSocket server = null;
		try {
			server = new ServerSocket(portNumber);
			System.out.println("Dictionary Server has started and is listening on port " + portNumber + ".");
			serverGUI = new ServerGUI(); // Instantiate the serverGUI here
			serverGUI.updateStatus("Server is up and running on port " + portNumber);
			
		} catch (BindException e) {
			handleError("Port " + portNumber + " is already in use. Please close any other processes using this port or use a different port.", "Port Error");
			return; // Exit the program
		} catch(IOException e) {
			handleError("Error initializing the server: " + e.getMessage(), "Initialization Error");
			return;
		} catch(Exception e) {
			handleError("Unexpected error occurred: " + e.getMessage(), "Unexpected Error");
			return;
		}

		// Read dictionary if exists	
		try {
			readDictionary();
		} catch(IOException e) {
			System.out.println("Error reading the dictionary: " + e.getMessage());
			return;
		}

		// Thread per connection 
		while(true) {
			try {
				// When there is a connection from client
				Socket request = server.accept();
				serverGUI.clientConnected(); // Update the GUI when a client connects
				System.out.println("Established a new connection with a client.");

				// Start new connection to maintain connection between client and server		
				Connection connection = new Connection(request, dict, serverGUI);
				connection.start();
			} catch(IOException e) {
				serverGUI.updateStatus("Error accepting client connection: " + e.getMessage());
				System.out.println("Error accepting client connection: " + e.getMessage());
			}
		}
	}

	// Read the dictionary.txt file
	public static void readDictionary() throws IOException {
		if(file.isFile() && file.exists()) {
			try(InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
				
				String line;
				// Read, convert and store the key-value format line into the dict.		
				while((line = bufferedReader.readLine()) != null) {
					String [] lines = line.split(":");
					dict.put(lines[0], lines[1]);
				}
			} catch(IOException e) {
				System.out.println("Error reading the dictionary file: " + e.getMessage());
				throw e;
			}
		}
	}
	
	// Write hash map to dictionary	
	public static void writeDictionary() throws IOException {
		try (BufferedWriter Writer = new BufferedWriter(new FileWriter(file))) {
			for(Entry<String, String> entry: dict.entrySet()) {
				Writer.write(entry.getKey() + ":" + entry.getValue() + "\n");		
			}
		} catch (IOException e) {
			System.out.println("Error writing to the dictionary file: " + e.getMessage());
			throw e;
		}
	}

	private static void handleError(String logMessage, String title) {
		System.out.println(logMessage);
		JOptionPane.showMessageDialog(null, logMessage, title, JOptionPane.ERROR_MESSAGE);
	}

}
