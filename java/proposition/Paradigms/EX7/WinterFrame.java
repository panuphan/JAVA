import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class WinterFrame extends JFrame 
{
	private JLabel          contentpane;
	private MyToggleLabel   tgLabel;
	private int frameWidth = 900, frameHeight = 650;

	public WinterFrame()
	{
		setTitle("Winter is coming");
		setBounds(50, 50, frameWidth, frameHeight);
		setVisible(true);
		setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );

		// set background image by using JLabel as contentpane
		// Set layout to null because we'll move the label manually
		setContentPane(contentpane = new JLabel());
		MyImageIcon background = new MyImageIcon("winterscene.jpg");
		contentpane.setIcon( background.resize(contentpane.getWidth(),
			                                   contentpane.getHeight()) );
		contentpane.setLayout( null );

		tgLabel = new MyToggleLabel();
		contentpane.add(tgLabel);
		repaint();
	}
	public static void main(String[] args) 
	{
		new WinterFrame();
	}	
}


class MyToggleLabel extends JLabel //implements KeyListener, MouseListener
{
	private int curX  = 250, curY   = 300;
	private int width = 300, height = 300;
	private MyImageIcon wolfIcon, dragonIcon;
	private boolean dragon = false;

	public MyToggleLabel()				
	{ 
		wolfIcon   = new MyImageIcon("wolf.png").resize(width, height);
		dragonIcon = new MyImageIcon("dragon.png").resize(width, height);
		setHorizontalAlignment(JLabel.CENTER);
		setIcon(wolfIcon);
		setBounds(curX, curY, width, height);	
	}

	public void updateLocation()
	{
		// update and check curX, curY
	}
}


// auxiliary class to resize image
class MyImageIcon extends ImageIcon
{
	public MyImageIcon(String fname)  { super(fname); }
	public MyImageIcon(Image image)   { super(image); }

	public MyImageIcon resize(int width, int height)
	{
		Image oldimg = this.getImage();
		Image newimg = oldimg.getScaledInstance(width, height,
			                                    java.awt.Image.SCALE_SMOOTH);
		return new MyImageIcon(newimg);
	}
};