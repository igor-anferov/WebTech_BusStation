package bus_station.web.controllers;

import bus_station.DAO.Clients;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Igor on 23.04.2017.
 */

@Controller
@RequestMapping("/clients")
public class ClientsController {
    @RequestMapping(method = RequestMethod.GET)
    public String list(ModelMap model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "bus_station.jpa" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Clients clients = new Clients(entityManager);

        model.addAttribute("ClientsList", clients.list());

        return "clients";
    }
}
