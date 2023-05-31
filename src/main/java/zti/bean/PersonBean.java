package zti.bean;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.util.LangUtils;
import zti.model.Person;
import zti.dao.PersonDao;
import java.io.Serializable;
import java.util.List;
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

    @Getter @Setter
    private List<Person> selectedPersons;

    private PersonDao personDao;

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

    public void createNew()
    {
//        this.person = new Person();
        this.person = persons.get(0);
    }

    public void savePerson()
    {
        personDao.update(person);
    }

    public void deletePerson()
    {
        personDao.delete(person);
        persons.remove(person);
        PrimeFaces.current().ajax().update("form:dt-persons");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedPersons()) {
            int size = this.selectedPersons.size();
            return size > 1 ? size + " persons selected" : "1 person selected";
        }

        return "Delete";
    }

    public boolean hasSelectedPersons() {
        return this.selectedPersons != null && !this.selectedPersons.isEmpty();
    }

    public void deleteSelectedPersons() {
        for (Person selectedPerson : selectedPersons) {
            personDao.delete(selectedPerson);
        }
        this.persons.removeAll(this.selectedPersons);
        this.selectedPersons = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Persons Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-persons");
        PrimeFaces.current().executeScript("PF('dtPersons').clearFilters()");
    }
}
