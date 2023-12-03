package cs;

public class Client {

  public static void main(String[] args) {
    ClientModel model = new ClientModel();
    ClientView view = new ClientView();
    new ClientController(view, model);
  }
}
