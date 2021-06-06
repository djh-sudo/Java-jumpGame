import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Monitor implements ActionListener,KeyListener{
	private int LeftKey = 32;
	private int rightKey = 40;

	/*single play*/
	private long timeStart = 0;
	private long timeEnd = 0;
	private long timeInterval = 0;
	private boolean flag = false;

	/*dual play*/
	private boolean leftPlay = false;
	private boolean rightPlay = false;

	private long leftTimeStart = 0;
	private long rightTimeStart = 0;

	private long leftTimeEnd = 0;
	private long rightTimeEnd = 0;

	private long leftTimeInterval = 0;
	private long rightTimeInterval = 0;



	public void setLeftKey(int leftKey) {
		this.LeftKey = leftKey;
	}
	public void setRightKey(int rightKey) {
		this.rightKey = rightKey;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		/*single play*/

		if(e.getSource().getClass() == SinglePlay.class && e.getKeyCode() == LeftKey) {//SinglePlay
			if(flag == false) {
				timeStart = System.currentTimeMillis();
				flag = true;
			}
			if(((SinglePlay) e.getSource()).getState() == true) {
			}
			else {
				((SinglePlay) e.getSource()).setFlag();
			}
		}
		/*dualplay*/
		if(e.getSource().getClass() == DualPlay.class && e.getKeyCode() == LeftKey) {
			new Thread() {
				public void run() {
					if(e.getKeyCode() == LeftKey) {
						if(leftPlay == false) {
							leftPlay = true;
							leftTimeStart = System.currentTimeMillis();
							leftTimeInterval = 0;
						}
						if(((DualPlay) e.getSource()).getLeftState() == true) {
						}
						else {
							((DualPlay) e.getSource()).setLeftFlag();
						}
					}
				};
			}.start();
		}

		if(e.getSource().getClass() == DualPlay.class && e.getKeyCode() == rightKey) {
			new Thread() {
				public void run() {
					if(e.getKeyCode() == rightKey) {
						if(rightPlay == false) {
							rightPlay = true;
							rightTimeStart = System.currentTimeMillis();
							rightTimeInterval = 0;
						}
						if(((DualPlay) e.getSource()).getRightState() == true) {
						}
						else {
							((DualPlay) e.getSource()).setRightFlag();
						}
					}	
				};
			}.start();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		/*single play*/
		if(e.getSource().getClass() == SinglePlay.class && ((SinglePlay) e.getSource()).getState() == true) {
			timeInterval = 0;
			flag = false;
			return;
		}
		timeEnd = System.currentTimeMillis();
		flag = false;
		timeInterval = timeEnd - timeStart;
		if(e.getSource().getClass() == SinglePlay.class && e.getKeyCode() == LeftKey) {
			MusicThread mThread = new MusicThread("./src/source/2310.wav", 0);
			mThread.start();
			((SinglePlay) e.getSource()).resetFlag();
			((SinglePlay) e.getSource()).setTimeInterval(timeInterval);
		}

		/*dual play left key*/

		if(e.getSource().getClass() == DualPlay.class) {
			if(e.getKeyCode() == LeftKey)
				new Thread() {
				public void run() {
					if(((DualPlay) e.getSource()).getLeftState() == true) {
						leftTimeInterval = 0;
						leftPlay = false;
						return;
					}
					else {
						MusicThread mThread = new MusicThread("./src/source/2310.wav", 0);
						mThread.start();
						leftTimeEnd = System.currentTimeMillis();
						leftPlay = false;
						leftTimeInterval = leftTimeEnd - leftTimeStart;
						((DualPlay) e.getSource()).resetLeftFlag();
						((DualPlay) e.getSource()).setLeftTimeInterval(leftTimeInterval);
					}
				};
			}.start();



			/*dual play right key*/
			if(e.getKeyCode() == rightKey)
				new Thread() {
				public void run() {
					if(((DualPlay) e.getSource()).getRightState() == true) {
						rightTimeInterval = 0;
						rightPlay = false;
						return;
					}
					else {
						MusicThread mThread = new MusicThread("./src/source/2310.wav", 0);
						mThread.start();
						rightTimeEnd = System.currentTimeMillis();
						rightPlay = false;
						rightTimeInterval = rightTimeEnd - rightTimeStart;
						((DualPlay) e.getSource()).resetRightFlag();
						((DualPlay) e.getSource()).setRightTimeInterval(rightTimeInterval);

					}
				}
			}.start();
		}
	}

}
