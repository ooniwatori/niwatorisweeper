import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.SecureRandom;

public class Application {

	private JFrame frame;
	private Tile[][] base;
	private int x;
	private int y;
	private int firstI;
	private int firstJ;
	private boolean ini = false;
	private int bombNum = 30;
	private JMenu gameMenu = new JMenu("Game");
	private JMenuBar menuBar = new JMenuBar();
	private JMenuItem newGameMenuItem = new JMenuItem("New game");
	private JMenu levelNewMenu = new JMenu("Level");
	private JMenuItem hardMenuItem = new JMenuItem("Hard");
	private JMenuItem mediumMenuItem = new JMenuItem("Medium");
	private JMenuItem easyMenuItem = new JMenuItem("Easy");
	private JMenu settingMenu = new JMenu("Setting");
	private JMenuItem customMenuItem = new JMenuItem("Custom");
	private JPanel panel_1 = new JPanel();
	private JPanel panel = new JPanel();
	private JLabel bombNumLabel = new JLabel();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Application() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		x = 15;
		y = 20;
		base = new Tile[x][y];
		
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.activeCaption);
		frame.setBounds(100, 100, 700, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		

		panel.setBounds(46, 81, 600, 450);
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(x,1,0,0));
		
		for(int i = 0; i < x; ++i) {
			for(int j = 0; j < y; ++j) {
				base[i][j] = new Tile(i, j);
				base[i][j].addActionListener(new openEvent());
				base[i][j].addMouseListener(new mouseOpen());
				panel.add(base[i][j]);
			}
		}
		base[0][0].setTotalBomb(bombNum);
		base[0][0].setRemain(x*y - bombNum);

		
		
		menuBar.setBounds(0, 0, 684, 22);
		frame.getContentPane().add(menuBar);
		
		gameMenu.add(newGameMenuItem);
		gameMenu.add(levelNewMenu);
		
		newGameMenuItem.addActionListener(new menuEvent());
		hardMenuItem.addActionListener(new menuEvent());
		mediumMenuItem.addActionListener(new menuEvent());
		easyMenuItem.addActionListener(new menuEvent());
		customMenuItem.addActionListener(new menuEvent());
		levelNewMenu.add(hardMenuItem);	
		levelNewMenu.add(mediumMenuItem);
		levelNewMenu.add(easyMenuItem);
		
		menuBar.add(gameMenu);
		menuBar.add(settingMenu);
		
		settingMenu.add(customMenuItem);
		
		panel_1.setBackground(SystemColor.control);
		panel_1.setLayout(null);
		panel_1.setBounds(10, 32, 664, 39);
		frame.getContentPane().add(panel_1);
		base[0][0].setT(Integer.toString(bombNum));
		base[0][0].setF(new Font("PMingLiU", Font.PLAIN, 40));
		base[0][0].setB(Color.DARK_GRAY);
		base[0][0].setFore(Color.RED);
		base[0][0].setHA(SwingConstants.CENTER);
		base[0][0].setBou(489, 0, 102, 45);
		panel_1.add(base[0][0].getMarkLabel());
		
		ImageIcon icon = new ImageIcon(this.getClass().getResource("mark.png"));
		JLabel iconLabel = new JLabel(icon);
		iconLabel.setBounds(466, 0, 75, 39);
		panel_1.add(iconLabel);
	}
	public void detect(int i, int j) {
		if(!base[i][j].getBomb()) {
			int n = 0;
			for(int a = i - 1; a <= i + 1; ++a) {
				for(int b = j - 1; b <= j + 1; ++b) {
					try {
						if(base[a][b].getBomb())
							n++;
					}
					catch(Exception e) {
						
					}
				}
			}
			base[i][j].setbombNum(n);	
		}
		
	}
	public class mouseOpen extends MouseAdapter{
		@Override
		public void mousePressed(MouseEvent event) {
			if(event.getButton() == MouseEvent.BUTTON2) {
				Tile temp = (Tile)event.getSource();
				int n = temp.getbombNum();
				int i = temp.getI();
				int j = temp.getJ();
				if(base[i][j].getMarked() || !base[i][j].isOpened()) {
					return;
				}
				for(int a = i - 1; a <= i + 1; ++a) {
					for(int b = j - 1; b <= j + 1; ++b) {
						try {
							if(base[a][b].getMarked()) {
								n--;
								
							}
						}
						catch(Exception e) {
							
						}
					}
				}
				if(n == 0){
					for(int a = i - 1; a <= i + 1; ++a) {
						for(int b = j - 1; b <= j + 1; ++b) {
							try {
								if(!base[a][b].isOpened() && !base[a][b].getMarked()) {
									base[a][b].autoClick();
									
								}
							}
							catch(Exception e) {
								
							}
						}
					}
				}
			}
		}
	}
	
	
	
	public class openEvent implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event) {
			Tile temp = (Tile)event.getSource();
			int i = temp.getI();
			int j = temp.getJ();
			if(temp.getMarked()) {
				return;
			}
			else if(!ini) {
				ini = true;
				firstI = i;
				firstJ = j;
				setBomb();
				base[firstI][firstJ].autoClick();
				return;
			}
			else if(temp.getbombNum() == 0 && !temp.getBomb() && !base[i][j].getMarked()) {
				base[i][j].open();
				for(int a = i - 1; a <= i + 1; ++a) {
					for(int b = j - 1; b <= j + 1; ++b) {
						try {
							if(!base[a][b].isOpened()) {
								base[a][b].autoClick();
							}
						}
						catch(Exception e) {
							
						}
					}
				}
			}
			else if(temp.getBomb()) {
				for(int a = 0; a < x; ++a) {
					for(int b = 0; b < y; ++b) {
						base[a][b].open();
					}
				}
				JButton okButton = new JButton("OK");
				JDialog dialog = new JDialog(frame, "You lose", true);
				dialog.getContentPane().setLayout(null);
				dialog.getContentPane().add(okButton);
				dialog.setSize(200, 150);
				okButton.setBounds(50, 60, 80, 30);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						dialog.dispose();
					}
				});
				dialog.setResizable(false);
				dialog.setVisible(true);
			} 
		}
	}
	public void setBomb() {
		SecureRandom sc = new SecureRandom();
		for(int i = 0; i < bombNum;) {
			int j = sc.nextInt(x);
			int k = sc.nextInt(y);
			if(!base[j][k].getBomb() && (Math.abs(j - firstI) > 2 || Math.abs(k - firstJ) > 2)) {
				base[j][k].setbomb();
				i++;
			}
		}
		for(int i = 0; i < x; ++i) {
			for(int j = 0; j < y; ++j) {
				detect(i,j);
			}
		}
	}
	public class menuEvent implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event) {
			JMenuItem temp = (JMenuItem)event.getSource();
			if(temp == newGameMenuItem) {
				reset(30);
			}
			else if(temp == easyMenuItem) {
				reset(30);
			}
			else if(temp == mediumMenuItem) {
				reset(50);
			}
			else if(temp == hardMenuItem) {
				reset(99);
			}
			else if(temp == customMenuItem) {
				JButton okButton = new JButton("OK");
				JDialog dialog = new JDialog(frame, "Custom", true);
				JSlider bombSlider = new JSlider(1, 150, 30);
				JLabel bombLabel = new JLabel("Bomb");
				bombNumLabel.setText(Integer.toString(bombNum));
				bombNumLabel.setBounds(150, 28, 50, 30);
				bombLabel.setBounds(5, 27, 40, 30);
				bombSlider.setBounds(40,30,100,30);
				bombSlider.addChangeListener(new sliderAction());
				
				dialog.getContentPane().setLayout(null);
				dialog.getContentPane().add(okButton);
				dialog.getContentPane().add(bombSlider);
				dialog.getContentPane().add(bombNumLabel);
				dialog.getContentPane().add(bombLabel);
				dialog.setSize(200, 150);
				okButton.setBounds(50, 60, 80, 30);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						reset(bombNum);
						dialog.dispose();
					}
				});
				dialog.setResizable(false);
				dialog.setVisible(true);
				
			}
		}
	}
	public void reset(int bn) {
		panel.removeAll();
		bombNum = bn;
		ini = false;
		base = new Tile[x][y];
		for(int i = 0; i < x; ++i) {
			for(int j = 0; j < y; ++j) {
				base[i][j] = new Tile(i, j);
				base[i][j].addActionListener(new openEvent());
				base[i][j].addMouseListener(new mouseOpen());
				panel.add(base[i][j]);
			}
		}
		base[0][0].setTotalBomb(bombNum);
		base[0][0].setRemain(x*y - bombNum);
		base[0][0].setT(Integer.toString(bombNum));
	}
	public class sliderAction implements ChangeListener{
		@Override
		public void stateChanged(ChangeEvent event) {
			bombNum = ((JSlider)event.getSource()).getValue();
			bombNumLabel.setText(Integer.toString(bombNum));
		}
	}
}
