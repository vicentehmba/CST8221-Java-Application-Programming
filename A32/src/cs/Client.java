/**
 * File Name: CA.java
 * Identification: Vicente Mba Engung 041029226 // Ken Dekpor 041054266
 * Course: CST 8221 – JAP, Lab Section: [300, 302]
 * Assignment: A12
 * Professor: Daniel Cormier
 * Date: October 1, 2023.
 * Compiler: Eclipse IDE for Java Developers – Version: 2023-09 (4.29.0)
 * Purpose: Source code for the Cellular Automata
 */

/*
 * References: https://introcs.cs.princeton.edu/java/52turing/
 */

package cs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class Client extends JFrame {

  private static final long serialVersionUID = 1L;
  private static JTextField usernameField;
  private static JButton connectButton;
  private static JButton disconnectButton;
  private static JTextArea logArea;
  private static JLabel logoLabel;
  private static JTextField modelField;
  private static JButton validateButton;
  private static JButton sendButton;
  private static JButton receiveButton;
  private static JButton runButton;
  private static JTextField portField;
  private static JTextField serverAddressField;
  private static String clientInit;
  private boolean errorDisplayed = false;
  private StringBuilder receivedOutput = new StringBuilder(); // Accumulate received Turing Machine output

  private Socket socket;
  private BufferedReader in;
  private PrintWriter out;

  public Client() {
    setTitle("Turing Machine Client");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(580, 320);
    setResizable(false);

    // Header Panel
    JPanel headerPanel = new JPanel();
    headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    logoLabel = new JLabel("");
    logoLabel.setIcon(new ImageIcon("src/A32-resources/tm-client.png"));
    headerPanel.add(logoLabel);

    // Content Panel
    JPanel contentPanel1 = new JPanel();
    contentPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
    usernameField = new JTextField("User", 3);
    serverAddressField = new JTextField("localhost", 5);
    portField = new JTextField("12345", 4);
    connectButton = new JButton("Connect");
    disconnectButton = new JButton("Disconnect");
    disconnectButton.setEnabled(false);
    logArea = new JTextArea();
    logArea.setEditable(false);

    JScrollPane scrollPane = new JScrollPane(logArea);

    contentPanel1.add(new JLabel("User:"));
    contentPanel1.add(usernameField);
    contentPanel1.add(new JLabel("Server:"));
    contentPanel1.add(serverAddressField);
    contentPanel1.add(new JLabel("Port:"));
    contentPanel1.add(portField);
    contentPanel1.add(connectButton);
    contentPanel1.add(disconnectButton);

    JPanel contentPanelPlus = new JPanel();
    contentPanelPlus.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
    modelField = new JTextField("00000 01000 10010 11000");
    validateButton = new JButton("Validate");
    sendButton = new JButton("Send");
    receiveButton = new JButton("Receive");
    runButton = new JButton("Run");

    contentPanelPlus.add(new JLabel("TM:"));
    contentPanelPlus.add(modelField);
    contentPanelPlus.add(validateButton);
    contentPanelPlus.add(sendButton);
    contentPanelPlus.add(receiveButton);
    contentPanelPlus.add(runButton);

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(headerPanel, BorderLayout.NORTH);
    panel.add(contentPanel1, BorderLayout.CENTER);
    panel.add(contentPanelPlus, BorderLayout.SOUTH);

    getContentPane().add(panel, "North");
    getContentPane().add(scrollPane, "Center");

    clientInit = "Client initialized...";
    logMessage(clientInit);

    setVisible(true);

    // Connect
    connectButton.addActionListener(e -> {
      Runnable connectTask = () -> connectToServer();
      Thread connectThread = new Thread(connectTask);
      connectThread.start();
    });

    // Disconnect
    disconnectButton.addActionListener(e -> {
      Runnable disconnectTask = () -> disconnectFromServer();
      Thread disconnectThread = new Thread(disconnectTask);
      disconnectThread.start();
    });

    validateButton.addActionListener(e -> {
      Runnable validateModelTask = () -> validateModel();
      Thread validateModelThread = new Thread(validateModelTask);
      validateModelThread.start();
    });

    //Send
    sendButton.addActionListener(e -> {
      Runnable sendModelTask = () -> sendModelToServer();
      Thread sendModelThread = new Thread(sendModelTask);
      sendModelThread.start();
    });

    receiveButton.addActionListener(e -> {
      Runnable receiveModelTask = () -> receiveTuringMachine();
      Thread receiveModelThread = new Thread(receiveModelTask);
      receiveModelThread.start();
    });

    // Run
    runButton.addActionListener(e -> {
      Runnable runModelTask = () -> runTuringMachine();
      Thread runModelThread = new Thread(runModelTask);
      runModelThread.start();
    });
  }

  // Method to check if the entered TM model is valid
  private boolean isValidTMModel(String model) {
    // Check if the model consists of groups of 5 binary digits separated by spaces
    return model.matches("^([01]{5} )*[01]{5}$");
  }

  private void validateModel() {
    String model = modelField.getText().trim();
    if (isValidTMModel(model)) {
      logMessage("Valid Turing Machine Model entered.");
    } else {
      JOptionPane.showMessageDialog(
        null,
        "Please enter a valid binary model.",
        "Invalid Model",
        JOptionPane.ERROR_MESSAGE
      );
    }
  }

  private void connectToServer() {
    try {
      String serverAddress = serverAddressField.getText();
      String portString = portField.getText();

      if (portString.isEmpty()) {
        JOptionPane.showMessageDialog(
          null,
          "Please enter a valid port number."
        );
        return;
      }

      int serverPort = Integer.parseInt(portString);

      socket = new Socket(serverAddress, serverPort);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintWriter(socket.getOutputStream(), true);

      // Send the username to the server
      out.println(usernameField.getText()); // Ensure usernameField is initialized

      logMessage("Connecting...");

      // Read the greeting message from the server
      String greetingMessage = in.readLine(); // Read the server's response

      if (greetingMessage != null) {
        logMessage(greetingMessage); // Display the welcome message
      } else {
        logMessage("Error: No response from the server.");
      }

      connectButton.setEnabled(false);
      disconnectButton.setEnabled(true);
    } catch (ConnectException e) {
      // Handle the ConnectException
      logMessage("Connection error: Unable to connect to the server.");
      e.printStackTrace();
    } catch (IOException e) {
      logMessage("Connection error: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private void disconnectFromServer() {
    try {
      if (socket != null && !socket.isClosed()) {
        out.println("DISCONNECT");
        logMessage("Disconnected from server.");
        socket.close();
        in.close();
        out.close();

        connectButton.setEnabled(true);
        disconnectButton.setEnabled(false);
      }
    } catch (IOException e) {
      logMessage("Disconnection error: " + e.getMessage());
    }
  }

  private void receiveTuringMachine() {
    try {
      if (in != null) {
        logMessage("Received Turing Machine output from server.");
        String newLine;
        while ((newLine = in.readLine()) != null) {
          receivedOutput.append(newLine).append("\n"); // Accumulate output
        }
      } else {
        logMessage(
          "Error: Input stream is null. Please establish a connection to the server."
        );
      }
    } catch (IOException e) {
      logMessage("Error receiving Turing Machine output: " + e.getMessage());
    }
  }

  private void runTuringMachine() {
    if (out != null && !socket.isClosed()) {
      out.println("RUN"); // Send a request to the server to run the Turing Machine
      logMessage("Requesting Turing Machine execution...");
    } else {
      logMessage("Client is not connected to the server.");
    }

    // Display the accumulated Turing Machine output in a text area on a new frame
    outputTuringMachine();
  }

  private void outputTuringMachine() {
    JFrame outputFrame = new JFrame("Turing Machine");
    JTextArea outputArea = new JTextArea(10, 40); // Define the size of the text area
    outputArea.setEditable(false);
    outputArea.setText(receivedOutput.toString()); // Set the accumulated output in the text area

    JScrollPane scrollPane = new JScrollPane(outputArea);
    outputFrame.add(scrollPane);
    outputFrame.pack();
    outputFrame.setLocationRelativeTo(null); // Center the frame on the screen
    outputFrame.setVisible(true);
  }

  private void sendModelToServer() {
    if (isValidTMModel(modelField.getText())) {
      if (out == null || socket == null || socket.isClosed()) {
        if (!errorDisplayed) {
          logMessage("Client is not connected to the server.");
          errorDisplayed = true; // Set the flag to indicate the error was displayed
        }
        return;
      } else {}

      out.println(modelField.getText());
      logMessage("Turing Machine Model sent to server.");
    } else {
      JOptionPane.showMessageDialog(
        null,
        "Invalid Turing Machine Model. Please enter a valid model.",
        "Invalid Model",
        JOptionPane.ERROR_MESSAGE
      );
    }
  }

  static void logMessage(String message) {
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    String timeStamp = formatter.format(new Date());
    logArea.append("[" + timeStamp + "] " + message + "\n");
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(
      new Runnable() {
        public void run() {
          Client clientGUI = new Client();
          clientGUI.setVisible(true);
        }
      }
    );
  }
}
