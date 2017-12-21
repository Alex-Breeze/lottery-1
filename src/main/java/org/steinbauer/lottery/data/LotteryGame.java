package org.steinbauer.lottery.data;

import lombok.Data;

@Data
public class LotteryGame {

	private final Ticket ticket;
	private LotteryResult result;
	
	public int getRow1() {
		return this.result.getRows()[0];
	}
	
	public int getRow2() {
		return this.result.getRows()[1];
	}
	
	public int getRow3() {
		return this.result.getRows()[2];
	}
	
}
