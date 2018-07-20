package javmarr.mazeGame;

import javmarr.mazeGame.TankFrame;
import javax.swing.*;

public class Driver {

    public static void main(String[] args) {
        int frameX = 1024;
        int frameY = 764;
        frameY = 784;

        TankFrame frame = new TankFrame("Tank Skirmish", frameX, frameY - 20);

        frame.setVisible(true);
        frame.setSize(frameX, frameY);
        JOptionPane.showMessageDialog(frame, "Welcome to TANK SKIRMISH");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}
