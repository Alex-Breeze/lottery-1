package org.steinbauer.lottery;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.steinbauer.lottery.dao.PriceRepository;
import org.steinbauer.lottery.dao.TicketRepository;
import org.steinbauer.lottery.data.LotteryGame;
import org.steinbauer.lottery.data.LotteryResult;
import org.steinbauer.lottery.data.Pot;
import org.steinbauer.lottery.data.Price;
import org.steinbauer.lottery.data.Ticket;

@Component
public class LotteryService {
	
	public final static long DEFAULT_PRICE_ID = 1;
	
	public final static int GAME_ROWS = 3;
	
	public final static double TWO_WIN = 0.1;
	public final static double THREE_WIN = 0.3;
	public final static double JACKPOT_WIN = 0.7;
	
	public final static long SLEEPING_TIME = 150;
	
	static Logger logger = LoggerFactory.getLogger(LotteryService.class);
	
	@Autowired
	private PriceRepository priceRepository;
	
	@Autowired
	private TicketRepository ticketRepository;

	@Cacheable
	public Price getTicketPrice() {
		return this.priceRepository.findOne(DEFAULT_PRICE_ID);
	}
	
	@Transactional
	public Ticket createTicket() {
		Price price = this.getTicketPrice();
		Ticket ticket = new Ticket();
		ticket.setBid(price.getPrice());
		return this.ticketRepository.save(ticket);
	}
	
	/** Unfortunately this method cannot be executed in parallel */
	@Transactional
	public synchronized BigDecimal computeCurrentJackpot() {
		BigDecimal sumBids = this.ticketRepository.sumBids();
		if(sumBids == null) sumBids = BigDecimal.ZERO;
		BigDecimal sumPrizes = this.ticketRepository.sumPrize();
		if(sumPrizes == null) sumPrizes = BigDecimal.ZERO;
		BigDecimal jackpot = sumBids.subtract(sumPrizes);
		logger.info("Computed a jackpot of {}", jackpot);
		Sleeper.sleep(SLEEPING_TIME);
		return jackpot;
	}
	
	public Pot computePotInfo() {
		return new Pot(this.computeCurrentJackpot(), this.getTicketPrice().getPrice());
	}
	
	public LotteryGame playTicket(Ticket ticket) {
		LotteryGame game = new LotteryGame(ticket);
		this.executeGame(game, this.computeCurrentJackpot());
		return game;
	}
	
	public void executeGame(LotteryGame game, BigDecimal currentJackpot) {
		int[] rows = new int[GAME_ROWS];
		for(int i=0; i< GAME_ROWS; i++) {
			rows[i] = Random.randomGame();
		}
		LotteryResult result = new LotteryResult(rows);
		game.setResult(result);
		
		BigDecimal prize = null;
		switch (result.getLotteryResultStatus()) {
		case Blank:
			prize = BigDecimal.ZERO;
			break;
		case Win2:
			prize = currentJackpot.multiply(new BigDecimal(TWO_WIN)).setScale(2, RoundingMode.HALF_UP);
			break;
		case Win3:
			prize = currentJackpot.multiply(new BigDecimal(THREE_WIN)).setScale(2, RoundingMode.HALF_UP);
			break;
		case Jackpot:
			prize = currentJackpot.multiply(new BigDecimal(JACKPOT_WIN)).setScale(2, RoundingMode.HALF_UP);
			break;
		}
		game.getTicket().setOutcome(result.getLotteryResultStatus());
		game.getTicket().setPrize(prize);
		this.ticketRepository.save(game.getTicket());
	} 
	
}
