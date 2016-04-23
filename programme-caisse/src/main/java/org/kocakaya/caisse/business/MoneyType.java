package org.kocakaya.caisse.business;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MONEY_TYPE")
public class MoneyType implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final MoneyType ESPECES = new MoneyType("ESPECES", "ESPECES");
    public static final MoneyType CHEQUE_BANCAIRE = new MoneyType("CHEQUE_BANCAIRE", "CHEQUE_BANCAIRE");
    public static final MoneyType TICKET_RESTAURANT = new MoneyType("TICKET_RESTAURANT", "TICKET_RESTAURANT");
    public static final MoneyType BON_COMMANDE = new MoneyType("BON_COMMANDE", "BON_COMMANDE");
    public static final MoneyType CARTE_BANCAIRE = new MoneyType("CARTE_BANCAIRE", "CARTE_BANCAIRE");
    public static final MoneyType ALL = new MoneyType("ALL", "ALL");

    @Id
    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "DESCRIPTION")
    private String description;

    public MoneyType() {
	super();
    }

    public MoneyType(String code, String description) {
	super();
	this.code = code;
	this.description = description;
    }
}
