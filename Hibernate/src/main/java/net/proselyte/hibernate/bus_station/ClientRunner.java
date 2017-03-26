package net.proselyte.hibernate.bus_station;

import net.proselyte.hibernate.bus_station.model.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ClientRunner {
    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        sessionFactory = new Configuration().configure().buildSessionFactory();

        ClientRunner clientRunner = new ClientRunner();

//        System.out.println("Adding client's records to the DB");
//        /**
//         *  Adding client's records to the database (DB)
//         */
//        clientRunner.addClient(
//                "Игорь",
//                "Анфёров",
//                "Сергеевич",
//                "Lomonosovsky pr-t, 27k11",
//                "+79996662401",
//                "igor-anferov@mail.ru"
//        );

        System.out.println("List of clients");
        /**
         * List clients
         */
        List<Client> clients = clientRunner.listClients();
        for (Client client : clients) {
            System.out.println(client);
        }
        System.out.println("===================================");
//        System.out.println("Removing Some Client");
//        /**
//         * Update and Remove clients
//         */
//        clientRunner.updateClient(10, 3);
//        clientRunner.removeClient(6);

//        System.out.println("Final list of clients");
//        /**
//         * List clients
//         */
//        clients = clientRunner.listClients();
//        for (Client client : clients) {
//            System.out.println(client);
//        }
//        System.out.println("===================================");

        sessionFactory.close();
    }

    public void addClient(String firstName, String lastName, String patronymic, String address, String telephone, String email) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        Client client = new Client(firstName, lastName, patronymic, address, telephone, email);
        session.save(client);
        transaction.commit();
        session.close();
    }

    public List<Client> listClients() {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        List<Client> clients = session.createQuery("FROM Client").list();

        transaction.commit();
        session.close();
        return clients;
    }

//    public void updateClient(int clientId, int experience) {
//        Session session = this.sessionFactory.openSession();
//        Transaction transaction = null;
//
//        transaction = session.beginTransaction();
//        Client client = (Client) session.get(Client.class, clientId);
//        client.setExperience(experience);
//        session.update(client);
//        transaction.commit();
//        session.close();
//    }

    public void removeClient(int clientId) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        Client client = (Client) session.get(Client.class, clientId);
        session.delete(client);
        transaction.commit();
        session.close();
    }

}
