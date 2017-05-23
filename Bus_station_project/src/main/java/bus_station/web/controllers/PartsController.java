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
 * Created by Igor on 22.05.2017.
 */

@Controller
public class PartsController {
    @RequestMapping(value = "/runs/parts/edit", method = { RequestMethod.POST, RequestMethod.GET })
    public String edit_parts(@RequestParam Integer run_id,
                             @RequestParam(required = false) Boolean adding,
                             ModelMap model) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bus_station.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Runs runs = new Runs(entityManager);
        Parts parts = new Parts(entityManager);

        List<Stop> stops = runs.getSchedule(runs.getById(run_id));

        model.addAttribute("run_id", run_id);
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

        if (adding != null && adding)
            return "/runs/parts/add";

        return "/runs/parts/edit";
    }

    @RequestMapping(value = "/runs/parts/edit_done", method = { RequestMethod.POST, RequestMethod.GET })
    public String parts_edit_done(@RequestParam Integer run_id,
                                  @RequestParam List<Integer> stops_from,
                                  @RequestParam List<Integer> stops_to,
                                  @RequestParam List<Integer> parts_list,
                                  @RequestParam List<BigDecimal> priceList,
                                  ModelMap model,
                                  RedirectAttributes redirectAttributes) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bus_station.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Parts parts = new Parts(entityManager);
        Stops stops = new Stops(entityManager);

        entityManager.getTransaction().begin();

        while (parts_list.size() < stops_to.size())
            parts_list.add(null);

        while (priceList.size() < stops_to.size())
            priceList.add(null);

        for (int i = 0; i < priceList.size(); i++) {
            if (parts_list.get(i) != null) {
                Part p = parts.getById(parts_list.get(i));
                if (priceList.get(i) == null) {
                    parts.remove(p);
                } else {
                    p.setPrice(priceList.get(i));
                }
            } else {
                if (priceList.get(i) != null) {
                    parts.add(new Part(stops.getById(stops_from.get(i)), stops.getById(stops_to.get(i)), priceList.get(i)));
                }
            }
        }

        entityManager.flush();
        entityManager.getTransaction().commit();

        redirectAttributes.addAttribute("run", run_id);
        return "redirect:/runs/info";
    }
}
