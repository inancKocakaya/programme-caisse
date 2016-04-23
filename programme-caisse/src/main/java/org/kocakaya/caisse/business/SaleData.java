package org.kocakaya.caisse.business;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SALE_DATA")
public class SaleData {

    @EmbeddedId
    SaleId id;

    @Column(name = "NB_COUVERT")
    private Integer nbCouvert;

    @Column(name = "AMOUNT_10_TAXES")
    private Double amount10Taxes;

    @Column(name = "AMOUNT_20_TAXES")
    private Double amount20Taxes;

    @Column(name = "IS_LOCKED")
    private boolean isLocked;

    public SaleData() {
	super();
    }

    public SaleId getId() {
	return id;
    }

    public void setId(SaleId id) {
	this.id = id;
    }

    public Integer getNbCouvert() {
	return nbCouvert;
    }

    public void setNbCouvert(Integer nbCouvert) {
	this.nbCouvert = nbCouvert;
    }

    public Double getAmount10Taxes() {
	return amount10Taxes;
    }

    public void setAmount10Taxes(Double amount10Taxes) {
	this.amount10Taxes = amount10Taxes;
    }

    public Double getAmount20Taxes() {
	return amount20Taxes;
    }

    public void setAmount20Taxes(Double amount20Taxes) {
	this.amount20Taxes = amount20Taxes;
    }

    public boolean isLocked() {
	return isLocked;
    }

    public void setLocked(boolean isLocked) {
	this.isLocked = isLocked;
    }
}