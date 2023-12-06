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
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class Server extends JFrame {

  private static final long serialVersionUID = 1L;
  private static int DEFAULT_PORT = 12345;
  private static ServerSocket serverSocket;
  private static JLabel logoLabel;
  private static JTextField portField;
  private static JButton startButton;
  private static JButton stopButton;
  private static JButton modelButton;
  private static JCheckBox finalizeCheckBox;
  private static JTextArea logArea;
  private static String serverInit;
  private static int connectedClients;
  private static Map<String, PrintWriter> clientMap = new HashMap<>();
  private static volatile boolean isServerRunning = false;
  private static String receivedModel;
  static String defaultTape = "0000000000000000000000000";

  public Server() {
    setTitle("Turing Machine Sever1");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(560, 280);
    setResizable(false);

    JPanel headerPanel = new JPanel();
    headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    logoLabel = new JLabel("");
    logoLabel.setIcon(new ImageIcon("src/A32-resources/tm-server.png"));
    headerPanel.add(logoLabel);

    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

    portField = new JTextField(5);
    portField.setText(Integer.toString(DEFAULT_PORT));

    startButton = new JButton("Start");
    modelButton = new JButton("Model");

    finalizeCheckBox = new JCheckBox("Finalizes");

    stopButton = new JButton("Stop");

    logArea = new JTextArea();
    logArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(logArea);

    contentPanel.add(new JLabel("Port:"));
    contentPanel.add(portField);
    contentPanel.add(startButton);
    contentPanel.add(modelButton);
    contentPanel.add(finalizeCheckBox);
    contentPanel.add(stopButton);

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(headerPanel, BorderLayout.NORTH);
    panel.add(contentPanel, BorderLayout.CENTER);

    getContentPane().add(panel, "North");
    getContentPane().add(scrollPane, "Center");

    startButton.addActionListener(e -> startServer());
    stopButton.addActionListener(e -> stopServer());
    stopButton.setEnabled(false);

    serverInit = "Sever1 initialized...";
    logMessage(serverInit);

    setVisible(true);

    modelButton.addActionListener(e -> {
      if (receivedModel != null && !receivedModel.isEmpty()) {
        logMessage("Model received from client: " + receivedModel);
      } else {
        logMessage("No model received from client or empty model.");
      }
    });
  }

  public static void main(String[] args) {
    new Server();
  }

  private void startServer() {
    try {
      int port = Integer.parseInt(portField.getText());
      if (port < 10000 || port > 65535) {
        JOptionPane.showMessageDialog(
          null,
          "Please enter a valid port number between 10000 and 65535."
        );
        return;
      }

      serverSocket = new ServerSocket(port);
      logMessage("Sever1 running on port: " + port);
      startButton.setEnabled(false);
      stopButton.setEnabled(true);

      isServerRunning = true;

      Thread serverThread = new Thread(() -> {
        while (isServerRunning) {
          try {
            Socket clientSocket = serverSocket.accept();
            if (!isServerRunning) {
              break; // Exit the loop if server is stopped
            }
            Thread clientThread = new Thread(() -> handleClient(clientSocket));
            clientThread.start();
          } catch (IOException e) {
            if (isServerRunning) {
              e.printStackTrace();
            }
          }
        }
      });
      serverThread.start();
    } catch (NumberFormatException | IOException e) {
      e.printStackTrace();
    }
  }

  private static void stopServer() {
    try {
      isServerRunning = false; // Set flag to stop accepting new connections
      if (serverSocket != null) {
        serverSocket.close();
        logMessage("Sever1 stopped.");
        stopButton.setEnabled(false);
        startButton.setEnabled(true);
      } else {
        logMessage("Sever1 stopped due to no clients remaining.");
        startButton.setEnabled(true); // Enable start button after stopping the server
        stopButton.setEnabled(false); // Disable stop button after stopping the server
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void checkFinalizeStatus() {
    if (finalizeCheckBox.isSelected() && clientMap.isEmpty()) {
      stopServer(); // Call a method to stop the server
    }
  }

  private static void handleClient(Socket clientSocket) {
    try {
      BufferedReader in = new BufferedReader(
        new InputStreamReader(clientSocket.getInputStream())
      );
      PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

      // Read the username sent by the client
      String username = in.readLine();

      clientMap.put(username, out);
      logMessage("Client connected: " + username);

      connectedClients = clientMap.size();
      logMessage("Total Clients: " + connectedClients);

      // Send a welcome disconnectMessage to the client
      String welcomeMessage = "Hello, " + username + "! You are connected.";
      out.println("Message from server: " + welcomeMessage);

      String model = in.readLine(); // Read the client's model
      receivedModel = model;

      String command;
      while ((command = in.readLine()) != null) {
        if (command.equals("RUN")) {
          // The client requested to run the Turing Machine
          logMessage(
            "Received request to execute Turing Machine from: " + username
          );

          // Perform Turing Machine operations with the received model
          String simulationResult = TuringMachineOperations(model);

          // Sending the result back to the client
          out.println(simulationResult);

          break; // Break out of the loop after handling the "RUN" command
        }
      }

      // Start a separate thread to handle this client's requests
      Thread clientThread = new Thread(
        new ClientHandler(clientSocket, username)
      );
      clientThread.start();
    } catch (IOException e) {
      e.printStackTrace(); // Handle exceptions
    }
  }

  private static String TuringMachineOperations(String model) {
    Server.TuringMachine tm = new Server.TuringMachine(
      defaultTape,
      parseModel(model)
    );
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    tm.setLogMessage(printWriter);
    tm.run();

    return stringWriter.toString();
  }

  static class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final String username;

    public ClientHandler(Socket clientSocket, String username) {
      this.clientSocket = clientSocket;
      this.username = username;
    }

    @Override
    public void run() {
      TuringMachine tm = null;

      try {
        BufferedReader in = new BufferedReader(
          new InputStreamReader(clientSocket.getInputStream())
        );
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        String model = in.readLine();
        String tape = in.readLine();

        int[][] transitions = parseModel(model);

        if (model == null || model.isEmpty()) {
          // Assuming tm is an instance of the TuringMachine class
          tm = new TuringMachine(model, transitions); // Update or create a new Turing machine
          tm.run(); // Execute the Turing machine
        }

        // Validate tape data received from the client
        if (tape != null) {
          tm = new TuringMachine(tape, transitions);
          // Proceed with TuringMachine operations
        } else if (tape == null || tape.isEmpty()) {
          tape = defaultTape; // Assign default tape value
        }

        String disconnectMessage;
        try {
          StringWriter stringWriter = new StringWriter();
          tm.setLogMessage(new PrintWriter(stringWriter));
          tm.run();
          out.println(stringWriter.toString());
          while ((disconnectMessage = in.readLine()) != null) {
            if (disconnectMessage.equals("DISCONNECT")) {
              break; // Break out of the loop on disconnection signal
            }
          }
        } catch (IOException e) {
          e.printStackTrace();
        } finally {
          clientMap.remove(username); // Remove the disconnected client
          connectedClients = clientMap.size(); // Update connected clients count
          logMessage("Client disconnected: " + username);
          logMessage("Total Clients: " + connectedClients);
          try {
            clientSocket.close(); // Close the client socket
            in.close();
            out.close();
          } catch (IOException e) {
            e.printStackTrace(); // Handle socket closure exception
          }
          // Check if finalizeCheckBox is selected and no clients are connected
          Server.checkFinalizeStatus();
        }
      } catch (IOException e) {
        // Handle exceptions (client disconnected)
        e.printStackTrace();
      }
    }
  }

  public static Map<String, PrintWriter> getClientMap() {
    return clientMap;
  }

  static void logMessage(String disconnectMessage) {
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    String timeStamp = formatter.format(new Date());
    logArea.append("[" + timeStamp + "] " + disconnectMessage + "\n");
  }

  private static int[][] parseModel(String model) {
    int transitionsCount = model.length() / 5;
    int[][] transitions = new int[transitionsCount][5];

    for (int i = 0; i < transitionsCount; i++) {
      String transitionString = model.substring(i * 5, (i + 1) * 5);
      for (int j = 0; j < 5; j++) {
        transitions[i][j] =
          Character.getNumericValue(transitionString.charAt(j));
      }
    }

    return transitions;
  }

  static class TuringMachine {

    private char[] tape;
    private int headPosition;
    private int currentState;
    private int[][] transitions;
    private PrintWriter showResult;

    public TuringMachine(String input, int[][] transitions) {
      this.tape = input.toCharArray();
      this.headPosition = input.length() / 2;
      this.currentState = 1; // Start from state 1
      this.transitions = transitions;
    }

    public void run() {
      int step = 1;

      while (
        currentState != 0 && headPosition >= 0 && headPosition < tape.length
      ) {
        char currentSymbol = tape[headPosition];
        int[] currentTransition = transitions[currentState];

        showResult.append("\nStep: " + step + "\n");
        showResult.append("Tape position: " + headPosition + "\n");
        showResult.append("Current state: " + currentState + "\n");

        int transitionIndex = -1;
        for (int i = 0; i < currentTransition.length; i += 5) {
          if (currentSymbol == (char) currentTransition[i + 1]) {
            transitionIndex = i;
            break;
          }
        }

        if (transitionIndex != -1) {
          currentState = currentTransition[transitionIndex + 2];
          tape[headPosition] = (char) currentTransition[transitionIndex + 3];
          if (currentTransition[transitionIndex + 4] == 1) {
            headPosition++;
          } else {
            headPosition--;
          }

          displayTape();
        } else {
          break;
        }
        step++;
      }

      showResult.append("\nFinal state: " + currentState + "\n");
      showResult.append("Final tape configuration: ");
      displayTape();
    }

    private void displayTape() {
      for (int i = 0; i < tape.length; i++) {
        if (i == headPosition) {
          showResult.append("[" + tape[i] + "]");
        } else {
          showResult.append(String.valueOf(tape[i]));
        }
      }
      showResult.append("\n");
    }

    public void setLogMessage(PrintWriter printWriter) {
      this.showResult = printWriter;
    }
  }
}
