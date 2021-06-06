
public class PolyGenerator {
	private double a;
	private double b;
	private double c;
	
	public PolyGenerator(int x0, int y0, int x1, int y1) {
		//x0,y0人物坐标
		//x1,y1顶点坐标
		a = Math.sqrt(2.5) * 0.5 / (double)(x1-x0);
		b = (double)x1;
		c = (double)y1;
	}
	void setParameter(int x0, int y0, int x1, int y1) {
		this.a = Math.sqrt(2.5) * 0.5 / (double)(x1-x0);
		this.b = (double)x1;
		this.c = (double)y1;
	}
	public double Calculate(double x) {
		double y = 0;
		y = a * (x-b) * (x-b) + c;
		return y;
	}
	
	public double Solve(double y) {
		double x = 0;
		//平台高度高于至高点则无解
		if (y-c < 0)
			return -1;
		x = b + Math.sqrt((y-c)/a);
		return x;
	}
}
