import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class PlayWindow extends JPanel implements ActionListener{


	long timeInterval = 0;
	int timerPeopleCount = 30;
	int timeFlashCount = 20;
	Timer timerFlash = new Timer(timeFlashCount,this);
	Timer timerPeople = new Timer(timerPeopleCount,this);
	int totalTime = 500;
	int toalFlashTime = 500;
	boolean Start = false;
	boolean peopleJumpOver = false;
	int jumpNumber = 0;
	boolean waiting = false;
	Random random = new Random();
	int cnt = 0;
	boolean lastPillar = false;
	boolean outImageIsSet = false;
	long  useTime = 0;
	long score = 0;
	static final int WINDOW_HEIGHT = 357;
	static final int WINDOW_WIDTH = 1033;
	PolyGenerator generator = new PolyGenerator(0,0,0,0);
	int xInLand = 0;
	boolean gameOver = false;

	ImageIcon leftImageIcon = new ImageIcon(Data.pillar);
	ImageIcon rightImageIcon = new ImageIcon(Data.pillar);
	ImageIcon outImageIcon = new ImageIcon(Data.pillar);
	ImageIcon peoplIcon = new ImageIcon(Data.down);
	ImageIcon successfullyIcon = new ImageIcon(Data.successfully);
	ImageIcon background = new ImageIcon(Data.backgrounfDualPlay);
	ImageIcon failIcon = new ImageIcon(Data.fail);
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

	int delta = (xRight - xLeft)/(totalTime/10);//

	int xPeople = 0;
	int yPeople = 0;

	private Pillar[] pillars = {};

	void Inicial() {
		Start = false;
		waiting = false;
		lastPillar = false;
		cnt = 0;

		score = 0;

		useTime = 0;
		peopleJumpOver = false;
		outImageIsSet = false;
		gameOver = false;
		
		xInLand = 0;
		generator.setParameter(0, 0, 0, 0);
		this.repaint(10,10,1033,357);
	}

	public void SinglePillarSetting(Pillar[] mypillars) {
		this.pillars= mypillars.clone();
		xLeftTemp = xLeft = 25; xRightTemp = xRight = xLeft + pillars[0].getD();
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

	public class myThread extends Thread{
		private ActionEvent e;

		public myThread(ActionEvent e, String name) {
			super(name);
			this.e = e;
		}
		public void run() {
			if(e.getSource().equals(timerFlash)) {
				xRight -= delta;
				xLeft -= delta;
				xout -= delta;

				repaint();

				if(xLeftTemp>=xRight) {
					waiting = false;
					timerFlash.stop();
					peopleJumpOver = false;
					outImageIsSet = false;
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

				repaint();
				if(yPeople>WINDOW_HEIGHT) {
					xPeople = xLeftTemp + leftWidth/2;
					yPeople = yLeft;
					timerPeople.stop();
					waiting = false;
				}
				if(xPeople>=xInLand && xInLand>xLeft && xInLand <= (xLeft+leftWidth)) {//跳到本柱子
					xPeople = xLeftTemp + leftWidth/2;
					yPeople = yLeft;
					timerPeople.stop();
					waiting = false;

				}else if(xPeople<WINDOW_WIDTH && xPeople>=xInLand && (xInLand > (xRight + rightWidth))) {//跳出界面
					xPeople = xLeftTemp + leftWidth/2;
					yPeople = yLeft;
					timerPeople.stop();
					waiting = false;

					//failed to jump to another pillar![right]
				}else if(xPeople>xInLand &&(xInLand<xRight && xInLand>xLeft)) {//跳在中间
					if(yPeople>WINDOW_HEIGHT) {
						xPeople = xLeftTemp + leftWidth/2;
						yPeople = yLeft;
						timerPeople.stop();
						waiting = false;
					}else {}
					// fail to jump to another pillat [left]
				}else if(((xPeople>xRight && xPeople<xRight+rightWidth) && (yPeople<yRight+36 && yPeople>yRight-36))) {
					MusicThread mThread = new MusicThread(Data.jumpOkMusic.getPath(), 0);
					mThread.start();
					timerPeople.stop();
					peopleJumpOver = true;
					cnt++;
					score += (10 + random.nextInt(10));
					if(jumpNumber-1 == cnt) {
						lastPillar = true;//不再产生柱子
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
	}

	public boolean getGameIsOver() {
		return this.lastPillar;
	}
	public void setFlag() {
		if(lastPillar == false) {
			this.Start = true;
		}
		else {
			waiting = true;
			new Thread() {
				public void run() {
					repaint();	
				};
			}.start();
		}
	}

	public void resetFlag() {
		this.Start = false;
		new Thread() {
			public void run() {
				repaint();	
			};
		}.start();

		timerPeople.start();
		peopleJumpOver = false;
		waiting = true;
	}
	public boolean getState() {
		return this.lastPillar;
	}

	public void setPillarNumber(int pillarNumber) {
		this.jumpNumber = pillarNumber - 1;
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
		if(lastPillar == true && gameOver == false) {
			successfullyIcon.paintIcon(this, g2d, 300, -20);
			return;
		}
		if(gameOver == true) {
			failIcon.paintIcon(this, g2d, 300, -100);
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
			}
			rightImageIcon.paintIcon(this, g2d,xRight, yRight);

			if((peopleJumpOver == true) && !(timerPeople.isRunning())) {
				peoplIcon.paintIcon(this, g2d, xRight+rightWidth/2, yRight-30);
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
			peoplIcon.paintIcon(this, g2d, xPeople,yPeople-30);
		}
		g2d.dispose();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		myThread mT = new myThread(e, this.getName());
		mT.start();
	}
	public void setTimeInterval(long timerInterval) {
		this.timeInterval = timerInterval;
		double ratio = timeInterval>=1500?1:timeInterval/1500.0;
		int ymax = 0;
		int xmax = (yLeft-ymax) + xLeft;
		int Ypoly = (int) (yLeft - ratio*(yLeft-ymax));
		int Xpoly = (int) (xLeft + ratio*(xmax-xLeft));
		generator.setParameter(xLeftTemp+leftWidth/2,yLeft,Xpoly,Ypoly);

	}
	long getScore() {
		return this.score;
	}
	boolean getGameIsFailed(){
		return this.gameOver;
	}
	void setGameOver() {
		this.gameOver = true;
		repaint();
		
	}
	
}
