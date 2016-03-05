package org.kocakaya.caisse.business;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SALE_DETAILS")
public class Sale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;
    @Column(name = "DATE_OPERATION")
    @Temporal(TemporalType.DATE)
    private Date dateOperation;
    @Column(name = "QTY")
    private Integer quantity;
    @Column(name = "AMOUNT")
    private double amount;
    @JoinColumn(name = "PAYMENT_TYPE_ID", nullable = false)
    @ManyToOne
    private MoneyType paymentType;
    @JoinColumn(name = "SERVER_ID", nullable = false)
    @ManyToOne
    private Server server;
}
