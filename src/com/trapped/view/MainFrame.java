package com.trapped.view;

import com.trapped.GameHandler;
import com.trapped.utilities.Sounds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.file.Files;
import java.nio.file.Path;


public class MainFrame extends JFrame {

    GameHandler gHandler;
    public JButton  startButton, settingButton, exitButton;
    public JPanel MenuBG_panel, menuPanel,itemsPanel,MainBG_Panel,TextBox_panel;
    public JLabel themeLabel,roomLabel,keyLabel,matchLabel,laptopLabel,walletLabel,candleLabel,paperLabel,crowbarLabel;
    public JLabel matches,key,wallet,laptop,candle,paper,crowbar;
    public JTextArea introText = new JTextArea();
    public JTextArea textArea = new JTextArea();
    public TextField inputText = new TextField("What would you say ?", 20);

    public MainFrame(GameHandler gHandler) {
        super("Trapped");
        setUpMainMenu();
        showMainMenu();
        this.gHandler = gHandler;
    }

    public void setUpMainMenu() {
        Container con = getContentPane();
        // GUI setting up
        setFrameConfigs();
        setAllButtons();
        setAllPanels();
        createStatusField();
        inputTextField();

        // ---- LABELS ADDED TO PANELS ----
        themeLabel = createJLabel("resources/SwingArt/MainTheme1.png");
        MenuBG_panel.add(themeLabel);

        roomLabel = createJLabel("resources/SwingArt/room1.png");
        MainBG_Panel.add(roomLabel);

        menuPanel.add(startButton);
        menuPanel.add(settingButton);
        menuPanel.add(exitButton);

        con.add(MenuBG_panel);
        con.add(MainBG_Panel);
        con.add(menuPanel);
        con.add(textArea);
        con.add(inputText);
        con.add(itemsPanel);
        con.add(introText);
        setVisible(true);
    }

    public void settingScreen() {
        menuPanel.setVisible(true);
        startButton.addActionListener(e -> bedScreen());
        exitButton.addActionListener(e -> System.exit(0));
        MainBG_Panel.updateUI();  // reset the panels
        MainBG_Panel.removeAll(); // remove all the layers
        menuPanel.setVisible(true);
        changeVolume();
        introText.setVisible(false);
        startButton.addActionListener(e -> introScreen("introstory"));
        exitButton.addActionListener(e -> System.exit(0));
    }

    public void changeVolume() {
        JFrame frame = new JFrame("Volume Changer");
        frame.setSize(200, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        JOptionPane.showMessageDialog(frame, "Hello, this is the volume selector");
        int result = JOptionPane.showConfirmDialog(null, "Do wish to change the volume? "
        );
        switch (result) {
            case JOptionPane.YES_OPTION:
                String name = JOptionPane.showInputDialog(null,
                        "Please enter a number from -80 to 6 to change volume");
                float num = Float.parseFloat(name);
                if (-80.0f <= num && num <= 6.0206f) {
                    Sounds.changeSoundVolume("startsound.wav", 0, num);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a number from -80 to 6 to change volume");
                    frame.dispose();
                    changeVolume();
                    break;
                }
                break;
            case JOptionPane.NO_OPTION:
            case JOptionPane.CANCEL_OPTION:
                frame.dispose();
                break;
        }
    }

    private void setFrameConfigs() {
        setSize(500, 750);
        setResizable(false);        // enable the resize of the frame
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.black);
        setLayout(null);
        setVisible(true);
    }

    private void setAllPanels() {
        MenuBG_panel = createJPanel(-10, 0, 500, 750, false);
        menuPanel = createJPanel(150, 350, 100, 180, false);
        menuPanel.setBackground(Color.decode("#302a1e"));

        MainBG_Panel = createJPanel(10, 40, 460, 500, false);
        itemsPanel = createJPanel(350, 550, 120, 150, false);
        itemsPanel.setBackground(Color.lightGray);
        itemsPanel.setLayout(new GridLayout(2, 3));
    }

