package org.kocakaya.caisse.business;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
}
