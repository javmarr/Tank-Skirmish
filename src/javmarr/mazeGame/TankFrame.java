package javmarr.mazeGame;

import javmarr.mazeGame.AnimationThread;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class TankFrame extends JFrame {

    int p1Score, p2Score;
    MazePanel panel;

    //JPanel tankSkirmishPanel;
    JPanel stats;
    JFrame highscores;
    JTextArea scores;
    JLabel playerInfo;

    DoublyLinkedList scoreList;

    TankFrame(String str, int frameX, int frameY) {
        super(str);
        scoreList = new DoublyLinkedList();
        p1Score = 0;
        p2Score = 0;
        panel = new MazePanel(frameX, frameY);

        stats = new JPanel();
        playerInfo = new JLabel(String.format("P1 Score: %s                   P2 Score: %s", p1Score, p2Score));
        scores = new JTextArea("===HIGH SCORES===\n");
        scores.setEditable(false);

        stats.add(playerInfo);

        getContentPane().add(panel);
        getContentPane().add(stats, "South");

        //create highscore frame
        highscores = new JFrame("HIGH SCORES");
        highscores.getContentPane().add(scores);
        highscores.setVisible(true);
        highscores.setSize(100, 300);

        AnimationThread animethread = new AnimationThread(this);
        animethread.start();
    }

    public void update() {
        panel.update();

        if (p1Score != panel.getP1Kills() || p2Score != panel.getP2Kills()) {
            int scoreToBeAdded = -1;
            String msg = "";

//			if(p1Score != panel.getP1Kills()) //p1 died so get his score before death
//				scoreToBeAdded = p2Score;
//			else
//				scoreToBeAdded = p1Score;
            //System.out.println("P1 score: " + p1Score + "   P1 kills: " + panel.getP1Kills());
            //System.out.println("P2 score: " + p2Score + "   P2 kills: " + panel.getP2Kills());
            if (p1Score >= panel.getP1Kills()) {
                scoreToBeAdded = p1Score;
                msg += "\nPlayer 1, enter your name to be shown on the highscore chart";
            } else {
                scoreToBeAdded = p2Score;
                msg += "\nPlayer 2, enter your name to be shown on the highscore chart";
            }

            p1Score = panel.getP1Kills();
            p2Score = panel.getP2Kills();

            playerInfo.setText(String.format("P1 Score: %s                   P2 Score: %s", p1Score, p2Score));

            //JOptionPane.showMessageDialog(this, msg);
            //JOptionPane.showInputDialog(highscores, msg);
            String userInput;
            do {
                userInput = JOptionPane.showInputDialog(highscores, msg);
            } while (userInput == null || userInput.length() <= 1);

            //scoreData.add(new Score(userInput,scoreToBeAdded));
            System.out.println("Adding" + userInput + ": " + scoreToBeAdded);

            scoreList.add(userInput, scoreToBeAdded);
            //scoreList.sort();

            String s = scoreList.displayBackwards();
            System.out.println(s);

            scores.setText("===HIGH SCORES===\n" + s);
            JOptionPane.showMessageDialog(panel, "Resetting the game for next round");
            panel.resetMaze();

        }

    }
}
