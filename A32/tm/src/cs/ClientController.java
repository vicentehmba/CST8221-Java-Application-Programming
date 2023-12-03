package cs;

// ClientController.java
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientController {

  private ClientView view;
  private ClientModel model;

  public ClientController(ClientView view, ClientModel model) {
    this.view = view;
    this.model = model;

    this.view.setConnectButtonAction(new ConnectListener());
    this.view.setDisconnectButtonAction(new DisconnectListener());
  }

  class ConnectListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      try {
        model.setServerAddress(view.getServerAddress());
        model.setPort(Integer.parseInt(view.getPort()));
        model.setUsername(view.getUsername());
        Socket socket = model.connectToServer();
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(model.getUsername());
        view.showMessage("Connected to server");
        view.setConnectButtonEnabled(false);
        view.setDisconnectButtonEnabled(true);
      } catch (IOException ex) {
        view.showMessage("Error: " + ex.getMessage());
      }
    }
  }

  class DisconnectListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      try {
        model.disconnectFromServer();
        view.showMessage("Disconnected from server.");
        view.setConnectButtonEnabled(true);
        view.setDisconnectButtonEnabled(false);
      } catch (IOException ex) {
        view.showMessage("Connection Error: " + ex.getMessage());
      }
    }
  }
}
