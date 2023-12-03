package cs;

// Server.java
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class Server {

  private static final int DEFAULT_PORT = 8080;
  private static ServerSocket serverSocket;
  private static JLabel logoLabel;
  private static JTextField portField;
  private static JButton startButton;
  private static JButton stopButton;
  private static JButton modelButton;
  private static JCheckBox finalizeCheckBox;
  private static JTextArea logArea;
  private static Map<String, PrintWriter> clientMap = new HashMap<>();

  public static void main(String[] args) {
    JFrame frame = new JFrame("Turing Machine Server");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(560, 280);
    frame.setResizable(false);

    //
    JPanel headerPanel = new JPanel();
    headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    logoLabel = new JLabel("");
    logoLabel.setIcon(new ImageIcon("src/A32-resources/tm-server.png"));
    headerPanel.add(logoLabel);

    //
    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

    portField = new JTextField(5);
    portField.setText(Integer.toString(DEFAULT_PORT));

    startButton = new JButton("Start");
    modelButton = new JButton("Model");

    finalizeCheckBox = new JCheckBox("Finalizes");

    stopButton = new JButton("Stop");
    logArea = new JTextArea();

    JScrollPane scrollPane = new JScrollPane(logArea);

    contentPanel.add(new JLabel("Port:"));
    contentPanel.add(portField);
    contentPanel.add(startButton);
    contentPanel.add(modelButton);
    contentPanel.add(finalizeCheckBox);
    contentPanel.add(stopButton);

    // Main panel
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(headerPanel, BorderLayout.NORTH);
    panel.add(contentPanel, BorderLayout.CENTER);

    frame.getContentPane().add(panel, "North");
    frame.getContentPane().add(scrollPane, "Center");
    frame.setVisible(true);

    startButton.addActionListener(
      new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          try {
            int port = Integer.parseInt(portField.getText());
            serverSocket = new ServerSocket(port);

            InetAddress ipAddress = InetAddress.getLocalHost();
            logMessage(
              "Server running at: " + ipAddress.getHostAddress() + ":" + port
            );

            Thread serverThread = new Thread(new ServerTask());
            serverThread.start();

            startButton.setEnabled(false);
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        }
      }
    );

    stopButton.addActionListener(
      new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          try {
            if (serverSocket != null && !serverSocket.isClosed()) {
              serverSocket.close();
              logMessage("Server stopped.");
              startButton.setEnabled(true);
            }
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        }
      }
    );
  }

  static class ServerTask implements Runnable {

    public void run() {
      try {
        while (!serverSocket.isClosed()) {
          Socket clientSocket = serverSocket.accept();

          BufferedReader in = new BufferedReader(
            new InputStreamReader(clientSocket.getInputStream())
          );
          String username = in.readLine();
          PrintWriter out = new PrintWriter(
            clientSocket.getOutputStream(),
            true
          );
          clientMap.put(username, out);

          int connectedClients = clientMap.size();
          logMessage(
            "Client connected: " +
            username +
            ". Total Clients: " +
            connectedClients
          );

          out.println("Hello, " + username + "! You are connected.");

          new Thread(new ClientHandler(clientSocket, username)).start();
        }
      } catch (IOException e) {
        if (!serverSocket.isClosed()) {
          e.printStackTrace();
          logMessage("Server encountered an error: " + e.getMessage());
        }
      }
    }
  }

  static void logMessage(String message) {
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    String timeStamp = formatter.format(new Date());
    logArea.append("[" + timeStamp + "] " + message + "\n");
  }

  public static Map<String, PrintWriter> getClientMap() {
    return clientMap;
  }
}
