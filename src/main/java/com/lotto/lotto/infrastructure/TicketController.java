package com.lotto.lotto.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/create")
    public Ticket createTicket(@RequestParam String id) {
        return ticketService.saveTicket(id);
    }
}
