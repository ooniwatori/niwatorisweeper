import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class Tile extends JButton {
	private int i;
	private int j;
	private boolean isBomb = false;
	private int bombNum = 0;
	private ImageIcon icon;
	private boolean mark = false;
	private boolean opened = false;
	private static int totalBomb = 0;
	private static JLabel markLabel = new JLabel("");
	private static int remain;
	public Tile() {
		
	}
	public Tile(int i, int j) {
		super();
		this.addActionListener(new btnEvent());
		this.addMouseListener(new btnEvent2());
		this.i = i;
		this.j = j;
	}
	public void open() {
		
		opened = true;
		if(isBomb) {
			icon = new ImageIcon(this.getClass().getResource("bomb.png"));
		}
		else {
			if(remain < 0) {
				JButton okButton = new JButton("OK");
				JDialog dialog = new JDialog();
				dialog.setTitle("You win");
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
			switch(bombNum) {
			case 1:
				icon = new ImageIcon(this.getClass().getResource("1.png"));
				break;
			case 2:
				icon = new ImageIcon(this.getClass().getResource("2.png"));
				break;
			case 3:
				icon = new ImageIcon(this.getClass().getResource("3.png"));
				break;
			case 4:
				icon = new ImageIcon(this.getClass().getResource("4.png"));
				break;
			case 5:
				icon = new ImageIcon(this.getClass().getResource("5.png"));
				break;
			case 6:
				icon = new ImageIcon(this.getClass().getResource("6.png"));
				break;
			case 7:
				icon = new ImageIcon(this.getClass().getResource("7.png"));
				break;
			case 8:
				icon = new ImageIcon(this.getClass().getResource("8.png"));
				break;
			default:
				icon = new ImageIcon("");
				break;
			}
		}
		this.setEnabled(false);
		this.setIcon(icon);
		this.setDisabledIcon(icon);
		
		
	}
	public void setMark() {
		if(!mark && totalBomb>0) {
			totalBomb--;
			mark = true;
			icon = new ImageIcon(this.getClass().getResource("mark.png"));
			this.setIcon(icon);
		}
		else if(mark){
			totalBomb++;
			mark = false;
			this.setIcon(null);
		}
		markLabel.setText(Integer.toString(totalBomb));
	}
	public class btnEvent implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event) {
			if(!mark) {
				remain--;
				open();
			}
		}
	}
	public class btnEvent2 extends MouseAdapter{
		@Override
		public void mousePressed(MouseEvent event) {
			if(event.getButton() == MouseEvent.BUTTON3 && !opened) {
				setMark();
			}
		}
	}
	
	public void autoClick() {
		this.doClick(0);
	}
	
	public void setbombNum(int n) {
		
		bombNum = n;
	}
	public void setbomb() {
		isBomb = true;
	}
	public int getbombNum() {
		return bombNum;
	}
	public boolean getBomb() {
		return isBomb;
	}
	public int getI() {
		return i;
	}
	public int getJ() {
		return j;
	}
	public boolean isOpened() {
		return opened;
	}
	public boolean getMarked() {
		return mark;
	}
	public void setTotalBomb(int totalBomb) {
		this.totalBomb = totalBomb;
	}
	public int getTotalBomb() {
		return totalBomb;
	}
	public static JLabel getMarkLabel() {
		return markLabel;
	}
	
	public void setT(String s) {
		markLabel.setText(s);
	}
	public void setF(Font f) {
		markLabel.setFont(f);
	}
	public void setB(Color c) {
		markLabel.setBackground(c);
	}
	public void setFore(Color c) {
		markLabel.setForeground(c);
	}
	public void setHA(int alig) {
		markLabel.setHorizontalAlignment(alig);
	}
	public void setBou(int x, int y, int w, int h) {
		markLabel.setBounds(x, y, w, h);
	}
	public void setRemain(int r) {
		remain = r;
	}
	public int remain() {
		return remain;
	}
}
