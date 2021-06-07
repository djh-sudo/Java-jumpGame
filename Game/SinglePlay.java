import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Random;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;





@SuppressWarnings("serial")
public class SinglePlay extends JPanel implements ActionListener{
	//
	long timeInterval = 0;
	int timerPeopleCount = 30;
	int timeFlashCount = 20;
	Timer timer = new Timer(13,this);
	Timer timerFlash = new Timer(timeFlashCount,this);
	Timer timerPeople = new Timer(timerPeopleCount,this);
	Timer timeCount = new Timer(1000,this);
	int totalTime = 500;
	int toalFlashTime = 500;

	boolean Start = false;
	boolean peopleJumpOver = false;
	int jumpNumber = 0;
	int lifeNumber = 5;
	boolean waiting = false;
	Random random = new Random();
	int cnt = 0;
	boolean lastPillar = false;
	boolean gameFail = false;
	int liftTime = 60;
	long  useTime = 0;
	long score = 0;
	boolean outImageIsSet = false;
	static final int WINDOW_HEIGHT = 713;
	static final int WINDOW_WIDTH = 1014;
	int xInLand;
	PolyGenerator generator = new PolyGenerator(0,0,0,0);

	ImageIcon leftImageIcon = new ImageIcon(Data.pillar);
	ImageIcon rightImageIcon = new ImageIcon(Data.pillar);
	ImageIcon outImageIcon = new ImageIcon(Data.pillar);
	ImageIcon peoplIcon = new ImageIcon(Data.down);
	ImageIcon successfullyIcon = new ImageIcon(Data.successfully);
	ImageIcon failIcon = new ImageIcon(Data.fail);
	ImageIcon background = new ImageIcon(Data.backgroundSinglePlay);
	Vector<URL>backGroundIcons = new Vector<URL>();
	int xLeft = 25;
	int yLeft = 274;
	int xRight = 450;
	int yRight = 274;

	int xRightTemp = 450;
	int xLeftTemp = 25;

	int xout = 800;
	int yout = 274;

	int leftWidth = 82;
	int rightWidth = 82;
	int outWidth = 82;

	int leftHeight = 257;
	int rightHeight = 257;
	int outHeight = 257;

	int delta = (xRight - xLeft)/(totalTime/timerPeopleCount);

	int xPeople = 0;
	int yPeople = 0;

	private Pillar[] pillars = {};
	public SinglePlay() {
		// TODO Auto-generated constructor stub
		backGroundIcons.clear();
		backGroundIcons.add(Data.backgroundSinglePlay);
		backGroundIcons.add(Data.backgrounfDualPlay1);
		backGroundIcons.add(Data.backgrounfDualPlay2);
		backGroundIcons.add(Data.backgrounfDualPlay3);
		backGroundIcons.add(Data.backgrounfDualPlay4);
		backGroundIcons.add(Data.backgrounfDualPlay5);
		backGroundIcons.add(Data.backgrounfDualPlay6);
		backGroundIcons.add(Data.backgrounfDualPlay7);
		backGroundIcons.add(Data.backgrounfDualPlay8);
		backGroundIcons.add(Data.backgrounfDualPlay9);
		backGroundIcons.add(Data.backgrounfDualPlay10);
		backGroundIcons.add(Data.backgrounfDualPlay11);
		backGroundIcons.add(Data.backgrounfDualPlay12);
		backGroundIcons.add(Data.backgrounfDualPlay13);
		backGroundIcons.add(Data.backgrounfDualPlay14);
		backGroundIcons.add(Data.backgrounfDualPlay15);
	}
	void Inicial() {
		for(int i=1;i<=5;i++) {
			Component LB = getLife(i);
			if(LB!=null && LB.getClass() == JButton.class) {
				((JButton)LB).setVisible(true);
			}
		}
		int index = random.nextInt(16);
		background = new ImageIcon(backGroundIcons.get(index));
		generator.setParameter(0,0,0,0);
		Component BT = this.getBtn();
		((JButton) BT).setBorder(null);
		((JButton) BT).setOpaque(false);
		Start = false;
		waiting = false;
		lastPillar = false;
		cnt = 0;
		score = 0;
		useTime = 0;
		peopleJumpOver = false;
		outImageIsSet = false;
		lifeNumber = 5;
		gameFail = false;
		this.repaint();
		timeCount.start();

		Component LBT = this.getLableTime();
		if(LBT != null) {
			((JLabel)LBT).setText("Time:" + useTime + "second!");
		}

		Component LBS = this.getLableScore();
		if(LBS != null) {
			((JLabel)LBS).setText("SCORE:" + score);
		}
	}

