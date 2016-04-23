package org.kocakaya.caisse.service.dto;

import java.util.List;

import org.kocakaya.caisse.business.Sale;
import org.kocakaya.caisse.business.SaleData;

public class DailySalesDTO {

    private SaleData saleData;

    private List<Sale> salesByEspeces;
    private List<Sale> salesByChequeBancaire;
    private List<Sale> salesByTicketRestaurant;
    private List<Sale> salesByBonDeCommande;
    private List<Sale> salesByCarteBancaire;

    public SaleData getSaleData() {
	return saleData;
    }

    public void setSaleData(SaleData saleData) {
	this.saleData = saleData;
    }

    public List<Sale> getSalesByEspeces() {
	return salesByEspeces;
    }

    public void setSalesByEspeces(List<Sale> salesByEspeces) {
	this.salesByEspeces = salesByEspeces;
    }

    public List<Sale> getSalesByChequeBancaire() {
	return salesByChequeBancaire;
    }

    public void setSalesByChequeBancaire(List<Sale> salesByChequeBancaire) {
	this.salesByChequeBancaire = salesByChequeBancaire;
    }

    public List<Sale> getSalesByTicketRestaurant() {
	return salesByTicketRestaurant;
    }

    public void setSalesByTicketRestaurant(List<Sale> salesByTicketRestaurant) {
	this.salesByTicketRestaurant = salesByTicketRestaurant;
    }

    public List<Sale> getSalesByBonDeCommande() {
	return salesByBonDeCommande;
    }

    public void setSalesByBonDeCommande(List<Sale> salesByBonDeCommande) {
	this.salesByBonDeCommande = salesByBonDeCommande;
    }

    public List<Sale> getSalesByCarteBancaire() {
	return salesByCarteBancaire;
    }

    public void setSalesByCarteBancaire(List<Sale> salesByCarteBancaire) {
	this.salesByCarteBancaire = salesByCarteBancaire;
    }
}