package zti.bean;

import jakarta.annotation.PostConstruct;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import org.primefaces.util.LangUtils;
import zti.model.Person;
import zti.dao.PersonDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@ManagedBean(name="personBean")
@SessionScoped
public class PersonBean implements Serializable {

    @Getter @Setter
    private Person person;

    @Getter @Setter
    private List<Person> persons;

    @Getter @Setter
    private List<Person> filteredPersons;

    private PersonDao personDao;

    @Getter @Setter
    private boolean globalFilterOnly;

    @PostConstruct
    public void init() {
        person = new Person();
        personDao = new PersonDao();
        persons = personDao.findAll();
    }

    public boolean globalFilterFunction(Object value, Object filter) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isBlank(filterText)) {
            return true;
        }
        Person customer = (Person) value;
        return customer.getLname().toLowerCase().contains(filterText)
                || customer.getFname().toLowerCase().contains(filterText)
                || customer.getEmail().toLowerCase().contains(filterText);
    }
    public void toggleGlobalFilter() {
        setGlobalFilterOnly(!isGlobalFilterOnly());
    }

    public void createNew() {
        this.person = new Person();
    }

    public void savePerson()
    {
        personDao.save(person);
    }
}
