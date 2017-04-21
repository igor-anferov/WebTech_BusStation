package bus_station.DAO;

import bus_station.ScriptRunner.ScriptRunner;
import bus_station.model.Company;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;

import static org.testng.Assert.*;

/**
 * Created by Igor on 07.04.2017.
 */
public class StopsTest {
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
    public void testAddRemove() throws Exception {
        Stops stops = new Stops(entityManager);
        Runs runs = new Runs(entityManager);
        Stations stations = new Stations(entityManager);

        entityManager.getTransaction().begin();
        List<Run> lr = runs.list();
        List<Station> ls = stations.list();
        entityManager.getTransaction().commit();

        Run spbMsc = null;
        Station yar = null;

        for (Run run : lr) {
            if (run.getId() == 1) {
                spbMsc = run;
                break;
            }
        }
        for (Station station : ls) {
            if (station.getName().equals("Ярославль")) {
                yar = station;
            }
        }

        entityManager.getTransaction().begin();
        stops.add(new Stop(spbMsc, yar,
                new GregorianCalendar(2017, Calendar.FEBRUARY, 28, 19, 00,00).getTime(),
                new GregorianCalendar(2017, Calendar.FEBRUARY, 28, 19, 15,00).getTime()));
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        lr = runs.list();
        for (Run run : lr) {
            if (run.getId() == 1) {
                spbMsc = run;
                break;
            }
        }
        Assert.assertEquals(spbMsc.getStops().size(), 5);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        List<Stop> lsms = spbMsc.getStops();
        stops.remove(lsms.get(4));
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        lr = runs.list();
        for (Run run : lr) {
            if (run.getId() == 1) {
                spbMsc = run;
                break;
            }
        }
        Assert.assertEquals(spbMsc.getStops().size(), 4);
        entityManager.getTransaction().commit();
    }
}