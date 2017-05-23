package bus_station.web.controllers;

import bus_station.DAO.*;
import bus_station.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Igor on 21.04.2017.
 */

@Controller
public class RunsController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list_parts(ModelMap model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bus_station.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Stations stations = new Stations(entityManager);
        Parts parts = new Parts(entityManager);

        model.addAttribute("StationsList", stations.list());
        model.addAttribute("PartsList", parts.list());

        return "runs";
    }

    @RequestMapping(value = "/runs/search", method = RequestMethod.POST)
    public String search_runs(@RequestParam String departure_point,
                              @RequestParam String arrival_point,
                              @RequestParam(required=false) List<Integer> intermediate_point,
                              @RequestParam String departure_date,
                              @RequestParam String upper_price,
                              @RequestParam String free_places,
                              ModelMap model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bus_station.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Stations stations = new Stations(entityManager);
        Parts parts = new Parts(entityManager);

        model.addAttribute("departure_point", departure_point);
        model.addAttribute("arrival_point", arrival_point);
        model.addAttribute("intermediate_point", intermediate_point);
        model.addAttribute("departure_date", departure_date);
        model.addAttribute("upper_price", upper_price);
        model.addAttribute("free_places", free_places);

        Station dep_point;

        if (departure_point.equals("null"))
            dep_point = null;
        else
            dep_point = stations.getById(new Integer(departure_point));

        Station arr_point;

        if (arrival_point.equals("null"))
            arr_point = null;
        else
            arr_point = stations.getById(new Integer(arrival_point));

        List<Station> inter_points;

        if (intermediate_point == null)
            inter_points = null;
        else {
            inter_points = new ArrayList<>();
            for (Integer integer : intermediate_point) {
                inter_points.add(stations.getById(integer));
            }
        }

        Date dep_date;

        if (departure_date.equals(""))
            dep_date = null;
        else {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            df.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
            try {
                dep_date = df.parse(departure_date);
            } catch (ParseException e) {
                dep_date = null;
            }
        }

        BigDecimal up_p;

        if (upper_price.equals(""))
            up_p = null;
        else
            up_p = new BigDecimal(upper_price);

        Integer free_seats;

        if (free_places.equals(""))
            free_seats = null;
        else
            free_seats = new Integer(free_places);

        model.addAttribute("StationsList", stations.list());
        model.addAttribute("PartsList", parts.find(
                dep_point,
                arr_point,
                inter_points,
                dep_date,
                up_p,
                free_seats));

        return "runs/search_results";
    }

    @RequestMapping(value = "/runs/info", method = { RequestMethod.POST, RequestMethod.GET })
    public String run_info(@RequestParam Integer run,
                              ModelMap model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bus_station.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Runs runs = new Runs(entityManager);
        Parts parts = new Parts(entityManager);

        List<Stop> stops = runs.getSchedule(runs.getById(run));

        model.addAttribute("run", runs.getById(run));
        model.addAttribute("StopsList", stops);

        List<List<Part>> parts_table = new ArrayList<>(stops.size());
        for (int i = 0; i < stops.size(); i++) {
            parts_table.add(new ArrayList<Part>(stops.size()));
            for (int j = 0; j < stops.size(); j++) {
                parts_table.get(i).add(null);
            }
        }

        for (int i=0; i<stops.size(); i++)
            for (int j=i+1; j<stops.size(); j++) {
                parts_table.get(i).set(j, parts.getByStops(stops.get(i), stops.get(j)));
            }

        model.addAttribute("PartsTable", parts_table);

        return "runs/info";
    }

    @RequestMapping(value = "/runs/buy", method = RequestMethod.POST)
    public String buy_tickets_page(@RequestParam Integer run,
                                   ModelMap model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bus_station.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Clients clients = new Clients(entityManager);
        Parts parts = new Parts(entityManager);

        model.addAttribute("part", parts.getById(run));
        model.addAttribute("ClientsList", clients.list());

        return "runs/buy";
    }

    @RequestMapping(value = "/runs/buy_tickets", method = RequestMethod.POST)
    public String buy_tickets(@RequestParam Integer part,
                              @RequestParam(required = false) Integer num_tickets,
                              @RequestParam(required = false) Integer client,
                              @RequestParam(required = false) Integer id,
                              ModelMap model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bus_station.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Clients clients = new Clients(entityManager);
        Orders orders = new Orders(entityManager);
        Parts parts = new Parts(entityManager);
        Stations stations = new Stations(entityManager);

        if (num_tickets != null && num_tickets < 1)
            num_tickets = null;

        model.addAttribute("part", parts.getById(part));
        model.addAttribute("client", client);
        model.addAttribute("num_tickets", num_tickets);
        model.addAttribute("ClientsList", clients.list());

        if (num_tickets == null || client == null) {
            model.addAttribute("error_unfilled", true);
            if (id == null)
                return "runs/buy";
            else
                return "clients/orders/edit";
        }

        try {
            entityManager.getTransaction().begin();
            if (id == null) {
                orders.add(new Order(clients.getById(client), parts.getById(part), num_tickets));
            } else {
                Order order = orders.getById(id);
                order.setClient(clients.getById(client));
                order.setCount(num_tickets);
            }
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            model.addAttribute("error_no_tickets", true);
            if (id == null)
                return "runs/buy";
            else
                return "clients/orders/edit";
        }

        entityManager.clear();

        if (id == null) {
            model.addAttribute("StationsList", stations.list());
            model.addAttribute("PartsList", parts.list());

            return "runs";
        } else {
            model.addAttribute("Client", orders.getById(id).getClient());

            return "clients/orders";
        }
    }

    @RequestMapping(value = "/runs/rm", method = RequestMethod.POST)
    public String remove_run(@RequestParam Integer run_id,
                              ModelMap model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bus_station.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Runs runs = new Runs(entityManager);

        entityManager.getTransaction().begin();
        runs.remove(runs.getById(run_id));
        entityManager.flush();
        entityManager.getTransaction().commit();

        return "redirect:/";
    }

    @RequestMapping(value = "/runs/add", method = RequestMethod.GET)
    public String add_run(ModelMap model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bus_station.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Companies companies = new Companies(entityManager);

        model.addAttribute("CompaniesList", companies.list());

        return "/runs/add";
    }

    @RequestMapping(value = "/runs/edit", method = RequestMethod.POST)
    public String edit_run(@RequestParam Integer run_id,
            ModelMap model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bus_station.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Runs runs = new Runs(entityManager);
        Companies companies = new Companies(entityManager);

        Run r = runs.getById(run_id);

        model.addAttribute("run_id", run_id);
        model.addAttribute("company", r.getCompany().getId());
        model.addAttribute("CompaniesList", companies.list());
        model.addAttribute("run_number", r.getNumber());
        model.addAttribute("bus_capacity", r.getBusCapacity());

        return "/runs/edit";
    }

    @RequestMapping(value = "/runs/edit_done", method = RequestMethod.POST)
    public String run_edit_done(@RequestParam(required = false) Integer run_id,
                                @RequestParam(required = false) Integer company,
                                @RequestParam(required = false) String run_number,
                                @RequestParam(required = false) Integer bus_capacity,
                                @RequestParam(required = false) Integer stops_count,
                                ModelMap model,
                                RedirectAttributes redirectAttributes) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bus_station.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Runs runs = new Runs(entityManager);
        Companies companies = new Companies(entityManager);
        Stations stations = new Stations(entityManager);

        if (run_number.equals("")) {
            run_number = null;
        }

        model.addAttribute("run_id", run_id);
        model.addAttribute("company", company);
        model.addAttribute("CompaniesList", companies.list());
        model.addAttribute("run_number", run_number);
        model.addAttribute("bus_capacity", bus_capacity);
        model.addAttribute("stops_count", stops_count);

        if (company == null || run_number == null || bus_capacity == null || (run_id == null && stops_count == null) ) {
            model.addAttribute("error_unfilled", true);
            if (run_id == null)
                return "runs/add";
            else
                return "runs/edit";
        }

        if (run_id != null) {
            Run r = runs.getById(run_id);
            entityManager.getTransaction().begin();
            r.setCompany(companies.getById(company));
            entityManager.flush();
            entityManager.getTransaction().commit();
            try {
                entityManager.getTransaction().begin();
                r.setNumber(run_number);
                entityManager.flush();
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                entityManager.getTransaction().rollback();
                model.addAttribute("error_duplicate_number", true);
                return "runs/edit";
            }
            try {
                entityManager.getTransaction().begin();
                r.setBusCapacity(bus_capacity);
                entityManager.flush();
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                entityManager.getTransaction().rollback();
                model.addAttribute("error_low_bus_capacity", true);
                return "runs/edit";
            }
            redirectAttributes.addAttribute("run", run_id);
            return "redirect:/runs/info";
        } else {
            if (bus_capacity < 0) {
                model.addAttribute("error_negative_bus_capacity", true);
                return "runs/add";
            }
            if (stops_count < 2) {
                model.addAttribute("error_low_stops_count", true);
                return "runs/add";
            }
            Run r = new Run(run_number, companies.getById(company), bus_capacity);
            try {
                entityManager.getTransaction().begin();
                runs.add(r);
                entityManager.flush();
                entityManager.getTransaction().commit();
                model.addAttribute("run_id", r.getId());
            } catch (Exception e) {
                entityManager.getTransaction().rollback();
                model.addAttribute("error_duplicate_number", true);
                return "runs/add";
            }
            model.addAttribute("stationsList", stations.list());
            return "runs/set_stops";
        }
    }

    @RequestMapping(value = "/runs/stops_set", method = RequestMethod.POST)
    public String stops_set(@RequestParam Integer run_id,
                            @RequestParam Integer stops_count,
                            @RequestParam(required = false) List<Integer> selectedStations,
                            @RequestParam(required = false) List<String> arrival,
                            @RequestParam(required = false) List<String> departure,
                            ModelMap model,
                            RedirectAttributes redirectAttributes) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bus_station.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Runs runs = new Runs(entityManager);
        Stops stops = new Stops(entityManager);
        Stations stations = new Stations(entityManager);

        model.addAttribute("run_id", run_id);
        model.addAttribute("stops_count", stops_count);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));

        boolean unfilled = false;

        for (Integer station : selectedStations) {
            if (station == null) {
                unfilled = true;
                break;
            }
        }

        for (int i = 0; i < arrival.size(); i++) {
            if (arrival.get(i) == null) {
                unfilled = true;
            } else {
                try {
                    df.parse(arrival.get(i));
                } catch (ParseException e) {
                    arrival.set(i, null);
                    unfilled = true;
                }
            }
        }

        for (int i = 0; i < departure.size(); i++) {
            if (departure.get(i) == null) {
                unfilled = true;
            } else {
                try {
                    df.parse(departure.get(i));
                } catch (ParseException e) {
                    departure.set(i, null);
                    unfilled = true;
                }
            }
        }

        if (unfilled) {
            model.addAttribute("selectedStations", selectedStations);
            model.addAttribute("arrival", arrival);
            model.addAttribute("departure", departure);
            model.addAttribute("stationsList", stations.list());
            model.addAttribute("error_unfilled", true);
            return "runs/set_stops";
        }

        try {
            entityManager.getTransaction().begin();

            Run run = runs.getById(run_id);

            for (int i = 0; i < selectedStations.size(); i++) {
                Station station = stations.getById(selectedStations.get(i));
                Date arr_date;
                try {
                    arr_date = df.parse(arrival.get(i-1));
                } catch (Exception e) {
                    arr_date = null;
                }
                Date dep_date;
                try {
                    dep_date = df.parse(departure.get(i));
                } catch (Exception e) {
                    dep_date = null;
                }

                stops.add(new Stop(run, station, arr_date, dep_date));
            }

            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            model.addAttribute("selectedStations", selectedStations);
            model.addAttribute("arrival", arrival);
            model.addAttribute("departure", departure);
            model.addAttribute("stationsList", stations.list());
            model.addAttribute("error_wrong_timeline", true);
            return "runs/set_stops";
        }

        redirectAttributes.addAttribute("adding", true);
        redirectAttributes.addAttribute("run_id", run_id);
        return "redirect:/runs/parts/edit";
    }
}