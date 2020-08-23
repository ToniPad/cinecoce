package com.tp.cinoche_back.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data
@Table(name = "cine")
public class Cinema implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nom", length = 75)
    private String name;
    private double longitude,latitude,altitude;
    @Column(name = "nombre_salle")
    private int nombreSalles;
    @ManyToOne
    private Ville ville;
    @OneToMany(mappedBy = "cinema")
    private Collection<Salle> salles;
}
