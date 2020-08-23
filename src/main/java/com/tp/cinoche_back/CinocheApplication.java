package com.tp.cinoche_back;

import com.tp.cinoche_back.entities.Film;
import com.tp.cinoche_back.entities.Salle;
import com.tp.cinoche_back.entities.Ticket;
import com.tp.cinoche_back.service.ICinemaInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;



@SpringBootApplication
public class CinocheApplication implements CommandLineRunner {
	@Autowired
	private ICinemaInitService cinemaInitService;
	@Autowired
	private RepositoryRestConfiguration restConfiguration;


	public static void main(String[] args) {
		SpringApplication.run(CinocheApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		restConfiguration.exposeIdsFor(Film.class, Salle.class, Ticket.class);
		cinemaInitService.initVilles();
		cinemaInitService.initCinemas();
		cinemaInitService.initSalles();
		cinemaInitService.initPlaces();
		cinemaInitService.initSeances();
		cinemaInitService.initCategories();
		cinemaInitService.initFilms();
		cinemaInitService.initDiffusions();
		cinemaInitService.initTickets();

	}

}
