package com.backend.helpdeskpro.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.backend.helpdeskpro.dto.tickets.ticketDto.TicketCreateDto;
import com.backend.helpdeskpro.dto.tickets.ticketDto.TicketResponseDto;
import com.backend.helpdeskpro.security.CustomUserPrincipal;

public interface TicketService {

    TicketResponseDto createTicket(CustomUserPrincipal authUser, TicketCreateDto dto, List<MultipartFile> files);

    List<TicketResponseDto> getAllTickets(CustomUserPrincipal authUser);

}
