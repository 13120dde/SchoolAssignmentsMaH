package flertrådatProgrammeringL1;

import javax.swing.*;

/**
 * Created by Patrik Lind on 2016-11-15.
 */
public class DisplayThread2 extends Thread
{
    private JPanel panel;
    private JLabel text;
    private boolean isRunning;

    int y = 0;

    public DisplayThread2(JPanel panel){
        this.panel=panel;
        isRunning = false;

        text = new JLabel("Display Thread");
        text.setBounds((int) (panel.getPreferredSize().getWidth()/2-50),y, 100,20);
        panel.add(text);
    }

    public void run(){
        isRunning = true;

        while(isRunning){

            try {
                Thread.sleep(30);
                animateDisplay();
                System.out.println("Running");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void stopThread(){
        if(isRunning){
            isRunning = false;
            y = (int) text.getBounds().getY();
            panel.remove(text);
        }
    }

    private void animateDisplay() {
        System.out.println("hej");
        if(y> panel.getHeight()){
            y=0;
        }
        text.setBounds(panel.getWidth()/2-50, y,100,20);
        y+=2;
        panel.repaint();
    }


}
