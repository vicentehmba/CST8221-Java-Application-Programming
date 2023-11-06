package gameoflife;

import javax.swing.*;

import org.apache.derby.shared.common.error.PublicAPI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Random;



public class GameView {

    private JFrame mainFrame;
    private JMenuBar menuBar;
    private JPanel gamePanel, controlPanel;
    private JButton randomBtn, manualBtn, multicolorBtn, colorBtn, startBtn, execBtn, stopBtn;
    private JTextField modelTextField, stepsTextField;
    private JLabel gameLabel;
    private GameModel gameModel;
    private final Color[] selectedColors = new Color[9];

    public GameView() {
        this.gameModel =new GameModel(50);
        mainFrame = new JFrame("Game of Life");
        menuBar = new JMenuBar();
        gamePanel = new JPanel(new GridLayout(gameModel.getDimension(), gameModel.getDimension())); // Setting GridLayout for equal sizing of buttons
        controlPanel = new JPanel();
        initializeUI();

    }


    public void showGUI() {
        mainFrame.setVisible(true);
    }
    private ImageIcon resizeIcon(String path, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(getClass().getResource(path));
        Image resizedImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    private void showColorByNeighbourhoodDialog() {
        JDialog colorDialog = new JDialog(mainFrame, "Color by Neighbourhood", true);
        colorDialog.setLayout(new GridLayout(3, 3));

        for (int i = 0; i <= 8; i++) {
            JButton colorButton = new JButton(String.valueOf(i));
            colorButton.setBackground(selectedColors[i]);
            colorButton.addActionListener(e -> {
                Color newColor = JColorChooser.showDialog(colorDialog, "Choose Color", ((JButton) e.getSource()).getBackground());
                if (newColor != null) {
                    ((JButton) e.getSource()).setBackground(newColor);
                    selectedColors[Integer.parseInt(((JButton) e.getSource()).getText())] = newColor;
                }
            });
            colorDialog.add(colorButton);
        }

        colorDialog.pack();
        colorDialog.setLocationRelativeTo(mainFrame);
        colorDialog.setVisible(true);
    }


    private void initializeUI() {
        menuBar.add(new JMenu("Game"));
        menuBar.add(new JMenu("Language"));
        menuBar.add(new JMenu("Help"));
        mainFrame.setJMenuBar(menuBar);
/**
 * Game menu
 */
        JMenu gameMenu = menuBar.getMenu(0);
        ImageIcon newGameIcon = resizeIcon("/newgame.gif", 20, 20);
        JMenuItem newGameMenuItem = new JMenuItem("New Game", newGameIcon);
        newGameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        ImageIcon solutionIcon = resizeIcon("/solution.gif", 20, 20);
        JMenuItem solutionMenuItem = new JMenuItem("Solution", solutionIcon);
        solutionMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        ImageIcon exitIcon = resizeIcon("/exit.gif", 20, 20);
        JMenuItem exitMenuItem = new JMenuItem("Exit", exitIcon);
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        gameMenu.add(newGameMenuItem);
        gameMenu.add(solutionMenuItem);
        gameMenu.add(exitMenuItem);
        /**
         * language menu
         */
        JMenu languageMenu = menuBar.getMenu(1);

        JMenuItem englishMenuItem = new JMenuItem("English");
        languageMenu.add(englishMenuItem);

        JMenuItem persianMenuItem = new JMenuItem("Persian");
        languageMenu.add(persianMenuItem);
        /**
         *  Help menu
         */
        JMenu helpMenu = menuBar.getMenu(2);

        ImageIcon colorsIcon = resizeIcon("/colors.gif", 20, 20);
        JMenuItem colorsMenuItem = new JMenuItem("Colors", colorsIcon);
        helpMenu.add(colorsMenuItem);

        //JMenu helpMenu = new JMenu("Help");
        //  JMenuItem colorsMenuItem = new JMenuItem("Colors");
        colorsMenuItem.addActionListener(e -> showColorByNeighbourhoodDialog());
        helpMenu.add(colorsMenuItem);
        menuBar.add(helpMenu);

        ImageIcon aboutIcon = resizeIcon("/about.gif", 20, 20);
        JMenuItem aboutMenuItem = new JMenuItem("About", aboutIcon);
        helpMenu.add(aboutMenuItem);

        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        // gameLabel = new JLabel("GAME OF LIFE");
        //  gameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        // gameLabel.setHorizontalAlignment(JLabel.CENTER);



        randomBtn = new JButton("Random");
        manualBtn = new JButton("Manual");
        modelTextField = new JTextField(15);
        multicolorBtn = new JButton("Multicolor");
        colorBtn = new JButton("Color");

        topPanel.add(randomBtn);
        topPanel.add(manualBtn);
        topPanel.add(new JLabel("Model:"));
        topPanel.add(modelTextField);
        topPanel.add(multicolorBtn);
        topPanel.add(colorBtn);

        startBtn = new JButton("Start");
        stepsTextField = new JTextField(5);
        execBtn = new JButton("Exec-c");
        stopBtn = new JButton("Stop");

        bottomPanel.add(startBtn);
        bottomPanel.add(new JLabel("Steps:"));
        bottomPanel.add(stepsTextField);
        bottomPanel.add(execBtn);
        bottomPanel.add(stopBtn);

        controlPanel.add(topPanel);
        controlPanel.add(bottomPanel);

        mainFrame.setLayout(new BorderLayout());
        try {
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/logo.png")));
            JLabel gameLabel = new JLabel();
            Image image = icon.getImage();
            Image newImage = image.getScaledInstance(550, 40, java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(newImage);
            gameLabel.setIcon(icon);
            mainFrame.add(gameLabel, BorderLayout.NORTH);
        } catch (Exception e) {
            System.out.print(e);
        }
        mainFrame.add(gamePanel, BorderLayout.CENTER);
        mainFrame.add(controlPanel, BorderLayout.SOUTH);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(550, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
    public JButton getRandomBtn() {
        return randomBtn;
    }

    public JButton getStartBtn() {
        return startBtn;
    }

    public JTextField getStepsField() {
        return stepsTextField;
    }

    public JTextField getModelTextField() {
        return modelTextField;
    }

    public JPanel getGamePanel() {
        return gamePanel;
    }



}

