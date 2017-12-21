package org.steinbauer.lottery.data;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Pot {

	private final BigDecimal jackpot;
	private final BigDecimal pricePerGame;
	
}
