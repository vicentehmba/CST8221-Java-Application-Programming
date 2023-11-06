package cs;

import javax.swing.*;

public class GameOfLife {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameModel model = new GameModel(30, 30);
            GameView view = new GameView(model);
            GameController controller = new GameController(model, view);
            view.setController(controller); // Set the controller in the view
            view.createAndShowGUI();
        });
    }
}
