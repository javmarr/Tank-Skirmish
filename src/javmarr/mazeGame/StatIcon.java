package javmarr.mazeGame;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class StatIcon {

    ImageIcon picture;
    int x;
    int y;
    int width;
    int height;

    StatIcon(ImageIcon icon) {
        picture = icon;
        width = picture.getIconWidth();
        height = picture.getIconHeight();
    }

    public void draw(Graphics g, Component c, int theX, int theY) {
        x = theX;
        y = theY;

        g.drawImage(picture.getImage(), x, y, picture.getIconWidth(), picture.getIconHeight(), c);
    }

}
