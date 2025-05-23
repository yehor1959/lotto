package com.lotto.lotto.domain.resultchecker;

import com.lotto.lotto.domain.numbergenerator.WinningNumbersGeneratorFacade;
import com.lotto.lotto.domain.numbergenerator.dto.WinningNumbersDto;
import com.lotto.lotto.domain.numberreceiver.NumberReceiverFacade;
import com.lotto.lotto.domain.numberreceiver.dto.TicketDto;
import com.lotto.lotto.domain.resultchecker.dto.PlayersDto;
import com.lotto.lotto.domain.resultchecker.dto.ResultDto;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Set;

import static com.lotto.lotto.domain.resultchecker.ResultCheckerMapper.mapPlayersToResults;

@AllArgsConstructor
public class ResultCheckerFacade {

    WinningNumbersGeneratorFacade winningNumbersGeneratorFacade;
    NumberReceiverFacade numberReceiverFacade;
    PlayerRepository playerRepository;
    WinnersRetriever winnerGenerator;

    public PlayersDto generateWinners() {
        List<TicketDto> allTicketsByDate = numberReceiverFacade.retrieveAllTicketsByNextDrawDate();
        List<Ticket> tickets = ResultCheckerMapper.mapFromTicketDto(allTicketsByDate);
        WinningNumbersDto winningNumbersDto = winningNumbersGeneratorFacade.generateWinningNumbers();
        Set<Integer> winningNumbers = winningNumbersDto.getWinningNumbers();

        if (winningNumbers == null || winningNumbers.isEmpty()) {
            return PlayersDto.builder()
                    .message("Winners failed to retrieve")
                    .build();
        }

        List<Player> players = winnerGenerator.retrieveWinners(tickets, winningNumbers);
        playerRepository.saveAll(players);
        return PlayersDto.builder()
                .results(mapPlayersToResults(players))
                .message("Winners succeeded to retrieve")
                .build();
    }

    public ResultDto findByHash(String hash) {
        Player player = playerRepository.findById(hash).orElseThrow(() -> new RuntimeException("Not found"));
        return ResultDto.builder()
                .hash(hash)
                .numbers(player.numbers())
                .hitNumbers(player.hitNumbers())
                .drawDate(player.drawDate())
                .isWinner(player.isWinner())
                .build();
    }
}
