package bus_station.DAO;

import bus_station.ScriptRunner.ScriptRunner;
import bus_station.model.Company;
import bus_station.model.Run;
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
import java.util.List;
import java.util.regex.Pattern;

import static org.testng.Assert.*;

/**
 * Created by Igor on 05.04.2017.
 */

@Test(singleThreaded=true)
public class RunsTest {
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
    public void testAddRemoveList() throws Exception {
        Runs runs = new Runs(entityManager);
        Companies companies = new Companies(entityManager);

        List<Run> allRuns = runs.list();
        Assert.assertEquals(allRuns.size(), 10);

        Company comp = companies.list().get(0);

        Run new_run = new Run("NEW-001", comp, 60);

        entityManager.getTransaction().begin();
        runs.add(new_run);
        entityManager.getTransaction().commit();

        allRuns = runs.list();
        Assert.assertEquals(allRuns.size(), 11);

        entityManager.getTransaction().begin();
        runs.remove(new_run);
        entityManager.getTransaction().commit();

        allRuns = runs.list();
        Assert.assertEquals(allRuns.size(), 10);
    }

    @Test
    public void testGetSchedule() throws Exception {
        Runs runs = new Runs(entityManager);

        List<Run> allRuns = runs.list();

        Run run_sp248 = null;
        for (Run run : allRuns) {
            if (run.getNumber().equals("СП-248")) {
                run_sp248 = run;
                break;
            }
        }

        List<Stop> run_sp248_sch = runs.getSchedule(run_sp248);

        Assert.assertEquals(run_sp248_sch.size(), 6);
        Assert.assertEquals(run_sp248_sch.get(0).getId(), new Integer(43));
        Assert.assertEquals(run_sp248_sch.get(1).getId(), new Integer(44));
        Assert.assertEquals(run_sp248_sch.get(2).getId(), new Integer(45));
        Assert.assertEquals(run_sp248_sch.get(3).getId(), new Integer(46));
        Assert.assertEquals(run_sp248_sch.get(4).getId(), new Integer(47));
        Assert.assertEquals(run_sp248_sch.get(5).getId(), new Integer(48));
    }

}