package com.tp.cinoche_back.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 75)
    private String titre;
    private double duree;
    @Column(length = 75)
    private String realisateur;
    private String description;
    private String photo;
    @Column(name = "date_sortie")
    private Date dateSortie;
    @OneToMany(mappedBy = "film")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Diffusion> diffusions;
    @ManyToOne
    private Categorie categorie;
}
