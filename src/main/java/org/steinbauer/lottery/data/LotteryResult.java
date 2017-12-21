package org.steinbauer.lottery.data;

import lombok.Data;

@Data
public class LotteryResult {

	final int rows[];
	
	public LotteryResultStatus getLotteryResultStatus() {
		if(this.rows[0] == this.rows[1] && this.rows[1] == this.rows[2]) {
			if(this.rows[0] == 0) {
				return LotteryResultStatus.Jackpot;
			}else {
				return LotteryResultStatus.Win3;
			}
		}else if(this.rows[0] == this.rows[1] || this.rows[1] == this.rows[2] || this.rows[0] == this.rows[2]){
			return LotteryResultStatus.Win2;
		}
		return LotteryResultStatus.Blank;
	}
	
}
