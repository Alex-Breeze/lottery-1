package org.steinbauer.lottery.dao;

import org.springframework.data.repository.CrudRepository;
import org.steinbauer.lottery.data.Price;

public interface PriceRepository extends CrudRepository<Price, Long>{

}
