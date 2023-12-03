package cs;

// ClientModel.java
import java.io.IOException;
import java.net.Socket;

public class ClientModel {

  private String serverAddress;
  private int port;
  private String username;
  private Socket socket;

  public void setServerAddress(String serverAddress) {
    this.serverAddress = serverAddress;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Socket connectToServer() throws IOException {
    socket = new Socket(serverAddress, port);
    return socket;
  }

  public void disconnectFromServer() throws IOException {
    if (socket != null && !socket.isClosed()) {
      socket.close();
    }
  }

  public String getUsername() {
    return username;
  }
}
