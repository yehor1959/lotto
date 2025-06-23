package com.lotto.lotto.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsdadadRepository extends MongoRepository<Ticket, String> {
}
