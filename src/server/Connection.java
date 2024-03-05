// Name: Shaolong Xu Username: SHAOLONGX Student ID: 1067946
package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

public class Connection extends Thread {
    private Socket socket;
    private BufferedReader input;
    private BufferedWriter output;
    private ConcurrentHashMap<String, String> dict;
    private ServerGUI serverGUI;
    private static final int REQ_ARRAY_LENGTH_WITH_MEANING = 3;


    /**
     * Constructor to initialize the Connection thread.
     * 
     * @param socket The client socket.
     * @param dict   The dictionary data.
     * @throws IOException If there's an error initializing input/output streams.
     */
    public Connection(Socket socket, ConcurrentHashMap<String, String> dict, ServerGUI serverGUI) throws IOException {
        this.socket = socket;
        this.dict = dict;
        this.serverGUI = serverGUI; // Set the serverGUI instance
    }

    @Override
    public void run() {
        // Initialize the input and output streams
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.err.println("Error initialising streams: " + e.getMessage());
            return;
        }

        String request;

        try {
            while (true) {
                request = input.readLine();

                if (request == null) {
                	serverGUI.clientDisconnected();
                    System.out.println("Connection terminated by the client.");
                    socket.close();
                    return;
                } else {
                    handleRequest(request);
                }
            }
        } catch (Exception e) {
            System.err.println("Error handling client request: " + e.getMessage());
        }
    }

    /**
     * Handle client requests based on the provided method (ADD, DELETE, UPDATE, QUERY).
     * 
     * @param request The client request string.
     * @throws IOException If there's an error writing to the output stream.
     */
    private void handleRequest(String request) throws IOException {
        String[] reqArray = request.split("/");
        String method = reqArray[0];
        String text = reqArray[1];
        // Assign the third element of reqArray to 'meaning' if it exists, otherwise assign 'null' to 'meaning'
        String meaning = reqArray.length == REQ_ARRAY_LENGTH_WITH_MEANING ? reqArray[2] : null;

        switch (method) {
            case "ADD":
                handleAdd(text, meaning);
                break;
            case "DELETE":
                handleDelete(text);
                break;
            case "UPDATE":
                handleUpdate(text, meaning);
                break;
            case "QUERY":
                handleQuery(text);
                break;
        }
    }

    private void handleAdd(String text, String meaning) throws IOException {
        if (dict.containsKey(text)) {
            output.write("The text \"" + text + "\" already exists in the dictionary. Please enter a unique text.\n");
            output.flush();
            return;
        }

        dict.put(text, meaning);
        DictionaryServer.writeDictionary();
        output.write("\"" + text + "\" has been successfully added to the dictionary.\n");
        output.flush();
    }

    private void handleDelete(String text) throws IOException {
        if (!dict.containsKey(text)) {
            output.write("The text \"" + text + "\" is not found in the dictionary. Please try again.\n");
            output.flush();
            return;
        }

        dict.remove(text);
        DictionaryServer.writeDictionary();
        output.write("\"" + text + "\" has been successfully removed from the dictionary.\n");
        output.flush();
    }

    private void handleUpdate(String text, String meaning) throws IOException {
        if (!dict.containsKey(text)) {
            output.write("The text \"" + text + "\" does not exist in the dictionary. You might want to add it first.\n");
            output.flush();
            return;
        }

        if (dict.get(text).equals(meaning)) {
            output.write("The provided meaning is the same as the existing one for \"" + text + "\".\n");
            output.flush();
            return;
        }

        dict.put(text, meaning);
        DictionaryServer.writeDictionary();
        output.write("The meaning of \"" + text + "\" has been successfully updated in the dictionary.\n");
        output.flush();
    }

    private void handleQuery(String text) throws IOException {
        if (!dict.containsKey(text)) {
            output.write("The text \"" + text + "\" is not present in the dictionary. Ensure you've entered it correctly.\n");
            output.flush();
            return;
        }

        String queryMeaning = dict.get(text);
        output.write("QUERY_RESPONSE_PREFIX" + "/" + queryMeaning + "\n");
        output.flush();
    }
}
