package bus_station.web.controllers;

import bus_station.DAO.Parts;
import bus_station.DAO.Runs;
import bus_station.DAO.Stations;
import bus_station.DAO.Stops;
import bus_station.model.Part;
import bus_station.model.Run;
import bus_station.model.Stop;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static bus_station.Main.entityManager;

/**
 * Created by Igor on 22.05.2017.
 */

@Controller
public class StopsController {
    @RequestMapping(value = "/runs/stops/rm", method = RequestMethod.POST)
    public String remove_stop(@RequestParam Integer stop_id,
                              Model model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bus_station.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Stops stops = new Stops(entityManager);
        Stop stop = stops.getById(stop_id);
        Run run = stop.getRun();
        entityManager.getTransaction().begin();
        stops.remove(stop);
        entityManager.flush();
        entityManager.getTransaction().commit();

        entityManager.clear();

        Runs runs = new Runs(entityManager);
        Parts parts = new Parts(entityManager);

        List<Stop> stops_list = runs.getSchedule(run);

        model.addAttribute("run", run);
        model.addAttribute("StopsList", stops_list);

        List<List<Part>> parts_table = new ArrayList<>(stops_list.size());
        for (int i = 0; i < stops_list.size(); i++) {
            parts_table.add(new ArrayList<Part>(stops_list.size()));
            for (int j = 0; j < stops_list.size(); j++) {
                parts_table.get(i).add(null);
            }
        }

        for (int i=0; i<stops_list.size(); i++)
            for (int j=i+1; j<stops_list.size(); j++) {
                parts_table.get(i).set(j, parts.getByStops(stops_list.get(i), stops_list.get(j)));
            }

        model.addAttribute("PartsTable", parts_table);

        return "/runs/info";
    }

    @RequestMapping(value = "/runs/stops/add", method = RequestMethod.POST)
    public String add_stop(@RequestParam Integer run_id,
                           Model model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "bus_station.jpa" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Stations stations = new Stations(entityManager);

        model.addAttribute("run_id", run_id);
        model.addAttribute("StationsList", stations.list());

        return "/runs/stops/add";
    }

    @RequestMapping(value = "/runs/stops/edit", method = RequestMethod.POST)
    public String edit_stop(@RequestParam Integer stop_id,
                            @RequestParam Integer run_id,
                            Model model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "bus_station.jpa" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Stations stations = new Stations(entityManager);
        Stops stops = new Stops(entityManager);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH);
        Date arrival = stops.getById(stop_id).getArrival();
        Date departure = stops.getById(stop_id).getDeparture();
        String arr_str = "";
        String dep_str = "";
        if (arrival != null)
            arr_str = formatter.format(arrival);
        if (departure != null)
            dep_str = formatter.format(departure);

        model.addAttribute("run_id", run_id);
        model.addAttribute("stop", stops.getById(stop_id));
        model.addAttribute("station", stops.getById(stop_id).getStation().getId());
        model.addAttribute("arrival", arr_str);
        model.addAttribute("departure", dep_str);
        model.addAttribute("StationsList", stations.list());

        return "/runs/stops/edit";
    }

    @RequestMapping(value = "/runs/stops/edit_done", method = RequestMethod.POST)
    public String edit_stop_done(@RequestParam Integer run_id,
                                 @RequestParam(required = false) Integer stop_id,
                                 @RequestParam(required = false) Integer station,
                                 @RequestParam(required = false) String arrival,
                                 @RequestParam(required = false) String departure,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "bus_station.jpa" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Stations stations = new Stations(entityManager);
        Stops stops = new Stops(entityManager);
        Runs runs = new Runs(entityManager);

        boolean check_arrival = true;
        boolean check_departure = true;

        if (stop_id != null && stops.getById(stop_id).getArrival() == null)
            check_arrival = false;
        if (stop_id != null && stops.getById(stop_id).getDeparture() == null)
            check_departure = false;

        Date dep_date;
        Date arr_date;

        if (departure == null || departure.equals("")) {
            dep_date = null;
            departure = null;
        } else {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH);
            df.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
            try {
                dep_date = df.parse(departure);
            } catch (ParseException e) {
                dep_date = null;
                departure = null;
            }
        }

        if (arrival == null || arrival.equals("")) {
            arr_date = null;
            arrival = null;
        } else {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH);
            df.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
            try {
                arr_date = df.parse(arrival);
            } catch (ParseException e) {
                arr_date = null;
                arrival = null;
            }
        }

        model.addAttribute("run_id", run_id);
        model.addAttribute("station", station);
        model.addAttribute("arrival", arrival);
        model.addAttribute("departure", departure);
        model.addAttribute("StationsList", stations.list());

        if (stop_id != null)
            model.addAttribute("stop", stops.getById(stop_id));

        if (station == null || (check_arrival && arr_date == null) || (check_departure && dep_date == null)) {
            model.addAttribute("error_unfilled", true);
            if (stop_id == null)
                return "/runs/stops/add";
            else
                return "/runs/stops/edit";
        }

        if (stop_id == null) {
            try {
                entityManager.getTransaction().begin();
                stops.add(new Stop(runs.getById(run_id), stations.getById(station), arr_date, dep_date));
                entityManager.flush();
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                entityManager.getTransaction().rollback();
                model.addAttribute("error_wrong_timeline", true);
                if (stop_id == null)
                    return "/runs/stops/add";
                else
                    return "/runs/stops/edit";
            }
        } else {
            try {
                entityManager.getTransaction().begin();
                Stop s = stops.getById(stop_id);
                s.setStation(stations.getById(station));
                s.setArrival(arr_date);
                s.setDeparture(dep_date);
                entityManager.flush();
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                entityManager.getTransaction().rollback();
                model.addAttribute("error_wrong_timeline", true);
                if (stop_id == null)
                    return "/runs/stops/add";
                else
                    return "/runs/stops/edit";
            }
        }

        redirectAttributes.addAttribute("run", run_id);
        return "redirect:/runs/info";
    }
}
