package cs;

// GameView.java
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class GameView {
    private GameModel model;
    private JFrame frame;
    private GamePanel gamePanel;
    private JButton randomButton;
    private JButton manualButton;
    private JLabel modelLabel;
    private JTextField modelTextField;
    private JCheckBox validatCheckBox;
    private JLabel multicolorLabel;
    private JButton colorButton;
    protected JButton playButton;
    private JLabel generationLabel;
    private GameController controller;
    private int cellSize = 20;

    public GameView(GameModel model) {
        this.model = model;
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public void createAndShowGUI() {
        frame = new JFrame("Game of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(560, 700);
        //frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        // Create a menu bar
        JMenuBar menu = new JMenuBar();
        menu.setVisible(true);
        frame.add(menu);

        // Create menu items
        JMenuItem game = new JMenuItem("Game");
        menu.add(game);
        
        JMenuItem languages = new JMenuItem("Languages");
        menu.add(languages);
        
        JMenuItem help = new JMenuItem("Help");
        menu.add(help);

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
        modelLabel = new JLabel("Model: ");
        modelTextField = new JTextField(15);
        validatCheckBox = new JCheckBox();
        multicolorLabel = new JLabel("Multicolor");
        colorButton = new JButton("Color");
        playButton = new JButton("Play");
        generationLabel = new JLabel(" Generations: " + model.getGeneration() + " ");
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        generationLabel.setBorder(border);

        //
        JPanel controlPanel = new JPanel();
        controlPanel.add(randomButton);
        controlPanel.add(manualButton);
        controlPanel.add(playButton);
        controlPanel.add(generationLabel);
        controlPanel.add(modelLabel);
        controlPanel.add(modelTextField);
        controlPanel.add(validatCheckBox);
        controlPanel.add(multicolorLabel);
        controlPanel.add(colorButton);

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

        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int mouseX = e.getX() / cellSize;
                int mouseY = e.getY() / cellSize;
                controller.handleMouseClick(mouseX, mouseY);
            }
        });

        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.add(gamePanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void updateUI() {
        generationLabel.setText(" Generations: " + model.getGeneration() + " ");
        gamePanel.repaint();
    }
}
