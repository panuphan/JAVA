package Paradigms;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

class WinterFrame extends JFrame implements KeyListener {

    private JLabel contentpane;
    private MyToggleLabel tgLabel;
    private int frameWidth = 900, frameHeight = 650;

    public WinterFrame() {
        setTitle("Winter is coming");
        setBounds(50, 50, frameWidth, frameHeight);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // set background image by using JLabel as contentpane
        // Set layout to null because we'll move the label manually
        setContentPane(contentpane = new JLabel());
        MyImageIcon background = new MyImageIcon("proposition/Paradigms/EX7/winterscene.jpg");
        contentpane.setIcon(background.resize(contentpane.getWidth(), contentpane.getHeight()));
        contentpane.setLayout(null);
        addKeyListener(this);
        tgLabel = new MyToggleLabel();
        contentpane.add(tgLabel);
        repaint();
    }

    public static void main(String[] args) {
        new WinterFrame();
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {tgLabel.keyPressed(e);}

    public void keyReleased(KeyEvent e) {}
}

class MyToggleLabel extends JLabel implements KeyListener, MouseListener {

    private int curX = 250, curY = 300;
    private int width = 300, height = 300;
    private MyImageIcon wolfIcon, dragonIcon;
    private boolean dragon = false;

    public MyToggleLabel() {
        wolfIcon = new MyImageIcon("proposition/Paradigms/EX7/wolf.png").resize(width, height);
        dragonIcon = new MyImageIcon("proposition/Paradigms/EX7/dragon.png").resize(width, height);
        setHorizontalAlignment(JLabel.CENTER);
        setIcon(wolfIcon);
        setBounds(curX, curY, width, height);
        addMouseListener(this);
        addKeyListener(this);

    }

    public void updateLocation() {
        // update and check curX, curY
        int frameWidth = 900, frameHeight = 650;
        
        if (curX + width < 0) {
            curX = frameWidth;
        } else if (curX > frameWidth) {
            curX = 0 - width;
        }
        if (curY < 0) {
            curY = 0;
        } else if (curY + height > frameHeight) {
            curY = frameHeight - height;
        }
        setLocation(curX, curY);
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        if (dragon) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                dragon = false;
                setIcon(wolfIcon);
                curY = 300;
                updateLocation();
            }

            if (e.getKeyCode() == KeyEvent.VK_UP) {
                Random r = new Random();
                int rand = r.nextInt(200 - 30) + 30;
                curY -= rand;
                updateLocation();
            }

            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                Random r = new Random();
                int rand = r.nextInt(200 - 30) + 30;
                curY += rand;
                updateLocation();
            }

        } else {

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                curX -= 50;
                updateLocation();
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                curX += 50;
                updateLocation();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            if (!dragon) {
                dragon = true;
                setIcon(dragonIcon);
            }

            Random r = new Random();
            int rand = r.nextInt(200 - 30) + 30;
            curY -= rand;
            updateLocation();

        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        if (dragon) {
            if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                Random r = new Random();
                int rand = r.nextInt(200 - 30) + 30;
                curY -= rand;
                updateLocation();
            }
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}

// auxiliary class to resize image
class MyImageIcon extends ImageIcon {

    public MyImageIcon(String fname) {
        super(fname);
    }

    public MyImageIcon(Image image) {
        super(image);
    }

    public MyImageIcon resize(int width, int height) {
        Image oldimg = this.getImage();
        Image newimg = oldimg.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new MyImageIcon(newimg);
    }
};
