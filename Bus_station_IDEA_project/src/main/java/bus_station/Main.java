package bus_station;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.*;

public class Main {
    public static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "bus_station.jpa" );
    public static EntityManager entityManager = entityManagerFactory.createEntityManager();
    public static void main(String[] args) {

        entityManager.close();
        entityManagerFactory.close();
    }

}
