package flertrådatProgrammeringL2;


import com.sun.corba.se.pept.transport.ReaderThread;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**Modified by Patrik Lind.
 * The GUI for assignment 2
 *
 * This class has the main-method to run this program.
 *
 */
public class GUIMutex
{
	/**
	 * These are the components you need to handle.
	 * You have to add listeners and/or code
	 */
	private JFrame frame;			// The Main window
	private JLabel lblTrans;		// The transmitted text
	private JLabel lblRec;			// The received text
	private JRadioButton bSync;		// The sync radiobutton
	private JRadioButton bAsync;	// The async radiobutton
	private JTextField txtTrans;	// The input field for string to transfer
	private JButton btnRun;         // The run button
	private JButton btnClear;		// The clear button
	private JPanel pnlRes;			// The colored result area
	private JLabel lblStatus;		// The status of the transmission
	private JTextArea listW;		// The write logger pane
	private JTextArea listR;		// The read logger pane
	private boolean synchSelected;

	/**
	 * Constructor
	 */
	public GUIMutex()
	{
	}

	/**
	 * Starts the application
	 */
	public void Start()
	{
		frame = new JFrame();
		frame.setBounds(0, 0, 601, 482);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setTitle("Concurrent Read/Write");
		InitializeGUI();					// Fill in components
		addListeners();
		frame.setVisible(true);
		frame.setResizable(false);			// Prevent user from change size
		frame.setLocationRelativeTo(null);	// Start middle screen
	}

	/**
	 * Sets up the GUI with components
	 */
	private void InitializeGUI()
	{
		// First, create the static components
		// First the 4 static texts
		JLabel lab1 = new JLabel("Writer Thread Logger");
		lab1.setBounds(18, 29, 128, 13);
		frame.add(lab1);
		JLabel lab2 = new JLabel("Reader Thread Logger");
		lab2.setBounds(388, 29, 128, 13);
		frame.add(lab2);
		JLabel lab3 = new JLabel("Transmitted:");
		lab3.setBounds(13, 394, 100, 13);
		frame.add(lab3);
		JLabel lab4 = new JLabel("Received:");
		lab4.setBounds(383, 394, 100, 13);
		frame.add(lab4);
		// Then add the two lists (of string) for logging transfer
		listW = new JTextArea();
		listW.setBounds(13, 45, 197, 342);
		listW.setBorder(BorderFactory.createLineBorder(Color.black));
		listW.setEditable(false);
		frame.add(listW);
		listR = new JTextArea();
		listR.setBounds(386, 45, 183, 342);
		listR.setBorder(BorderFactory.createLineBorder(Color.black));
		listR.setEditable(false);
		frame.add(listR);
		// Next the panel that holds the "running" part
		JPanel pnlTest = new JPanel();
		pnlTest.setBorder(BorderFactory.createTitledBorder("Concurrent Tester"));
		pnlTest.setBounds(220, 45, 155, 342);
		pnlTest.setLayout(null);
		frame.add(pnlTest);
		lblTrans = new JLabel("Transmitted string goes here");	// Replace with sent string
		lblTrans.setBounds(13, 415, 200, 13);
		frame.add(lblTrans);
		lblRec = new JLabel("Received string goes here");		// Replace with received string
		lblRec.setBounds(383, 415, 200, 13);
		frame.add(lblRec);

		// These are the controls on the user panel, first the radiobuttons
		bSync = new JRadioButton("Syncronous Mode", false);
		bSync.setBounds(8, 37, 131, 17);
		pnlTest.add(bSync);
		bAsync = new JRadioButton("Asyncronous Mode", true);
		bAsync.setBounds(8, 61, 141, 17);
		pnlTest.add(bAsync);
		ButtonGroup grp = new ButtonGroup();
		grp.add(bSync);
		grp.add(bAsync);
		// then the label and textbox to input string to transfer
		JLabel lab5 = new JLabel("String to Transfer:");
		lab5.setBounds(6, 99, 141, 13);
		pnlTest.add(lab5);
		txtTrans = new JTextField();
		txtTrans.setBounds(6, 124, 123, 20);
		pnlTest.add(txtTrans);
		// The run button
		btnRun = new JButton("Run");
		btnRun.setBounds(26, 150, 75, 23);
		pnlTest.add(btnRun);
		JLabel lab6 = new JLabel("Running status:");
		lab6.setBounds(23, 199, 110, 13);
		pnlTest.add(lab6);
		// The colored rectangle holding result status
		pnlRes = new JPanel();
		pnlRes.setBorder(BorderFactory.createLineBorder(Color.black));
		pnlRes.setBounds(26, 225, 75, 47);
		pnlRes.setBackground(Color.LIGHT_GRAY);
		pnlTest.add(pnlRes);
		// also to this text
		lblStatus = new JLabel("Staus goes here");
		lblStatus.setBounds(23, 275, 100, 13);
		pnlTest.add(lblStatus);
		// The clear input button, starts disabled
		btnClear = new JButton("Clear");
		btnClear.setBounds(26, 303, 75, 23);
		btnClear.setEnabled(false);
		pnlTest.add(btnClear);
	}

