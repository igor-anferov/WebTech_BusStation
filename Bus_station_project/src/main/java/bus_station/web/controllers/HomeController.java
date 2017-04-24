package bus_station.web.controllers;

import bus_station.DAO.Parts;
import bus_station.DAO.Stations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Igor on 21.04.2017.
 */

@Controller
@RequestMapping("/")
public class HomeController {

    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "bus_station.jpa" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Stations stations = new Stations(entityManager);
        Parts parts = new Parts(entityManager);

        model.addAttribute("StationsList", stations.list());
        model.addAttribute("PartsList", parts.list());

        return "home";
    }
}