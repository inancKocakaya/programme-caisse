package org.kocakaya.caisse.business;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class SaleId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "DATE_OPERATION")
    @Temporal(TemporalType.DATE)
    Date dateOperation;

    @Column(name = "SERVER_ID")
    Integer serverId;

    public SaleId() {
	super();
    }

    public SaleId(Date dateOperation, Integer serverId) {
	super();
	this.dateOperation = dateOperation;
	this.serverId = serverId;
    }

    public Date getDateOperation() {
	return dateOperation;
    }

    public void setDateOperation(Date dateOperation) {
	this.dateOperation = dateOperation;
    }

    public Integer getServerId() {
	return serverId;
    }

    public void setServerId(Integer serverId) {
	this.serverId = serverId;
    }

    @Override
    public int hashCode() {
	return serverId.hashCode() + dateOperation.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
	if (obj instanceof SaleId) {
	    SaleId saleId = (SaleId) obj;
	    return this.dateOperation.equals(saleId.getDateOperation()) && this.serverId.equals(saleId.getServerId());
	}
	return false;
    }
}
