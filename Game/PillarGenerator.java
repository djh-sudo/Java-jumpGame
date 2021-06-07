import java.util.Random;

import sun.nio.cs.Surrogate.Generator;

import java.lang.Math;

public class PillarGenerator {
	private int PillarNumber;
	private int Difficulty;
	private double muWidth, sigmaWidth;
	private double muHeight, sigmaHeight;
	private double muDist, sigmaDist;
	public PillarGenerator(int Difficulty) {
		this.Difficulty = Difficulty;
		muWidth = 150; sigmaWidth = 40;
		muHeight = 250; sigmaHeight = 50;
		muDist = 550; sigmaDist = 30;
		PillarNumber = 5 + Difficulty + 1;
	}
	
	void setmuHeight(double mu, double sigma) {
		this.muHeight = mu;
		this.sigmaHeight = sigma;
	}
	
	void setmuDist(double mu, double sigma) {
		this.muDist = mu;
		this.sigmaDist = sigma;
	}

	private double GaussianRandom(double mu, double sigma) {
		Random myRandom = new Random();
		return myRandom.nextGaussian() * Math.sqrt(sigma) + mu;
	}
	
	public Pillar[] RandomGenerator() {
		int tmpHeight, tmpWidth, tmpd;
		Random random = new Random();
		Pillar[] resultPillars = new Pillar[PillarNumber];
		for (int i = 0; i < PillarNumber; i++) {
			tmpHeight = (int)Math.floor(GaussianRandom(muHeight, sigmaHeight));
			tmpWidth = (int)Math.floor(GaussianRandom(muWidth, sigmaWidth));
			tmpd = (int)Math.floor(GaussianRandom(muDist, sigmaDist));
			
			resultPillars[i] = new Pillar(tmpHeight, tmpWidth, tmpd);
			muHeight = tmpHeight*(random.nextBoolean()==true?0.9:1.1); sigmaHeight *= 1.2;
			muWidth *= 0.95; 
			muDist = tmpd*(random.nextBoolean()==true?0.9:1.1); sigmaDist *= 1.2;
		}
		return resultPillars;
	}

	public int getPillarNumber () {
		return this.PillarNumber;
	}
}
