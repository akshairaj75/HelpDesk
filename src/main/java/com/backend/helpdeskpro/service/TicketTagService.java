package com.backend.helpdeskpro.service;

import java.util.List;

import com.backend.helpdeskpro.dto.tickets.ticketTag.TicketTagCreateDto;
import com.backend.helpdeskpro.dto.tickets.ticketTag.TicketTagResponseDto;
import com.backend.helpdeskpro.security.CustomUserPrincipal;

public interface TicketTagService {

    TicketTagResponseDto createTicketTag(CustomUserPrincipal authUser, TicketTagCreateDto dto);

    List<TicketTagResponseDto> getAllTicketTags();

}
