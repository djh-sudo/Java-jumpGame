import java.io.File;

public class MusicThread extends Thread {
	private BigSoundPlayer myPlayer;
	private String fnameString;
	private int flag;
	
	public MusicThread(String name, int flag) {
		myPlayer = new BigSoundPlayer();
		this.fnameString = name;
		this.flag = flag;
		// TODO Auto-generated constructor stub
	}
	
	public void run() {
		if (flag==1) {
			while(true) {
			myPlayer.play(new File(fnameString), 1);
			}
		}
		else {
			myPlayer.play(new File(fnameString), 1);
		}
	}
}
