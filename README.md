# Dictionary

**Introduction**

In the dynamic world of digital communication, the demand for systems adept at concurrent and efficient data processing is escalating. To address this need, the project presents a dictionary system based on a client-server architecture. The system features functionalities for adding, deleting, updating, and querying words. It is designed with a robust server capable of concurrently managing multiple client requests, thereby ensuring quick and accurate processing.

To meet the stipulations of the assignment, we have constructed a client-server architecture where the server is tailored to concurrently manage multiple client requests. This system's foundation lies in the strategic use of threads and sockets, chosen for communication and managing potential failures. Specifically, we've adopted a 'thread-per-connection' architecture on the server side, enabling a multi-threaded environment. The dictionary's data repository is a txt file, ensuring ease of access and modification. For reliable client-server communication, we've employed TCP sockets. We've also prioritised error management; both ends are equipped to handle common issues like ‘Bind Exception’, ‘IO Exception’, ‘Unknown Host Exception’, ‘Null Pointer Exception’ and ‘General Exception’ without disrupting the system's flow. The exchange of information between the client and server is facilitated using Strings embedded with distinct characters, acting as directives for the required actions.

**Components of the system**

The multi-threaded dictionary system is structured within five Java classes, broadly segmented into three core components: Server, Client, and UI.

The Server acts as the central hub, managing the dictionary data and listening for incoming client connections. Upon initialization, the server reads the dictionary data and stands ready to process requests. For every client that connects, the server spawns a dedicated thread, ensuring that multiple clients can be serviced simultaneously without interference. This design promotes efficient and concurrent processing of client requests, such as adding, deleting, updating, and querying words.

The Client serves as the bridge between the user and the server. It establishes a connection to the server and handles the two-way communication. When a user interacts with the client's UI to make a request, the client sends this request to the server, waits for the server's response, and then processes and displays the result to the user through the UI.

The UI component is twofold. On the server side, the UI provides a visual representation of server activity, including logs and the number of active client connections. This gives administrators a real-time overview of the server's status. On the client side, the UI offers an interactive platform for users to engage with the dictionary system. It captures user inputs, displays messages, and showcases explanations based on the server's feedback. The client's UI is designed to be user-friendly, ensuring that users can easily perform operations and receive immediate feedback.

In essence, these three components work in harmony, with the Client and Server communicating seamlessly through established connections, and the UI components on both sides ensuring that users and administrators have a smooth and informative experience.

**Class design**

In the architecture of the multi-threaded dictionary system, five pivotal classes come into play, each serving a distinct role while collaborating seamlessly to achieve the system's objectives.

1.	‘DictionaryServer’: This class is the backbone of the server component. It initializes the server, listens for incoming client connections, and manages the dictionary data. The class employs a ‘ConcurrentHashMap’ to store the dictionary entries in-memory and uses a txt file for persistent storage. It also leverages the thread-per-connection model, spawning a new thread for each client connection to ensure concurrent processing.

2.	‘Connection’: This class is instantiated by the ‘DictionaryServer’ for each client connection. It runs as a separate thread and is responsible for handling client requests. The class uses input and output streams to read from and write to the client, thereby processing requests like adding, deleting, updating, and querying words in the dictionary.

3.	‘DictionaryClient’: This class acts as the client's main entry point. It establishes a connection to the server and sets up input and output streams for communication. The class sends user requests to the server and processes the server's responses, updating the UI accordingly.

4.	‘ServerGUI’: This class provides a graphical user interface for the server. It displays real-time logs and the number of active client connections. The class is instantiated by the ‘DictionaryServer’ and is updated via method calls whenever there is a change in the server's status or client activity.

5.	‘ClientUI’: This class offers an interactive user interface for the client. It captures user inputs for various operations like adding, deleting, updating, and querying words. The class also displays messages and explanations based on the server's feedback.

The architecture employs a client-server model with a thread-per-connection strategy on the server side. The ‘DictionaryServer’ and ‘Connection’ classes work in tandem to manage multiple client connections concurrently. The ‘DictionaryClient’ class interacts with the ‘Connection’ threads on the server side through TCP sockets, facilitated by input and output streams. The ‘ServerGUI’ and ‘ClientUI’ classes provide the user interfaces for the server and client components, respectively, and are updated based on activities and responses from their corresponding main classes (‘DictionaryServer’ for ‘ServerGUI’ and ‘DictionaryClient’ for ‘ClientUI’). The sequence diagram below illustrates the relationship between the classes ‘DictionaryServer’, ‘DictionaryClient’, ‘Connection’, ‘ClientUI’, and ‘ServerGUI’.


 <img width="1000" alt="image" src="https://github.com/Shaolong214/Dictionary/assets/103941617/ecce254a-3fb8-47b7-bd54-3fcabe3556cd">

