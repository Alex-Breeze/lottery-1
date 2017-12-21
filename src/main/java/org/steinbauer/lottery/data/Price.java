package org.steinbauer.lottery.data;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import lombok.Data;

@Data
@Entity
public class Price {
	
	@Id @GeneratedValue
	private Long id;
	
	@Version
	private Long version;
	
	private BigDecimal price;

}
