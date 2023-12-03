package cs;

// ClientView.java
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ClientView {

  private JTextField usernameField;
  private JButton connectButton;
  private JButton disconnectButton;
  private JTextArea logArea;
  private static JLabel logoLabel;
  private static JTextField tmField;
  private static JButton validateButton;
  private static JButton sendButton;
  private static JButton receiveButton;
  private static JButton runButton;
  private static JTextField portField;
  private static JTextField serverAddressField;

  public ClientView() {
    JFrame frame = new JFrame("Turing Machine Client");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(560, 320);
    //frame.setResizable(false);

    // Header Panel
    JPanel headerPanel = new JPanel();
    headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    logoLabel = new JLabel("");
    logoLabel.setIcon(new ImageIcon("src/A32-resources/tm-client.png"));
    headerPanel.add(logoLabel);

    // Content Panel
    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
    usernameField = new JTextField("User", 4);
    serverAddressField = new JTextField("localhost", 5);
    portField = new JTextField("8080", 3);
    connectButton = new JButton("Connect");
    disconnectButton = new JButton("Disconnect");
    disconnectButton.setEnabled(false);
    logArea = new JTextArea(10, 40);

    JScrollPane scrollPane = new JScrollPane(logArea);

    contentPanel.add(new JLabel("User:"));
    contentPanel.add(usernameField);
    contentPanel.add(new JLabel("Server:"));
    contentPanel.add(serverAddressField);
    contentPanel.add(new JLabel("Port:"));
    contentPanel.add(portField);
    contentPanel.add(connectButton);
    contentPanel.add(disconnectButton);

    //
    JPanel contentPanelPlus = new JPanel();
    contentPanelPlus.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
    tmField = new JTextField(17);
    validateButton = new JButton("Validate");
    sendButton = new JButton("Send");
    receiveButton = new JButton("Receive");
    runButton = new JButton("Run");

    contentPanelPlus.add(new JLabel("TM:"));
    contentPanelPlus.add(tmField);
    contentPanelPlus.add(validateButton);
    contentPanelPlus.add(sendButton);
    contentPanelPlus.add(receiveButton);
    contentPanelPlus.add(runButton);

    // Main panel
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(headerPanel, BorderLayout.NORTH);
    panel.add(contentPanel, BorderLayout.CENTER);
    panel.add(contentPanelPlus, BorderLayout.SOUTH);

    frame.getContentPane().add(panel, "North");
    frame.getContentPane().add(scrollPane, "Center");
    frame.setVisible(true);

    connectButton.addActionListener(e -> {
      if (connectButton.isEnabled()) {
        connectButton.setEnabled(false);
        disconnectButton.setEnabled(true);
      }
    });

    disconnectButton.addActionListener(e -> {
      if (disconnectButton.isEnabled()) {
        disconnectButton.setEnabled(false);
        connectButton.setEnabled(true);
      }
    });
  }

  public String getUsername() {
    return usernameField.getText();
  }

  public String getServerAddress() {
    return ""; // Get server address from field
  }

  public String getPort() {
    String portText = portField.getText().trim(); // Get port from field and trim whitespace
    if (portText.isEmpty()) {
      return "8080";
    }
    return portText;
  }

  public void setConnectButtonAction(ActionListener actionListener) {
    connectButton.addActionListener(actionListener);
  }

  public void setDisconnectButtonAction(ActionListener actionListener) {
    disconnectButton.addActionListener(actionListener);
  }

  public void showMessage(String message) {
    logArea.append(message + "\n");
  }

  public void setConnectButtonEnabled(boolean enabled) {
    connectButton.setEnabled(enabled);
  }

  public void setDisconnectButtonEnabled(boolean enabled) {
    disconnectButton.setEnabled(enabled);
  }
}
