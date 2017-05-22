package bus_station.web.controllers;

import bus_station.DAO.Companies;
import bus_station.model.Company;
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
public class CompaniesController {
    @RequestMapping(value = "/companies/**", method = RequestMethod.GET)
    public String listCompanies(ModelMap model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "bus_station.jpa" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Companies companies = new Companies(entityManager);

        model.addAttribute("CompaniesList", companies.list());

        return "companies";
    }

    @RequestMapping(value = "/companies/rm", method = RequestMethod.POST)
    public String remove_company(@RequestParam Integer company, ModelMap model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "bus_station.jpa" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Companies companies = new Companies(entityManager);

        entityManager.getTransaction().begin();
        companies.remove(companies.getById(company));
        entityManager.getTransaction().commit();

        model.addAttribute("CompaniesList", companies.list());

        return "companies";
    }

    @RequestMapping(value = "/companies/add", method = RequestMethod.GET)
    public String add_company(ModelMap model) {
        return "companies/add";
    }

    @RequestMapping(value = "/companies/edit", method = RequestMethod.POST)
    public String edit_company(@RequestParam Integer company, ModelMap model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "bus_station.jpa" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Companies companies = new Companies(entityManager);

        Company cl = companies.getById(company);

        model.addAttribute("id", company);
        model.addAttribute("name", cl.getName());

        return "companies/edit";
    }

    @RequestMapping(value = "/companies/edit_done", method = RequestMethod.POST)
    public String edit_done(Integer id,
                            @RequestParam String name,
                            ModelMap model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "bus_station.jpa" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Companies companies = new Companies(entityManager);

        if (name.equals("")) {
            model.addAttribute("error", true);

            if (id != null) {
                model.addAttribute("id", id);
                return "companies/edit";
            } else {
                return "companies/add";
            }
        }

        if (id != null) {
            entityManager.getTransaction().begin();
            Company cl = companies.getById(id);
            cl.setName(name);
            entityManager.flush();
            entityManager.getTransaction().commit();
        } else {
            Company cl = new Company(name);
            entityManager.getTransaction().begin();
            companies.add(cl);
            entityManager.flush();
            entityManager.getTransaction().commit();
        }

        model.addAttribute("CompaniesList", companies.list());

        return "companies";
    }
}
