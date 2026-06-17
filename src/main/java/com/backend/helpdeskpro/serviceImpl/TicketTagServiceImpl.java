package com.backend.helpdeskpro.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.helpdeskpro.dto.tickets.ticketTag.TicketTagCreateDto;
import com.backend.helpdeskpro.dto.tickets.ticketTag.TicketTagResponseDto;
import com.backend.helpdeskpro.entity.TicketTag;
import com.backend.helpdeskpro.repository.TicketTagRepository;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.TicketTagService;

import jakarta.transaction.Transactional;

@Service
public class TicketTagServiceImpl implements TicketTagService {

    @Autowired
    TicketTagRepository ticketTagRepository;

    @Transactional
    @Override
    public TicketTagResponseDto createTicketTag(CustomUserPrincipal authUser, TicketTagCreateDto dto) {

        TicketTag tag = new TicketTag();
        tag.setName(dto.getName());
        tag.setColorHex(dto.getColorHex());

        tag = ticketTagRepository.save(tag);

        return TicketTagResponseDto.fromEntity(tag);

    }

    @Override
    public List<TicketTagResponseDto> getAllTicketTags() {
        List<com.backend.helpdeskpro.entity.TicketTag> tags = ticketTagRepository.findAll();
        return tags.stream()
                .map(TicketTagResponseDto::fromEntity)
                .toList();

    }

}
