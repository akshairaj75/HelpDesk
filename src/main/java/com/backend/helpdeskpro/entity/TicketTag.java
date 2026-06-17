package com.backend.helpdeskpro.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ticket_tags", uniqueConstraints = @UniqueConstraint(name = "uq_tag_name", columnNames = "name"))
public class TicketTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false, unique = true, length = 60)
    private String name;

    @Column(name = "color_hex", length = 7)
    private String colorHex = "#185FA5";

    @ManyToMany(mappedBy = "tags")
    private List<Ticket> tickets = new ArrayList<>();

    @OneToMany(mappedBy = "tag")
    private List<TicketTagMap> ticketTagMaps = new ArrayList<>();

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

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<TicketTagMap> getTicketTagMaps() {
        return ticketTagMaps;
    }

    public void setTicketTagMaps(List<TicketTagMap> ticketTagMaps) {
        this.ticketTagMaps = ticketTagMaps;
    }
}
