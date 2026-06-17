package com.backend.helpdeskpro.dto.tickets.ticketTag;

import com.backend.helpdeskpro.entity.TicketTag;

public class TicketTagResponseDto {
    private Integer id;
    private String name;
    private String colorHex;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    public static TicketTagResponseDto fromEntity(TicketTag tag) {
        TicketTagResponseDto dto = new TicketTagResponseDto();
        dto.setId(tag.getId());
        dto.setName(tag.getName());
        dto.setColorHex(tag.getColorHex());
        return dto;
    }

}
