package org.kocakaya.caisse.business;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CB_RECOLTE")
public class CbRecolte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "DATE_OPERATION")
    @Temporal(TemporalType.DATE)
    private Date dateOperation;

    @Column(name = "AMOUNT")
    private double amount;

    public CbRecolte() {
	super();
    }

    public CbRecolte(Integer id, Date dateOperation, double amount) {
	super();
	this.id = id;
	this.dateOperation = dateOperation;
	this.amount = amount;
    }

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public Date getDateOperation() {
	return dateOperation;
    }

    public void setDateOperation(Date dateOperation) {
	this.dateOperation = dateOperation;
    }

    public double getAmount() {
	return amount;
    }

    public void setAmount(double amount) {
	this.amount = amount;
    }
}