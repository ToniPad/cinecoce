package com.tp.cinoche_back.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nom_client", length = 75)
    private String nomClient;
    private double Prix;
    @Column(name = "code_payement")
    private Integer codePayement;
    private boolean reservee;
    @ManyToOne
    private Place place;
    @ManyToOne
    private Diffusion diffusion;
}
