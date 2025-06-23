package com.lotto.lotto.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final AsdadadRepository ticketRepository;

    public Ticket saveTicket(String id) {
        Ticket ticket = new Ticket(id);
        return ticketRepository.save(ticket);
    }
}
