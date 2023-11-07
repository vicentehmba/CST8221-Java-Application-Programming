/**
 * File Name: CSModel.java
 * Identification: Vicente Mba Engung 041029226 // Ken Dekpor 041054266
 * Course: CST 8221 – JAP, Lab Section: [300, 302]
 * Assignment: A12
 * Professor: Daniel Cormier
 * Date: November 6, 2023.
 * Compiler: Eclipse IDE for Java Developers – Version: 2023-09 (4.29.0)
 * Purpose: Source code for the startup menu screen
 */

package cs;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Class Name: CSModel
 * Method List: main
 * Constants List:
 * Purpose: Staring point of the all the program
 *
 * @author Vicente Mba Engung / Ken Dekpor
 * @version 1.0
 * @see cs
 * @since (4.29.0)
 */

public class CSModel {

    /**
     * Default constructor for the class CSModel.
     * This constructor is automatically generated and does not require any parameters.
     * It can be used for basic initialization or setup of the CSModel object.
     */
    public CSModel() {}

    /**
     * Entry point of the CA program.
     *
     * @param args The command-line arguments passed to the program.
     */
    public static void main(String[] args) {
        // Set the look and feel to the system default
        try {
            UIManager.LookAndFeelInfo LAFIArray[] = UIManager.getInstalledLookAndFeels();
            UIManager.setLookAndFeel(LAFIArray[1].getClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        GameOfLife gl = new GameOfLife();

        // Creates and modifies Swing components on the event dispatch thread.
        SwingUtilities.invokeLater(() -> {
            // Create the dialog
            JDialog dialog = new JDialog();
            dialog.setTitle("[JAP — Computer Science]");
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setResizable(false);
            dialog.setSize(460, 180);
            dialog.setLayout(null); // Set layout to null

            // Create a label
            JLabel lbLogo = new JLabel("");
            lbLogo.setIcon(new ImageIcon("./src/resources/CSmin.png"));
            lbLogo.setBounds(40, 10, 120, 100); // Set position and size
            dialog.add(lbLogo);

            // Create a drop-down menu with ComboBox
            String[] assignments = {"[A12]CA - Cellular Automata",
                    "[A22]GL - Game of Life", "[A32]TMS - Turing Machine Server",
                    "[A32]TMC - Turing Machine Client"};
            JComboBox<String> cbAssignments = new JComboBox<>(assignments);
            cbAssignments.setBounds(175, 50, 230, 20); // Set position and size of comboBox cbAssignments
            dialog.add(cbAssignments);

            // Create three buttons
            JButton okButton = new JButton("OK");
            JButton cancelButton = new JButton("Cancel");
            JButton helpButton = new JButton("Help");

            // Add action listeners to the buttons
            okButton.addActionListener(e -> {

                // Get the selected item from the ComboBox
                String selectedItem = (String) cbAssignments.getSelectedItem();

                switch (Objects.requireNonNull(selectedItem)) {
                    case "[A12]CA - Cellular Automata" -> {
                        SwingUtilities.invokeLater(CellularAutomata::new);
                        dialog.setVisible(false);
                    }
                    case "[A22]GL - Game of Life" -> {
                        gl.initGameOfLife();
                    }
                    case "[A32]TMS - Turing Machine Server" -> {
                        //SwingUtilities.invokeLater(TMS::new);
                        dialog.setVisible(true);
                    }
                    case "[A32]TMC - Turing Machine Client" -> {
                        //SwingUtilities.invokeLater(TMC::new);
                        dialog.setVisible(true);
                    }
                }
            });

            cancelButton.addActionListener(e -> System.exit(0));

            helpButton.addActionListener(e -> {
                ImageIcon helpIcon = new ImageIcon("./src/resources/help.png");
                JOptionPane.showMessageDialog(dialog, "Help button clicked", "Help",
                        JOptionPane.INFORMATION_MESSAGE, helpIcon);
            });

            // Set positions and sizes for the buttons
            okButton.setBounds(82, 100, 80, 30);
            cancelButton.setBounds(190, 100, 80, 30);
            helpButton.setBounds(300, 100, 80, 30);

            // Add buttons to the dialog
            dialog.add(okButton);
            dialog.add(cancelButton);
            dialog.add(helpButton);

            // Center the dialog on the screen
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (dim.width - dialog.getWidth()) / 2;
            int y = (dim.height - dialog.getHeight()) / 2;
            dialog.setLocation(x, y);

            // Set dialog visibility
            dialog.setVisible(true);
        });
    }
}
