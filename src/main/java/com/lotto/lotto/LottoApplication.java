package com.lotto.lotto;

import com.lotto.lotto.infrastructure.AsdadadRepository;
import com.lotto.lotto.infrastructure.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
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
