package org.steinbauer.lottery;

public class Random {

	public static int randomInt(int min, int max) {
		return min + (int) (Math.random() * max); 
	}
	
	public static int randomInt(int max) {
		return randomInt(0, max);
	}
	
	public static int randomGame() {
		return randomInt(5);
	}
	
}
