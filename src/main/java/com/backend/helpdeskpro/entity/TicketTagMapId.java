package com.backend.helpdeskpro.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TicketTagMapId implements Serializable {

    @Column(name = "ticket_id", nullable = false)
    private Long ticketId;

    @Column(name = "tag_id", nullable = false)
    private Integer tagId;

    public TicketTagMapId() {
    }

    public TicketTagMapId(Long ticketId, Integer tagId) {
        this.ticketId = ticketId;
        this.tagId = tagId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof TicketTagMapId that)) {
            return false;
        }
        return Objects.equals(ticketId, that.ticketId) && Objects.equals(tagId, that.tagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, tagId);
    }
}
