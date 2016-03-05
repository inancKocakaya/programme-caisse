package org.kocakaya.caisse.service;

import java.util.Date;
import java.util.List;

import org.kocakaya.caisse.business.CbRecolte;
import org.kocakaya.caisse.business.Server;
import org.kocakaya.caisse.service.dto.DailySalesDTO;

public interface SaleService {
    
    public DailySalesDTO salesDetails(Date dateOperation, Server server);
    
    public void saveSales(DailySalesDTO dailySalesDTO);
    
    public DailySalesDTO saleData(DailySalesDTO dailySalesDTO);
    
    public void updateSaleData(DailySalesDTO dailySalesDTO);
    
    public List<String[]> salesByDay(String dateWithFrenchFormat);
    
    public List<String[]> salesByMonth(String month);
    
    public List<String[]> salesByYear(String year);
    
    public List<String[]> ticketsByJour(String month);
    
    public List<String[]> detailsForTicketsJour(String dateWithFrenchFormat);
    
    public List<String[]> ticketsByCumul(String month);
    
    public List<String[]> detailsForCumulTickets(String month);
    
    public List<String[]> detailsForCumulCB();
    
    public void saveCbRecolte(List<CbRecolte> cbRecoltes);
}