package com.lotto.lotto.domain.numbergenerator;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface WinningNumbersRepository extends MongoRepository<WinningNumbers, String> {

    Optional<WinningNumbers> findNumbersByDate(LocalDateTime date);

    boolean existsByDate(LocalDateTime nextDrawDate);
}
