package flertrådatProgrammeringL5Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Patrik Lind on 2016-12-19.
 */
public class User implements Runnable{

    private GUIChatServer gui;
    private Socket socket;
    private String response;
    private DataOutputStream dos;
    private DataInputStream dis;

    public User(Socket socket, GUIChatServer gui){
        this.gui=gui;
        this.socket =socket;
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void run() {
        try {
            while(true) {
                response = dis.readUTF();
                gui.displayMessage(response);
            }
        } catch(IOException e) {}
        try {
            socket.close();
        } catch(Exception e) {}
    }

    public void sendMessage(String message){
        try {
            dos.writeUTF(message);
            dos.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
}
