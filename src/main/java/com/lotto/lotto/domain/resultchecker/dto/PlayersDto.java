package com.lotto.lotto.domain.resultchecker.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PlayersDto(
        List<ResultDto> results,
        String message
) {
}
