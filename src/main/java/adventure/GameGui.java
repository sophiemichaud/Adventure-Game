package adventure;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Container;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import java.io.File;

public class GameGui extends JFrame implements ActionListener{

    private Game theGame;
    private String fileName;
    private String saveName;
    public static final int WIDTH = 650;
    public static final int HEIGHT = 550;
    public static final int IN = 10;
    public static final int R_EIGHT = 8;
    public static final int R_SEVEN = 7;
    public static final int R_SIX = 6;
    public static final int R_FIVE = 5;
    public static final int R_FOUR = 4;
    public static final int R_THREE = 3;
    public static final int PIX_W = 22;
    public static final int PIX_H = 15;
    private GridBagConstraints layoutConst;
    private Container contentPane;
    private JTextArea gameOutput;
    private JTextArea listArea;
    private JTextArea inventoryArea;
    private JScrollPane scrollPane;
    private JLabel playerName;
    private JLabel commandLabel;
    private JLabel inventoryTitle;
    private JLabel listLabel;
    private JTextField inputField;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem menuName;
    private JMenuItem menuLoadJSON;
    private JMenuItem menuLoadSave;
    private JMenuItem menuSave;
    private JButton startButton;
    private JFileChooser fileChooser;

    public GameGui(Game newGame){

        super();
        theGame = newGame;
        createMainFrame();
        createFields();
        setStartButton();
        setMenuBar();  
    }

    private void createMainFrame(){

        setUpSize();
        setMainContainer();
    }

    private void createFields(){

        setGameOutputField();
        setPlayerNameField();
        setCommandLabelField();
        setCommandInputField();
        setInventoryTitle();
        setListLabel();
        setListArea();
        setInventoryArea();
    }

