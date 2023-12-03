package cs;

// ClientHandler.java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

  private Socket clientSocket;
  private String username;
  private PrintWriter out;

  public ClientHandler(Socket clientSocket, String username) {
    this.clientSocket = clientSocket;
    this.username = username;
    try {
      this.out = new PrintWriter(clientSocket.getOutputStream(), true);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void run() {
    try {
      BufferedReader in = new BufferedReader(
        new InputStreamReader(clientSocket.getInputStream())
      );

      while (true) {
        String clientMessage = in.readLine();
        if (clientMessage == null) {
          break; // Exit loop if clientMessage is null (client disconnected)
        }

        // Handle client's requests or messages here...

        // Example: Sending a message back to the client
        sendMessageToClient("Received: " + clientMessage);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        clientSocket.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }

      Server.logMessage("Client disconnected: " + username);
      Server.getClientMap().remove(username);
      int connectedClients = Server.getClientMap().size();
      Server.logMessage("Total Clients: " + connectedClients);
    }
  }

  // Method to send a message to the client
  private void sendMessageToClient(String message) {
    if (out != null) {
      out.println(message);
    }
  }
}
