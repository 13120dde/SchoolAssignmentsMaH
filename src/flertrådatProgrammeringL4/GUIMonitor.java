
package flertrådatProgrammeringL4;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Set;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * The GUI for assignment 4
 */
public class GUIMonitor
{
	/**
	 * These are the components you need to handle.
	 * You have to add listeners and/or code
	 */
	private JFrame frame;				// The Main window
	private JMenu fileMenu;				// The menu
	private JMenuItem openItem;			// File - open
	private JMenuItem saveItem;			// File - save as
	private JMenuItem exitItem;			// File - exit
	private JTextField txtFind;			// Input string to find
	private JTextField txtReplace; 		// Input string to replace
	private JCheckBox chkNotify;		// Client notification choise
	private JLabel lblInfo;				// Hidden after file selected
	private JButton btnCreate;			// Start copying
	private JButton btnClear;			// Removes dest. file and removes marks
	private JLabel lblChanges;			// Label telling number of replacements


	private ButtonListener listener = new ButtonListener(); //Handling user interaction.
	private JTextPane txtPaneSource, txtPaneDest ; //Moved from local to global scope.

    private BoundedBuffer buffer;
    private LinkedList<String> textList; //Holds all the words read from the document.
	private JOptionPane jop = new JOptionPane();

	/**
	 * Constructor
	 */
	public GUIMonitor( )
	{

	}

