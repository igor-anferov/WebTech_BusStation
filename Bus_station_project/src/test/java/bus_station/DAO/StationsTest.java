package bus_station.DAO;

import bus_station.ScriptRunner.ScriptRunner;
import bus_station.model.Station;
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
 * Created by Igor on 09.04.2017.
 */
public class StationsTest {
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
        Stations stations = new Stations(entityManager);

        entityManager.getTransaction().begin();
        List<Station> l = stations.list();
        Assert.assertEquals(l.size(), 20);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        Station prm = new Station("Пермь");
        stations.add(prm);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        l = stations.list();
        Assert.assertEquals(l.size(), 21);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        stations.remove(prm);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        l = stations.list();
        Assert.assertEquals(l.size(), 20);
        entityManager.getTransaction().commit();
    }

}