package cs;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.io.Serial;

/**
 * A simple Java Swing application for Game of Life.
 */
public class GL extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new GameOfLife panel.
     *
     * @param rows    The number of rows in the grid.
     * @param columns The number of columns in the grid.
     */
    public GL(int rows, int columns) {
        // Create a blank Game of Life grid
    }

    /**
     * Create the GUI and show it on the Event Dispatch Thread.
     */
    static void createAndShowGUI() {
    	// Number of rows and columns for the Game of Life grid
        int rows = 50;
        int columns = 50;

        // Create the main application window
        JFrame frame = new JFrame("Game of Life");
        frame.setLayout(new BorderLayout());
        
        // Create a menu bar
        JMenuBar menu = new JMenuBar();
        menu.setVisible(true);
        frame.add(menu);

        // Create a header panel at the top of the window
        JPanel headerPanel = new JPanel();
        frame.add(headerPanel, BorderLayout.NORTH);
        
        // Create menu items
        JMenuItem game = new JMenuItem("Game");
        menu.add(game);
        
        JMenuItem languages = new JMenuItem("Languages");
        menu.add(languages);
        
        JMenuItem help = new JMenuItem("Help");
        menu.add(help);

        // Create a label for the header panel
        JLabel lbLogo = new JLabel();
        lbLogo.setText("");
        lbLogo.setIcon(new ImageIcon("./src/resources/GL_Logo.png"));
        headerPanel.add(lbLogo);
        
        

        // Create a footer panel at the bottom of the window
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        frame.add(footerPanel, BorderLayout.SOUTH);

        // Create buttons and components for the footer panel
        JButton btnRandom = new JButton();
        btnRandom.setText("Random");

        JButton btnManual = new JButton();
        btnManual.setText("Manual");

        JLabel lbModel = new JLabel();
        lbModel.setText("Model:");

        JTextField txtModel = new JTextField(18);

        JCheckBox cbCheck = new JCheckBox();

        JLabel lbMultiColor = new JLabel();
        lbMultiColor.setText("Multicolor");

        JButton btnColor = new JButton();
        btnColor.setText("Color");

        // Add buttons and components to the footer panel
        footerPanel.add(btnRandom);
        footerPanel.add(btnManual);
        footerPanel.add(lbModel);
        footerPanel.add(txtModel);
        footerPanel.add(cbCheck);
        footerPanel.add(lbMultiColor);
        footerPanel.add(btnColor);

        // Create an additional control panel
        JPanel additionalControlPanel = new JPanel();
        additionalControlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        // Create buttons and components for the additional control panel
        JButton btnStart = new JButton();
        btnStart.setText("Start");

        JLabel lbSteps = new JLabel();
        lbSteps.setText("Steps:");

        JTextField txtSteps = new JTextField(4);

        JLabel lbExec = new JLabel();
        lbExec.setText("\t\tExecutions: 0\t\t");

        Border border = BorderFactory.createLineBorder(Color.BLACK);
        lbExec.setBorder(border);

        JButton btnStop = new JButton();
        btnStop.setText("Stop");

        // Add buttons and components to the additional control panel
        additionalControlPanel.add(btnStart);
        additionalControlPanel.add(lbSteps);
        additionalControlPanel.add(txtSteps);
        additionalControlPanel.add(lbExec);
        additionalControlPanel.add(btnStop);

        // Create a control panel to stack footerPanel and additionalControlPanel vertically
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.add(footerPanel);
        controlPanel.add(additionalControlPanel);

        // Add the control panel to the main frame
        frame.add(controlPanel, BorderLayout.SOUTH);

        // Create the GameOfLife panel
        GL gamePanel = new GL(rows, columns);
        frame.add(gamePanel);
        frame.setSize(650, 650);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Display the main window
        frame.setVisible(true);
    }
}
