package org.steinbauer.lottery;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.steinbauer.lottery.data.LotteryGame;
import org.steinbauer.lottery.data.Price;
import org.steinbauer.lottery.data.Ticket;

@Controller
public class LotteryController {
	
	private LotteryGame lotteryGame;
	
	@Autowired
	private LotteryService lotteryService;
	
	@ModelAttribute("lotteryGame")
	public LotteryGame getLotteryGame() {
		return this.lotteryGame;
	}
	
	@ModelAttribute("currentJackpot")
	public BigDecimal getCurrentJackpot() {
		return this.lotteryService.computeCurrentJackpot();
	}
	
	@ModelAttribute("pricePerGame")
	public BigDecimal getPricePerGame() {
		Price price = this.lotteryService.getTicketPrice();
		return price.getPrice();
	}
	
	@ModelAttribute("hostinfo")
	public String getHostinfo() {
		return Net.getIPInfo();
	}

	@GetMapping("/play")
	public String playGame(Model model) {
		Ticket ticket = this.lotteryService.createTicket();
		this.lotteryGame = this.lotteryService.playTicket(ticket);
		model.addAttribute("lotteryGame", this.lotteryGame);
		return "result";
	}
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
}
