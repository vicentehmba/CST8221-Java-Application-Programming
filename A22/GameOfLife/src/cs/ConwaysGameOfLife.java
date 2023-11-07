package cs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ConwaysGameOfLife extends JFrame {
    private final int[][] grid;
    private final int gridSize = 30;
    private int generation = 0;
    private int numGenerationsToExecute = 1;
    private boolean isRunning = true; // Start with isRunning set to true
    private final Timer timer;
    private final int cellSize = 20;
    private final boolean[][] nextGeneration;
    private JTextField binaryInputField;
    private JTextField numGenerationsField;

    public ConwaysGameOfLife() {
        setTitle("Conway's Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        grid = new int[gridSize][gridSize];
        nextGeneration = new boolean[gridSize][gridSize];

        JPanel controlPanel = new JPanel();
        JButton randomButton = new JButton("Random Setup");
        JButton manualButton = new JButton("Manual Setup");
        JButton playButton = new JButton("Pause/Play"); // Renamed button
        JLabel generationLabel = new JLabel("Generation: " + generation);

        randomButton.addActionListener(e -> setupRandomConfiguration());
        manualButton.addActionListener(e -> setupManualConfiguration());
        playButton.addActionListener(e -> togglePlayMode());

        controlPanel.add(randomButton);
        controlPanel.add(manualButton);
        controlPanel.add(playButton);
        controlPanel.add(generationLabel);

        add(controlPanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        binaryInputField = new JTextField(18);
        numGenerationsField = new JTextField(5);
        binaryInputField.addActionListener(e -> setConfigurationFromBinaryInput());
        numGenerationsField.addActionListener(e -> setNumGenerations());

        inputPanel.add(new JLabel("Enter 18-bit binary:"));
        inputPanel.add(binaryInputField);
        inputPanel.add(new JLabel("Generations:"));
        inputPanel.add(numGenerationsField);

        add(inputPanel, BorderLayout.SOUTH);

        timer = new Timer(500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isRunning) {
                    step();
                    generationLabel.setText("Generation: " + generation);
                }
            }
        });

        //pack();
        setSize(600, 700);
        setLocationRelativeTo(null);
        setVisible(true);

        GamePanel gamePanel = new GamePanel();
        add(gamePanel, BorderLayout.CENTER);
        timer.start(); // Start the timer when the application starts
    }

    private void setupRandomConfiguration() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = Math.random() < 0.3 ? 1 : 0;
            }
        }
        generation = 0;
        repaint();
    }

    private void setupManualConfiguration() {
        isRunning = false;
        timer.stop();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int mouseX = e.getX() / cellSize;
                int mouseY = e.getY() / cellSize;

                if (isValidCell(mouseX, mouseY)) {
                    grid[mouseX][mouseY] = grid[mouseX][mouseY] == 1 ? 0 : 1;
                    repaint();
                }
            }
        });
    }

    private void togglePlayMode() {
        isRunning = !isRunning;
    }

    private void step() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                int neighbors = countAliveNeighbors(i, j);

                if (grid[i][j] == 1) {
                    nextGeneration[i][j] = neighbors >= 2 && neighbors <= 3;
                } else {
                    nextGeneration[i][j] = neighbors == 3;
                }
            }
        }

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = nextGeneration[i][j] ? 1 : 0;
            }
        }

        generation++;
        repaint();

        if (generation >= numGenerationsToExecute) {
            isRunning = false;
            timer.stop();
        }
    }

    private int countAliveNeighbors(int x, int y) {
        int count = 0;
        int[][] neighbors = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

        for (int[] neighbor : neighbors) {
            int newX = x + neighbor[0];
            int newY = y + neighbor[1];

            if (isValidCell(newX, newY) && grid[newX][newY] == 1) {
                count++;
            }
        }

        return count;
    }

    private boolean isValidCell(int x, int y) {
        return x >= 0 && x < gridSize && y >= 0 && y < gridSize;
    }

    private void setConfigurationFromBinaryInput() {
        String binaryInput = binaryInputField.getText();
        if (binaryInput.length() != 18 || !binaryInput.matches("[01]+")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid 18-bit binary sequence.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int i = 0; i < binaryInput.length(); i++) {
            int cellValue = binaryInput.charAt(i) == '1' ? 1 : 0;
            int row = i / gridSize;
            int col = i % gridSize;
            grid[row][col] = cellValue;
        }

        generation = 0;
        repaint();
    }

    private void setNumGenerations() {
        String numGenerationsInput = numGenerationsField.getText();
        try {
            numGenerationsToExecute = Integer.parseInt(numGenerationsInput);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number of generations.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            for (int i = 0; i < gridSize; i++) {
                for (int j = 0; j < gridSize; j++) {
                    if (grid[i][j] == 1) {
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ConwaysGameOfLife::new);
    }
}

