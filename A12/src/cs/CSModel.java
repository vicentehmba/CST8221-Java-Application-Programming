/**
 * File Name: CSModel.java
 * Identification: Vicente Mba Engung 041029226
 * Course: CST 8221 – JAP, Lab Section: [300, 302]
 * Assignment: A12
 * Professor: Daniel Cormier
 * Date: September 25, 2023
 * Compiler: Eclipse IDE for Java Developers – Version: 2023-09 (4.29.0)
 * Purpose: Source code for the startup menu screen
 */

package cs;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Class Name: CSModel
 * Method List: main
 * Constants List:
 * Purpose: Staring point of the program
 * @author Vicente Mba Engung
 * @version 1.0
 * @see cs
 * @since (4.29.0)
 */
public class CSModel {
    public static void main(String[] args) {
    	
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create the dialog
                JDialog dialog = new JDialog();
                dialog.setTitle("[JAP – Computer Science]");
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setResizable(false);
                dialog.setSize(440, 180);
                dialog.setLayout(null); // Set layout to null

                // Create a label
                JLabel lbLogo = new JLabel("");
                lbLogo.setIcon(new ImageIcon("src/resources/CSmin.png"));
                lbLogo.setBounds(55, 10, 120, 100); // Set position and size
                dialog.add(lbLogo);

                // Create a drop down menu with ComboBox
                String[] assignments = {"[A12]CA – Cellular Automata", 
                		"[A22]GL – Game of Life", "[A32]TM – Turning Machine", 
                		"[A32] – TM Client"};
                JComboBox<String> cbAssignments = new JComboBox<>(assignments);
                cbAssignments.setBounds(175, 50, 230, 20); // Set position and size of comboBox cbAssignments
                dialog.add(cbAssignments);
                
                // Create three buttons
                JButton okButton = new JButton("OK");
                JButton cancelButton = new JButton("Cancel");
                JButton helpButton = new JButton("Help");

                // Add action listeners to the buttons
                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	
                    	// Get the selected item from the ComboBox
                        String selectedItem = (String) cbAssignments.getSelectedItem();
                        
                        switch (selectedItem) {
                        case "[A12]CA – Cellular Automata":
                        	SwingUtilities.invokeLater(() -> {
                                new CA();
                            });
                        	dialog.setVisible(false);
                        	break;
                        	
                        case "[A22]GL – Game of Life":
                        	JOptionPane.showMessageDialog(dialog, selectedItem);
                        	break;
                        	
                        case "[A32]TM – Turning Machine":
                        	JOptionPane.showMessageDialog(dialog, selectedItem);
                        	break;
                        	
                        case "[A32] – TM Client":
                        	JOptionPane.showMessageDialog(dialog, selectedItem);
                        }
                    }
                });

                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                });

                helpButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	ImageIcon helpIcon = new ImageIcon("src/resources/help.png");
                        JOptionPane.showMessageDialog(dialog, "Help button clicked", "Help", 
                        		JOptionPane.INFORMATION_MESSAGE, helpIcon);
                    }
                });

                // Set positions and sizes for the buttons
                okButton.setBounds(75, 100, 100, 30);
                cancelButton.setBounds(175, 100, 100, 30);
                helpButton.setBounds(275, 100, 100, 30);

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
            }
        });
    }
}
