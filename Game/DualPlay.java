import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class DualPlay extends JPanel implements ActionListener{
	static final int WIDTH = 1014;
	static final int HEIGHT = 735;

	long leftScore = 0;
	long rightScore = 0;
	long liftTime = 60;
	Timer lefTimer = new Timer(15,this);
	Timer rightTimer = new Timer(15, this);
	Timer TimeUse = new Timer(1000, this);

	long timeCount = 0;

	void inicial() {
		timeCount = 0;

		leftScore = 0;
		rightScore = 0;

		TimeUse.start();

		Component LP = getLeftPanel();
		if(LP!=null) {
			((PlayWindow)LP).Inicial();
		}
		Component RP = getRightPanel();
		if(RP!=null) {
			((PlayWindow)RP).Inicial();
		}
	}

	public Component getPlay1ProgressBar() {
		for(Component Com:this.getComponents()) {
			if(Com.getClass() == JProgressBar.class && Com.getName().equals("dualProgressBar1")) {
				return Com;
			}
		}
		return null;
	}

	public Component getPlay2ProgressBar() {
		for(Component Com:this.getComponents()) {
			if(Com.getClass() == JProgressBar.class && Com.getName().equals("dualProgressBar2")) {
				return Com;
			}
		}
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(lefTimer)) {
			Component LPB = getPlay1ProgressBar();
			if(LPB.getClass() == JProgressBar.class) {
				int v = ((JProgressBar)LPB).getValue();
				((JProgressBar)LPB).setValue(v+1);
			}
		}

		if(e.getSource().equals(rightTimer)) {
			Component RPB = getPlay2ProgressBar();
			if(RPB.getClass() == JProgressBar.class) {
				int v = ((JProgressBar)RPB).getValue();
				((JProgressBar)RPB).setValue(v+1);
			}
		}

		if(e.getSource().equals(TimeUse)) {
			timeCount++;
			if(liftTime<timeCount) {
				Component LP = getLeftPanel();
				if(LP != null) {
					((PlayWindow)LP).setGameOver();
				}

				Component RP = getRightPanel();
				if(RP != null) {
					((PlayWindow)RP).setGameOver();
				}
				TimeUse.stop();
				return;
			}
			Component LLT = getLeftLabelTime();
			Component RLT = getRightLabelTime();

			if(LLT.getClass() == JLabel.class && RLT.getClass() == JLabel.class) {
				if((getLeftState() == false && getRightState() == false) && liftTime>=timeCount){
					((JLabel)LLT).setText("Time: " + (liftTime-timeCount) + "second");
					((JLabel)RLT).setText("Time: " + (liftTime-timeCount) + "second");
				}
			}
		}
	}

	public Component getLeftPanel() {
		for(Component Com:this.getComponents()) {
			if(Com.getClass() == PlayWindow.class && Com.getName().equals("windowPanelLeft")) {
				return Com;
			}
		}
		return null;
	}

	public Component getRightPanel() {
		for(Component Com:this.getComponents()) {
			if(Com.getClass() == PlayWindow.class && Com.getName().equals("windowPanelRight")) {
				return Com;
			}
		}
		return null;
	}
	boolean getLeftState() {
		Component LP = getLeftPanel();
		if(LP != null) {
			if(((PlayWindow)LP).getState() == true) {
				Component RP = getRightPanel();
				if(RP != null) {
					((PlayWindow)RP).setGameOver();
					TimeUse.stop();
					return true;
				}
			}
			return ((PlayWindow)LP).getState();
		}
		return false;
	}
	boolean getRightState() {
		Component RP = getRightPanel();
		if(RP != null) {
			if(((PlayWindow)RP).getState() == true) {
				Component LP = getLeftPanel();
				if(LP != null) {
					((PlayWindow)LP).setGameOver();
					TimeUse.stop();
					return true;
				}
			}
			return ((PlayWindow)RP).getState();
		}
		return false;
	}
	void resetLeftFlag() {
		Component LP = getLeftPanel();
		if(LP != null) {
			((PlayWindow)LP).resetFlag();
			leftScore = ((PlayWindow)LP).getScore();
		}
		lefTimer.stop();
		Component LPB = getPlay1ProgressBar();
		if(LPB.getClass() == JProgressBar.class) {
			((JProgressBar)LPB).setValue(0);
		}
		Component LS = getLeftScore();
		if(LS.getClass() == JLabel.class) {
			((JLabel)LS).setText("SCOREPlay1: " + leftScore);
		}
	}
	void resetRightFlag() {
		Component RP = getRightPanel();
		if(RP!=null) {
			((PlayWindow)RP).resetFlag();
			rightScore = ((PlayWindow)RP).getScore();
		}
		rightTimer.stop();
		Component RPB = getPlay2ProgressBar();
		if(RPB.getClass() == JProgressBar.class) {
			((JProgressBar)RPB).setValue(0);
		}
		Component RS = getRightScore();
		if(RS.getClass() == JLabel.class) {
			((JLabel)RS).setText("SCOREPlay2: " + rightScore);
		}
	}
	void setLeftTimeInterval(long interval) {
		Component LP = getLeftPanel();
		if(LP!=null) {
			((PlayWindow)LP).setTimeInterval(interval);
		}
	}
	void setRightTimeInterval(long interval) {
		Component RP = getRightPanel();
		if(RP!=null) {
			((PlayWindow)RP).setTimeInterval(interval);
		}
	}
	void setLeftFlag() {
		Component LP = getLeftPanel();
		if(LP!=null) {
			((PlayWindow)LP).setFlag();
			lefTimer.start();
		}
	}
	void setRightFlag() {

		Component RP = getRightPanel();
		if(RP!=null) {
			((PlayWindow)RP).setFlag();
			rightTimer.start();
		}
	}
	public Component getLeftLabelTime() {
		for(Component Com:this.getComponents()) {
			if(Com.getClass() == JLabel.class && Com.getName().equals("useTime_1")) {
				return Com;
			}
		}
		return null;
	}

	public Component getRightLabelTime() {
		for(Component Com:this.getComponents()) {
			if(Com.getClass() == JLabel.class && Com.getName().equals("useTime_2")) {
				return Com;
			}
		}
		return null;
	}
	public Component getLeftScore() {
		for(Component Com:this.getComponents()) {
			if(Com.getClass() == JLabel.class && Com.getName().equals("score_1")) {
				return Com;
			}
		}
		return null;
	}

	public Component getRightScore() {
		for(Component Com:this.getComponents()) {
			if(Com.getClass() == JLabel.class && Com.getName().equals("score_2")) {
				return Com;
			}
		}
		return null;
	}
	public void setTotalTime(long time) {
		this.liftTime = time;
	}
}

