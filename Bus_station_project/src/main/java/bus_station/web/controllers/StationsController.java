package bus_station.web.controllers;

import bus_station.DAO.Clients;
import bus_station.DAO.Stations;
import bus_station.DAO.Parts;
import bus_station.DAO.Stations;
import bus_station.model.Client;
import bus_station.model.Station;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Igor on 02.05.2017.
 */

@Controller
public class StationsController {
    @RequestMapping(value = "/stations/**", method = RequestMethod.GET)
    public String listStations(ModelMap model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "bus_station.jpa" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Stations stations = new Stations(entityManager);

        model.addAttribute("StationsList", stations.list());

        return "stations";

    }

    @RequestMapping(value = "/stations/rm", method = RequestMethod.POST)
    public String remove_station(@RequestParam Integer station, ModelMap model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "bus_station.jpa" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Stations stations = new Stations(entityManager);

        entityManager.getTransaction().begin();
        stations.remove(stations.getById(station));
        entityManager.getTransaction().commit();

        model.addAttribute("StationsList", stations.list());

        return "stations";
    }

    @RequestMapping(value = "/stations/add", method = RequestMethod.GET)
    public String add_station(ModelMap model) {
        return "stations/add";
    }

    @RequestMapping(value = "/stations/edit", method = RequestMethod.POST)
    public String edit_station(@RequestParam Integer station, ModelMap model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "bus_station.jpa" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Stations stations = new Stations(entityManager);

        Station cl = stations.getById(station);

        model.addAttribute("id", station);
        model.addAttribute("name", cl.getName());

        return "stations/edit";
    }

    @RequestMapping(value = "/stations/edit_done", method = RequestMethod.POST)
    public String edit_done(Integer id,
                            @RequestParam String name,
                            ModelMap model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "bus_station.jpa" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Stations stations = new Stations(entityManager);

        if (name.equals("")) {
            model.addAttribute("error", true);

            if (id != null) {
                model.addAttribute("id", id);
                return "stations/edit";
            } else {
                return "stations/add";
            }
        }

        if (id != null) {
            entityManager.getTransaction().begin();
            Station cl = stations.getById(id);
            cl.setName(name);
            entityManager.flush();
            entityManager.getTransaction().commit();
        } else {
            Station cl = new Station(name);
            entityManager.getTransaction().begin();
            stations.add(cl);
            entityManager.flush();
            entityManager.getTransaction().commit();
        }

        model.addAttribute("StationsList", stations.list());

        return "stations";
    }
}