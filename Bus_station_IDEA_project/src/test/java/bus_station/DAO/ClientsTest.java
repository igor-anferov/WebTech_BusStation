package bus_station.DAO;

import bus_station.Main;
import bus_station.ScriptRunner.ScriptRunner;
import bus_station.model.Client;
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

/**
 * Created by Igor on 05.04.2017.
 */

@Test(singleThreaded=true)
public class ClientsTest {
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
    public void testFind() throws Exception {
        Clients clients = new Clients(entityManager);

        entityManager.getTransaction().begin();
        List<Client> l = clients.find(
                "Татьяна",
                null,
                null,
                null,
                null,
                null,
                null);
        entityManager.getTransaction().commit();
        Assert.assertEquals(l.size(), 2);
        Assert.assertEquals(l.get(0), new Client(
                "Татьяна",
                "Меркушева",
                "Сергеевна",
                "Карла Маркса, 37",
                "+79992847501",
                "tatyana-merk@mail.ru"));
        Assert.assertEquals(l.get(1), new Client(
                "Татьяна",
                "Нечаева",
                "Юрьевна",
                "50 лет Октября, 27-104",
                "+79254074876",
                "nech.tat685@gmail.com"));

        entityManager.getTransaction().begin();
        l = clients.find(
                null,
                "Плотников",
                "Вадимович",
                "Ломоносовский пр-т, 27к11",
                "+79505463892",
                "maxyta88@gmail.com",
                true);
        entityManager.getTransaction().commit();
        Assert.assertEquals(l.size(), 1);
        Assert.assertEquals(l.get(0), new Client(
                "Максим",
                "Плотников",
                "Вадимович",
                "Ломоносовский пр-т, 27к11",
                "+79505463892",
                "maxyta88@gmail.com"));

        entityManager.getTransaction().begin();
        l = clients.find(
                null,
                null,
                null,
                null,
                null,
                null,
                false);
        entityManager.getTransaction().commit();
        Assert.assertEquals(l.size(), 5);
    }

    @Test
    public void testGetByRun() throws Exception {
        Clients clients = new Clients(entityManager);

        entityManager.getTransaction().begin();
        List<Client> l = clients.getByRun("AB-379");
        entityManager.getTransaction().commit();
        Assert.assertEquals(l.size(), 2);
        Assert.assertEquals(l.get(0), new Client(
                "Татьяна",
                "Меркушева",
                "Сергеевна",
                "Карла Маркса, 37",
                "+79992847501",
                "tatyana-merk@mail.ru"));
        Assert.assertEquals(l.get(1), new Client(
                "Сторожева",
                "Зоя",
                "Андреевна",
                "Володарского, 27а",
                "+79080481269",
                "storozheva.z11@icloud.com"));
    }

    @Test
    public void testGetByCompany() throws Exception {
        Clients clients = new Clients(entityManager);

        entityManager.getTransaction().begin();
        List<Client> l = clients.getByCompany("Мострансавто");
        entityManager.getTransaction().commit();
        Assert.assertEquals(l.size(), 5);
    }
}