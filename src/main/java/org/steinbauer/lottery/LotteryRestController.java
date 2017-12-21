package org.steinbauer.lottery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.steinbauer.lottery.data.LotteryGame;
import org.steinbauer.lottery.data.Pot;
import org.steinbauer.lottery.data.Ticket;

@RestController
public class LotteryRestController {
	
	@Autowired
	private LotteryService lotteryService;
	
	@GetMapping(value="/api/pot-info", produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Pot pot() {
		return this.lotteryService.computePotInfo();
	}

	@GetMapping(value="/api/play-game", produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody LotteryGame playGame() {
		Ticket ticket = this.lotteryService.createTicket();
		return this.lotteryService.playTicket(ticket);
	}
	
}
