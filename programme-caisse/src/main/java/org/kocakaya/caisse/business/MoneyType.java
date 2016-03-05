package org.kocakaya.caisse.business;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "create", buildMethodName = "get")
@Table(name = "MONEY_TYPE")
public class MoneyType implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final MoneyType ESPECES = MoneyType.create().code("ESPECES").get();
    public static final MoneyType CHEQUE_BANCAIRE = MoneyType.create().code("CHEQUE_BANCAIRE").get();
    public static final MoneyType TICKET_RESTAURANT = MoneyType.create().code("TICKET_RESTAURANT").get();
    public static final MoneyType BON_COMMANDE = MoneyType.create().code("BON_COMMANDE").get();
    public static final MoneyType CARTE_BANCAIRE = MoneyType.create().code("CARTE_BANCAIRE").get();
    public static final MoneyType ALL = MoneyType.create().code("ALL").get();

    @Id
    @Column(name = "CODE", nullable = false)
    private String code;
    @Column(name = "DESCRIPTION")
    private String description;
}
