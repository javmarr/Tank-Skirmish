package javmarr.mazeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MazePanel extends JPanel implements KeyListener {

    GridMaze gridMaze;
    int gridX, gridY;
    int boundX, boundY;
    int SIZE;
    int p1_SPEED;
    int p2_SPEED;

    static int NORMAL_SPEED = 3;
    static int FAST_SPEED = 5;
    static int SLOW_SPEED = 1;

    char p1_prevKey;
    int p2_prevKey;
    int p1_score, p2_score;

    MazePanel(int x, int y) {
        boundX = x;
        boundY = y;

        p1_SPEED = NORMAL_SPEED;
        p2_SPEED = NORMAL_SPEED;

        p1_score = 0;
        p2_score = 0;

        SIZE = 40; //size of the gridSquare
        gridX = (int) (x / SIZE);
        gridY = (int) (y / SIZE);
        System.out.println("Grid x: " + gridX + "\n Grid y: " + gridY);
        gridMaze = new GridMaze(gridX, gridY - 1, SIZE);

        //AnimationThread animethread = new AnimationThread(this);
        //animethread.start();
        addKeyListener(this);
        setFocusable(true);

        System.out.println("FINISHED");

    }

    public void resetMaze() {
        gridMaze = new GridMaze(gridX, gridY - 1, SIZE);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("TimesRoman", Font.BOLD, 18));
        g.setColor(Color.WHITE);

        gridMaze.draw(g, this, p1_score, p2_score);

        //draw the status bar
        //display status background
        //table.paintIcon(this,g,0,0);
        //System.out.println("Paint maze panel");
    }

    public void keyPressed(KeyEvent e) {
        char c = e.getKeyChar();
        int i = e.getKeyCode();

        if ("asdw".contains(String.valueOf(c))) //any keys for p1 except shooting and speed
        {
            p1_prevKey = c;
        }

        //if("1235".contains(String.valueOf(c))) //any keys for p2 except shooting and speed
        if (i == e.VK_UP || i == e.VK_DOWN || i == e.VK_LEFT || i == e.VK_RIGHT) {
            p2_prevKey = i;
        }

        if (gridMaze.p1.dead && c == 'r') //p1
        {
            gridMaze.respawn(1);
        }

        if (gridMaze.p2.dead && c == '5') //p2
        {
            gridMaze.respawn(2);
        }

        if (!gridMaze.p1.dead) {
            //speedup button hit
            if (c == 'l') {
                //player is not currently speeding AND player is allowed to speedUp
                if (!gridMaze.p1.isSpeeding && gridMaze.p1.allowSpeedUp) {
                    //speedUp
                    System.out.println("Call the speedup method");

                    gridMaze.p1.activateSpeedUp();
                    p1_SPEED = FAST_SPEED;

                } //player is speeding, turn off the speed
                else if (gridMaze.p1.isSpeeding) {
                    //player is speeding, stop the speed up
                    gridMaze.p1.pauseSpeedUp();
                    p1_SPEED = NORMAL_SPEED;
                }

                changeSpeed(1);
            }

            if (gridMaze.p1.isSpeeding) {
                p1_SPEED = FAST_SPEED;
            } else {
                p1_SPEED = NORMAL_SPEED;
            }

            changeSpeed(1);

            //System.out.println("P1 SPEED: "+ p1_SPEED);
            if (c == 'k') //rocket
            {
                gridMaze.p1.shootRocket();
            }
            if (c == 'j') //bullet
            {
                gridMaze.p1.fireBullet();
            }
            if (c == 'w') //up
            {
                gridMaze.p1.picture = new ImageIcon(getClass().getClassLoader().getResource("tank1UP.png"));
                gridMaze.p1.direction = "UP";
                gridMaze.p1.dy = -1 * p1_SPEED;
                gridMaze.p1.dx = 0;
            }
            if (c == 'a') //left
            {
                gridMaze.p1.picture = new ImageIcon(getClass().getClassLoader().getResource("tank1LEFT.png"));
                gridMaze.p1.direction = "LEFT";
                gridMaze.p1.dx = -1 * p1_SPEED;
                gridMaze.p1.dy = 0;
            }
            if (c == 'd') //right
            {
                gridMaze.p1.picture = new ImageIcon(getClass().getClassLoader().getResource("tank1RIGHT.png"));
                gridMaze.p1.direction = "RIGHT";
                gridMaze.p1.dx = p1_SPEED;
                gridMaze.p1.dy = 0;

            }
            if (c == 's') //down
            {
                gridMaze.p1.picture = new ImageIcon(getClass().getClassLoader().getResource("tank1DOWN.png"));
                gridMaze.p1.direction = "DOWN";
                gridMaze.p1.dy = p1_SPEED;
                gridMaze.p1.dx = 0;
            }

        }

        if (!gridMaze.p2.dead) {
            if (c == '3' && gridMaze.p2.allowSpeedUp) {
                //player is not currently speeding AND player is allowed to speedUp
                if (!gridMaze.p2.isSpeeding && gridMaze.p2.allowSpeedUp) {
                    //speedUp
                    System.out.println("P2: Call the speedup method");

                    gridMaze.p2.activateSpeedUp();
                    p2_SPEED = FAST_SPEED;

                } //player is speeding, turn off the speed
                else if (gridMaze.p2.isSpeeding) {
                    //player is speeding, stop the speed up
                    gridMaze.p2.pauseSpeedUp();
                    p2_SPEED = NORMAL_SPEED;
                }

                changeSpeed(2);
            }

            if (gridMaze.p2.isSpeeding) {
                p2_SPEED = FAST_SPEED;
            } else {
                p2_SPEED = NORMAL_SPEED;
            }

            //changeSpeed(2);
            //System.out.println("P2 SPEED: "+ p2_SPEED);
            if (c == '1') //bullet
            {
                gridMaze.p2.fireBullet();
            }

            if (c == '2') //rocket
            {
                gridMaze.p2.shootRocket();
            }

            if (i == e.VK_UP) //up
            {
                gridMaze.p2.picture = new ImageIcon(getClass().getClassLoader().getResource("tank2UP.png"));
                gridMaze.p2.direction = "UP";
                gridMaze.p2.dy = -1 * p2_SPEED;
                gridMaze.p2.dx = 0;
            }
            if (i == e.VK_LEFT) //left
            {
                gridMaze.p2.picture = new ImageIcon(getClass().getClassLoader().getResource("tank2LEFT.png"));
                gridMaze.p2.direction = "LEFT";
                gridMaze.p2.dx = -1 * p2_SPEED;
                gridMaze.p2.dy = 0;
            }
            if (i == e.VK_RIGHT) //right
            {
                gridMaze.p2.picture = new ImageIcon(getClass().getClassLoader().getResource("tank2RIGHT.png"));
                gridMaze.p2.direction = "RIGHT";
                gridMaze.p2.dx = p2_SPEED;
                gridMaze.p2.dy = 0;

            }
            if (i == e.VK_DOWN) //down
            {
                gridMaze.p2.picture = new ImageIcon(getClass().getClassLoader().getResource("tank2DOWN.png"));
                gridMaze.p2.direction = "DOWN";
                gridMaze.p2.dy = p2_SPEED;
                gridMaze.p2.dx = 0;
            }
        }

        //System.out.println("p1_pressed: " + p1_prevKey);
        //System.out.println("p2_release: " + p2_prevKey);
    }

    public void keyReleased(KeyEvent e) {
        char c = e.getKeyChar();
        int i = e.getKeyCode();

        if (p1_prevKey == c) {

            if ("asdw ".contains(String.valueOf(c))) {
                gridMaze.p1.dx = 0;
                gridMaze.p1.dy = 0;
                //	System.out.println("release: " + c);
            }
        }

        if (p2_prevKey == i) {
            //if("12350".contains(String.valueOf(c)))
            if (i == e.VK_UP || i == e.VK_DOWN || i == e.VK_LEFT || i == e.VK_RIGHT) {
                gridMaze.p2.dx = 0;
                gridMaze.p2.dy = 0;
                //	System.out.println("release: " + c);
            }
        }
        //System.out.println("STOOP");

    }

    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();

    }

    public void changeSpeed(int i) {
        if (i == 1) {
            if (gridMaze.p1.dx < 0) //left
            {
                gridMaze.p1.dx = -1 * p1_SPEED;
            } else if (gridMaze.p1.dx > 0) //right
            {
                gridMaze.p1.dx = p1_SPEED;
            } else if (gridMaze.p1.dy < 0) //up
            {
                gridMaze.p1.dy = -1 * p1_SPEED;
            } else if (gridMaze.p1.dy > 0)//down
            {
                gridMaze.p1.dy = p1_SPEED;
            }

            //System.out.print("Changing speed to for p1 " + p1_SPEED);
        } else if (i == 2) {
            if (gridMaze.p2.dx < 0) //left
            {
                gridMaze.p2.dx = -1 * p2_SPEED;
            } else if (gridMaze.p1.dx > 0) //right
            {
                gridMaze.p2.dx = p2_SPEED;
            } else if (gridMaze.p1.dy < 0) //up
            {
                gridMaze.p2.dy = -1 * p2_SPEED;
            } else if (gridMaze.p1.dy > 0)//down
            {
                gridMaze.p2.dy = p2_SPEED;
            }
            //System.out.print("Changing speed to for p2 " + p2_SPEED);
        }

    }

    public int getP1Kills() {
        return p1_score;
    }

    public int getP2Kills() {
        return p2_score;
    }

    public void update() {
        gridMaze.update();

        //check collision against the window bounds
        gridMaze.boundPlayer(gridMaze.p1, boundX, boundY); // stop player from going over the edges
        gridMaze.boundPlayer(gridMaze.p2, boundX, boundY);

        //check collisions against the grid squares 
        //and make appropriate effects
        gridMaze.playerHitsGridSquare(gridMaze.p1);
        gridMaze.playerHitsGridSquare(gridMaze.p2);

        //check collision against other tanks
        gridMaze.playerHitsPlayer(gridMaze.p1, gridMaze.p2);

        //check collision against rockets
        gridMaze.p1.rocketsHitPlayer(gridMaze.p2); //p1's rocket hit p2
        gridMaze.p2.rocketsHitPlayer(gridMaze.p1);

        //check wall vs rockets
        gridMaze.p1.rocketsHitWall(gridMaze.grid);
        gridMaze.p2.rocketsHitWall(gridMaze.grid);

        //check collision against bullets
        gridMaze.p1.bulletsHitPlayer(gridMaze.p2); //p1's rocket hit p2
        gridMaze.p2.bulletsHitPlayer(gridMaze.p1);

        //check wall vs bullet
        gridMaze.p1.bulletsHitWall(gridMaze.grid);
        gridMaze.p2.bulletsHitWall(gridMaze.grid);

        gridMaze.playerHitsRockyTerrain(gridMaze.p1);
        gridMaze.playerHitsRockyTerrain(gridMaze.p2);

        //check if tanks are dead
        if (gridMaze.p1.dead) {
            if (!gridMaze.p1.blewUp) {
                gridMaze.p1.blowUp();

                p1_score = 0;
                p2_score++;
            }
        }
        if (gridMaze.p2.dead) {
            if (!gridMaze.p2.blewUp) {
                gridMaze.p2.blowUp();

                p1_score++;
                p2_score = 0;
            }

        }

        repaint();
    }
}
