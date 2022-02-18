package com.trapped.view;

import com.trapped.Main;
import com.trapped.utilities.FileManager;
import com.trapped.utilities.Sounds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainFrame extends JFrame {

    Main gHandler;

    public JButton startButton, settingButton, exitButton,SUBMITbtn, HELPbtn;
    public JPanel MenuBG_panel, menuPanel, itemsPanel, MainBG_Panel;
    public JLabel themeLabel, roomLabel, keyLabel, matchLabel, walletLabel, candleLabel, paperLabel, crowbarLabel;

    public JLabel matches, key, wallet, laptop, candle, paper, crowbar, windowWithKey, windowWithoutKey;
    public JTextArea introText = new JTextArea();
    public JTextArea textArea = new JTextArea();
    public TextField inputText = new TextField(20);
    public static float VOLUME;

    static List<Boolean> initArr = Arrays.asList(true, false, false, false, false, false, false, false, false, false, false, false, false, false,false,true,false);
    static List<Boolean> invArr = Arrays.asList(false,false,false,false,false,false);
    Container con = getContentPane();


    public MainFrame(Main gHandler) {
        super("Trapped");
        setUpMainMenu();
        showMainMenu();
        this.gHandler = gHandler;
        Sounds.changeSoundVolumeLoop("creepy_noise_3.wav", 0,-20);
    }

    public void setUpMainMenu() {
        // GUI setting up
        setFrameConfigs();
        setAllButtons();
        setAllPanels();
//        createStatusField();
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
        con.add(SUBMITbtn);
        con.add(HELPbtn);
        con.add(itemsPanel);
        con.add(introText);
        setVisible(true);
    }

    public void settingScreen() {
//        menuPanel.setVisible(true);
        startButton.addActionListener(e -> gameScreen(initArr,invArr));
        exitButton.addActionListener(e -> System.exit(0));
        MainBG_Panel.updateUI();  // reset the panels
        MainBG_Panel.removeAll(); // remove all the layers
//        menuPanel.setVisible(true);
        changeVolume();
        introText.setVisible(false);
        startButton.addActionListener(e -> {introScreen("introstory");
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    loseScreen("exploded");
                }
            };
            timer.schedule(task, 480000);
        });
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
                VOLUME = Float.parseFloat(name);
                if (-80.0f <= VOLUME && VOLUME <= 6.0206f) {
                    Sounds.changeSoundVolume("creepy_noise_3.wav", 0, VOLUME);
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
        MainBG_Panel = createJPanel(12, 40, 460, 500, false);
        itemsPanel = createJPanel(320,550,155,155, false);
        itemsPanel.setBackground(Color.black);
        itemsPanel.setLayout(new GridLayout(3, 2));
    }

    private void setAllButtons() {
        startButton = createJButton("Start", 100, 40, false, Color.lightGray, Color.decode("#302a1e"));
        settingButton = createJButton("Setting", 100, 40, false, Color.lightGray, Color.decode("#302a1e"));
        exitButton = createJButton("Exit", 100, 40, false, Color.lightGray, Color.black);
        SUBMITbtn = createJButton("Submit",80,40,false,Color.red,Color.BLACK);
        SUBMITbtn.setBounds(220,660,80,40);
        HELPbtn=createJButton("Help",60,40,false,Color.red,Color.black);
        HELPbtn.setBounds(392,2,80,40);
    }


    // display the MainMenu
    public void showMainMenu() {
        MainBG_Panel.updateUI();
        MainBG_Panel.removeAll();
        MainBG_Panel.repaint();
        MainBG_Panel.revalidate();

        menuPanel.setVisible(true);
        MenuBG_panel.setVisible(true);

        SUBMITbtn.setVisible(false);
        HELPbtn.setVisible(false);
        startButton.addActionListener(e -> {introScreen("introstory");
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    loseScreen("exploded");
                }
            };
            timer.schedule(task, 480000);
    });
        exitButton.addActionListener(e -> System.exit(0));
        settingButton.addActionListener(e -> settingScreen());
    }

    public void winScreen(String fileName) {
        MainBG_Panel.updateUI();
        MainBG_Panel.removeAll();
        MainBG_Panel.repaint();
        MainBG_Panel.revalidate();
        menuPanel.setVisible(false);
        textArea.setVisible(false);
        MainBG_Panel.setVisible(false);
        MenuBG_panel.setVisible(false);
        itemsPanel.setVisible(false);
        inputText.setVisible(false);
        SUBMITbtn.setVisible(false);
        HELPbtn.setVisible(false);
        introText.setVisible(true);
        writeToIntro(readFileFromResources(fileName));
        introText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    System.exit(0);
                } else if (e.getKeyChar() == 27)
                    System.exit(0);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("you released " + e.getKeyCode());

            }
        });

    }

    public void loseScreen(String fileName) {
        MainBG_Panel.updateUI();
        MainBG_Panel.removeAll();
        MainBG_Panel.repaint();
        MainBG_Panel.revalidate();
        menuPanel.setVisible(false);
        textArea.setVisible(false);
        MainBG_Panel.setVisible(false);
        MenuBG_panel.setVisible(false);
        itemsPanel.setVisible(false);
        inputText.setVisible(false);
        SUBMITbtn.setVisible(false);
        HELPbtn.setVisible(false);

        introText.setVisible(true);
        writeToIntro(readFileFromResources(fileName));
        Sounds.changeSoundVolume("EvilLaugh.wav", 0, -50);
        introText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    System.exit(0);
                } else if (e.getKeyChar() == 27)
                    System.exit(0);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("you released " + e.getKeyCode());

            }
        });

    }

    public void introScreen(String fileName) {
        MainBG_Panel.updateUI();
        MainBG_Panel.removeAll();
        MainBG_Panel.repaint();
        MainBG_Panel.revalidate();
        menuPanel.setVisible(false);
        textArea.setVisible(false);
        MainBG_Panel.setVisible(false);
        MenuBG_panel.setVisible(false);
        inputText.setVisible(false);
        SUBMITbtn.setVisible(false);
        HELPbtn.setVisible(false);

        introText.setVisible(true);
        writeToIntro(readFileFromResources(fileName));
        introText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    introScreenAfterPhone("intro_after_phone");
                } else if (e.getKeyChar() == 27)
                    System.exit(0);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("you released " + e.getKeyCode());

            }
        });
        Sounds.changeSoundVolume("phone.wav", 0, -20);
    }

    public void introScreenAfterPhone(String fileName) {
        MainBG_Panel.updateUI();
        MainBG_Panel.removeAll();
        MainBG_Panel.repaint();
        MainBG_Panel.revalidate();
        menuPanel.setVisible(false);
        textArea.setVisible(false);
        MainBG_Panel.setVisible(false);
        MenuBG_panel.setVisible(false);
        inputText.setVisible(false);
        SUBMITbtn.setVisible(false);
        HELPbtn.setVisible(false);

        introText.setVisible(true);
        writeToIntro(readFileFromResources(fileName));
        introText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    gameScreen(initArr,invArr);
                } else if (e.getKeyChar() == 27)
                    System.exit(0);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("you released " + e.getKeyCode());

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
    public void gameScreen(List<Boolean> arr, List<Boolean> invArr) {
        introText.setVisible(false);
        MainBG_Panel.updateUI();
        MainBG_Panel.removeAll();
        MainBG_Panel.repaint();
        MainBG_Panel.revalidate();
        itemsPanel.updateUI();
        itemsPanel.removeAll();
        itemsPanel.repaint();
        itemsPanel.revalidate();

        MainBG_Panel.setVisible(true);
        MenuBG_panel.setVisible(false);
        menuPanel.setVisible(false);
        textArea.setVisible(true);
        itemsPanel.setVisible(true);
        inputText.setVisible(true);
        SUBMITbtn.setVisible(true);
        HELPbtn.setVisible(true);

        HELPbtn.addActionListener(e -> writeToTextArea(readFileFromResources("help")));

        JLabel lamp = createGameObj(130, 190, 190, 190, "Inspect", "inspect lamp", "resources/SwingArt/lamp1.png");
        JLabel door = createGameObj(130, 170, 200, 200, "Inspect", "Input Code", "inspect door", "final door", "resources/SwingArt/door1.png");
        JLabel bed = createGameObj(130, 250, 200, 200, "Inspect", "inspect bed", "resources/SwingArt/bed1.png");
        JLabel chair = createGameObj(180, 240, 200, 200, "Inspect", "inspect chair", "resources/SwingArt/chair1.png");
        JLabel safe = createGameObj(160, 230, 150, 150, "Inspect", "Open", "inspect safe", "riddles safe", "resources/SwingArt/vault1.png");
        JLabel desk = createGameObj(130, 250, 200, 150, "Inspect", "Open", "inspect drawer", "tool puzzle", "resources/SwingArt/desk1.png");
        crowbar = createGameObj(200, 400, 60, 52, "Inspect", "Get", "inspect crowbar", "get crowbar", "resources/SwingArt/crowbar_world_item.png");
        candle = createGameObj(100, 430, 24, 51, "Inspect", "Get", "inspect candle", "get candle", "resources/SwingArt/candle_world_item.png");
        matches = createGameObj(150, 450, 18, 17, "Inspect", "Get", "inspect matchbox", "get matchbox", "resources/SwingArt/matches.png");
        paper = createGameObj(330, 400, 40, 36, "Inspect", "Get", "inspect paper", "get paper", "resources/SwingArt/paper_world_item.png");
        wallet = createGameObj(300, 450, 36, 31, "Inspect", "Get", "inspect wallet", "get wallet", "resources/SwingArt/wallet_world_item.png");
        windowWithKey = createGameObj(180, 210, 100, 100, "inspect", "Break", "inspect window", "tool puzzle", "resources/SwingArt/window_world_item_with_key2.png");
        windowWithoutKey = createGameObj(180, 210, 100, 100, "inspect", "inspect window", "resources/SwingArt/window_world_item_no_key2.png");
        key = createGameObj(50, 400, 30, 30, "Inspect", "Get", "inspect key", "get key", "resources/SwingArt/key_world_item.png");
        JButton lftBtn = createNavButton(0, 400, 80, 80, "resources/SwingArt/left.png", "go left");
        JButton rgtBtn = createNavButton(380, 400, 80, 80, "resources/SwingArt/right.png", "go right");
        keyLabel = createGameObj(350, 550, 50, 50,"Inspect","Drop","inspect key","drop key","resources/SwingArt/key.png");
        matchLabel=createGameObj(350, 550, 50, 50,"Inspect","Drop","inspect matchbox","drop matchbox","resources/SwingArt/matchbox.png");
        walletLabel = createGameObj(350, 550, 50, 50,"Inspect","Drop","inspect wallet","drop wallet","resources/SwingArt/wallet.png");
        crowbarLabel = createGameObj(350, 550, 50, 50,"Inspect","Drop","inspect crowbar","drop crowbar","resources/SwingArt/crowbar.png");
        paperLabel = createGameObj(350, 550, 50, 50,"Inspect","Drop","inspect paper","drop paper","resources/SwingArt/folded-paper.png");
        candleLabel = createGameObj(350, 550, 50, 50,"Inspect","Drop","inspect candle","drop candle","resources/SwingArt/candle-holder.png");
        JLabel  cornerRight = createJLabel("resources/SwingArt/rightcorner.png");
        cornerRight.setBounds(380, 5, 90, 500);
        JLabel cornerLeft = createJLabel("resources/SwingArt/leftcorner.png");
        cornerLeft.setBounds(0, 5, 90, 500);


        keyLabel.setVisible(invArr.get(0)); //17
        walletLabel.setVisible(invArr.get(1)); //18
        paperLabel.setVisible(invArr.get(2)); //19
        matchLabel.setVisible(invArr.get(3)); //20
        candleLabel.setVisible(invArr.get(4)); //21
        crowbarLabel.setVisible(invArr.get(5)); //22

        itemsPanel.add(keyLabel);
        itemsPanel.add(matchLabel);
        itemsPanel.add(walletLabel);
        itemsPanel.add(crowbarLabel);
        itemsPanel.add(paperLabel);
        itemsPanel.add(candleLabel);


        MainBG_Panel.setLayout(null);
        MainBG_Panel.add(bed);
        MainBG_Panel.add(door);
        MainBG_Panel.add(safe);
        MainBG_Panel.add(desk);
        MainBG_Panel.add(lamp);
        MainBG_Panel.add(chair);
        MainBG_Panel.add(windowWithKey);
        MainBG_Panel.add(windowWithoutKey);
        MainBG_Panel.add(key);
        MainBG_Panel.add(wallet);
        MainBG_Panel.add(paper);
        MainBG_Panel.add(matches);
        MainBG_Panel.add(candle);
        MainBG_Panel.add(crowbar);
        MainBG_Panel.add(lftBtn);
        MainBG_Panel.add(rgtBtn);
        MainBG_Panel.add(cornerLeft);
        MainBG_Panel.add(cornerRight);
        MainBG_Panel.setLayout(null);
        MainBG_Panel.add(roomLabel);


        // showing the corner of the room when turning around in the room
        if(arr.get(14)){
            cornerLeft.setVisible(false);
            cornerRight.setVisible(false);
            System.out.println("center triggered");
        }else if (arr.get(15)) {
            cornerLeft.setVisible(true);
            cornerRight.setVisible(false);
            System.out.println("corner left triggered");
        }
        else if (arr.get(16)){

            cornerLeft.setVisible(false);
            cornerRight.setVisible(true);
            System.out.println("corner right triggered");
        }else{
            System.out.println("nothing was triggered");
        }

        bed.setVisible(arr.get(0));
        door.setVisible(arr.get(1));
        safe.setVisible(arr.get(2));
        desk.setVisible(arr.get(3));
        lamp.setVisible(arr.get(4));
        chair.setVisible(arr.get(5));
        windowWithKey.setVisible(arr.get(6));
        windowWithoutKey.setVisible(arr.get(7));
        key.setVisible(arr.get(8));
        wallet.setVisible(arr.get(9));
        paper.setVisible(arr.get(10));
        matches.setVisible(arr.get(11));
        candle.setVisible(arr.get(12));
        crowbar.setVisible(arr.get(13));
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
        introText.setBackground(Color.black);
        introText.setForeground(Color.white);
        introText.setText(string);
        introText.setPreferredSize(new Dimension(480, 150));
        introText.setEditable(false);
    }

    // method creating the text field
    public void writeToTextArea(String s) {
        textArea.setText(s);
        textArea.setFont(new Font("Arial", Font.BOLD, 12));
        textArea.setBounds(12, 550, 300, 100);
        textArea.setBackground(Color.black);
        textArea.setForeground(Color.white);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
    }

    public void inputTextField() {
        inputText.setFont(new Font("Arial", Font.BOLD, 13));
        inputText.setForeground(Color.red);
        inputText.setBackground(Color.black);
        inputText.setBounds(12, 660, 200, 40);
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

    public JLabel createGameObj(int x, int y, int width, int height, String action1, String action2,
                                String Command1, String Command2, String fileName) {

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

    public JLabel createGameObj(int x, int y, int width, int height, String action1,
                                String Command1, String fileName) {

        JPopupMenu popMenu = new JPopupMenu();
        JMenuItem[] menuItem = new JMenuItem[5];
        menuItem[1] = new JMenuItem(action1);
        popMenu.add(menuItem[1]);
        menuItem[1].addActionListener(gHandler.aHandler);
        menuItem[1].setActionCommand(Command1);

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

}