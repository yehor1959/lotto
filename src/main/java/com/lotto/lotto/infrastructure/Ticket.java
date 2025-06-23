package com.lotto.lotto.infrastructure;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("tickets")
public record Ticket(@Id String id) {
}
