package algoritmerProjekt1;

import javax.swing.*;

/**
 * Created by Patrik Lind on 2016-09-07.
 */
public class TestParser {
    public static void main(String[] args){
        /*String result=JOptionPane.showInputDialog(null, "Type a string to parse:");
        String message = new StringParser().parseString(result);
        JOptionPane.showMessageDialog(null, message);*/

        JOptionPane.showMessageDialog(null, StringParser.parseString(JOptionPane.showInputDialog(null, "Type a string to parse:")));
    }
}
