package com.backend.helpdeskpro.dto.tickets.ticketTag;

public class TicketTagCreateDto {
    private String name;
    private String colorHex;

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
    

}
