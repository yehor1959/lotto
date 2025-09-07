package com.lotto.lotto.domain.numbergenerator;

import com.lotto.lotto.domain.numberreceiver.NumberReceiverFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class NumberGeneratorConfiguration {

    @Bean
    WinningNumbersRepository repository() {
        return new WinningNumbersRepository() {
            @Override
            public Optional<WinningNumbers> findNumbersByDate(LocalDateTime date) {
                return Optional.empty();
            }

            @Override
            public boolean existsByDate(LocalDateTime nextDrawDate) {
                return false;
            }

            @Override
            public WinningNumbers save(WinningNumbers winningNumbers) {
                return null;
            }
        };
    }

    @Bean
    NumberReceiverFacade numberReceiverFacade() {
        return new NumberReceiverFacade(null, null, null, null);
    }

    @Bean
    RandomNumberGenerable randomNumberGenerable() {
        return new RandomNumberGenerable() {
            public static final int MAXIMAL_WINNING_NUMBERS = 6;
            public static final String RANDOM_NUMBER_SERVICE_PATH = "/api/v1.0/random";

            private final RestTemplate restTemplate = new RestTemplate();
            private final String uri = "http://ec2-3-127-218-34.eu-central-1.compute.amazonaws.com";
            private final int port = 9090;

            @Override
            public SixRandomNumbersDto generateSixRandomNumbers(int count, int lowerBand, int upperBand) {
//                log.info("Started fetching winning numbers using http client");
                HttpHeaders headers = new HttpHeaders();
                final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
                try {
                    final ResponseEntity<List<Integer>> response = makeGetRequest(count, lowerBand, upperBand, requestEntity);
                    Set<Integer> sixRandomDistinctNumbers = getSixRandomDistinctNumbers(response);
                    if (sixRandomDistinctNumbers.size() != MAXIMAL_WINNING_NUMBERS) {
//                        log.error("Set is less than: {} Have to request one more time", count);
                        return generateSixRandomNumbers(count, lowerBand, upperBand);
                    }
                    return SixRandomNumbersDto.builder()
                            .numbers(sixRandomDistinctNumbers)
                            .build();
                } catch (ResourceAccessException e) {
//                    log.error("Error while fetching winning numbers using http client: " + e.getMessage());
                    return SixRandomNumbersDto.builder().build();
                }
            }

            private ResponseEntity<List<Integer>> makeGetRequest(int count, int lowerBand, int upperBand, HttpEntity<HttpHeaders> requestEntity) {
                final String url = UriComponentsBuilder.fromHttpUrl(getUrlForService(RANDOM_NUMBER_SERVICE_PATH))
                        .queryParam("min", lowerBand)
                        .queryParam("max", upperBand)
                        .queryParam("count", count)
                        .toUriString();
                ResponseEntity<List<Integer>> response = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        requestEntity,
                        new ParameterizedTypeReference<>() {
                        }
                );

                return response;
            }

            private Set<Integer> getSixRandomDistinctNumbers(ResponseEntity<List<Integer>> response) {
                List<Integer> numbers = response.getBody();
                if (numbers == null) {
//                    log.error("Response Body was null returning empty collection");
                    return Collections.emptySet();
                }
//                log.info("Success Response Body Returned: " + response);
                Set<Integer> distinctNumbers = new HashSet<>(numbers);

                return distinctNumbers.stream()
                        .limit(6)
                        .collect(Collectors.toSet());
            }

            private String getUrlForService(String service) {
                return uri + ":" + port + service;
            }
        };
    }

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
