package com.lotto.lotto.infrastructure.resultannouncer.controller.error;

import org.springframework.http.HttpStatus;

public record ResultAnnouncerErrorResponse(
        String message,
        HttpStatus status) {
}