	public void test() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
					ex.printStackTrace();
				}

				setDefaultSize(24);
				frame = new JFrame();
				frame.setBounds(0, 0, 1200,900);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLayout(null);
				frame.setTitle("Text File Copier - with Find and Replace");
				InitializeGUI();					// Fill in components
				addListener();
				frame.setVisible(true);
				frame.setResizable(false);			// Prevent user from change size
				frame.setLocationRelativeTo(null);	// Start middle screen

			}
		});
	}

	public static void setDefaultSize(int size) {

		Set<Object> keySet = UIManager.getLookAndFeelDefaults().keySet();
		Object[] keys = keySet.toArray(new Object[keySet.size()]);

		for (Object key : keys) {

			if (key != null && key.toString().toLowerCase().contains("font")) {

				System.out.println(key);
				Font font = UIManager.getDefaults().getFont(key);
				if (font != null) {
					font = font.deriveFont((float)size);
					UIManager.put(key, font);
				}

			}

		}

	}

	/**
	 * Starts the application
	 */
	public void Start()
	{
		frame = new JFrame();
		frame.setBounds(0, 0, 714,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setTitle("Text File Copier - with Find and Replace");
		InitializeGUI();					// Fill in components
		addListener();
		frame.setVisible(true);
		frame.setResizable(false);			// Prevent user from change size
		frame.setLocationRelativeTo(null);	// Start middle screen

	}

	private void addListener() {
		openItem.addActionListener(listener);
		saveItem.addActionListener(listener);
		exitItem.addActionListener(listener);
		btnClear.addActionListener(listener);
		btnCreate.addActionListener(listener);

	}

	/**
	 * Sets up the GUI with components
	 */
	private void InitializeGUI()
	{
		fileMenu = new JMenu("File");
		openItem = new JMenuItem("Open Source File");
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		saveItem = new JMenuItem("Save Destination File As"); 
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveItem.setEnabled(false);
		exitItem = new JMenuItem("Exit");
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		JMenuBar  bar = new JMenuBar();
		frame.setJMenuBar(bar);
		bar.add(fileMenu);
		
		JPanel pnlFind = new JPanel();
		pnlFind.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Find and Replace"));
		pnlFind.setBounds(12, 32, 700, 150);
		pnlFind.setLayout(null);
		frame.add(pnlFind);
		JLabel lab1 = new JLabel("Find:");
		lab1.setBounds(7, 40, 120, 25);
		pnlFind.add(lab1);
		JLabel lab2 = new JLabel("Replace with:");
		lab2.setBounds(7, 73, 220, 25);
		pnlFind.add(lab2);
		
		txtFind = new JTextField();
		txtFind.setBounds(308, 40, 327, 25);
		pnlFind.add(txtFind);
		txtReplace = new JTextField();
		txtReplace.setBounds(308, 73, 327, 25);
		pnlFind.add(txtReplace);
		chkNotify = new JCheckBox("Notify user on every match");
		chkNotify.setBounds(88, 117, 380, 25);
		pnlFind.add(chkNotify);
		
		//lblInfo = new JLabel("Select Source File..");
		//lblInfo.setBounds(485, 42, 120, 13);
		//frame.add(lblInfo);
		
		btnCreate = new JButton("Create the destination file");
		btnCreate.setBounds(720, 119, 430, 25);
		frame.add(btnCreate);
		btnClear = new JButton("Clear Dest. file and remove marks");
		btnClear.setBounds(720, 151, 430, 25);
		frame.add(btnClear);
		
		lblChanges = new JLabel("No. of Replacements:");
		lblChanges.setBounds(400, 200, 400, 25);
		frame.add(lblChanges);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 200, 1100, 600);
		frame.add(tabbedPane);
		txtPaneSource = new JTextPane();
		JScrollPane scrollSource = new JScrollPane(txtPaneSource);
		tabbedPane.addTab("Source", null, scrollSource, null);
		txtPaneDest = new JTextPane();
		JScrollPane scrollDest = new JScrollPane(txtPaneDest);
		tabbedPane.addTab("Destination", null, scrollDest, null);
	}


    /**Iterates trough the list passed in as argument and adds each string to the text-pane.
     *
     * @param textList : LinkedList<String>
     */
    public void setSourceText(LinkedList<String> textList) {

        txtPaneSource.setText("");

        Document doc = txtPaneSource.getDocument();
        try {


            for (int i =0; i<textList.size(); i++){
                doc.insertString(doc.getLength(), textList.get(i)+" ", null);

            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    /**Iterates trough the list passed in as argument and adds each String object to the destination-textPane.
     * Updates the lblChanges-Jlabel with amount of replaced strings.
     * Enables the save-button in the drop-down menu.
     *
     * @param text : LinkedList<String>
     * @param nbrReplacement : int
     */
    public void setDestinationText(LinkedList<String> text, int nbrReplacement){

        txtPaneDest.setText("");
        lblChanges.setText("No. Of Replacements: "+Integer.toString(nbrReplacement));

        Document doc = txtPaneDest.getDocument();

        try{
            for(int i =0; i<text.size(); i++){
                doc.insertString(doc.getLength(), text.get(i)+" ", null);
            }

        }catch(BadLocationException e){

        }
        saveItem.setEnabled(true);
    }

    /**Opens a text-file by user interaction with JFileChooser, streams in the Strings from the file and stores them
     * in a LinkedList object which in turn will be used by the writer-thread to fill the buffer.
     */
    public void openFile() {

        JFileChooser fileChooser = new JFileChooser();
        File selectedFile=null;

        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            System.out.println(selectedFile.getName());
			BufferedReader in;
			String line="";
			textList = new LinkedList<String>();

			try {
				in = new BufferedReader( new InputStreamReader(new FileInputStream(selectedFile), StandardCharsets.ISO_8859_1));
				line = in.readLine();

				while(line != null)
				{
					String[] wordsInLine = line.split(" ");
					for(int i = 0; i<wordsInLine.length; i++){
						textList.addLast(wordsInLine[i]);
					}
					textList.addLast("\n");
					line =in.readLine();
				}
				in.close();
				setSourceText(textList);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }


    }


    /**Saves the String from destPanel as a text-file by user interaction with JFileChooser.
     *
     */
    private void saveFile() {
		JFileChooser fileChooser = new JFileChooser();
		File file= new File("destinattion.txt");
		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			Writer out = null;
			try {
				out = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(file), StandardCharsets.ISO_8859_1));
				out.write( txtPaneDest.getDocument().getText(0, txtPaneDest.getDocument().getLength()));
				out.flush();
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (BadLocationException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

    /**Instantiates the buffer and starts 3 threads that work with the coping of strings.
     *
     */
    private void startCopying() {

        boolean notify;

        if(chkNotify.isSelected()){
            notify = true;
        }else{
            notify=false;
        }


        buffer = new BoundedBuffer(20, txtPaneSource, txtPaneDest, notify, txtFind.getText(), txtReplace.getText());
        Thread writerT = new Thread(new WriterThread(buffer, textList)) ;
        Thread readerT = new Thread(new ReaderThread(buffer, textList.size(), this));
        Thread modifierT= new Thread( new ModifierThread(buffer, textList.size()));


		try {
			writerT.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(buffer.toString());
        writerT.start();
        readerT.start();

        modifierT.start();

        try {
            writerT.join();
            readerT.join();
            modifierT.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


	public void clearDestAndRemoveMarks() {
		txtPaneDest.getHighlighter().removeAllHighlights();
		txtPaneDest.setText("");
		txtPaneSource.getHighlighter().removeAllHighlights();
		saveItem.setEnabled(false);
	}

    public static void main(String[] args) {
        new GUIMonitor().test(); //.Start();
    }


    /**Inner class that handles button interaction.
     *
     */
    private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource()==openItem){
				openFile();

			}
			if (e.getSource()==saveItem){
                saveFile();

			}
			if (e.getSource()==exitItem){
				System.exit(0);
			}
			if(e.getSource()==btnCreate){
                if(textList!=null){
                    startCopying();
                }else{
                    JOptionPane.showMessageDialog(null, "Open a document first");
                }

			}
			if(e.getSource()==btnClear){
				clearDestAndRemoveMarks();

			}
		}
	}



}
