package com.backend.helpdeskpro.service;

import com.backend.helpdeskpro.dto.tickets.ticketDto.TicketResponseDto;
import com.backend.helpdeskpro.enums.TicketStatus;
import com.backend.helpdeskpro.security.CustomUserPrincipal;

public interface TicketStatusHistoryService {

    TicketResponseDto updateTicketStatus(CustomUserPrincipal authUser, Long ticketId, TicketStatus status,
            String reason);

}
