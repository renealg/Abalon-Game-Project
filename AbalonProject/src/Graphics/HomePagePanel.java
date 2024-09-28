package Graphics;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import Logic.Game;

public class HomePagePanel extends JPanel implements ActionListener
{
	private JButton _play, _rules, _playcomp;
	private ButtonListener _listener;
	private Img _homepage, _playpic, _howtoplay, _playVScomputer;
	private Game _game;
	//private boolean rules = false;
	
	//Description- constructive action that creates the home page
	//Input- object type Game
	//Output- there is no output
	public HomePagePanel(Game game) {
		super();
		
		_game = game;
		_homepage = new Img("Images\\homepage.png", 0, 0, 1400, 700);
		_playpic = new Img("Images\\playalone.png", 220, 70, 150, 150);
		_howtoplay = new Img("Images\\howtoplayabalone.jpg", 950, 70, 230, 150);
		_playVScomputer = new Img("Images\\playcomp.png", 220, 235, 150, 150);
		
		_play = new JButton();
		
		_play.setOpaque(false);
		_play.setContentAreaFilled(false);
		_play.setBorderPainted(false);
		
		setLayout(null);
		_play.setBounds(220, 70, 150, 150);
		
		this.add(_play);
		_play.addActionListener(this);
		
		_rules = new JButton();
		
		_rules.setOpaque(false);
		_rules.setContentAreaFilled(false);
		_rules.setBorderPainted(false);
		
		setLayout(null);
		_rules.setBounds(950, 70, 230, 150);
		
		this.add(_rules);
		_rules.addActionListener(this);
		
		_playcomp = new JButton();
		
		_playcomp.setOpaque(false);
		_playcomp.setContentAreaFilled(false);
		_playcomp.setBorderPainted(false);
		
		setLayout(null);
		_playcomp.setBounds(220, 235, 150, 150);
		
		this.add(_playcomp);
		_playcomp.addActionListener(this);
		
		this.setSize(1400, 700);
		this.setVisible(true);	
	}

	public ButtonListener get_listener() {
		return _listener;
	}

	public void set_listener(ButtonListener _listener) {
		this._listener = _listener;
	}
	
	// switch from the home page to the game board or to the rules page
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource() == _play) {
			_listener.switchPanels(1);
			_game._alone = true;
			_game._computer = false;
		}	
		else if(arg0.getSource() == _rules) {
			_listener.switchPanels(2);
		}
		else if(arg0.getSource() == _playcomp) {
			_listener.switchPanels(1);
			_game._alone = false;
			_game._computer = true;
		}
	}
	
	// paint the images
	@Override
	public void paintComponent(Graphics g) {
		if(_homepage != null) {
			_homepage.drawImg(g);
		}
		if(_playpic != null) {
			_playpic.drawImg(g);
		}
		if(_howtoplay != null) {
			_howtoplay.drawImg(g);
		}
		if(_playVScomputer != null) {
			_playVScomputer.drawImg(g);
		}
	}
}