/*Figure 1: Client-Server Interaction in a Multi-threaded Dictionary Application*/

The sequence diagram depicts the multi-threaded interaction between a dictionary server and its clients. Initially, the DictionaryServer initializes, setting up a listening socket and a GUI interface. As clients connect, individual threads (Connection) are spawned to handle their requests. For instance, when a client, through its ClientUI, sends a "query" request for a word's meaning, this request is channelled through the DictionaryClient to the server's Connection thread. The server processes the query, retrieves the word's meaning, and sends a response back. The client then displays this response on its user interface, completing the request-response cycle.

By employing this architecture and methodological design, the system achieves efficient, concurrent, and user-friendly dictionary services.

**Analysis and Conclusion**

The multi-threaded dictionary system, as presented, is a testament to thoughtful design and architectural decisions. At its core, the system employs a client-server model, a widely recognized and effective architecture for distributed applications. This choice ensures a clear separation of concerns, with the server managing the dictionary data and the client focusing on user interactions.

One of the primary design decisions was the adoption of the thread-per-connection strategy. This approach ensures that each client connection is handled concurrently, promoting scalability and responsiveness. However, it's worth noting that this model might not be the most efficient for systems with a very high number of simultaneous connections, as managing many threads can be resource intensive.

When initialising a GUI in Java, the placement of ‘frame.setVisible(true);’ is crucial. If set at the beginning, the frame becomes visible while components are still being added, causing multiple repaints. This can lead to a leggy appearance, flickering, and partial rendering, negatively impacting user experience. Conversely, placing it at the end ensures the GUI is rendered only once, after all components are initialized. This approach enhances performance by avoiding unnecessary repaints, offers a smoother user experience without visual disruptions, and aids in debugging by ensuring any GUI issues are addressed before it's displayed to the user.

The protocol employed, which uses strings and specially designed characters for communication, is straightforward and easy to implement. While this simplicity is advantageous for clarity and debugging, it might not be the most efficient in terms of data transfer, especially for larger payloads. Binary protocols or more compressed formats could offer better performance and reduced network overhead.

The decision to store the dictionary in a txt file and load it into a ConcurrentHashMap is pragmatic for a moderate-sized dictionary. The in-memory storage ensures rapid access and modifications. However, for larger datasets, a more scalable storage solution, like a database, might be more appropriate.

Comparatively, while the system's architecture and protocol are effective for its intended scale, there are alternative design decisions that could be considered. Thread-worker-pool architecture, for instance, might offer better scalability for very high concurrent connections. Additionally, more advanced protocols, like gRPC or Protocol Buffers, could provide more efficient serialization and communication.

**Excellence**

1.	Client-Side Error Handling
As mentioned, certain errors are handled on the client side to reduce the server's workload. This includes checking if the word or its meaning are provided before attempting to add, delete, update or query a new entry. If not, a pop-up window is displayed to the user with an error message and instructions related to that action.

2.	Server Shutdown Notification
If the server is closed or shut down while a client is connected, the client is immediately notified through a pop-up window. This ensures that the user is aware of the server's status and can close the client without attempting further interactions with a non-responsive server.

3.	Port Number Conflict Handling
The system checks if the chosen port number for the server is already in use. If there's a conflict, a pop-up window notifies the user, guiding them to select a different port number.

4.	Client Connection Port Error
If a user enters an incorrect port number when trying to connect the client, or if the server isn't running, a pop-up window appears to inform the user of the potential issue. This provides clarity and guides the user to either check the port number or ensure the server is up.

5.	Server GUI for Monitoring
The server has a graphical user interface (GUI) that displays the connections between the server and clients. This provides a visual representation of the server's status and connected clients, enhancing the user experience for whoever is managing the server.

6.	Concurrent Data Structure
The dictionary data is stored in a ConcurrentHashMap, which is a thread-safe variant of HashMap. This ensures that multiple threads can access the dictionary concurrently without facing synchronization issues, enhancing the system's performance and reliability.

7.	Comprehensive UI Feedback
The client UI provides feedback for various actions. For instance, when a word is queried, its meaning is displayed in a dedicated text area. Similarly, messages from the server, such as errors or confirmations, are displayed in a separate message area.

8.	Logging Server Responses to Console
The line ‘System.out.println("Received from server: " + receive)’ ; prints a message to the console, indicating the data received from the server. It serves as a real-time log for developers or administrators to monitor server-client communication. This aids in debugging by visually confirming and tracking the exact data the client obtains from the server.

9.	Graceful Error Handling
Beyond the standard exceptions, the system also handles specific scenarios, like when a word already exists in the dictionary or when a word is not found. These scenarios are managed gracefully, with clear messages sent to the user.

These unique implementation aspects highlight the thoughtful design and user-centric approach adopted in the system's development. The combination of robust backend functionality with intuitive frontend interactions ensures a seamless and efficient user experience.
