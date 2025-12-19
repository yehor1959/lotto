package com.lotto.lotto.infrastructure.resultannouncer.controller;

import com.lotto.lotto.domain.resultannouncer.ResultAnnouncerFacade;
import com.lotto.lotto.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ResultAnnouncerRestController {

    ResultAnnouncerFacade resultAnnouncerFacade;

    @GetMapping("/results/{id}")
    public ResponseEntity<ResultAnnouncerResponseDto> checkResultsById(@PathVariable String id) {
        ResultAnnouncerResponseDto resultAnnouncerResponseDto = resultAnnouncerFacade.checkResult(id);
        return ResponseEntity.ok(resultAnnouncerResponseDto);
    }
}