	public void SinglePillarSetting(Pillar[] mypillars) {
		this.pillars= mypillars.clone();
		xLeftTemp = xLeft = 25; 
		xRightTemp = xRight = xLeft + pillars[0].getD();
		yLeft = WINDOW_HEIGHT - pillars[0].getHeight();
		yRight = WINDOW_HEIGHT - pillars[1].getHeight();
		xout = xRight + pillars[1].getD();
		yout = WINDOW_HEIGHT - pillars[2].getHeight();
		leftWidth = pillars[0].getWidth();
		rightWidth = pillars[1].getWidth();
		outWidth = pillars[2].getWidth();
		leftHeight = pillars[0].getHeight();
		rightHeight = pillars[1].getHeight();
		outHeight = pillars[2].getHeight();
		delta = (xRight - xLeft)/(totalTime/timeFlashCount);//每次移动的像素	

		xPeople = xLeft + leftWidth/2;
		yPeople = yLeft;
	}

	public Component getProgressBar(){
		for(Component Com: this.getComponents()) {
			if(Com.getClass() == JProgressBar.class) {
				return Com;
			}
		}
		return null;
	}

	public Component getBtn() {
		for(Component Com:this.getComponents()) {
			if(Com.getClass() == JButton.class) {
				return Com;
			}
		}
		return null;
	}

	public Component getLableTime() {
		for(Component Com:this.getComponents()) {
			if(Com.getClass() == JLabel.class && Com.getName().equals("useTime")) {
				return Com;
			}
		}
		return null;
	}

	public Component getLableScore() {
		for(Component Com:this.getComponents()) {
			if(Com.getClass() == JLabel.class && Com.getName().equals("score")) {
				return Com;
			}
		}
		return null;
	}

	public boolean getGameIsOver() {
		return this.lastPillar;
	}
	public void setFlag() {
		if(lastPillar == false) {
			this.Start = true;
			timer.start();
		}
		else {
			waiting = true;
			this.repaint();
		}
	}

	public void resetFlag() {
		this.Start = false;
		Component PB = getProgressBar();
		if(PB.getClass() == JProgressBar.class) {
			((JProgressBar)PB).setValue(0);
		}
		timer.stop();
		this.repaint();
		timerPeople.start();
		peopleJumpOver = false;
		waiting = true;
		
	}
	public boolean getState() {
		return this.waiting;
	}

	public void setPillarNumber(int pillarNumber) {
		this.jumpNumber = pillarNumber - 1;
		liftTime = 60 + (pillarNumber-5)*10;
	}
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		BasicStroke bs = new BasicStroke(6);

		g2d.setStroke(bs);

		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		background.paintIcon(this, g2d, 10, 10);

		if(lastPillar == true && gameFail == false) {
			successfullyIcon.paintIcon(this, g2d, 300, 100);
			g2d.dispose();
			return;
		}
		if(gameFail) {
			failIcon.paintIcon(this, g2d, 200, 100);
			g2d.dispose();
			return;
		}
		if(xLeft>=-leftWidth) {
			if(!timerFlash.isRunning()) {
				leftImageIcon = new ImageIcon(leftImageIcon.getImage().getScaledInstance(leftWidth, leftHeight, 1));
			}
			leftImageIcon.paintIcon(this, g2d, xLeft, yLeft);
		}

