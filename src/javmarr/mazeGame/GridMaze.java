package javmarr.mazeGame;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class GridMaze {

    GridSquare[][] grid;

    Player p1;
    Player p2;

    static int maxHitTimesForPlayer = 8;
    static int maxDistanceForPlayerRockets = 4;
    static int p1_heartX = 40;
    static int p1_heartY = 860;

    static int p2_heartX = 800;
    static int p2_heartY = 860;

    static double wallPercent = .45; // % of all squares that are walls
    static double itemPercent = .5; //% of all walls have an item

    //terrain slowdown doesn't work, 
    static double rockyPercent = .0; //% of all squares that are rocky

    Random dice;
    int n, m, size;

    GridMaze(int theN, int theM, int s) {
        dice = new Random(500);

        grid = new GridSquare[theN][theM];

        for (int i = 0; i < theN; i++) {
            for (int j = 0; j < theM; j++) {
                grid[i][j] = new GridSquare(i * s, j * s, s - 1, s - 1);

                // if(Math.random() < wallPercent)
                //generates a random number from 1-100 to calculate which are the walls
                if ((dice.nextInt(100) + 1) < (wallPercent * 100)) {
                    changeToWall(i, j);
                }

                // if(Math.random() < rockyPercent)
                if ((dice.nextInt(100) + 1) < (rockyPercent * 100)) {
                    changeToRocky(i, j);
                }

                //set a one square border around the grid
                if (i == 0 || i == theN - 1 || j == 0 || j == theM - 1) {
                    changeToWall(i, j);
                }
            }
        }

        n = theN;
        m = theM;
        size = s;

        //x,y,times they can get hit by rockets, max distance rocket travels
        p1 = new Player(dice.nextInt(n) * size, dice.nextInt(m) * size, maxHitTimesForPlayer, maxDistanceForPlayerRockets, new ImageIcon(getClass().getResource("/data/tank1UP.png")));
        p2 = new Player(dice.nextInt(n) * size, dice.nextInt(m) * size, maxHitTimesForPlayer, maxDistanceForPlayerRockets, new ImageIcon(getClass().getResource("/data/tank2UP.png")));

        respawn(1);
        respawn(2);

    }

    public void changeToRocky(int i, int j) {
        //System.out.println("Adding item");
        grid[i][j].makeRocky();
    }

    public void changeToWall(int i, int j) {
        grid[i][j].wall = true;
        //possibly add item
        // if(Math.random() < itemPercent)
        if ((dice.nextInt(100) + 1) < (itemPercent * 100)) {
            //System.out.println("Adding item");
            grid[i][j].addItem();
        }
    }

    public void respawn(int playerNumber) {
        switch (playerNumber) {
            case 1: {
                do {
                    p1 = new Player(dice.nextInt(n) * size, dice.nextInt(m) * size, maxHitTimesForPlayer,
                            maxDistanceForPlayerRockets, new ImageIcon(getClass().getResource("/data/tank1UP.png")));
                } while (playerHitsGridSquare(p1) || playerHitsPlayer(p1, p2));
                break;
            }

            case 2: {
                do {
                    p2 = new Player(dice.nextInt(n) * size, dice.nextInt(m) * size, maxHitTimesForPlayer,
                            maxDistanceForPlayerRockets, new ImageIcon(getClass().getResource("/data/tank2UP.png")));
                } while (playerHitsGridSquare(p2) || playerHitsPlayer(p1, p2));
                break;
            }

            default:
                break;
        }
    }

    public void draw(Graphics g, Component c, int p1_score, int p2_score) {
        //maze
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j].draw(g, c);
            }
        }

        //players
        p1.draw(g, c);
        p2.draw(g, c);

        //player killcount
        //g.drawString(String.format("%s",p1_score), p1.x + 16, p1.y + p1.height);
        //g.drawString(String.format("%s",p2_score), p2.x + 16, p2.y + p2.height) ;
    }

    public boolean playerHitsPlayer(Player p1, Player p2) {
        Rectangle temp = p1.intersection(p2);

        //players intersect
        if (temp.height >= 5 && temp.width >= 5) {
            //System.out.println("x: " + p.x + " y: " + p.y + " width: " + p.width + " height: " + p.height);
            //System.out.println("x: " + grid[i][j].x + " y: " + grid[i][j].y + " width: " + 
            //	grid[i][j].width + " height: " + grid[i][j].height);

            //System.out.println("===HIT PLAYER===");
            p1.x -= p1.dx;
            p1.y -= p1.dy;
            p1.dx = 0;
            p1.dy = 0;

            p2.x -= p2.dx;
            p2.y -= p2.dy;
            p2.dx = 0;
            p2.dy = 0;

            return true;
        }
        return false;
    }

    public boolean playerHitsGridSquare(Player p) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                Rectangle temp = grid[i][j].intersection(p);

                //is a wall and intersects
                if (grid[i][j].wall && temp.height >= 5 && temp.width >= 5) {
                    //System.out.println("x: " + p.x + " y: " + p.y + " width: " + p.width + " height: " + p.height);
                    //System.out.println("x: " + grid[i][j].x + " y: " + grid[i][j].y + " width: " + 
                    //	grid[i][j].width + " height: " + grid[i][j].height);
                    //System.out.println("===HIT WALL===");

                    p.x -= p.dx;
                    p.y -= p.dy;
                    p.dx = 0;
                    p.dy = 0;

                    return true;
                }

                //is has item and is broken and intersects
                if (grid[i][j].hasItem && grid[i][j].broken && temp.height >= 5 && temp.width >= 5) {
                    //System.out.println("x: " + p.x + " y: " + p.y + " width: " + p.width + " height: " + p.height);
                    //System.out.println("x: " + grid[i][j].x + " y: " + grid[i][j].y + " width: " + 
                    //	grid[i][j].width + " height: " + grid[i][j].height);
                    //System.out.println("===GOT ITEM===\n" + grid[i][j].itemNumber);

                    //apply item effect
                    switch (grid[i][j].itemNumber) {
                        case 0: //ammo
                            p.refillAmmo();
                            break;
                        case 1: //shield
                            p.activateShield();
                            break;
                        case 2: //health
                            p.refillHP();
                            break;
                        case 3: //speed
                        {
                            //player already has speedup
                            if (p.allowSpeedUp) {
                                p.refillSpeedUp();
                            } else {
                                p.allowSpeedUP();
                            }
                        }
                        break;
                        default:
                        //System.out.println("===ITEM EFFECT ERROR===");
                    }

                    grid[i][j].hasItem = false;
                    return false;
                }
            }

        }
        return false;
    }

    public boolean playerHitsRockyTerrain(Player p) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                Rectangle temp = grid[i][j].intersection(p);

                //steped on a rocky terrain, slow the player down
                if (grid[i][j].slowDown && !grid[i][j].wall && temp.height >= 5 && temp.width >= 5) //if(grid[i][j].slowDown && grid[i][j].intersects(p))
                {
                    //System.out.println("====SLOW DOWN====");
                    p.isSlowing = true;
                    return true;
                }
            }
        }
        p.isSlowing = false;
        return false;
    }

    public void boundPlayer(Player p, int maxX, int maxY) {
        maxX -= 20;
        maxY -= 40;
        if (p.x < 0 || (p.x + p.width) >= maxX) {
            /*
			if(p.x < 0)
				p.x = 0;
			else
				p.x = maxX - p.width;
             */
            p.x -= p.dx;
            p.dx = 0;

            //System.out.println("maxX:" +maxX);
            //System.out.println("x:" + p.x + "width: " + p.width + " dx: "+p.dx);
            //	System.out.println("====HIT WIDTH BOUND====");
        }
        if (p.y < 0 || (p.y + p.height) >= maxY) {
            /*
			if(p.y < 0)
				p.y = 0;
			else
				p.y = maxY - p.height;
             */
            p.y -= p.dy;
            p.dy = 0;

            //System.out.println("maxY:" +maxY);
            //System.out.println("y:" + p.y + "height: " + p.height + " dy: "+p.dy);
            //System.out.println("====HIT HEIGHT BOUND====");
        }
    }

    public void update() {
        //update the players in the grid
        p1.update();
        p2.update();
    }
}
