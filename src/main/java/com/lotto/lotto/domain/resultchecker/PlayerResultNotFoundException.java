package com.lotto.lotto.domain.resultchecker;

public class PlayerResultNotFoundException extends RuntimeException {
    PlayerResultNotFoundException(String message) {
        super(message);
    }
}
