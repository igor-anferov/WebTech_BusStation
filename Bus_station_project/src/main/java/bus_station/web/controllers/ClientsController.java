package bus_station.web.controllers;

import bus_station.DAO.Clients;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Igor on 23.04.2017.
 */

@Controller
public class ClientsController {

    @RequestMapping(value = "/clients/**", method = RequestMethod.GET)
    public String list(ModelMap model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "bus_station.jpa" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Clients clients = new Clients(entityManager);

        model.addAttribute("ClientsList", clients.list());

        return "clients";
    }

    @RequestMapping(value = "/clients/search_by_run_number", method = RequestMethod.POST)
    public String search_by_run_number(@RequestParam String run_number, ModelMap model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "bus_station.jpa" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Clients clients = new Clients(entityManager);

        if (run_number.equals(""))
            model.addAttribute("ClientsList", clients.list());
        else
            model.addAttribute("ClientsList", clients.getByRun(run_number));
        model.addAttribute("run_number", run_number);

        return "clients";
    }

    @RequestMapping(value = "/clients/search_by_company", method = RequestMethod.POST)
    public String search_by_company(@RequestParam String company, ModelMap model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "bus_station.jpa" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Clients clients = new Clients(entityManager);

        if (company.equals(""))
            model.addAttribute("ClientsList", clients.list());
        else
            model.addAttribute("ClientsList", clients.getByCompany(company));
        model.addAttribute("company", company);

        return "clients";
    }

    @RequestMapping(value = "/clients/search_by_private_information", method = RequestMethod.POST)
    public String search_by_private_information(@RequestParam String firstName,
                                                @RequestParam String lastName,
                                                @RequestParam String patronymic,
                                                @RequestParam String address,
                                                @RequestParam String telephone,
                                                @RequestParam String email,
                                                Boolean order_history,
                                                ModelMap model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "bus_station.jpa" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Clients clients = new Clients(entityManager);

        if (firstName.equals(""))  firstName = null;
        if (lastName.equals(""))  lastName = null;
        if (patronymic.equals(""))  patronymic = null;
        if (address.equals(""))  address = null;
        if (telephone.equals(""))  telephone = null;
        if (email.equals(""))  email = null;

        model.addAttribute("ClientsList", clients.find(firstName, lastName, patronymic, address, telephone, email, order_history));
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("patronymic", patronymic);
        model.addAttribute("address", address);
        model.addAttribute("telephone", telephone);
        model.addAttribute("email", email);
        model.addAttribute("order_history", order_history);

        return "clients";
    }
}
