package org.steinbauer.lottery;

public class Sleeper {

	public static void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
		}
	}
	
}
