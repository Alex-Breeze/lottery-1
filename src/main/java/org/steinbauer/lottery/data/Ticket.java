package org.steinbauer.lottery.data;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Version;

import lombok.Data;

@Data
@Entity
public class Ticket {

	@Id @GeneratedValue
	private Long id;
	
	@Version
	private Long version;
	
	@Enumerated(EnumType.STRING)
	private LotteryResultStatus outcome;
	
	private BigDecimal bid;
	
	private BigDecimal prize;
	
	@Transient
	public String getTicketNumber() {
		return String.format("ticket-%d", this.id);
	}
	
}