		if((xRight >= xLeftTemp)) {
			if(!timerFlash.isRunning()) {
				rightImageIcon = new ImageIcon(rightImageIcon.getImage().getScaledInstance(rightWidth, rightHeight, 1));
			}rightImageIcon.paintIcon(this, g2d,xRight, yRight);

			if((peopleJumpOver == true) && !(timerPeople.isRunning())) {
				if(xPeople<WINDOW_WIDTH)
					peoplIcon.paintIcon(this, g2d, xRight+rightWidth/2, yRight-35);
			}
		}
		else{
			if(!timerFlash.isRunning()) {
				rightImageIcon = new ImageIcon(rightImageIcon.getImage().getScaledInstance(rightWidth, rightHeight, 1));
			}
			rightImageIcon.paintIcon(this, g2d,xLeftTemp, yRight);
		}
		if((xout + outWidth) <WINDOW_WIDTH) {
			if(outImageIsSet == false) {
				outImageIcon = new ImageIcon(outImageIcon.getImage().getScaledInstance(outWidth, outHeight, 1));
				outImageIsSet = true;
			}
			outImageIcon.paintIcon(this, g2d,xout, yout);
		}
		if(peopleJumpOver == false || timerPeople.isRunning() == true) {	
			if(xPeople<WINDOW_WIDTH)
				peoplIcon.paintIcon(this, g2d, xPeople,yPeople-35);
		}
		g2d.dispose();
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		// TODO Auto-generated method stub
		if(e.getSource().equals(timer)) {
			Component PB = getProgressBar();
			if(PB.getClass() == JProgressBar.class) {
				int v = ((JProgressBar)PB).getValue();
				((JProgressBar)PB).setValue(v+1);
			}
		}
		if(e.getSource().equals(timeCount)) {
			useTime++;
			Component LBT = this.getLableTime();
			if(LBT != null) {
				((JLabel)LBT).setText("Time:" + (liftTime - useTime) + "second!");
			}
		}
		if(e.getSource().equals(timerFlash)) {
			xRight -= delta;
			xLeft -= delta;
			xout -= delta;
			repaint(10,10,1014,713);


			if(xLeftTemp>=xRight) {
				waiting = false;
				timerFlash.stop();
				outImageIsSet = false;
				peopleJumpOver = false;
				leftHeight = rightHeight;
				leftWidth = rightWidth;
				xLeft = 30;
				yLeft = yRight;
				xRight = xout;
				rightHeight = outHeight;
				rightWidth = outWidth;
				yRight = yout;
				xRightTemp = xRight;
				xLeftTemp = xLeft;
				delta = (xRightTemp - xLeftTemp)/(totalTime/timeFlashCount);

				xPeople = xLeft +leftWidth/2;
				yPeople = yLeft;

			}
		}
		if(e.getSource().equals(timerPeople)) {
			delta = (xRightTemp - xLeftTemp)/(toalFlashTime/timerPeopleCount);
			xPeople += delta;

			yPeople = (int) generator.Calculate(xPeople);
			xInLand = (int) generator.Solve(yRight);
			this.repaint();
			
			if(yPeople>WINDOW_HEIGHT || xPeople>WINDOW_WIDTH) {
				xPeople = xLeftTemp + leftWidth/2;
				yPeople = yLeft;
				timerPeople.stop();
				waiting = false;
				lifeNumber--;
				checkGame();
			}
			if(xPeople>=xInLand && xInLand>xLeft && xInLand <= (xLeft+leftWidth)) {//跳到本柱子
				xPeople = xLeftTemp + leftWidth/2;
				yPeople = yLeft;
				timerPeople.stop();
				waiting = false;

			}else if(xPeople<WINDOW_WIDTH && xPeople>=xInLand && yPeople>=WINDOW_HEIGHT && (xInLand > (xRight + rightWidth))) {//跳出界面
				xPeople = xLeftTemp + leftWidth/2;
				yPeople = yLeft;
				timerPeople.stop();
				waiting = false;
				lifeNumber--;
				checkGame();

				//failed to jump to another pillar![right]
			}else if(xPeople>xInLand &&(xInLand<xRight && xInLand>xLeft)) {//跳在中间
				if(yPeople>WINDOW_HEIGHT) {
					xPeople = xLeftTemp + leftWidth/2;
					yPeople = yLeft;
					timerPeople.stop();
					waiting = false;
					lifeNumber--;
					checkGame();
				}
				else {}
				// fail to jump to another pillat [left]
			}else if(((xPeople>xRight-25 && xPeople<xRight+rightWidth) && (yPeople<yRight+36 && yPeople>yRight-36))) {
				MusicThread mThread = new MusicThread(Data.jumpOkMusic.getPath(), 0);
				mThread.start();
				timerPeople.stop();
				peopleJumpOver = true;
				cnt++;
				Component LBS = this.getLableScore();
				if(LBS != null) {
					int index = random.nextInt(10);
					score += (10 + index);
					if(index>=4) {
						if(lifeNumber<5) {
							Component LB = getLife(5-lifeNumber);
							lifeNumber++;
							if(LB != null && LB.getClass() == JButton.class) {
								((JButton)LB).setVisible(true);
							}
						}
					}
					((JLabel)LBS).setText("SCORE:" + score);
				}
				if(jumpNumber-1 == cnt) {
					lastPillar = true;//不再产生柱子
					timeCount.stop();
					//add your code...
				}
				if(lastPillar == false) {
					timerFlash.start();
					xout = xRight + pillars[cnt+1].getD(); //最外侧柱子
					outHeight = pillars[cnt+2].getHeight();
					outWidth = pillars[cnt+2].getWidth();
					yout = WINDOW_HEIGHT - pillars[cnt+2].getHeight();

				}

			}
		}
	}
	public void setTimeInterval(long timerInterval) {
		this.timeInterval = timerInterval;
		double ratio = timeInterval>=1500?1:timeInterval/1500.0;
		int ymax = -10;
		int xmax = (yLeft-ymax) + xLeft;
		int Ypoly = (int) (yLeft - ratio*(yLeft-ymax));
		int Xpoly = (int) (xLeft + ratio*(xmax-xLeft));
		generator.setParameter(xLeftTemp+leftWidth/2,yLeft,Xpoly,Ypoly);

	}
	void checkGame() {
		MusicThread mThread = new MusicThread(Data.jumpFailMusic.getPath(), 0);
		mThread.start();
		Component LB = getLife(5-lifeNumber);
		if(LB != null && LB.getClass() == JButton.class) {
			((JButton)LB).setVisible(false);
		}
		if(lifeNumber<=0 || useTime>liftTime) {
			lastPillar = true;
			if(jumpNumber-1 != cnt) {
				gameFail = true;
				timeCount.stop();
			}
		}
	}
	Component getLife(int i) {
		for(Component Com:this.getComponents()) {
			if(Com.getName()!=null && Com.getClass() == JButton.class && Com.getName().equals("SingleLife" + String.valueOf(i))) {
				return Com;
			}
		}
		return null;
	}
}