    private void setAllButtons() {
        startButton = createJButton("Start", 100, 40, false, Color.lightGray, Color.decode("#302a1e"));
        settingButton = createJButton("Setting", 100, 40, false, Color.lightGray, Color.decode("#302a1e"));
        exitButton = createJButton("Exit", 100, 40, false, Color.lightGray, Color.black);
    }



    // display the MainMenu
    public void showMainMenu() {
        MenuBG_panel.setVisible(true);
        menuPanel.setVisible(true);
        startButton.addActionListener(e -> introScreen("introstory"));
        exitButton.addActionListener(e -> System.exit(0));
        settingButton.addActionListener(e -> settingScreen());
        Sounds.changeSoundVolume("startsound.wav", 0, -50);
    }

    public void endScreen(String fileName) {
        menuPanel.setVisible(false);
        textArea.setVisible(false);
        MainBG_Panel.setVisible(false);
        MenuBG_panel.setVisible(false);
        itemsPanel.setVisible(false);
        inputText.setVisible(false);

        introText.setVisible(true);
        writeToIntro(readFileFromResources(fileName));
        introText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==10){
                    System.exit(0);
                }
                else if (e.getKeyChar()==27)
                    System.exit(0);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("you released "+e.getKeyCode());

            }
        });

    }

    public void introScreen(String fileName) {
        menuPanel.setVisible(false);
        textArea.setVisible(false);
        MainBG_Panel.setVisible(false);
        MenuBG_panel.setVisible(false);
        itemsPanel.setVisible(false);
        inputText.setVisible(false);

        introText.setVisible(true);
        writeToIntro(readFileFromResources(fileName));
        introText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==10){
                    bedScreen();
                }
                else if (e.getKeyChar()==27)
                    System.exit(0);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("you released "+e.getKeyCode());

            }
        });

    }

    private String readFileFromResources(String fileName) {
        String aboutUs = null;
        try {
            aboutUs = Files.readString(Path.of("resources", fileName + ".txt"));
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return aboutUs;
    }


    // Create the game sense
    public void bedScreen() {
        MainBG_Panel.updateUI();  // reset the panels
        MainBG_Panel.removeAll(); // remove all the layers

        introText.setVisible(false);
        MainBG_Panel.setVisible(true);
        MenuBG_panel.setVisible(false);
        menuPanel.setVisible(false);
        textArea.setVisible(true);
        itemsPanel.setVisible(true);
        inputText.setVisible(true);

        JLabel bed = createGameObj(130, 250, 200, 200, "Inspect", "", "", "inspect bed", "", "", "resources/SwingArt/bed1.png");
        matches = createGameObj(100, 380, 18, 17, "Inspect", "Get", "", "inspect matches", "get matches", "", "resources/SwingArt/matches.png");
        JButton lftBtn = createNavButton(0, 400, 80, 80, "resources/SwingArt/left.png", "go left");
        JButton rgtBtn = createNavButton(380, 400, 80, 80, "resources/SwingArt/right.png", "go right");

        MainBG_Panel.setLayout(null);
        MainBG_Panel.add(bed);
        MainBG_Panel.add(matches);
        matches.setVisible(false);
        MainBG_Panel.add(lftBtn);
        MainBG_Panel.add(rgtBtn);
        MainBG_Panel.setLayout(null);
        MainBG_Panel.add(roomLabel);


    }

    public void safeScreen() {
        MainBG_Panel.updateUI();  // reset the panels
        MainBG_Panel.removeAll(); // remove all the layers
        MainBG_Panel.setVisible(true);
        MenuBG_panel.setVisible(false);
        menuPanel.setVisible(false);
        textArea.setVisible(true);
        itemsPanel.setVisible(true);
        inputText.setVisible(true);

        JLabel vault = createGameObj(250, 250, 150, 150, "Inspect", "get", "drop", "inspect safe", "", "", "resources/SwingArt/vault1.png");
        candle = createGameObj(100, 380, 24, 51, "Inspect", "Get", "", "inspect candle", "get candle", "", "resources/SwingArt/candle_world_item.png");
        JButton lftBtn = createNavButton(0, 400, 80, 80, "resources/SwingArt/left.png", "go left");
        JButton rgtBtn = createNavButton(380, 400, 80, 80, "resources/SwingArt/right.png", "go right");

        MainBG_Panel.setLayout(null);
        MainBG_Panel.add(vault);
        MainBG_Panel.add(candle);
        MainBG_Panel.add(lftBtn);
        MainBG_Panel.add(rgtBtn);
        MainBG_Panel.setLayout(null);
        MainBG_Panel.add(roomLabel);
    }

    public void doorScreen() {
        MainBG_Panel.updateUI();  // reset the panels
        MainBG_Panel.removeAll(); // remove all the layers
        MainBG_Panel.setVisible(true);
        MenuBG_panel.setVisible(false);
        menuPanel.setVisible(false);
        textArea.setVisible(true);
        itemsPanel.setVisible(true);
        inputText.setVisible(true);
      
        JLabel door=createGameObj(200,180,200,200,"Inspect","puzzle","","inspect door","final door","","resources/SwingArt/door1.png");
        JButton lftBtn=createNavButton(0,400,80,80,"resources/SwingArt/left.png","go left");
        JButton rgtBtn=createNavButton(380,400,80,80,"resources/SwingArt/right.png","go right");

        MainBG_Panel.setLayout(null);
        MainBG_Panel.add(door);
        MainBG_Panel.add(lftBtn);
        MainBG_Panel.add(rgtBtn);
        MainBG_Panel.setLayout(null);
        MainBG_Panel.add(roomLabel);


    }

    public void deskScreen() {

        MainBG_Panel.updateUI();
        MainBG_Panel.removeAll();

        MainBG_Panel.setVisible(true);
        MenuBG_panel.setVisible(false);
        menuPanel.setVisible(false);
        textArea.setVisible(true);
        inputText.setVisible(true);

        JLabel desk = createGameObj(220, 230, 200, 200, "Inspect", "get", "drop", "inspect drawer", "GET", "DROP", "resources/SwingArt/desk1.png");
        wallet = createGameObj(210, 245, 36, 31, "Inspect", "Get", "", "inspect wallet", "get wallet", "", "resources/SwingArt/wallet_world_item.png");
        JButton lftBtn = createNavButton(0, 400, 80, 80, "resources/SwingArt/left.png", "go left");
        JButton rgtBtn = createNavButton(380, 400, 80, 80, "resources/SwingArt/right.png", "go right");

        MainBG_Panel.setLayout(null);
        MainBG_Panel.add(desk);
        MainBG_Panel.add(wallet);
        MainBG_Panel.add(lftBtn);
        MainBG_Panel.add(rgtBtn);
        MainBG_Panel.setLayout(null);
        MainBG_Panel.add(roomLabel);
    }

    public void lampScreen() {

        MainBG_Panel.updateUI();
        MainBG_Panel.removeAll();

        MainBG_Panel.setVisible(true);
        MenuBG_panel.setVisible(false);
        menuPanel.setVisible(false);
        textArea.setVisible(true);
        inputText.setVisible(true);

        JLabel lamp = createGameObj(90, 190, 200, 200, "Inspect", "get", "drop", "inspect lamp", "GET", "DROP", "resources/SwingArt/lamp1.png");
        JButton lftBtn = createNavButton(0, 400, 80, 80, "resources/SwingArt/left.png", "go left");
        JButton rgtBtn = createNavButton(380, 400, 80, 80, "resources/SwingArt/right.png", "go right");

        MainBG_Panel.setLayout(null);
        MainBG_Panel.add(lamp);
        MainBG_Panel.add(lftBtn);
        MainBG_Panel.add(rgtBtn);
        MainBG_Panel.setLayout(null);
        MainBG_Panel.add(roomLabel);
    }

    public void chairScreen() {

        MainBG_Panel.updateUI();
        MainBG_Panel.removeAll();
        MainBG_Panel.setVisible(true);
        MenuBG_panel.setVisible(false);
        menuPanel.setVisible(false);
        textArea.setVisible(true);
        inputText.setVisible(true);

        JLabel chair = createGameObj(60, 250, 200, 200, "Inspect", "get", "drop", "inspect chair", "GET", "DROP", "resources/SwingArt/chair1.png");
        JButton lftBtn = createNavButton(0, 400, 80, 80, "resources/SwingArt/left.png", "go left");
        JButton rgtBtn = createNavButton(380, 400, 80, 80, "resources/SwingArt/right.png", "go right");

        MainBG_Panel.setLayout(null);
        MainBG_Panel.add(chair);
        MainBG_Panel.add(lftBtn);
        MainBG_Panel.add(rgtBtn);
        MainBG_Panel.setLayout(null);
        MainBG_Panel.add(roomLabel);
    }

    public void windowScreen() {

        MainBG_Panel.updateUI();
        MainBG_Panel.removeAll();

        MainBG_Panel.setVisible(true);
        MenuBG_panel.setVisible(false);
        menuPanel.setVisible(false);
        textArea.setVisible(true);
        inputText.setVisible(true);

        JLabel windowWithKey = createGameObj(100, 210, 100, 100, "inspect", "", "", "inspect window", "", "", "resources/SwingArt/window_world_item_with_key2.png");
        JLabel windowWithoutKey = createGameObj(100, 210, 100, 100, "inspect", "", "", "inspect window", "", "", "resources/SwingArt/window_world_item_no_key2.png");

        key = createGameObj(60,250,30,30,"Inspect","Get","","inspect key","get key","","resources/SwingArt/key_world_item.png");
        JButton lftBtn = createNavButton(0, 400, 80, 80, "resources/SwingArt/left.png", "go left");
        JButton rgtBtn = createNavButton(380, 400, 80, 80, "resources/SwingArt/right.png", "go right");

        MainBG_Panel.setLayout(null);
        MainBG_Panel.add(windowWithKey);
        MainBG_Panel.add(windowWithoutKey);
        MainBG_Panel.add(key);
        key.setVisible(false);
        windowWithoutKey.setVisible(false);
        MainBG_Panel.add(lftBtn);
        MainBG_Panel.add(rgtBtn);
        MainBG_Panel.setLayout(null);
        MainBG_Panel.add(roomLabel);
    }


    // method to set the JButton
    private JButton createJButton(String title, int width, int height, boolean focusable, Color foreground, Color background) {
        JButton button = new JButton(title);
        button.setPreferredSize(new Dimension(width, height));
        button.setFocusable(focusable);
        button.setForeground(foreground);
        button.setBackground(background);
        return button;
    }

    // method to set the JPanel
    private JPanel createJPanel(int x, int y, int width, int height, boolean visible) {
        JPanel panel = new JPanel();
        panel.setBounds(x, y, width, height);
        panel.setBackground(null);
        panel.setVisible(visible);
        return panel;
    }

    // method to set the JLabel for the img
    private JLabel createJLabel(String imageFile) {
        JLabel label = new JLabel();
        ImageIcon icon = new ImageIcon(imageFile);
        label.setIcon(icon);
        return label;
    }

    public void writeToIntro(String string) {
        introText.setFont(new Font("Arial", Font.BOLD, 15));
        introText.setBounds(10, 150, 470, 500);
        introText.setBackground(Color.yellow);
        introText.setText(string);
        introText.setPreferredSize(new Dimension(480, 150));
        introText.setEditable(false);
    }

    // method creating the text field
    public void writeToTextArea(String s) {
        textArea.setText(s);
        textArea.setFont(new Font("Arial", Font.BOLD, 15));
        textArea.setBounds(10, 550, 300, 100);
        textArea.setBackground(Color.RED);
        textArea.setForeground(Color.white);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
    }

    public void inputTextField(){
        inputText.setFont(new Font("Arial", Font.BOLD, 13));
        inputText.setForeground(Color.red);
        inputText.setBackground(Color.white);
        inputText.setBounds(10, 660, 300, 40);
        inputText.setVisible(false);

        // to access the TextField using " getText() [ for example: inputText.getText() ]

    }


    // creating the gameObj on the main area
    public JLabel createGameObj(int x, int y, int width, int height, String action1, String action2, String action3,
                                String Command1, String Command2, String Command3, String fileName) {

        JPopupMenu popMenu = new JPopupMenu();
        JMenuItem[] menuItem = new JMenuItem[5];
        menuItem[1] = new JMenuItem(action1);
        popMenu.add(menuItem[1]);
        menuItem[1].addActionListener(gHandler.aHandler);
        menuItem[1].setActionCommand(Command1);

        menuItem[2] = new JMenuItem(action2);
        popMenu.add(menuItem[2]);
        menuItem[2].addActionListener(gHandler.aHandler);
        menuItem[2].setActionCommand(Command2);

        menuItem[3] = new JMenuItem(action3);
        popMenu.add(menuItem[3]);
        menuItem[3].addActionListener(gHandler.aHandler);
        menuItem[3].setActionCommand(Command3);


        JLabel ObjLabel;
        ObjLabel = createJLabel(fileName);
        ObjLabel.setBounds(x, y, width, height);
        ObjLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    popMenu.show(ObjLabel, e.getX(), e.getY());
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        return ObjLabel;
    }


    public JButton createNavButton(int x, int y, int width, int height, String arrowFileName,
                                   String command) {

        ImageIcon NavIcon = new ImageIcon(arrowFileName);
        JButton NavButton = new JButton();
        NavButton.setBounds(x, y, width, height);
        NavButton.setBackground(null);
        NavButton.setContentAreaFilled(false);
        NavButton.setFocusPainted(false);
        NavButton.setIcon(NavIcon);
        NavButton.addActionListener(gHandler.aHandler);
        NavButton.setActionCommand(command);
        NavButton.setBorderPainted(false);
        return NavButton;
    }

    public void createStatusField() {
        keyLabel = createJLabel("resources/SwingArt/key.png");
        keyLabel.setBounds(350, 550, 50, 50);
        keyLabel.setVisible(false);

        matchLabel = createJLabel("resources/SwingArt/matchbox.png");
        matchLabel.setBounds(350, 550, 50, 50);
        matchLabel.setVisible(false);

        walletLabel = createJLabel("resources/SwingArt/wallet.png");
        walletLabel.setBounds(350, 550, 50, 50);
        walletLabel.setVisible(false);

        laptopLabel = createJLabel("resources/SwingArt/laptop.png");
        laptopLabel.setBounds(350, 550, 50, 50);
        laptopLabel.setVisible(false);

        crowbarLabel = createJLabel("resources/SwingArt/crowbar.png");
        crowbarLabel.setBounds(350, 550, 50, 50);
        crowbarLabel.setVisible(false);

        paperLabel = createJLabel("resources/SwingArt/folded-paper.png");
        paperLabel.setBounds(350, 550, 50, 50);
        paperLabel.setVisible(false);

        candleLabel = createJLabel("resources/SwingArt/candle-holder.png");
        candleLabel.setBounds(350, 550, 50, 50);
        candleLabel.setVisible(false);


        itemsPanel.add(keyLabel);
        itemsPanel.add(matchLabel);
        itemsPanel.add(walletLabel);
        itemsPanel.add(laptopLabel);
        itemsPanel.add(crowbarLabel);
        itemsPanel.add(paperLabel);
        itemsPanel.add(candleLabel);

    }

}