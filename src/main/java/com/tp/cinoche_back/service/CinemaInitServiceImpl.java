package com.tp.cinoche_back.service;

import com.tp.cinoche_back.dao.*;
import com.tp.cinoche_back.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Transactional
public class CinemaInitServiceImpl implements ICinemaInitService {
    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private DiffusionRepository diffusionRepository;
    @Autowired
    private CategorieRepository categorieRepository;

    @Override
    public void initVilles() {
        Stream.of("Valenciennes", "Saint-Amand", "Lomme", "Douai", "Villeneuve d'ascq")
                .forEach(v -> {
                    Ville ville = new Ville();
                    ville.setName(v);
                    villeRepository.save(ville);
                });
    }

    @Override
    public void initCinemas() {
        villeRepository.findAll().forEach(v -> {
            Stream.of("Gaumont", "cinamand", "Kinepolis", "L'UGC")
                    .forEach(c -> {
                        Cinema cinema = new Cinema();
                        cinema.setName(c);
                        cinema.setNombreSalles(3 + (int) (Math.random() * 7));
                        cinema.setVille(v);
                        cinemaRepository.save(cinema);

                    });
        });
    }

    @Override
    public void initSalles() {
        cinemaRepository.findAll().forEach(c -> {
            for (int i = 0; i < c.getNombreSalles(); i++) {
                Salle salle = new Salle();
                salle.setName("Salle" + (i + 1));
                salle.setCinema(c);
                salle.setNombrePlaces(15 + (int) (Math.random() * 20));
                salleRepository.save(salle);
            }
        });
    }

    @Override
    public void initPlaces() {
        salleRepository.findAll().forEach(salle -> {
            for (int i = 0; i < salle.getNombrePlaces(); i++) {
                Place place = new Place();
                place.setNumero(i + 1);
                place.setSalle(salle);
                placeRepository.save(place);
            }
        });
    }

    @Override
    public void initSeances() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Stream.of("12:00", "15:00", "17:00", "19:00", "21:00").forEach(se -> {
            Seance seance = new Seance();
            try {
                seance.setHeureDebut(dateFormat.parse(se));
                seanceRepository.save(seance);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void initCategories() {
        Stream.of("Comedie", "Action", "Drame", "Fiction", "Sentimentale", "Thriller", "Policier").forEach(cat -> {
            Categorie categorie = new Categorie();
            categorie.setName(cat);
            categorieRepository.save(categorie);
        });
    }

    @Override
    public void initFilms() {
        double[] durees = new double[]{1, 1.5, 2, 2.5, 3};
        List<Categorie> categories = categorieRepository.findAll();
        Stream.of("D.A.R.Y.L.", "USUAL SUSPECTS", "INCEPTION", "PULP FICTION", "SHINING", "RESERVOIR DOGS", "POINT BREAK"
                , "SCARFACE", "DIE HARD", "MATRIX", "SDA", "FIGHT CLUB")
                .forEach(titreFilm -> {
                    Film film = new Film();
                    film.setTitre(titreFilm);
                    film.setDuree(durees[new Random().nextInt(durees.length)]);
                    film.setPhoto(titreFilm.replaceAll(" ", "") + ".jpg");
                    film.setCategorie(categories.get((new Random().nextInt(categories.size()))));
                    filmRepository.save(film);
                });
    }

    @Override
    public void initDiffusions() {
        double[] prices = new double[]{3, 5};
        List<Film> films = filmRepository.findAll();
        villeRepository.findAll().forEach(ville -> {
            ville.getCinemas().forEach(cinema -> {
                cinema.getSalles().forEach(salle -> {
                    int index = new Random().nextInt(films.size());
                    Film film = films.get(index);
                    seanceRepository.findAll().forEach(seance -> {
                        Diffusion diffusion = new Diffusion();
                        diffusion.setDateDiffusion(new Date());
                        diffusion.setFilm(film);
                        diffusion.setPrix(prices[new Random().nextInt(prices.length)]);
                        diffusion.setSalle(salle);
                        diffusion.setSeance(seance);
                        diffusionRepository.save(diffusion);
                    });
                });
            });
        });
        ;
    }

    @Override
    public void initTickets() {
        diffusionRepository.findAll().forEach(p -> {
            p.getSalle().getPlaces().forEach(place -> {
                Ticket ticket = new Ticket();
                ticket.setPlace(place);
                ticket.setPrix(p.getPrix());
                ticket.setDiffusion(p);
                ticket.setReservee(false);
                ticketRepository.save(ticket);
            });
        });
    }
}
