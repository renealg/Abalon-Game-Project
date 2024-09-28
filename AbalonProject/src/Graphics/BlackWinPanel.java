package Graphics;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BlackWinPanel extends JPanel implements ActionListener 
{
	private JButton _homepage;
	private ButtonListener _listener;
	private Img _background, _backhome;
	
	//Description- constructive action that creates the black win page
	//Input- there is no input
	//Output- there is no output
	public BlackWinPanel() {
		super();
		
		_background = new Img("Images\\blackWin.png", 0, 0, 1400, 700);
		_backhome = new Img("Images\\backHomePage.png", 0, 500, 200, 200);
		
		_homepage = new JButton();
		
		_homepage.setOpaque(false);
		_homepage.setContentAreaFilled(false);
		_homepage.setBorderPainted(false);
		
		setLayout(null);
		_homepage.setBounds(0, 500, 200, 200);
		
		this.add(_homepage);
		_homepage.addActionListener(this);
		
		this.setSize(1400, 700);
		this.setVisible(true);
	}
	
	public ButtonListener get_listener() {
		return _listener;
	}

	public void set_listener(ButtonListener _listener) {
		this._listener = _listener;
	}
	
	// switch from the black win page page to home page
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==_homepage) {
			_listener.switchPanels(7);
		}	
	}
	
	// paint the images
	@Override
	public void paintComponent(Graphics g) {
		if(_background != null) {
			_background.drawImg(g);
		}
		if(_backhome != null) {
			_backhome.drawImg(g);
		}
	}	
}
