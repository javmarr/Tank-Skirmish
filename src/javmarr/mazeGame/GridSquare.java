package javmarr.mazeGame;

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class GridSquare extends Rectangle {

    Random dice;
    boolean wall;
    boolean broken;
    boolean hasItem;
    boolean slowDown;

    int hp;
    int maxHp;
    int itemNumber;
    ImageIcon picture;
    ImageIcon itemPic;
    String item;

    GridSquare(int theX, int theY, int w, int h) {
        dice = new Random();
        maxHp = 10;
        hp = maxHp;
        setBounds(theX, theY, w, h);
        wall = false;
        broken = false;
        hasItem = false;
        itemNumber = 0;
    }

    public void addItem() {
        hasItem = true;
        itemNumber = dice.nextInt(4);
        //System.out.println("itemNumber: " + itemNumber);
    }

    public void makeRocky() {
        slowDown = true;
    }

    public void draw(Graphics g, Component c) {
        if (!wall) //is not a wall
        {
            if (hasItem) //has item
            {
                itemPic = getItem();
            }
            if (slowDown) //has rocky terrainn
            {
                picture = new ImageIcon(getClass().getResource("/data/rockyGround.png"));
            } else //normal ground
            {
                picture = new ImageIcon(getClass().getResource("/data/safeGround.png"));
            }
        } else {
            if (hp == maxHp) //full
            {
                picture = new ImageIcon(getClass().getResource("/data/wall.png"));
            } else if (hp >= maxHp / 2) //cracked
            {
                picture = new ImageIcon(getClass().getResource("/data/wall_semibroken.png"));
            } else // near dead
            {
                picture = new ImageIcon(getClass().getResource("/data/wall_broken.png"));
            }
        }

        g.drawImage(picture.getImage(), x, y, picture.getIconWidth(), picture.getIconHeight(), c);
        if (hasItem && broken) {
            g.drawImage(itemPic.getImage(), x, y, itemPic.getIconWidth(), itemPic.getIconHeight(), c);
        }

    }

    public ImageIcon getItem() {
        switch (itemNumber) {
            //ammo - fill up the ammo for the player
            //shield - protect the player for two shots
            //health - fill up the hp for the player
            //speed - speed boost for x distance

            case 0: //ammo
                return new ImageIcon(getClass().getResource("/data/item_ammo.png"));

            case 1: //shield
                return new ImageIcon(getClass().getResource("/data/item_shield.png"));

            case 2: //health
                return new ImageIcon(getClass().getResource("/data/item_hp.png"));

            case 3: //speed
                return new ImageIcon(getClass().getResource("/data/item_speed.png"));

            default:
                System.out.println("===ITEM ERROR===");
                return new ImageIcon(getClass().getResource("/data/blank.png"));
        }
    }
}