    private void setUpSize(){

        setSize(WIDTH,HEIGHT);
        setTitle("A3 GUI Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.lightGray);
    }

    private void setMainContainer(){

        contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
    }

    private void setGameOutputField(){

        gameOutput = new JTextArea(IN,PIX_W);
        scrollPane = new JScrollPane(gameOutput);
        gameOutput.setEditable(false);
        addGameOutputField();
    }

    private void addGameOutputField(){

        layoutConst = new GridBagConstraints();
        layoutConst.insets = new Insets(IN,IN,IN,IN);
        layoutConst.gridx = 0;
        layoutConst.gridy = 1;
        layoutConst.gridwidth = R_THREE;
        layoutConst.gridheight = R_FIVE;
        layoutConst.fill = GridBagConstraints.BOTH;
        add(scrollPane, layoutConst);
    }

    private void setPlayerNameField(){

        playerName = new JLabel("Set your Player Name in the Menu!");
        addPlayerNameField();
    }

    private void addPlayerNameField(){

        layoutConst = new GridBagConstraints();
        layoutConst.insets = new Insets(IN,IN,IN,IN);
        layoutConst.anchor = GridBagConstraints.FIRST_LINE_START;
        layoutConst.weightx = R_FOUR;
        layoutConst.gridx = 0;
        layoutConst.gridy = 0;
        add(playerName, layoutConst);
    }

    private void setCommandLabelField(){

        commandLabel = new JLabel("Enter a Command:");
        addCommandLabelField();
    }

    private void addCommandLabelField(){

        layoutConst = new GridBagConstraints();
        layoutConst.insets = new Insets(IN,IN,IN,IN);
        layoutConst.anchor = GridBagConstraints.FIRST_LINE_START;
        layoutConst.weighty = 1;
        layoutConst.gridx = 0;
        layoutConst.gridy = R_SIX;
        add(commandLabel, layoutConst);
    }

    private void setCommandInputField(){

        inputField = new JTextField(IN);
        inputField.setEditable(true);
        inputField.addActionListener(this);
        addCommandInputField();
    }

    private void addCommandInputField(){

        layoutConst = new GridBagConstraints();
        layoutConst.insets = new Insets(IN,IN,IN,IN);
        layoutConst.anchor = GridBagConstraints.FIRST_LINE_START;
        layoutConst.weightx = PIX_H;
        layoutConst.gridx = 1;
        layoutConst.gridy = R_SIX;
        add(inputField, layoutConst);
    }

    private void setStartButton(){

        startButton = new JButton("Start Adventure");
        startButton.addActionListener(this);
        addStartButton();
    }

    private void addStartButton(){

        layoutConst = new GridBagConstraints();
        layoutConst.insets = new Insets(IN,IN,IN,IN);
        layoutConst.anchor = GridBagConstraints.FIRST_LINE_START;
        layoutConst.gridx = 2;
        layoutConst.gridy = R_SIX;
        add(startButton, layoutConst);
    }

    private void setInventoryTitle(){

        inventoryTitle = new JLabel("Inventory");
        addInventoryTitle();
    }

    private void addInventoryTitle(){

        layoutConst = new GridBagConstraints();
        layoutConst.insets = new Insets(IN,IN,IN,IN);
        layoutConst.anchor = GridBagConstraints.FIRST_LINE_START;
        layoutConst.weighty = R_FIVE;
        layoutConst.gridx = 2;
        layoutConst.gridy = R_SEVEN;
        add(inventoryTitle, layoutConst);
    }

    private void setMenuBar(){

        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        menuBar.add(menu);
        addMenuBarItems();
        add(menuBar);
        setJMenuBar(menuBar);
    }

    private void addMenuBarItems(){

        addChangeNameOption();
        addLoadJsonOption();
        addLoadSaveGameOption();
        addSaveGameOption();
    }

    private void addChangeNameOption(){

        menuName = new JMenuItem("Change Name");
        menuName.addActionListener(this);
        menu.add(menuName);
        menu.addSeparator();
    }

    private void addLoadJsonOption(){

        menuLoadJSON = new JMenuItem("Load JSON File");
        menuLoadJSON.addActionListener(this);
        menu.add(menuLoadJSON);
        menu.addSeparator();
    }

    private void addLoadSaveGameOption(){

        menuLoadSave = new JMenuItem("Load Saved Game");
        menuLoadSave.addActionListener(this);
        menu.add(menuLoadSave);
        menu.addSeparator();
    }

    private void addSaveGameOption(){

        menuSave = new JMenuItem("Save Game");
        menuSave.addActionListener(this);
        menu.add(menuSave);
    }

    private void setListLabel(){

        listLabel = new JLabel("Game Commands");
        addListLabel();
    }
    
    private void addListLabel(){

        layoutConst = new GridBagConstraints();
        layoutConst.insets = new Insets(IN,IN,IN,IN);
        layoutConst.anchor = GridBagConstraints.FIRST_LINE_START;
        layoutConst.gridx = 0;
        layoutConst.gridy = R_SEVEN;
        add(listLabel, layoutConst);
    }

    private void setListArea(){

        listArea = new JTextArea(R_SIX,2);
        JScrollBar listBar = new JScrollBar();
        listArea.setEditable(false);
        listArea.setBackground(Color.white);
        listArea.setText("Eat <Item Name>\nGo <Direction>\nInventory\nLook\n");
        listArea.append("Look <Item Name>\nRead <Item Name>\nTake <Item Name>\n");
        listArea.append("Toss <Item Name>\nWear <Item Name>");
        listArea.add(listBar);
        addListArea();
    }

    private void addListArea(){

        layoutConst = new GridBagConstraints();
        layoutConst.insets = new Insets(IN,IN,IN,IN);
        layoutConst.anchor = GridBagConstraints.FIRST_LINE_START;
        layoutConst.weighty = IN;
        layoutConst.gridx = 0;
        layoutConst.gridy = R_EIGHT;
        add(listArea, layoutConst);
    }

    private void setInventoryArea(){

        inventoryArea = new JTextArea(R_SIX,2);
        JScrollBar inventoryBar = new JScrollBar();
        inventoryArea.setEditable(false);
        inventoryArea.add(inventoryBar);
        addInventoryArea();
    }

    private void addInventoryArea(){

        layoutConst = new GridBagConstraints();
        layoutConst.insets = new Insets(IN,IN,IN,IN);
        layoutConst.anchor = GridBagConstraints.FIRST_LINE_START;
        layoutConst.weighty = IN;
        layoutConst.gridwidth = 2;
        layoutConst.gridheight = 2;
        layoutConst.fill = GridBagConstraints.BOTH;
        layoutConst.gridx = 2;
        layoutConst.gridy = R_EIGHT;
        add(inventoryArea, layoutConst);
    }

    /**
     * Overrides the actionPerformed method
     * 
     * @param event
     */
    @Override
    public void actionPerformed(ActionEvent event){

        ifMenuBarActions(event);
        if(event.getSource() == startButton){

            startGameOperations();
        
        }else if(event.getSource() == inputField){

            loopOperations();
            inputField.setText("");
        }
    }

    private void ifMenuBarActions(ActionEvent event){

        if(event.getSource() == menuLoadJSON){

            fileOpenOperationsJSON();

        }else if(event.getSource() == menuLoadSave){

            fileOpenOperationsSave();

        }else if(event.getSource() == menuSave){

            saveGameOperations();
        
        }else if(event.getSource() == menuName){

            setNameOperations();

        }
    }

    private void fileOpenOperationsJSON(){

        File readFile = null;  
        fileChooser = new JFileChooser();
        int chooserVal = fileChooser.showOpenDialog(this);

        if(chooserVal == JFileChooser.APPROVE_OPTION){

            readFile = fileChooser.getSelectedFile();
            this.fileName = readFile.getPath();
        }
    }

    private void fileOpenOperationsSave(){

        File readFile = null;  
        fileChooser = new JFileChooser();
        int chooserVal = fileChooser.showOpenDialog(this);

        if(chooserVal == JFileChooser.APPROVE_OPTION){

            readFile = fileChooser.getSelectedFile();
            this.saveName = readFile.getPath();
            
        }
    }

    private void saveGameOperations(){

        String message = "What is the file name for your game?";
        String title = "Save Adventure File";
        String name = (String)JOptionPane.showInputDialog(this, message, title, JOptionPane.QUESTION_MESSAGE);
        theGame.saveGameProcessGUI(name);
    }

    private void setNameOperations(){

        String message = "What would you like your player name to be?";
        String title = "Player Name";
        String name = (String)JOptionPane.showInputDialog(this, message, title, JOptionPane.QUESTION_MESSAGE);
        playerName.setText(name + "'s Adventure");
        theGame.setPlayerNameGUI(name);
    }

    private void startGameOperations(){

        try{
            startGameOptions();
        }catch(InvalidJSONException ex){

            JOptionPane.showMessageDialog(this, ex.getMessage(), "Invalid JSON", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void startGameOptions() throws InvalidJSONException{

        if(this.fileName != null){

            gameOutput.append(theGame.newJsonAdventureSetUpGUI(this.fileName));

        }else if(this.saveName != null){

            ifSavePresentOperations();
        }else{

            defaultConfirmation();
        }
    }

    private void defaultConfirmation(){

        String message = "You haven't loaded a JSON file or Save Game. Do you want a default adventure?";
        int n = JOptionPane.showConfirmDialog(this, message, "Load Default Adventure", JOptionPane.YES_NO_OPTION);

        if(n == 0){

            gameOutput.append(theGame.defaultAdventureSetUpGUI());
        }
    }

    private void ifSavePresentOperations(){

        gameOutput.append(theGame.saveGameSetUpGUI(this.saveName));
        playerName.setText(theGame.getPlayerNameGUI() + "'s Adventure");
    }

    private void loopOperations(){

        try{
            if(inputField.getText().equals("quit")){

                ifQuitOperations();

            }else{
                
                ifNotQuitOperations();
            }
        }catch(InvalidCommandException ex){

            JOptionPane.showMessageDialog(this, ex.getMessage(), "Invalid Command", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ifNotQuitOperations() throws InvalidCommandException{

        gameOutput.append(theGame.gameLoopGUI(inputField.getText()));
        inventoryArea.setText("");
        inventoryArea.setText(theGame.getInventoryList());
    }

    private void ifQuitOperations(){

        String message = "Are you sure you want to quit the adventure?";
        int n = JOptionPane.showConfirmDialog(this, message, "Quit", JOptionPane.YES_NO_OPTION);

        if(n == 0){

            System.exit(0);
        }
    }

    public static void main(String[] args){

        Game theGame = new Game();
        theGame.setParser();
        GameGui theGui = new GameGui(theGame);
        theGui.setVisible(true);
    }


}
