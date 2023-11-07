/**
 * File Name: GameView.java
 * Identification: Vicente Mba Engung 041029226 // Ken Dekpor 041054266
 * Course: CST 8221 – JAP, Lab Section: [300, 302]
 * Assignment: A22
 * Professor: Daniel Cormier
 * Date: November 6, 2023.
 * Compiler: Eclipse IDE for Java Developers – Version: 2023-09 (4.29.0)
 *
 * Purpose: This class defines the view for the Game of Life application. It sets up the user interface
 * components, including buttons, labels, and menus, and provides methods for updating the UI.
 */

package cs;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The GameView class defines the user interface for the Game of Life application.
 */
class GameView {
    protected GameModel model;
    protected JFrame frame;
    protected GamePanel gamePanel;
    protected JButton randomButton;
    protected JButton manualButton;
    protected JLabel modelLabel;
    protected JTextField modelTextField;
    protected JCheckBox validatCheckBox;
    protected JLabel multicolorLabel;
    protected JButton colorButton;
    protected JButton playButton;
    protected JLabel generationLabel;
    protected GameController controller;
    protected int cellSize = 8;
    protected JMenuBar menuBar;
    protected JMenu gameMenu;
    protected JMenuItem subMenuNew;
    protected JMenuItem subMenuSolution;
    protected JMenuItem subMenuExit;
    protected JMenu languagesMenu;
    protected JMenuItem subMenuEnglish;
    protected JMenuItem subMenuFrench;
    protected JMenu helpMenu;
    protected JMenuItem subMenuColor;
    protected JMenuItem subMenuAbout;
    protected JLabel stepsLabel;
    protected JTextField stepsTextField;

    /**
     * Creates a new GameView with the specified GameModel.
     *
     * @param model The GameModel to associate with the view.
     */
    public GameView(GameModel model) {
        this.model = model;
    }

    /**
     * Sets the controller for this view.
     *
     * @param controller The GameController to associate with the view.
     */
    public void setController(GameController controller) {
        this.controller = controller;
    }

    /**
     * Creates and displays the user interface for the Game of Life application.
     */
    public void createAndShowGUI() {
        frame = new JFrame("Game of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(580, 708);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        // Create a menu bar
        menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar); // Set the menu bar for the frame

        // Create menu items
        gameMenu = new JMenu("Game");
        subMenuNew = new JMenuItem("New");
        subMenuNew.setIcon(new ImageIcon("src/resources/menuiconnew.gif")); // Load and set the icon
        gameMenu.add(subMenuNew);

        subMenuSolution = new JMenuItem("Solution");
        subMenuSolution.setIcon(new ImageIcon("src/resources/menuiconsol.gif")); // Load and set the icon
        gameMenu.add(subMenuSolution);

        subMenuExit = new JMenuItem("Exit");
        subMenuExit.setIcon(new ImageIcon("src/resources/menuiconext.gif")); // Load and set the icon
        gameMenu.add(subMenuExit);

        languagesMenu = new JMenu("Languages");
        subMenuEnglish = new JMenuItem("English");

        subMenuFrench = new JMenuItem("French");
        languagesMenu.add(subMenuEnglish);
        languagesMenu.add(subMenuFrench);

        helpMenu = new JMenu("Help");
        subMenuColor = new JMenuItem("Color");
        subMenuColor.setIcon(new ImageIcon("src/resources/menuiconcol.gif")); // Load and set the icon
        helpMenu.add(subMenuColor);

        subMenuAbout = new JMenuItem("About");
        subMenuAbout.setIcon(new ImageIcon("src/resources/menuiconabt.gif")); // Load and set the icon
        helpMenu.add(subMenuAbout);

        // Add the menus to the menu bar
        menuBar.add(gameMenu);
        menuBar.add(languagesMenu);
        menuBar.add(helpMenu);

        // Create a header panel at the top of the window
        JPanel headerPanel = new JPanel();
        frame.add(headerPanel, BorderLayout.NORTH);

        // Create a label for the header panel
        JLabel lbLogo = new JLabel();
        lbLogo.setText("");
        lbLogo.setIcon(new ImageIcon("./src/resources/gl.png"));
        headerPanel.add(lbLogo);

        gamePanel = new GamePanel(model);
        randomButton = new JButton("Random");
        manualButton = new JButton("Manual");
        modelLabel = new JLabel("Model:");
        modelTextField = new JTextField(14);
        validatCheckBox = new JCheckBox();
        multicolorLabel = new JLabel("Multicolor");
        colorButton = new JButton("Color");
        playButton = new JButton("Play");
        stepsLabel = new JLabel("Steps:");
        stepsTextField = new JTextField(5);

        generationLabel = new JLabel(" Generations: " + model.getGeneration() + " ");
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        generationLabel.setBorder(border);

        JPanel footerPanel1 = new JPanel();
        footerPanel1.add(randomButton);
        footerPanel1.add(manualButton);
        footerPanel1.add(playButton);
        footerPanel1.add(generationLabel);

        JPanel footerPanel2 = new JPanel();
        footerPanel2.add(modelLabel);
        footerPanel2.add(modelTextField);
        footerPanel2.add(validatCheckBox);
        footerPanel2.add(multicolorLabel);
        footerPanel2.add(colorButton);
        footerPanel2.add(stepsLabel);
        footerPanel2.add(stepsTextField);

        randomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.setupRandomConfiguration();
            }
        });

        manualButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.setupManualConfiguration();
            }
        });

        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.togglePlayMode();
            }
        });

        validatCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.validatCheckBoxAction();
            }
        });

        colorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.colorButtonAction();
            }
        });

        subMenuAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.subMenuAboutAction();
            }
        });

        subMenuEnglish.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.subMenuEnglishAction();
            }
        });

        subMenuFrench.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.subMenuFrenchAction();
            }
        });

        subMenuAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int mouseX = e.getX() / cellSize;
                int mouseY = e.getY() / cellSize;
                controller.handleMouseClick(mouseX, mouseY);
            }
        });

        // Panel to contain the first set of components
        footerPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        frame.add(footerPanel1, BorderLayout.SOUTH);

        // Panel to contain the second set of components
        footerPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        frame.add(footerPanel2, BorderLayout.SOUTH);

        // Panel to stack footerPanel1 and footerPanel2 vertically
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.add(footerPanel1);
        controlPanel.add(footerPanel2);

        // Add the control panel to the main frame
        frame.add(controlPanel, BorderLayout.SOUTH);
        // Add the game panel to the main frame
        frame.add(gamePanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Updates the user interface to reflect changes in the model, such as the current generation.
     */
    public void updateUI() {
        generationLabel.setText(" Generations: " + model.getGeneration() + " ");
        gamePanel.repaint();
    }
}