	private void addListeners(){
		ButtonListener listener = new ButtonListener();
		btnClear.addActionListener(listener);
		btnRun.addActionListener(listener);
		bSync.addActionListener(listener);
		bAsync.addActionListener(listener);
	}

	/**Returns true if sycnhronized-radiobutton is selected, otherwise false.
	 *
	 * @return : boolean
	 */
	public boolean getSynchSelected() {
		if(bSync.isSelected()){
			return true;
		}
		return false;
	}

	/**Appends the character passed in as argument to the right textarea.
	 *
	 * @param c : char
	 */
	public void appendToR(char c) {
		listR.append("Reading character: "+c+"\n");
	}

	/**Sets the string passed in as argument to the Jlabel that shows the received string.
	 *
	 * @param result : String
	 */
	public void setRecieved(String result) {
		lblRec.setText(result);
	}


	/**Compares both strings from the transmitted and received Jpanels and sets the result
	 *JLabel accordingly. Also changes the color for resultPanel.
	 *
	 */
	public void compareStrings() {
		if(lblTrans.getText().equals(lblRec.getText())){
			lblStatus.setText("SUCCESS");
			pnlRes.setBackground(Color.GREEN);
		}else{
			lblStatus.setText("NO MATCH");
			pnlRes.setBackground(Color.RED);
		}
	}

	/**Appends the character passed in as argument to the left textarea.
	 *
	 * @param c : char
	 */
	public void appendToW(char c) {
		listW.append("Writing character: "+c+"\n");
	}

	/**Sets the string passed in as argument to the Jlabel that shows the transmitted string.
	 *
	 * @param stringToWrite : String
	 */
	public void setTransmitted(String stringToWrite) {
		lblTrans.setText(stringToWrite);
	}

	/**
	 * ButtonListener implements ActionListener and is used by the buttons in this GUI.
	 *
	 */
	private class ButtonListener implements ActionListener{

		private CharacterBuffer buffer;
		private WriterThread writer;
		private ReadThread reader;

		@Override
		public void actionPerformed(ActionEvent e) {

			if(e.getSource()==btnRun){

				//Check if inputtext isn't empty
				if(txtTrans.getText().length()>0){


					//Instantiate the threads and the buffer object.
					buffer = new CharacterBuffer();
					writer = new WriterThread(buffer, GUIMutex.this, txtTrans.getText());
					reader = new ReadThread(buffer, GUIMutex.this, txtTrans.getText().length());

					//Start the threads.
					writer.start();
					reader.start();

					btnClear.setEnabled(true);
					bSync.setEnabled(false);
					bAsync.setEnabled(false);
					btnRun.setEnabled(false);

				}else {
					JOptionPane.showMessageDialog(null, "You must type in a String to transfer");
				}


			}
			if(e.getSource()==btnClear){

				if(reader!=null){
					reader.stop();
				}
				if(writer!=null){
					writer.stop();
				}
				buffer = null;

				listR.setText("");
				listW.setText("");
				lblRec.setText("");
				lblTrans.setText("");

				pnlRes.setBackground(Color.LIGHT_GRAY);
				lblStatus.setText("");

				btnClear.setEnabled(false);
				bSync.setEnabled(true);
				bAsync.setEnabled(true);
				btnRun.setEnabled(true);
			}
		}
	}

	public static void main(String[] args) {
		new GUIMutex().Start();
	}
}
