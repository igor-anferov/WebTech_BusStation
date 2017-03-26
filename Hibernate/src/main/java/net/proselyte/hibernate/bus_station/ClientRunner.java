package net.proselyte.hibernate.bus_station;

import net.proselyte.hibernate.bus_station.model.Client;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

public class ClientRunner {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "net.proselyte.hibernate.bus_station.jpa" );

        EntityManager entityManager;

//        entityManager = entityManagerFactory.createEntityManager();
//        entityManager.getTransaction().begin();
//        entityManager.persist( new Client(
//                "Игорь",
//                "Анфёров",
//                "Сергеевич",
//                "Lomonosovsky pr-t, 27k11",
//                "+79996662401",
//                "igor-anferov@mail.ru"
//        ) );
//        entityManager.getTransaction().commit();
//        entityManager.close();

        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Client> clients = entityManager.createQuery( "from Client", Client.class ).getResultList();
        for (Client client : clients) {
            System.out.println(client);
        }
        System.out.println("===================================");
        entityManager.getTransaction().commit();
        entityManager.close();

        entityManagerFactory.close();
    }

}
