package com.lotto.lotto.domain.resultchecker;

import com.lotto.lotto.domain.numbergenerator.WinningNumbersGeneratorFacade;
import com.lotto.lotto.domain.numberreceiver.NumberReceiverFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResultCheckerConfiguration {

    @Bean
    ResultCheckerFacade resultCheckerFacade(WinningNumbersGeneratorFacade generatorFacade, NumberReceiverFacade receiverFacade,
                                      PlayerRepository playerRepository) {
        WinnersRetriever winnerGenerator = new WinnersRetriever();
        return new ResultCheckerFacade(generatorFacade, receiverFacade, playerRepository, winnerGenerator);
    }
}
