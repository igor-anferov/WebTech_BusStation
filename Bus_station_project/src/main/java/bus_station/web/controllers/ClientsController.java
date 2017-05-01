package bus_station.web.controllers;

import bus_station.DAO.Clients;
import bus_station.model.Client;
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

        return "clients/search_results";
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

        return "clients/search_results";
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

        return "clients/search_results";
    }

    @RequestMapping(value = "/clients/orders", method = RequestMethod.POST)
    public String view_orders(@RequestParam Integer client, ModelMap model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "bus_station.jpa" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Clients clients = new Clients(entityManager);

        model.addAttribute("Client", clients.getById(client));

        return "clients/orders";
    }

    @RequestMapping(value = "/clients/rm", method = RequestMethod.POST)
    public String remove_client(@RequestParam Integer client, ModelMap model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "bus_station.jpa" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Clients clients = new Clients(entityManager);

        entityManager.getTransaction().begin();
        clients.remove(clients.getById(client));
        entityManager.getTransaction().commit();

        model.addAttribute("ClientsList", clients.list());

        return "clients";
    }

    @RequestMapping(value = "/clients/add", method = RequestMethod.GET)
    public String add_client(ModelMap model) {
        return "clients/add";
    }

        @RequestMapping(value = "/clients/edit", method = RequestMethod.POST)
    public String edit_client(@RequestParam Integer client, ModelMap model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "bus_station.jpa" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Clients clients = new Clients(entityManager);

        Client cl = clients.getById(client);

        model.addAttribute("id", client);
        model.addAttribute("firstName", cl.getFirstName());
        model.addAttribute("lastName", cl.getLastName());
        model.addAttribute("patronymic", cl.getPatronymic());
        model.addAttribute("address", cl.getAddress());
        model.addAttribute("telephone", cl.getTelephone());
        model.addAttribute("email", cl.getEmail());

        return "clients/edit";
    }

    @RequestMapping(value = "/clients/edit_done", method = RequestMethod.POST)
    public String edit_done(Integer id,
                            @RequestParam String firstName,
                            @RequestParam String lastName,
                            @RequestParam String patronymic,
                            @RequestParam String address,
                            @RequestParam String telephone,
                            @RequestParam String email,
                            ModelMap model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "bus_station.jpa" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Clients clients = new Clients(entityManager);

        if (firstName.equals("") || lastName.equals("") || address.equals("") || telephone.equals("") || email.equals("")) {
            model.addAttribute("error", true);
            model.addAttribute("firstName", firstName);
            model.addAttribute("lastName", lastName);
            model.addAttribute("patronymic", patronymic);
            model.addAttribute("address", address);
            model.addAttribute("telephone", telephone);
            model.addAttribute("email", email);

            if (id != null) {
                model.addAttribute("id", id);
                return "clients/edit";
            } else {
                return "clients/add";
            }
        }

        if (id != null) {
            entityManager.getTransaction().begin();
            Client cl = clients.getById(id);
            cl.setFirstName(firstName);
            cl.setLastName(lastName);
            cl.setPatronymic(patronymic);
            cl.setAddress(address);
            cl.setTelephone(telephone);
            cl.setEmail(email);
            entityManager.flush();
            entityManager.getTransaction().commit();
        } else {
            Client cl = new Client(firstName, lastName, patronymic, address, telephone, email);
            entityManager.getTransaction().begin();
            clients.add(cl);
            entityManager.flush();
            entityManager.getTransaction().commit();
        }

        model.addAttribute("ClientsList", clients.list());

        return "clients";
    }
}
