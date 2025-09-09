package com.lotto.lotto;

import com.lotto.lotto.domain.numbergenerator.WinningNumbersGeneratorFacadeConfigurationProperties;
import com.lotto.lotto.infrastructure.numbergenerator.http.RandomNumberGeneratorRestTemplateConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({WinningNumbersGeneratorFacadeConfigurationProperties.class, RandomNumberGeneratorRestTemplateConfigurationProperties.class})
public class LottoApplication {

	public static void main(String[] args) {
		SpringApplication.run(LottoApplication.class, args);
	}

}

//@SpringBootApplication
//public class LottoApplication implements CommandLineRunner {
//
//	@Autowired
//	AsdadadRepository aasdasd;
//
//	public static void main(String[] args) {
//		SpringApplication.run(LottoApplication.class, args);
//	}
//
//	@Override
//	public void run(String... args) {
//		aasdasd.save(new Ticket("asdasd"));
//	}
//
//}
