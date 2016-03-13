package org.kocakaya.caisse.service.dto;

import java.util.List;

import org.kocakaya.caisse.business.Sale;
import org.kocakaya.caisse.business.SaleData;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DailySalesDTO {

    private SaleData saleData;

    private List<Sale> salesByEspeces;
    private List<Sale> salesByChequeBancaire;
    private List<Sale> salesByTicketRestaurant;
    private List<Sale> salesByBonDeCommande;
    private List<Sale> salesByCarteBancaire;
}