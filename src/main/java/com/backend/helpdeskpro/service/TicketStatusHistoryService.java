package com.backend.helpdeskpro.service;

import com.backend.helpdeskpro.dto.tickets.ticketDto.TicketResponseDto;
import com.backend.helpdeskpro.dto.tickets.ticketStatus.TicketStatusHistoryDto;
import com.backend.helpdeskpro.security.CustomUserPrincipal;

import jakarta.servlet.http.HttpServletRequest;

public interface TicketStatusHistoryService {

    TicketResponseDto updateTicketStatus(CustomUserPrincipal authUser,TicketStatusHistoryDto dto, HttpServletRequest request);

}
