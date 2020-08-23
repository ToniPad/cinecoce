package com.tp.cinoche_back.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
@Entity
@Data
public class Seance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.TIME)
    @Column(name = "heure_debut")
    private Date heureDebut;
    @OneToMany(mappedBy = "seance")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Diffusion> diffusions;
}
