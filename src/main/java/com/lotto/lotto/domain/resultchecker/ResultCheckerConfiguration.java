package com.lotto.lotto.domain.resultchecker;

import com.lotto.lotto.domain.numbergenerator.WinningNumbersGeneratorFacade;
import com.lotto.lotto.domain.numberreceiver.NumberReceiverFacade;

public class ResultCheckerConfiguration {

    ResultCheckerFacade createForTest(WinningNumbersGeneratorFacade generatorFacade, NumberReceiverFacade receiverFacade,
                                      PlayerRepository playerRepository) {
        WinnersRetriever winnerGenerator = new WinnersRetriever();
        return new ResultCheckerFacade(generatorFacade, receiverFacade, playerRepository, winnerGenerator);
    }
}
