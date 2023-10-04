/**
 * File Name: CA.java
 * Identification: Vicente Mba Engung 041029226 // Ken Dekpor 041054266
 * Course: CST 8221 – JAP, Lab Section: [300, 302]
 * Assignment: A12
 * Professor: Daniel Cormier
 * Date: October 1, 2023.
 * Compiler: Eclipse IDE for Java Developers – Version: 2023-09 (4.29.0)
 * Purpose: Source code for the Cellular Automata
 */

/*
 * References: https://natureofcode.com/book/chapter-7-cellular-automata/
 */

package cs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.border.Border;
import java.io.Serial;
import java.util.Objects;
import java.util.Properties;

/**
 * Class Name: CSModel
 * Method List: CA
 * Constants List: serialVersionUID
 * Purpose: GUI, logic and calculations for the CA program
 * 
 * @author Vicente Mba Engung
 * @version 1.0
 * @see cs
 * @since (4.29.0)
 */
public class CA extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * Two-dimensional array representing the cellular automaton grid.
     * The matrix holds the state of each cell in the grid.
     */
    private int[][] matrix;
    
    /**
     * Number of rows in the cellular automaton grid.
     * This field represents the vertical dimension of the grid.
     */
    private final int numRows;
    
    /**
     * Number of columns in the cellular automaton grid.
     * This field represents the horizontal dimension of the grid.
     */
    private final int numCols;
    
    /**
     * Rule number for the cellular automaton.
     * This field stores the rule that determines cell state transitions.
     */
    private int rule;

    /**
     * Responsible for setting up the initial state of the CA simulation.
     * Creating the graphical user interface (GUI).
     * Configuring various parameters of the simulation.
     */
    public CA() {
        setTitle("Cellular Automata");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 580);
        setResizable(false);

        // Create header panel with a label component
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.lightGray);
        JLabel caLogo = new JLabel("");
        caLogo.setIcon(new ImageIcon("./src/resources/ca.png"));
        headerPanel.add(caLogo);

        // Create main panel
        JPanel mainPanel = new JPanel() {
            @Serial
            private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawCA(g);
            }
        };

        // Create footer panel with components
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout());
        footerPanel.setBackground(Color.lightGray);

        JLabel lbLanguage = new JLabel("Language");
        JComboBox<String> cbLanguages = new JComboBox<>(new String[]{"English", "French", "Portuguese"});
        JLabel lbModel = new JLabel("Model:");
        JTextField txtModel = new JTextField(12);
        JButton btSet = new JButton("Set");
        JLabel lbShowModel = new JLabel("\t\tModel: \t\t\t\t\t");

        // Add a border to the label lbShowModel
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        lbShowModel.setBorder(border);

        // Add action listener to the comboBox cbLanguages
        cbLanguages.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	// Cast input from comboBox cbLanguages
            	String selectedItem = (String) cbLanguages.getSelectedItem();
            	
            	// SWITCH statement to cycle through cbLanguages
                switch (Objects.requireNonNull(selectedItem)) {
                    case "English" -> {
                        // Load properties from the file CS_en_US.properties
                        Properties propEnglish = new Properties();
                        try (InputStream inputStream = getClass().getResourceAsStream("/resources/CS_en_US.properties")) {
                            if (inputStream != null) {
                                propEnglish.load(inputStream);
                            } else {
                                System.err.println("File not found.");
                            }
                        } catch (IOException ie) {
                            ie.printStackTrace();
                        }

                        // Set button titles using the properties
                        String lbLanguageEnglish = propEnglish.getProperty("lang");
                        String lbModelEnglish = propEnglish.getProperty("model");
                        String btSetEnglish = propEnglish.getProperty("set");
                        String lbShowModelEnglish = propEnglish.getProperty("model");
                        lbLanguage.setText(lbLanguageEnglish);
                        lbModel.setText(lbModelEnglish + ":");
                        btSet.setText(btSetEnglish);
                        lbShowModel.setText("\t\t" + lbShowModelEnglish + ":" + rule + "\t\t");
                    }
                    case "French" -> {
                        // Load properties from the file CS_fr_FR.properties
                        Properties propFrench = new Properties();
                        try (InputStream inputStream = getClass().getResourceAsStream("/resources/CS_fr_FR.properties")) {
                            if (inputStream != null) {
                                propFrench.load(inputStream);
                            } else {
                                System.err.println("File not found.");
                            }
                        } catch (IOException ie) {
                            ie.printStackTrace();
                        }

                        // Set button titles using the properties
                        String lbLanguageFrench = propFrench.getProperty("lang");
                        String lbModelFrench = propFrench.getProperty("model");
                        String btSetFrench = propFrench.getProperty("set");
                        String lbShowModelFrench = propFrench.getProperty("model");
                        lbLanguage.setText(lbLanguageFrench);
                        lbModel.setText(lbModelFrench + ":");
                        btSet.setText(btSetFrench);
                        lbShowModel.setText("\t\t" + lbShowModelFrench + ":" + rule + "\t\t");
                    }
                    case "Portuguese" -> {
                        // Load properties from the file CS_pt_BR.properties
                        Properties propPortuguese = new Properties();
                        try (InputStream inputStream = getClass().getResourceAsStream("/resources/CS_pt_BR.properties")) {
                            if (inputStream != null) {
                                propPortuguese.load(inputStream);
                            } else {
                                System.err.println("File not found.");
                            }
                        } catch (IOException ie) {
                            ie.printStackTrace();
                        }

                        // Set button titles using the properties
                        String lbLanguagePortuguese = propPortuguese.getProperty("lang");
                        String lbModelPortuguese = propPortuguese.getProperty("model");
                        String btSetPortuguese = propPortuguese.getProperty("set");
                        String lbShowModelPortuguese = propPortuguese.getProperty("model");
                        lbLanguage.setText(lbLanguagePortuguese);
                        lbModel.setText(lbModelPortuguese + ":");
                        btSet.setText(btSetPortuguese);
                        lbShowModel.setText("\t\t" + lbShowModelPortuguese + ":" + rule + "\t\t");
                    }
                }
            }
        });

        // Action listener for the button btSet
        btSet.addActionListener(e -> {

            // Set the rule using the text field input
            try {
                rule = Integer.parseInt(txtModel.getText(), 2);

                // Update the label to display the decimal rule value
                lbShowModel.setText("\t\tModel: " + rule + "\t\t");

                // Update the automaton when the rule is set
                runCA();
                mainPanel.repaint();

            } catch (NumberFormatException ex) {

                // Handle invalid input
                JOptionPane.showMessageDialog(null, "Invalid binary input for rule.");
            }
        });

        // Adding components to the footer panel
        footerPanel.add(lbLanguage);
        footerPanel.add(cbLanguages);
        footerPanel.add(lbModel);
        footerPanel.add(txtModel);
        footerPanel.add(btSet);
        footerPanel.add(lbShowModel);

        // Add panels to the frame with BorderLayout
        add(headerPanel, BorderLayout.NORTH);

        // Set up the CA parameters
        numRows = 500;
        numCols = 800;
        
        // Invokes the method initializeMatrix()
        initializeMatrix();

        mainPanel.setLayout(new BorderLayout());

        // Create a timer to update the CA output periodically
        Timer timer = new Timer(100, e -> {
            runCA();
            mainPanel.repaint();
        });
        timer.start();

        add(mainPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Initializes the binary matrix used for the CA simulation.
     * Starts with a single 1 in the middle of the first row.
     */
    private void initializeMatrix() {
        matrix = new int[numRows][numCols];
        // Set the initial configuration with a single 1 in the middle of the first row
        matrix[0][numCols / 2] = 1;
    }

    /**
     * Calculates the new states of all cells in the CA grid.
     * Handle the edges of each cell with the wrap-around approach.
     */
    private void runCA() {
        for (int i = 1; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
            	// IF true, left neighbor wraps around rightmost cell  in the row above
                int left = (j == 0) ? matrix[i - 1][numCols - 1] : matrix[i - 1][j - 1];
                
                // No conditions to consider for current cell
                int center = matrix[i - 1][j];
                
                // IF true, right neighbor wraps around leftmost cell in the same row
                int right = (j == numCols - 1) ? matrix[i - 1][0] : matrix[i - 1][j + 1];
                int newState = applyRule(left, center, right);
                matrix[i][j] = newState;	// New state assigned to the corresponding cell
            }
        }
    }

    /**
     * Applies the CA rule to determine the state of a cell based on its neighboring cells.
     *
     * @param left   The state of the cell to the left.
     * @param center The state of the current cell.
     * @param right  The state of the cell to the right.
     * @return The new state of the current cell.
     */
    private int applyRule(int left, int center, int right) {
        // Convert the left, center, and right values to a binary rule index
        int index = left * 4 + center * 2 + right;
        
        // Check the corresponding bit in the rule
        return (rule & (1 << index)) >> index;
    }

    /**
     * Draws the CA grid.
     * Calculates the cell size.
     * Uses Graphics2D to fill cells based on their state.
     *
     * @param g The Graphics2D object used for drawing.
     */
    private void drawCA(Graphics g) {
        // Calculate the cell size to fit the automaton within the mainPanel
        int cellSize = Math.min(getWidth() / numCols, getHeight() / numRows);

        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (matrix[i][j] == 1) {
                    g2d.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                }
            }
        }
    }
}

