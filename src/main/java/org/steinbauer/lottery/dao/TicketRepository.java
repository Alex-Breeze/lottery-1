package org.steinbauer.lottery.dao;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.steinbauer.lottery.data.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Long>{

	@Query("select sum(t.bid) from Ticket t where t.outcome is not null")
	public BigDecimal sumBids();
	
	@Query("select sum(t.prize) from Ticket t where t.outcome is not null")
	public BigDecimal sumPrize();
	
}
