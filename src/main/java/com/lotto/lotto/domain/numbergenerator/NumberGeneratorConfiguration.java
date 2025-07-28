package com.lotto.lotto.domain.numbergenerator;

import com.lotto.lotto.domain.numberreceiver.NumberReceiverFacade;
import org.springframework.context.annotation.Bean;

public class NumberGeneratorConfiguration {

    @Bean
    WinningNumbersGeneratorFacade winningNumbersGeneratorFacade(WinningNumbersRepository winningNumbersRepository, NumberReceiverFacade numberReceiverFacade,
                                                                RandomNumberGenerable randomNumberGenerable,
                                                                WinningNumbersGeneratorFacadeConfigurationProperties properties) {
        WinningNumberValidator winningNumberValidator = new WinningNumberValidator();
        return new WinningNumbersGeneratorFacade(randomNumberGenerable, winningNumberValidator, winningNumbersRepository,
                numberReceiverFacade, properties);
    }

    WinningNumbersGeneratorFacade createForTest(RandomNumberGenerable generator, WinningNumbersRepository winningNumbersRepository,
                                                NumberReceiverFacade numberReceiverFacade) {
        WinningNumbersGeneratorFacadeConfigurationProperties properties = WinningNumbersGeneratorFacadeConfigurationProperties.builder()
                .upperBand(99)
                .lowerBand(1)
                .count(6)
                .build();
        return winningNumbersGeneratorFacade(winningNumbersRepository, numberReceiverFacade, generator, properties);
    }
}
