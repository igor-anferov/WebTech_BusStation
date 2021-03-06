package bus_station.DAO;

import bus_station.ScriptRunner.ScriptRunner;
import bus_station.model.Part;
import bus_station.model.Run;
import bus_station.model.Station;
import bus_station.model.Stop;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;
import java.util.regex.Pattern;

import static org.testng.Assert.*;

/**
 * Created by Igor on 05.04.2017.
 */

@Test(singleThreaded=true)
public class PartsTest {
    private EntityManagerFactory entityManagerFactory = null;
    private EntityManager entityManager = null;

    @BeforeMethod
    public void setUp() throws Exception {
        String filling = new String(Files.readAllBytes(Paths.get(Paths.get(getClass().getResource("/"+"DB_fill.sql").toURI()).toString())));

        Pattern commentPattern = Pattern.compile("--.*\\n");
        String filling2 = commentPattern.matcher(filling).replaceAll("\n");


        Connection mConnection = DriverManager.getConnection("jdbc:mysql://localhost?user=Hibernate&password=A4h3HzqOOe1kg2vbk4PN&useUnicode=true&characterEncoding=UTF-8&Charset=UTF-8");
        ScriptRunner runner = new ScriptRunner(mConnection, false, false);

        String creation = Paths.get(getClass().getResource("/"+"DB_create.sql").toURI()).toString();
        runner.runScript(new BufferedReader(new FileReader(creation)));
        runner.runScript(new BufferedReader(new StringReader(filling2)));

        mConnection.close();

        entityManagerFactory = Persistence.createEntityManagerFactory( "bus_station.jpa" );
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    public void testListAddRemove() throws Exception {
        Parts parts = new Parts(entityManager);
        Runs runs = new Runs(entityManager);

        entityManager.getTransaction().begin();
        List<Part> l = parts.list();
        Assert.assertEquals(l.size(), 96);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        parts.remove(l.get(50));
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        l = parts.list();
        Assert.assertEquals(l.size(), 95);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        List<Run> lr = runs.list();
        Run r7 = null;
        for (Run run : lr) {
            if (run.getId()==7) {
                r7 = run;
                break;
            }
        }
        Stop s29 = null;
        Stop s30 = null;
        for (Stop stop : r7.getStops()) {
            if (stop.getId() == 29) {
                s29 = stop;
            }
            if (stop.getId() == 30) {
                s30 = stop;
            }
        }
        parts.add(new Part(s29, s30, new BigDecimal(600)));
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        l = parts.list();
        Assert.assertEquals(l.size(), 96);
        entityManager.getTransaction().commit();
    }

    @Test
    public void testFindByArrivalAndOthers() throws Exception {
        Parts parts = new Parts(entityManager);
        Stations stations = new Stations(entityManager);

        entityManager.getTransaction().begin();

        List<Station> allStations = stations.list();
        Station msc = null;

        for (Station station : allStations) {
            if (station.getName().equals("Москва")) {
                msc = station;
                break;
            }
        }

        List<Part> l = parts.find(
                null,
                msc,
                null,
                null,
                new BigDecimal(600),
                11);
        Assert.assertEquals(l.size(), 2);
        Assert.assertEquals(l.get(0).getId(), new Integer(36));
        Assert.assertEquals(l.get(1).getId(), new Integer(66));
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        l = parts.find(
                null,
                null,
                null,
                new GregorianCalendar(2017, Calendar.MARCH, 01, 16, 15,00).getTime(),
                null,
                null);

        Assert.assertEquals(l.size(), 3);
        Assert.assertEquals(l.get(0).getId(), new Integer(19));
        Assert.assertEquals(l.get(1).getId(), new Integer(20));
        Assert.assertEquals(l.get(2).getId(), new Integer(21));
        entityManager.getTransaction().commit();
    }

    @Test
    public void testFindByDepartureAndStop() throws Exception {
        Parts parts = new Parts(entityManager);
        Stations stations = new Stations(entityManager);

        entityManager.getTransaction().begin();

        List<Station> allStations = stations.list();
        List<Station> stops = new ArrayList<>();
        Station msc = null;

        for (Station station : allStations) {
            if (station.getName().equals("Москва")) {
                msc = station;
            }
            if (station.getName().equals("Тверь")) {
                stops.add(station);
            }
        }

        List<Part> l = parts.find(
                msc,
                null,
                stops,
                null,
                null,
                null);
        entityManager.getTransaction().commit();

        Assert.assertEquals(l.size(), 2);
        Assert.assertEquals(l.get(0).getId(), new Integer(2));
        Assert.assertEquals(l.get(1).getId(), new Integer(3));

        entityManager.getTransaction().begin();

        allStations = stations.list();
        stops = new ArrayList<>();
        Station spb = null;

        for (Station station : allStations) {
            if (station.getName().equals("Санкт-Петербург")) {
                spb = station;
            }
            if (station.getName().equals("Тверь")) {
                stops.add(station);
            }
            if (station.getName().equals("Великий Новгород")) {
                stops.add(station);
            }
        }

        l = parts.find(
                null,
                spb,
                stops,
                null,
                null,
                null);
        entityManager.getTransaction().commit();

        Assert.assertEquals(l.size(), 1);
        Assert.assertEquals(l.get(0).getId(), new Integer(3));

        entityManager.getTransaction().begin();
        l = parts.find(
                null,
                null,
                stops,
                null,
                null,
                null);
        Assert.assertEquals(l.size(), 2);
        Assert.assertEquals(l.get(0).getId(), new Integer(3));
        Assert.assertEquals(l.get(1).getId(), new Integer(9));

        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        l = parts.find(
                msc,
                spb,
                stops,
                null,
                null,
                null);
        Assert.assertEquals(l.size(), 1);
        Assert.assertEquals(l.get(0).getId(), new Integer(3));

        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        l = parts.find(
                null,
                spb,
                null,
                new GregorianCalendar(2017, Calendar.FEBRUARY, 28, 12, 15,00).getTime(),
                null,
                null);
        Assert.assertEquals(l.size(), 1);
        Assert.assertEquals(l.get(0).getId(), new Integer(5));

        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        l = parts.find(
                null,
                null,
                null,
                null,
                new BigDecimal(600),
                30);
        Assert.assertEquals(l.size(), 6);

        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        l = parts.find(
                null,
                null,
                null,
                null,
                null,
                60);
        Assert.assertEquals(l.size(), 4);

        entityManager.getTransaction().commit();


    }

}