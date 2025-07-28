package com.lotto.lotto.domain.numbergenerator;

public interface RandomNumberGenerable {

    SixRandomNumbersDto generateSixRandomNumbers(int count, int lowerBand, int upperBand);
}
