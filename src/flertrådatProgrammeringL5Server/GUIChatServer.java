
package flertrådatProgrammeringL5Server;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.*;

/**
 * The GUI for assignment 5
 */
public class GUIChatServer
{
	/**
	 * These are the components you need to handle.
	 * You have to add listeners and/or code
	 */
	private JFrame frame;				// The Main window
	private JTextField txt;				// Input for text to send
	private JButton btnSend;			// Send text in txt
	private JTextArea lstMsg;			// The logger listbox

	private ServerSocket socket;
	private LinkedList<User> users;
	private Executor threadPool;

	private int port;


	/**
	 * Constructor
	 */
	public GUIChatServer(int port)
	{
		this.port =port;
		users = new LinkedList<User>();
		Start();
		threadPool = Executors.newFixedThreadPool(50);
		threadPool.execute(new ConnectionHandler(port));
	}


		/**
	 * Starts the application
	 */
	public void Start()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 600,300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setTitle("Multi Chat Server");			// Change to "Multi Chat Server" on server part and vice versa
		InitializeGUI();					// Fill in components
		frame.setVisible(true);
		frame.setResizable(false);			// Prevent user from change size
	}
	
	/**
	 * Sets up the GUI with components
	 */
	private void InitializeGUI()
	{
		txt = new JTextField();
		txt.setBounds(13,  13, 177, 23);
		frame.add(txt);
		btnSend = new JButton("Send");
		btnSend.setBounds(197, 13, 75, 23);
		btnSend .addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayMessage(txt.getText());
			}
		});
		frame.add(btnSend);
		lstMsg = new JTextArea();
		lstMsg.setEditable(false);
		JScrollPane pane = new JScrollPane(lstMsg);
		pane.setBounds(12, 51, 260, 199);
		pane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		frame.add(pane);
	}


	public synchronized void displayMessage(String text) {
		lstMsg.append(text+"\n");
		Iterator<User> i = users.iterator();

		while(i.hasNext()){
			i.next().sendMessage(text);
		}

	}
	public static void main(String[] args) {

		GUIChatServer guiServer = new GUIChatServer(45450);
	}


	private class ConnectionHandler implements Runnable{
		private int port;

		public ConnectionHandler(int port) {
			this.port = port;
		}

		public void run() {
			displayMessage("Server: Connection handler started.");
			Socket socket = null;
			try (ServerSocket serverSocket = new ServerSocket(port)) {
				while (true) {
					try {
						socket = serverSocket.accept();
						User user = new User(socket, GUIChatServer.this);
						users.add(user);
						displayMessage("Server: user "+user.toString()+" connected to server");
						threadPool.execute(user);

					} catch (Exception e) {
						if (socket != null) {
							socket.close();
						}
					}
				}
			} catch (Exception e) {
			}
		}
	}
}
