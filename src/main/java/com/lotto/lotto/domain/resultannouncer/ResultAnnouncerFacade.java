package com.lotto.lotto.domain.resultannouncer;

import com.lotto.lotto.domain.resultannouncer.dto.ResponseDto;
import com.lotto.lotto.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import com.lotto.lotto.domain.resultchecker.ResultCheckerFacade;
import com.lotto.lotto.domain.resultchecker.dto.ResultDto;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static com.lotto.lotto.domain.resultannouncer.MessageResponse.*;

@AllArgsConstructor
public class ResultAnnouncerFacade {

    public static final LocalTime RESULTS_ANNOUNCEMENT_TIME = LocalTime.of(12, 0).plusMinutes(5);
    private final ResultCheckerFacade resultCheckerFacade;
    private final ResponseRepository responseRepository;
    private final Clock clock;

    public ResultAnnouncerResponseDto checkResult(String hash) {
        if (responseRepository.existsById(hash)) {
            Optional<ResultResponse> resultResponseCached = responseRepository.findById(hash);
            if(resultResponseCached.isPresent()){
                return new ResultAnnouncerResponseDto(ResultMapper.mapToDto(resultResponseCached.get()), ALREADY_CHECKED.info);
            }
        }

        ResultDto resultDto = resultCheckerFacade.findByHash(hash);

        if (resultDto == null) {
            return new ResultAnnouncerResponseDto(null, HASH_DOES_NOT_EXIST_MESSAGE.info);
        }

        ResponseDto responseDto = buildResponseDto(resultDto);
        responseRepository.save(buildResponse(responseDto));

        if (responseRepository.existsById(hash) && !isAfterResultAnnouncementTime(resultDto)) {
            return new ResultAnnouncerResponseDto(responseDto, WAIT_MESSAGE.info);
        }
        if (resultCheckerFacade.findByHash(hash).isWinner()) {
            return new ResultAnnouncerResponseDto(responseDto, WIN_MESSAGE.info);
        }

        return new ResultAnnouncerResponseDto(responseDto, LOSE_MESSAGE.info);
    }

    private static ResultResponse buildResponse(ResponseDto responseDto) {
        return ResultResponse.builder()
                .hash(responseDto.hash())
                .numbers(responseDto.numbers())
                .hitNumbers(responseDto.hitNumbers())
                .drawDate(responseDto.drawDate())
                .isWinner(responseDto.isWinner())
                .build();
    }

    private static ResponseDto buildResponseDto(ResultDto resultDto) {
        return ResponseDto.builder()
                .hash(resultDto.hash())
                .numbers(resultDto.numbers())
                .hitNumbers(resultDto.hitNumbers())
                .drawDate(resultDto.drawDate())
                .isWinner(resultDto.isWinner())
                .build();
    }

    private boolean isAfterResultAnnouncementTime(ResultDto resultDto) {
        LocalDateTime announcementDateTime = LocalDateTime.of(resultDto.drawDate().toLocalDate(), RESULTS_ANNOUNCEMENT_TIME);
        return LocalDateTime.now(clock).isAfter(announcementDateTime);
    }
}
