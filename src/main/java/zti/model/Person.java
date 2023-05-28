package zti.model;

import lombok.Data;
import jakarta.persistence.*;

import java.io.Serializable;

@Data
@Entity(name = "person")
@Table(name = "person", schema = "public")
@NamedQuery(name = "findAll", query = "SELECT p FROM person p ORDER BY p.lname")
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String city;

    private String email;

    private String fname;

    private String lname;

    private String sex;

    private String tel;
}
