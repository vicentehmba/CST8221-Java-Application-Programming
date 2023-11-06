package cs;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

class GameController {
    private GameModel model;
    private GameView view;
    private Timer timer;

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

    public void setupRandomConfiguration() {
        model.setupRandomConfiguration();
        view.updateUI();
    }

    public void handleMouseClick(int x, int y) {
    model.handleManualCellToggle(x, y);
    view.updateUI();
}

    public void setupManualConfiguration() {
        model.setupManualConfiguration();
        view.updateUI();
    }

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
}

class GamePanel extends JPanel {
    private GameModel model;

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

