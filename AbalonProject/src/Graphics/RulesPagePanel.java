package Graphics;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class RulesPagePanel extends JPanel implements ActionListener
{
	private JButton _back;
	private ButtonListener _listener;
	private Img _rules, _goback;

	//Description- constructive action that creates the rules page
	//Input- there is no input
	//Output- there is no output
	public RulesPagePanel() {
		super();
		
		_rules = new Img("Images\\rules.png", 0, 0, 1400, 700);
		_goback = new Img("Images\\back.png", 0, 0, 200, 100);
		
		_back = new JButton();
		
		_back.setOpaque(false);
		_back.setContentAreaFilled(false);
		_back.setBorderPainted(false);
		
		setLayout(null);
		_back.setBounds(0, 0, 200, 100);
		
		this.add(_back);
		_back.addActionListener(this);
		
		this.setSize(1400, 700);
		this.setVisible(true);	
	}
	
	public ButtonListener get_listener() {
		return _listener;
	}

	public void set_listener(ButtonListener _listener) {
		this._listener = _listener;
	}
	
	// switch from the rules page to home page
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==_back) {
			_listener.switchPanels(3);
		}
	}

	// paint the images
	@Override
	public void paintComponent(Graphics g) {
		if(_rules != null) {
			_rules.drawImg(g);
		}
		if(_goback != null) {
			_goback.drawImg(g);
		}
	}
	
}
