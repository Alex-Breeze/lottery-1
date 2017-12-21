package org.steinbauer.lottery;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.steinbauer.lottery.dao.PriceRepository;
import org.steinbauer.lottery.dao.TicketRepository;
import org.steinbauer.lottery.data.LotteryResultStatus;
import org.steinbauer.lottery.data.Price;
import org.steinbauer.lottery.data.Ticket;

@Component
public class LotteryApplicationStartedEvent implements ApplicationListener<ApplicationReadyEvent> {
	
	static Logger logger = LoggerFactory.getLogger(LotteryApplicationStartedEvent.class);
	
	public static final BigDecimal DEFAULT_PRICE = new BigDecimal(10);
	
	@Autowired
	private PriceRepository priceRepository;
	
	@Autowired
	private TicketRepository ticketRepository;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
		if(this.priceRepository.count() == 0L) {
			logger.info("No prices found in database, initializing");
			Price price = new Price();
			price.setPrice(DEFAULT_PRICE);
			this.priceRepository.save(price);
			logger.info("Creating initial ticket to initialize pot");
			Ticket ticket = new Ticket();
			ticket.setBid(DEFAULT_PRICE);
			ticket.setPrize(BigDecimal.ZERO);
			ticket.setOutcome(LotteryResultStatus.Blank);
			this.ticketRepository.save(ticket);
		}
	}

}
