package edu.northeastern.cs5200.controllers.hello;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Configure a POJO to map to an SQL table and record instances.
 *
 * The @Entity annotation above maps the HelloObject class to a table called hello.
 * All the properties in the class are mapped to fields of the same name.
 * The @Id annotation configures the id property as the primary key.
 * The @GeneratedValue annotation configures the id property to be generated automatically by the database
 *      e.g., AUTO_INCREMENT.
 */


@Entity(name="hello")
public class HelloObject {
    private String message;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public HelloObject(String message) {
        this.message = message;
    }
    public HelloObject() { }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
