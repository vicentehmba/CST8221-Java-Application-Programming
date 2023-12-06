package cs;

import javax.swing.SwingUtilities;

public class TuringMachineMain {

  /**
   * The entry point for starting the Client/Server GUI.
   * Creates an instance of the Client and server class and sets them visible.
   * @param args The command line arguments (not used).
   */
  public static void main(String[] args) {
    // Start the server
    Thread serverThread = new Thread(() -> {
      new Server();
    });
    serverThread.start();

    // Start the client
    Thread clientThread = new Thread(() -> {
      SwingUtilities.invokeLater(
        new Runnable() {
          public void run() {
            Client clientGUI = new Client();
            clientGUI.setVisible(true);
          }
        }
      );
    });
    clientThread.start();
  }
}
