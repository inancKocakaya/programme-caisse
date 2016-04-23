package org.kocakaya.caisse.business;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ROLE")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Role ROLE_ADMIN = new Role(1, "ADMIN", "ADMIN");
    public static final Role ROLE_SAISIE = new Role(2, "SAISIE", "SAISIE CAISSE");
    public static final Role ROLE_VISU_TR = new Role(3, "VISU_TR", "VISUALISATION TR");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    public Role() {
	super();
    }

    public Role(int id, String name, String description) {
	super();
	this.id = id;
	this.name = name;
	this.description = description;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }
}