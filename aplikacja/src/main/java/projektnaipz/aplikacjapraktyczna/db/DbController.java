package projektnaipz.aplikacjapraktyczna.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DbController {

    EntityManager em;

    public DbController(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("moja-baza");
        em = emf.createEntityManager();
    }

    public void register(String email, String haslo){
        Uzytkownik u = new Uzytkownik();
        u.setEmail(email);
        u.setHaslo(haslo);
        em.getTransaction();
        em.persist(u);
        em.getTransaction().commit();
    }
}
