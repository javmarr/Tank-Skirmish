package javmarr.mazeGame;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Player extends MovingObject {

    String direction;

    //life
    double maxHP;
    double hp;
    boolean dead;
    boolean blewUp;

    int maxHearts;
    int currentHearts;
    int statIconSize;

    //items
    ImageIcon shield;
    boolean hasShield;
    int shieldHp;

    static int maxSpeedDistance = 250;
    static int MAX_SHIELD_HP = 10;
    int speedUpDistanceLeft;
    boolean allowSpeedUp;
    boolean isSpeeding;
    boolean isSlowing;

    //rocket
    int rocketLimit;
    int rocketsReady;
    int currentRockets;
    int MAX_DISTANCE;
    int rx, ry;

    //bullets
    int bulletLimit;
    int bulletsReady;
    int bx, by;

    ImageIcon explosionPic;

    StatIcon[] hearts;
    StatIcon[] rockets;

    Rocket[] rocketRay;
    Bullet[] bulletRay;

    Player(int thex, int they, int h, int maxD, ImageIcon icon) {
        super(thex, they, icon);

        System.out.println("Created user on position: " + thex + ", " + they);

        hasShield = false;
        blewUp = false;
        isSlowing = false;
        speedUpDistanceLeft = maxSpeedDistance;
        dead = false;
        direction = "UP";
        statIconSize = 10; //size of heart png in px

        //heart stuff----
        maxHearts = h; // number of hits till death
        currentHearts = h;
        hearts = new StatIcon[maxHearts];

        for (int i = 0; i < maxHearts; i++) {
            hearts[i] = new StatIcon(new ImageIcon(getClass().getResource("/data/heart.png")));
        }
        //======================

        //ROCKET STUFF-------
        MAX_DISTANCE = maxD * 40; //from square to px
        rocketLimit = 6;
        rocketsReady = rocketLimit / 2;
        currentRockets = rocketsReady;
        rocketRay = new Rocket[rocketLimit]; //have 3 rockets, 0-2

        for (int i = 0; i < rocketLimit; i++) {
            rocketRay[i] = new Rocket(new ImageIcon(getClass().getResource("/data/rocket1DOWN.png")));
        }
        //======================

        //ammo stuff---
        rockets = new StatIcon[rocketLimit];
        for (int i = 0; i < rocketLimit; i++) {
            rockets[i] = new StatIcon(new ImageIcon(getClass().getResource("/data/rocketIcon1.png")));
        }

        //======================
        //shield stuff----
        shield = new ImageIcon(getClass().getResource("/data/shield.png"));

        //======================
        //Bullet STUFF-------
        bulletLimit = 1;
        bulletsReady = bulletLimit;
        bulletRay = new Bullet[bulletLimit]; //have 3 rockets, 0-2

        for (int i = 0; i < bulletLimit; i++) {
            bulletRay[i] = new Bullet(new ImageIcon(getClass().getResource("/data/rocket1DOWN.png")));
        }
        //======================

        //System.out.println("PLAYER CREATION: hittimes: " + h + ", " + rocketRay[0].damage);
        maxHP = h * rocketRay[0].damage;
        hp = maxHP;

    }

    public void shootRocket() {
        //there is at least one rocket that can be launched
        System.out.println("rocket method, rockets ready: " + rocketsReady);
        if (rocketsReady > 0) {
            System.out.println("Shoot rocket");
            rocketsReady--;
            currentRockets--;
            for (int i = 0; i < rocketLimit; i++) {
                //rocket is ready, launch it
                if (rocketRay[i].ready) {
                    rocketRay[i].launch(x + 15, y + 15, direction);
                    //System.out.println("Launching rocket number: " + i);
                    break;
                }
            }
        }
    }

    public void fireBullet() {
        //there is at least one rocket that can be launched
        System.out.println("bullet method, bullets ready: " + bulletsReady);
        if (bulletsReady > 0) {
            System.out.println("fire Bullet");
            bulletsReady--;
            for (int i = 0; i < bulletLimit; i++) {
                //rocket is ready, launch it
                if (bulletRay[i].ready) {
                    bulletRay[i].fire(x + 15, y + 15, direction);
                    System.out.println("Launching bullet number: " + i);
                    break;
                }
            }
        }
    }

    public int countHearts(Player p) {
        int count = 0;
        for (int i = 0; i < p.hp; i += 10) {
            count++;
        }
        return count;
    }

    public boolean rocketsHitPlayer(Player p) {
        for (int i = 0; i < rocketLimit; i++) {
            //rocket in the air
            if (!rocketRay[i].ready) {
                //check for collision agains player p
                Rectangle temp = rocketRay[i].intersection(p);

                //rocket hits
                if (temp.height >= 5 && temp.width >= 5) {
                    if (p.hasShield) //player has a shield
                    {
                        p.shieldHp -= rocketRay[i].damage;

                        System.out.println("Shield HP: " + p.shieldHp);
                        if (p.shieldHp <= 0) {
                            p.hasShield = false; //destroy shield
                        } else {
                            p.hasShield = true;
                        }
                    } else {
                        p.hp -= rocketRay[i].damage; // no shield, take damage
                        p.currentHearts = p.countHearts(p);
                        //p.currentHearts--; //deduct one heart per rocket hit
                    }
                    rocketRay[i].explode();

                    System.out.println("Player HP: " + p.hp);
                    System.out.println("Shield HP: " + p.shieldHp);
                    if (p.hasShield) {
                        System.out.println("PLAYER HAS SHIELD");
                    } else {
                        System.out.println("NO SHIELD");
                    }

                    if (rocketRay[i].ready);
                    {
                        //rocketsReady ++;
                        //System.out.println("Readying rocket number: " + i);
                        //System.out.println("[UPDATED]Rockets ready: " + rocketsReady );
                    }
                    if (p.hp <= 0) {
                        p.dead = true;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public boolean rocketsHitWall(GridSquare[][] grid) {
        //check all the rockets the player has
        for (int count = 0; count < rocketLimit; count++) {
            //rocket is in the air
            if (!rocketRay[count].ready) {
                //check for collision against all walls
                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid[i].length; j++) {
                        if (grid[i][j].wall) {
                            GridSquare square = grid[i][j];
                            Rectangle temp = square.intersection(rocketRay[count]);

                            //rocket intersect
                            if (square.intersects(rocketRay[count])) {
                                System.out.println(rocketRay[count]);
                                System.out.println(square);
                                System.out.println("ROCKET HIT WALL");
                                System.out.println("ROCKET PWR: " + rocketRay[count].damage);

                                square.hp -= rocketRay[count].damage;
                                System.out.println("Square hp = " + square.hp);
                                rocketRay[count].explode();
                                if (rocketRay[count].ready);
                                {
                                    //rocketsReady ++;
                                    //System.out.println("Readying rocket number: " + i);
                                    //System.out.println("[UPDATED]Rockets ready: " + rocketsReady );
                                }

                                if (square.hp <= 0) {
                                    square.broken = true;
                                    square.wall = false; // not a wall anymore
                                    //System.out.println("===BROKE WALL===");
                                }

                                return true;

                            }
                        }
                    }
                }

            }
        }
        return false;
    }

    public boolean bulletsHitPlayer(Player p) {
        for (int i = 0; i < bulletLimit; i++) {
            //rocket in the air
            //FIX THE COLLISION FOR HTE BULLETS
            if (!bulletRay[i].ready) {
                //check for collision agains player p
                Rectangle temp = bulletRay[i].intersection(p);

                //rocket hits
                if (temp.height >= 5 && temp.width >= 5) {
                    if (p.hasShield) //player has a shield
                    {
                        p.shieldHp -= bulletRay[i].damage;

                        System.out.println("Shield HP: " + p.shieldHp);
                        if (p.shieldHp <= 0) {
                            p.hasShield = false; //destroy shield
                        } else {
                            p.hasShield = true;
                        }
                    } else {
                        p.hp -= bulletRay[i].damage; // no shield, take damage
                        p.currentHearts = p.countHearts(p);
                        //p.currentHearts--; //deduct one heart per rocket hit
                    }
                    bulletRay[i].explode();

                    System.out.println("Player HP: " + p.hp);
                    if (p.hasShield) {
                        System.out.println("PLAYER HAS SHIELD");
                    } else {
                        System.out.println("NO SHIELD");
                    }

                    if (bulletRay[i].ready);
                    {
                        bulletsReady++;
                        //System.out.println("Readying rocket number: " + i);
                        //System.out.println("[UPDATED]Rockets ready: " + rocketsReady );
                    }
                    if (p.hp <= 0) {
                        p.dead = true;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public boolean bulletsHitWall(GridSquare[][] grid) {
        //check all the rockets the player has
        for (int count = 0; count < bulletLimit; count++) {
            //rocket is in the air
            if (!bulletRay[count].ready) {
                //check for collision against all walls
                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid[i].length; j++) {
                        if (grid[i][j].wall) {
                            GridSquare square = grid[i][j];
                            Rectangle temp = square.intersection(bulletRay[count]);

                            //bullets intersect
                            if (temp.height >= 2 && temp.width >= 2) //if(square.intersects(bulletRay[count]))
                            {
                                System.out.println(bulletRay[count]);
                                System.out.println(square);
                                System.out.println("BULLET HIT WALL");
                                System.out.println("BULLET PWR: " + bulletRay[count].damage);

                                square.hp -= bulletRay[count].damage;
                                System.out.println("Square hp = " + square.hp);
                                bulletRay[count].explode();
                                if (bulletRay[count].ready);
                                {
                                    bulletsReady++;
                                    //System.out.println("Readying rocket number: " + i);
                                    //System.out.println("[UPDATED]Rockets ready: " + rocketsReady );
                                }

                                if (square.hp <= 0) {
                                    square.broken = true;
                                    square.wall = false; // not a wall anymore
                                    //System.out.println("===BROKE WALL===");
                                }

                                return true;

                            }
                        }
                    }
                }

            }
        }
        return false;
    }

    public void refillAmmo() {
        System.out.println("Refill ammo");

        rocketsReady += 2;
        if (rocketsReady > rocketLimit) {
            rocketsReady = rocketLimit;

        }
        System.out.println("Rockets Ready" + rocketsReady);
        currentRockets = rocketsReady;
        System.out.println("Current Rockets" + currentRockets);
    }

    public void activateShield() {
        hasShield = true;
        shieldHp = MAX_SHIELD_HP;
    }

    public void refillHP() {
        hp += 2 * rocketRay[0].damage;
        currentHearts += 2; // heal 2 hearts
        if (hp > maxHP) {
            hp = maxHP;
        }
        if (currentHearts > maxHearts) {
            currentHearts = maxHearts;
        }

    }

    public void refillSpeedUp() {
        speedUpDistanceLeft = maxSpeedDistance;
    }

    public void allowSpeedUP() {
        System.out.println("Allow speed up");
        allowSpeedUp = true;
    }

    public void activateSpeedUp() {
        System.out.println("Activate Speed up");
        if (speedUpDistanceLeft <= 0) {
            speedUpDistanceLeft = maxSpeedDistance;
        }
        isSpeeding = true;
    }

    public void pauseSpeedUp() {
        System.out.println("Pausing speed up");
        isSpeeding = false;

    }

    public void draw(Graphics g, Component c) {
        //draw all player rockets
        for (int i = 0; i < rocketLimit; i++) {
            rocketRay[i].draw(g, c);
        }

        //draw all player bullets
        for (int i = 0; i < bulletLimit; i++) {
            bulletRay[i].draw(g, c);
        }
        //draw player
        super.draw(g, c);

        //draw shield
        if (hasShield) {
            g.drawImage(shield.getImage(), x - 9, y - 11, shield.getIconWidth(), shield.getIconHeight(), c);
        }

        if (!dead) {
            blewUp = false;
            //draw the player's life
            for (int i = 0; i < currentHearts; i++) {
                hearts[i].draw(g, c, (x + (statIconSize * i)) - 5, y - (statIconSize * 2) - 5);
            }

            //draw the player's ammo
            for (int i = 0; i < currentRockets; i++) {
                rockets[i].draw(g, c, (x + (statIconSize * i)) - 5, y - statIconSize - 5);
            }

        }
        if (blewUp && dead) {
            g.drawImage(explosionPic.getImage(), x - 9, y - 11, explosionPic.getIconWidth(), explosionPic.getIconHeight(), c);
        }

    }

    public void update() {
        //update player
        super.update();

        //update all player rockets
        for (int i = 0; i < rocketLimit; i++) {
            rocketRay[i].update();

            if (rocketRay[i].distanceTraveled >= MAX_DISTANCE * 2) //rockets go further than bullets
            {
                rocketRay[i].explode();
                if (rocketRay[i].ready);
                {
                    //rocketsReady ++;
                    //System.out.println("Readying rocket number: " + i);
                    //System.out.println("[UPDATED]Rockets ready: " + rocketsReady );
                }
            }
        }

        //update all player bullets
        for (int i = 0; i < bulletLimit; i++) {
            bulletRay[i].update();

            if (bulletRay[i].distanceTraveled >= MAX_DISTANCE) {
                bulletRay[i].explode();
                if (bulletRay[i].ready);
                {
                    bulletsReady++;
                    //System.out.println("Readying rocket number: " + i);
                    //System.out.println("[UPDATED]Rockets ready: " + rocketsReady );
                }
            }
        }

        //update player speed up distance
        //if user has distance to travel and is speeding
        if (speedUpDistanceLeft > 0 && isSpeeding) {
            System.out.println("Distance left: " + speedUpDistanceLeft);
            speedUpDistanceLeft -= 1;

        }
        //user ran out of distance and is speeding
        if (speedUpDistanceLeft <= 0 && isSpeeding) {
            allowSpeedUp = false;
            isSpeeding = false;
            System.out.println("Disable Speed up");
        }

    }

    public void blowUp() {
        blewUp = true;
        explosionPic = new ImageIcon(getClass().getResource("/data/tank_explosion.gif"));
        explosionPic.getImage().flush();
        picture = new ImageIcon(getClass().getResource("/data/tank_dead.png"));
    }

}
