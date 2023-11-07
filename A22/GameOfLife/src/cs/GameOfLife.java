/**
 * File Name: GameOfLife.java
 * Identification: Vicente Mba Engung 041029226 // Ken Dekpor 041054266
 * Course: CST 8221 – JAP, Lab Section: [300, 302]
 * Assignment: A12
 * Professor: Daniel Cormier
 * Date: November 6, 2023.
 * Compiler: Eclipse IDE for Java Developers – Version: 2023-09 (4.29.0)
 *
 * Purpose: This class serves as the entry point for the Game of Life application. It initializes the game by creating
 * a model, view, and controller, and sets up the user interface to display the game.
 */

package cs;

import javax.swing.SwingUtilities;

/**
 * The GameOfLife class initializes the Game of Life application.
 */
public class GameOfLife {

    /**
     * Initializes the Game of Life application by creating the model, view, and controller components and
     * setting up the user interface for the game.
     */
    void initGameOfLife() {
        SwingUtilities.invokeLater(() -> {
            // Create a new GameModel with a 30x30 grid
            GameModel model = new GameModel(30, 30);

            // Create a GameView with the model
            GameView view = new GameView(model);

            // Create a GameController with the model and view
            GameController controller = new GameController(model, view);

            // Set the controller in the view
            view.setController(controller);

            // Create and show the GUI for the game
            view.createAndShowGUI();
        });
    }
}
