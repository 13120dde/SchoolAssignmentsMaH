
package flertrådatProgrammeringL1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Modified by Patrik Lind on 2016-11-13.
 *
 * The GUI for assignment 1 - DualThreads and assigment 2 - rotate triangle.
 *
 * This class has been modified with following:
 * - additional variables.
 * - constructor.
 * - some additional code in initializeGUI().
 * - added a WindowListener to the frame in start() method that terminates all additional running threads (trough
 * controller) when user closes the application.
 *
 */
public class GUIDualThreads extends JComponent
{
	/**
	 * These are the components you need to handle.
	 * You have to add listeners and/or code
	 */
	private JFrame frame;		// The Main window
	private JButton btnDisplay;	// Start thread moving display
	private JButton btnDStop;	// Stop moving display thread
	private JButton btnTriangle;// Start moving graphics thread
	private JButton btnTStop;	// Stop moving graphics thread
	private JPanel pnlMove;		// The panel to move display in
	private JPanel pnlRotate;	// The panel to move graphics in

	//Additional variables for the Assigment
	private int movePanelHeight;
	private int y=0; //Used by animateDisplayText to move the text vertically inside the panel
	private JLabel movingLabel; //Label containing the moving text
	private ButtonListener listener;
	private Assignment1Main controller;
	private DrawTrianglePanel triangle;

	private DisplayThread2 displayThread;

	/**Instantiate this object by passing in Assigment1Main object as argument.
	 *
	 * @param assignment1Main : Assigemnt1Main
	 */
	public GUIDualThreads(Assignment1Main assignment1Main) {
		controller=assignment1Main;
	}



	/**
	 * Starts the application
	 */
	public void Start()
	{
		frame = new JFrame();
		frame.setBounds(0, 0, 494, 332);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setTitle("Multiple Thread Demonstrator");
		InitializeGUI();					// Fill in components
		frame.setVisible(true);
		frame.setResizable(false);			// Prevent user from change size
		frame.setLocation(580,600);	// Start middle screen

		//Used to force kill all additional threads when user closes the application.
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				controller.killAllThreads();
			}
		});
	}
	
	/**
	 * Sets up the GUI with components
	 */
	private void InitializeGUI()
	{

		listener = new ButtonListener();

		// The moving display outer panel
		JPanel pnlDisplay = new JPanel();
		Border b2 = BorderFactory.createTitledBorder("Display Thread");
		pnlDisplay.setBorder(b2);
		pnlDisplay.setBounds(12, 12, 222, 269);
		pnlDisplay.setLayout(null);
		
		// Add buttons and drawing panel to this panel
		btnDisplay = new JButton("Start Display");
		btnDisplay.setBounds(10, 226, 121, 23);
		btnDisplay.addActionListener(listener);
		pnlDisplay.add(btnDisplay);
		
		btnDStop = new JButton("Stop");
		btnDStop.setBounds(135, 226, 75, 23);
		btnDStop.addActionListener(listener);
		btnDStop.setEnabled(false);
		pnlDisplay.add(btnDStop);
		
		pnlMove = new JPanel();
		pnlMove.setBounds(10,  19,  200,  200);
		pnlMove.setLayout(null);
		pnlMove.setBackground(Color.GREEN);
		pnlMove.setOpaque(true);
		Border b21 = BorderFactory.createLineBorder(Color.black);
		pnlMove.setBorder(b21);



		movePanelHeight = pnlMove.getHeight();

		movingLabel = new JLabel("Display Thread");

		movingLabel.setBounds((pnlMove.getWidth()/2-50),0,100,20);
		//pnlMove.add(movingLabel);
		pnlDisplay.add(pnlMove);

		// Then add this to main window
		frame.add(pnlDisplay);
		
		// The moving graphics outer panel
		JPanel pnlTriangle = new JPanel();
		Border b3 = BorderFactory.createTitledBorder("Triangle Thread");
		pnlTriangle.setBorder(b3);
		pnlTriangle.setBounds(240, 12, 222, 269);
		pnlTriangle.setLayout(null);
		
		// Add buttons and drawing panel to this panel
		btnTriangle = new JButton("Start Rotate");
		btnTriangle.setBounds(10, 226, 121, 23);
		btnTriangle.addActionListener(listener);
		pnlTriangle.add(btnTriangle);
		
		btnTStop = new JButton("Stop");
		btnTStop.setBounds(135, 226, 75, 23);
		btnTStop.addActionListener(listener);
		btnTStop.setEnabled(false);

		pnlTriangle.add(btnTStop);
		
		pnlRotate = new JPanel();
		pnlRotate.setBounds(10,  19,  200,  200);
		Border b31 = BorderFactory.createLineBorder(Color.black);
		pnlRotate.setBorder(b31);

		triangle = new DrawTrianglePanel();
		pnlRotate.add(triangle);
		pnlTriangle.add(pnlRotate);
		// Add this to main window
		frame.add(pnlTriangle);


	}

	/**Used by DisplayThread-thread  (via the controller) to move the text in pnlMove-window. Mooves a text vertically
	 * inside it's panel.
	 *
	 */
	public void animateDisplayText() {
		if(y> movePanelHeight){
			y=0;
		}
		movingLabel.setBounds(pnlMove.getWidth()/2-50, y,100,20);
		y+=2;

	}

	/**Used by TriangleThread-thread (via the controller) to animate a rotating triangle. Rotates the triangle on it's
	 * axis by 6 degrees.
	 *
	 */
	public void rotateTriangle() {
		triangle.rotate();
	}

	/*I acknowledge that the assignment instructed not to make nestled classes but in this case I believe that it's
	acceptable to make the ButtonListener a inner class since its only used by the GUI.

	 */
	private class ButtonListener implements ActionListener{


		@Override
		public void actionPerformed(ActionEvent e) {

			if(e.getSource()==btnDisplay){

				//controller.startDisplayThread();

				if(displayThread==null){
					displayThread = new DisplayThread2(pnlMove);
					displayThread.start();
				}

				btnDStop.setEnabled(true);
				btnDisplay.setEnabled(false);
			}
			if(e.getSource()==btnDStop){
				//controller.stopDisplayThread();
				if(displayThread!=null){
					displayThread.stopThread();
					displayThread=null;
				}
				btnDisplay.setEnabled(true);
				btnDStop.setEnabled(false);
			}
			if(e.getSource()==btnTriangle){
				controller.startTriangleThread();
				btnTStop.setEnabled(true);
				btnTriangle.setEnabled(false);
			}
			if(e.getSource()==btnTStop){
				controller.stopTriangleThread();
				btnTriangle.setEnabled(true);
				btnTStop.setEnabled(false);
			}
		}
	}

}
