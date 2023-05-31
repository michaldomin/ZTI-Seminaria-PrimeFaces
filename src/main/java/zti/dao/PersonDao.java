package zti.dao;

import org.primefaces.model.SortOrder;
import zti.model.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PersonDao {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU_Postgresql");
    private EntityManager em = emf.createEntityManager();

    public List<Person> findAll() {
        TypedQuery<Person> query = em.createNamedQuery("findAll", Person.class);
        return query.getResultList();
    }

    public void update(Person person) {
        em.getTransaction().begin();
        em.merge(person);
        em.getTransaction().commit();
    }

    public void delete(Person person) {
        em.getTransaction().begin();
        em.remove(person);
        em.getTransaction().commit();
    }
}
