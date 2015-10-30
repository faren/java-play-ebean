package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Person extends Model{

    @Id
    public Long id;

    public String name;

    public static Finder<Integer, Person> find
            = new Finder<Integer, Person>(Integer.class, Person.class);
}
