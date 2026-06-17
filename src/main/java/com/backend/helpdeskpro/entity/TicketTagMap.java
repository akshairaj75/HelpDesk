package com.backend.helpdeskpro.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ticket_tag_map", indexes = @Index(name = "idx_ttm_tag", columnList = "tag_id"))
public class TicketTagMap {

    @EmbeddedId
    private TicketTagMapId id;

    @MapsId("ticketId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @MapsId("tagId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tag_id", nullable = false)
    private TicketTag tag;

    public TicketTagMapId getId() {
        return id;
    }

    public void setId(TicketTagMapId id) {
        this.id = id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public TicketTag getTag() {
        return tag;
    }

    public void setTag(TicketTag tag) {
        this.tag = tag;
    }
}
