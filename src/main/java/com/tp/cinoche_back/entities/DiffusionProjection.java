package com.tp.cinoche_back.entities;

import org.springframework.data.rest.core.config.Projection;

import java.util.Collection;
import java.util.Date;

@Projection(name = "p1", types = {Diffusion.class})
public interface DiffusionProjection {
    public Long getId();

    public double getPrix();

    public Date getDateDiffusion();

    public Salle getSalle();

    public Film getFilm();

    public Collection<Ticket> getTickets();

    public Seance getSeance();


}
