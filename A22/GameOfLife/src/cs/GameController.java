/**
 * File Name: GameController.java
 * Identification: Vicente Mba Engung 041029226 // Ken Dekpor 041054266
 * Course: CST 8221 – JAP, Lab Section: [300, 302]
 * Assignment: A22
 * Professor: Daniel Cormier
 * Date: November 6, 2023.
 * Compiler: Eclipse IDE for Java Developers – Version: 2023-09 (4.29.0)
 *
 * Purpose: This class serves as the controller for the Game of Life application. It handles user interactions,
 * updates the model, and controls the timer for the simulation.
 */

package cs;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import javax.swing.*;

/**
 * The GameController class is responsible for managing user interactions, updating the model,
 * and controlling the simulation timer in the Game of Life application.
 */
class GameController {
    private GameModel model;
    private GameView view;
    private Timer timer;

    /**
     * Creates a new GameController with the specified model and view.
     *
     * @param model The GameModel to associate with the controller.
     * @param view  The GameView to associate with the controller.
     */
    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
        this.timer = new Timer(500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (model.isRunning()) {
                    model.step();
                    view.updateUI();
                }
            }
        });
    }

    /**
     * Sets up a random configuration in the model and updates the view.
     */
    public void setupRandomConfiguration() {
        model.setupRandomConfiguration();
        view.updateUI();
    }

    /**
     * Handles a mouse click at the specified coordinates, toggling the cell's state and updating the view.
     *
     * @param x The x-coordinate of the clicked cell.
     * @param y The y-coordinate of the clicked cell.
     */
    public void handleMouseClick(int x, int y) {
        model.handleManualCellToggle(x, y);
        view.updateUI();
    }

    /**
     * Sets up a manual configuration in the model and updates the view.
     */
    public void setupManualConfiguration() {
        model.setupManualConfiguration();
        view.updateUI();
    }

    /**
     * Toggles the play mode in the model and updates the view. Starts or stops the simulation timer accordingly.
     */
    public void togglePlayMode() {
        model.togglePlayMode();
        view.updateUI();
        if (model.isRunning()) {
            view.playButton.setText("Pause");
            timer.start(); // Start the timer to advance the game
        } else {
            view.playButton.setText("Play");
            timer.stop(); // Stop the timer
        }
    }

    /**
     * Handles the action of the "Validate" checkbox in the view.
     * Shows a message dialog when the checkbox is clicked.
     */
    public void validatCheckBoxAction() {
        if (view.validatCheckBox.isSelected()) {
            // Code implementation here
        }
    }

    /**
     * Handles the action of the "Color" button in the view.
     * Shows a message dialog when the button is clicked.
     */
    public void colorButtonAction() {
        JOptionPane.showMessageDialog(null, "Clicked!");
    }

    /**
     * Handles the action of the "About" submenu in the menu bar.
     * Opens a web browser to the Wikipedia page about Conway's Game of Life.
     */
    public void subMenuAboutAction() {
        try {
            Desktop.getDesktop().browse(new URL("https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life").toURI());
        } catch (Exception ev) {
        }
    }

    /**
     * Handles the action of the "English" submenu in the menu bar.
     * Loads and applies English language properties for the user interface.
     */
    public void subMenuEnglishAction() {
        try {
            Properties propertyEN = new Properties();
            try (InputStream inputStream = getClass().getResourceAsStream("/resources/CS_en_US.properties")) {
                if (inputStream != null) {
                    propertyEN.load(inputStream);
                } else {
                    System.err.println("File not found.");
                }
            } catch (IOException ie) {
                ie.printStackTrace();
            }

            // Set button titles using the properties
            String gameMenuEN = propertyEN.getProperty("game");
                String languagesMenuEN = propertyEN.getProperty("lang");
                String englishMenuEN = propertyEN.getProperty("english");
                String frenchMenuEN = propertyEN.getProperty("french");
                String helpMenuEN = propertyEN.getProperty("help");
                String aboutMenuEN = propertyEN.getProperty("about");
                String btnRandomEN = propertyEN.getProperty("random");
                String btnManualEN = propertyEN.getProperty("manual");
                String lbModelEN = propertyEN.getProperty("model");
                String lbMulticolorEN = propertyEN.getProperty("multicolor");
                String btnColorEN = propertyEN.getProperty("color");
                String btnStartEN = propertyEN.getProperty("play");
                String lbStepsEN = propertyEN.getProperty("steps");
                String lbExecEN = propertyEN.getProperty("generations");
                String btnStopEN = propertyEN.getProperty("pause");
                String subMenuNew = propertyEN.getProperty("new");
                String subMenuSolution = propertyEN.getProperty("solution");
                String subMenuExit = propertyEN.getProperty("exit");
                String subMenuColor = propertyEN.getProperty("color");
                view.gameMenu.setText(gameMenuEN);
                view.languagesMenu.setText(languagesMenuEN);
                view.subMenuEnglish.setText(englishMenuEN);
                view.subMenuFrench.setText(frenchMenuEN);
                view.helpMenu.setText(helpMenuEN);
                view.randomButton.setText(btnRandomEN);
                view.subMenuAbout.setText(aboutMenuEN);
                view.manualButton.setText(btnManualEN);
                view.modelLabel.setText(lbModelEN);
                view.multicolorLabel.setText(lbMulticolorEN);
                view.colorButton.setText(btnColorEN);
                view.playButton.setText(btnStartEN);
                view.stepsLabel.setText(lbStepsEN);
                view.generationLabel.setText("\t\t" + lbExecEN + " 0\t\t");
                view.playButton.setText(btnStopEN);
                view.subMenuNew.setText(subMenuNew);
                view.subMenuSolution.setText(subMenuSolution);
                view.subMenuExit.setText(subMenuExit);
                view.subMenuColor.setText(subMenuColor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of the "French" submenu in the menu bar.
     * Loads and applies French language properties for the user interface.
     */
    public void subMenuFrenchAction() {
        try {
            Properties propertyFR = new Properties();
            try (InputStream inputStream = getClass().getResourceAsStream("/resources/CS_fr_FR.properties")) {
                if (inputStream != null) {
                    propertyFR.load(inputStream);
                } else {
                    System.err.println("File not found.");
                }
            } catch (IOException ie) {
                ie.printStackTrace();
            }

            // Set button titles using the properties
            String gameMenuFR = propertyFR.getProperty("game");
                String languagesMenuFR = propertyFR.getProperty("lang");
                String englishMenuFR = propertyFR.getProperty("english");
                String frenchMenuFR = propertyFR.getProperty("french");
                String helpMenuFR = propertyFR.getProperty("help");
                String aboutMenuFR = propertyFR.getProperty("about");
                String btnRandomFR = propertyFR.getProperty("random");
                String btnManualFR = propertyFR.getProperty("manual");
                String lbModelFR = propertyFR.getProperty("model");
                String lbMulticolorFR = propertyFR.getProperty("multicolor");
                String btnColorFR = propertyFR.getProperty("color");
                String btnStartFR = propertyFR.getProperty("play");
                String lbStepsFR = propertyFR.getProperty("steps");
                String lbExecFR = propertyFR.getProperty("generations");
                String btnStopFR = propertyFR.getProperty("pause");
                String subMenuNew = propertyFR.getProperty("new");
                String subMenuSolution = propertyFR.getProperty("solution");
                String subMenuExit = propertyFR.getProperty("exit");
                String subMenuColor = propertyFR.getProperty("color");
                view.gameMenu.setText(gameMenuFR);
                view.languagesMenu.setText(languagesMenuFR);
                view.subMenuEnglish.setText(englishMenuFR);
                view.subMenuFrench.setText(frenchMenuFR);
                view.helpMenu.setText(helpMenuFR);
                view.subMenuAbout.setText(aboutMenuFR);
                view.randomButton.setText(btnRandomFR);
                view.manualButton.setText(btnManualFR);
                view.modelLabel.setText(lbModelFR);
                view.multicolorLabel.setText(lbMulticolorFR);
                view.colorButton.setText(btnColorFR);
                view.playButton.setText(btnStartFR);
                view.stepsLabel.setText(lbStepsFR);
                view.generationLabel.setText("\t\t" + lbExecFR + " 0\t\t");
                view.playButton.setText(btnStopFR);
                view.subMenuNew.setText(subMenuNew);
                view.subMenuSolution.setText(subMenuSolution);
                view.subMenuExit.setText(subMenuExit);
                view.subMenuColor.setText(subMenuColor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * The GamePanel class represents the game board and is responsible for rendering the cells based on the model.
 */
class GamePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private GameModel model;

    /**
     * Creates a new GamePanel with the specified GameModel.
     *
     * @param model The GameModel to associate with the panel.
     */
    public GamePanel(GameModel model) {
        this.model = model;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int numRows = model.getNumRows();
        int numCols = model.getNumCols();

        int cellSize = 20;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (model.getCellState(i, j) == 1) {
                    g.setColor(Color.BLACK);
                    g.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
                }
            }
        }
    }
}
