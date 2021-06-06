public class Pillar {
	private int height;
	private int width;
	private int d;
	private int xLocation;
	private int yLocation;
	
	Pillar(int height, int width, int d){
		this.height = height;
		this.width = width;
		this.d = d;
		xLocation = yLocation = 0;
	}
	
	int getHeight() {
		return this.height;
	}
	
	int getWidth() {
		return this.width;
	}
	
	int getD() {
		return this.d;
	}
	
	int getXLoction() {
		return this.xLocation;
	}
	
	int getYLocation() {
		return this.yLocation;
	}
	
	void setHeight(int height) {
		this.height = height;
	}
	
	void setWidth(int width) {
		this.width = width;
	}
	
	void setD(int d) {
		this.d = d;
	}
	
	void setXLocation(int xLocation) {
		this.xLocation = xLocation;
	}
	
	void setYLocation(int yLocation) {
		this.yLocation = yLocation;
	}
	void setAttributes(int height,int width,int d) {
		this.height = height;
		this.width = width;
		this.d = d;
	}
	void setLocation(int x,int y) {
		this.xLocation = x;
		this.yLocation = y;
	}
}
