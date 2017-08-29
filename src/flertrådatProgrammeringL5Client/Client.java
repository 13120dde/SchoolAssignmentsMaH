package flertrådatProgrammeringL5Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Patrik Lind on 2016-12-21.
 */
public class Client implements Runnable {

    private Socket socket;
    private GUIChatClient gui;
    private String userName;
    private DataInputStream dis;
    private DataOutputStream dos;

    public Client(GUIChatClient gui, String userName, String ip, int port) {
        this.userName = userName;
        try {
            socket = new Socket(ip, port);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.gui = gui;
        gui.addClient(this);
        gui.Start();

        new Thread(this).start();

    }

    public void sendMessage(String message) {

        try {
            dos.writeUTF(userName + ": " + message);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        while (true) {
            try {
                String response = dis.readUTF();
                gui.displayMessage(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
