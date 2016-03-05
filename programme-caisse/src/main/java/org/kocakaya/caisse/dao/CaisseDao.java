package org.kocakaya.caisse.dao;

import java.util.Date;
import java.util.List;

import org.kocakaya.caisse.business.CbRecolte;
import org.kocakaya.caisse.business.MoneyType;
import org.kocakaya.caisse.business.Sale;
import org.kocakaya.caisse.business.SaleData;
import org.kocakaya.caisse.business.Server;
import org.kocakaya.caisse.business.User;

public interface CaisseDao {
    
    public List<User> users();
    
    public List<MoneyType> moneyTypes();
    
    public void deleteSales(Date date, Server server, MoneyType moneyType);

    public List<Sale> salesDetailsFromDate(Date date);

    public List<Sale> salesDetailsFromDateAndMoneyType(Date date, Server server, MoneyType moneyType);

    public SaleData saleData(Server server, Date dateOperation);

    public void saveSaleData(SaleData saleData);

    public void updateSaleData(SaleData saleData);

    public void saveSaleDetails(Sale sale);

    public void updateSaleDetails(Sale sale);

    public void addUser(User user);

    public User findUser(User user);

    public User findAdminUser();

    public void saveServer(Server server);

    public void updateServer(Server server);

    public void updateServers(List<Server> servers);

    public List<Server> servers();

    public List<Object[]> salesByDay(String dateWithFrenchFormat);

    public List<Object[]> salesByMonth(String month);

    public List<Object[]> salesByYear(String year);

    public List<Object[]> ticketsByJour(String month);
    
    public List<Object[]> detailsTicketsByJour(String dateWithFrenchFormat);
    
    public List<Object[]> ticketsCumul(String month);
     
    public List<Object[]> detailsTicketsCumul(String month);
    
    public CbRecolte cbRecolteByDate(Date dateOperation);
    
    public List<Object[]> cbSalesByDate(String dateWithFrenchFormat);
    
    public void saveCbRecolte(CbRecolte cbRecolte);
    
    public void deleteCbRecolteByMonth(String month);

    public List<String> usersLogin();
}
