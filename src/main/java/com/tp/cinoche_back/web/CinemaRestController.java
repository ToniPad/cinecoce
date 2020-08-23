package com.tp.cinoche_back.web;

import com.tp.cinoche_back.dao.FilmRepository;
import com.tp.cinoche_back.dao.TicketRepository;
import com.tp.cinoche_back.entities.Film;
import com.tp.cinoche_back.entities.Ticket;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin("*")
public class CinemaRestController {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private TicketRepository ticketRepository;

    public CinemaRestController() {
    }

    @GetMapping(path = "/imageFilm/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable(name = "id") Long id) throws Exception {
        Film f = filmRepository.findById(id).get();
        String photoName = f.getPhoto();
        File file = new File(System.getProperty("user.home") + "/cinema2/images/" + photoName);
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }

    @RequestMapping(value = "/payerTickets")
    public List<Ticket> payerTickets(@RequestBody TicketForm ticketForm) {
        List<Ticket> listTickets= new ArrayList<>();
        ticketForm.getTickets().forEach(idTicket->{
            Ticket ticket = ticketRepository.findById(idTicket).get();
            ticket.setNomClient(ticketForm.getNomClient());
            ticket.setReservee(true);
            ticketRepository.save(ticket);
            listTickets.add(ticket);
        });
    return listTickets;
    }
}

@Data
class TicketForm {
    public String nomClient;
    public   int codePayement;
    public List<Long> tickets = new ArrayList<>();
}

