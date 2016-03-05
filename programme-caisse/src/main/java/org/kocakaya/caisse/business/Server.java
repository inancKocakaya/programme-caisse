package org.kocakaya.caisse.business;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "SERVER")
@NamedQuery(name = "findAll", query = "SELECT s from Server s")
public class Server implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "FIRST_NAME")
    private String firstName;

    public Server(Integer id, String lastName, String firstName) {
	this.id = id;
	this.lastName = lastName;
	this.firstName = firstName;
    }

    public Server(String lastName, String firstName) {
	this.lastName = lastName;
	this.firstName = firstName;
    }

    @Override
    public String toString() {
	return lastName + " " + firstName;
    }
}
