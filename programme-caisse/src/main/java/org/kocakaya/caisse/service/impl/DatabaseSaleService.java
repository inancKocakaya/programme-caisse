package org.kocakaya.caisse.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.kocakaya.caisse.business.CbRecolte;
import org.kocakaya.caisse.business.MoneyType;
import org.kocakaya.caisse.business.Sale;
import org.kocakaya.caisse.business.SaleData;
import org.kocakaya.caisse.business.Server;
import org.kocakaya.caisse.dao.CaisseDao;
import org.kocakaya.caisse.service.SaleService;
import org.kocakaya.caisse.service.dto.DailySalesDTO;
import org.kocakaya.caisse.utils.DateUtils;
import org.kocakaya.caisse.utils.DoubleUtils;
import org.kocakaya.caisse.utils.MonthUtils;

public class DatabaseSaleService implements SaleService {

    private CaisseDao caisseDao;

    private DateUtils dateUtils = new DateUtils();

    public DatabaseSaleService(CaisseDao caisseDao) {
	super();
	this.caisseDao = caisseDao;
    }

    @Override
    public DailySalesDTO salesDetails(Date dateOperation, Server server) {
	DailySalesDTO dailySalesDTO = new DailySalesDTO();

	List<Sale> salesByEspeces = caisseDao.salesDetailsFromDateAndMoneyType(dateOperation, server, MoneyType.ESPECES);
	List<Sale> salesByChequeBancaire = caisseDao.salesDetailsFromDateAndMoneyType(dateOperation, server, MoneyType.CHEQUE_BANCAIRE);
	List<Sale> salesByTicketRestaurant = caisseDao.salesDetailsFromDateAndMoneyType(dateOperation, server, MoneyType.TICKET_RESTAURANT);
	List<Sale> salesByBonDeCommande = caisseDao.salesDetailsFromDateAndMoneyType(dateOperation, server, MoneyType.BON_COMMANDE);
	List<Sale> salesByCarteBancaire = caisseDao.salesDetailsFromDateAndMoneyType(dateOperation, server, MoneyType.CARTE_BANCAIRE);

	SaleData managedSaleData = caisseDao.saleData(server, dateOperation);

	dailySalesDTO.setSaleData(managedSaleData);
	dailySalesDTO.setSalesByEspeces(salesByEspeces);
	dailySalesDTO.setSalesByChequeBancaire(salesByChequeBancaire);
	dailySalesDTO.setSalesByTicketRestaurant(salesByTicketRestaurant);
	dailySalesDTO.setSalesByBonDeCommande(salesByBonDeCommande);
	dailySalesDTO.setSalesByCarteBancaire(salesByCarteBancaire);

	return dailySalesDTO;
    }

    public void saveSales(DailySalesDTO dailySalesDTO) {
	if (dailySalesDTO.getSaleData().getNbCouvert() == null) {
	    caisseDao.saveSaleData(dailySalesDTO.getSaleData());
	} else {
	    caisseDao.updateSaleData(dailySalesDTO.getSaleData());
	}

	// Delete all entities before
	deleteAllEntities(dailySalesDTO);

	for (Sale sale : dailySalesDTO.getSalesByCarteBancaire()) {
	    if (sale.getId() == null) {
		caisseDao.saveSaleDetails(sale);
	    } else {
		caisseDao.updateSaleDetails(sale);
	    }
	}

	for (Sale sale : dailySalesDTO.getSalesByEspeces()) {
	    if (sale.getId() == null) {
		caisseDao.saveSaleDetails(sale);
	    } else {
		caisseDao.updateSaleDetails(sale);
	    }
	}

	for (Sale sale : dailySalesDTO.getSalesByChequeBancaire()) {
	    if (sale.getId() == null) {
		caisseDao.saveSaleDetails(sale);
	    } else {
		caisseDao.updateSaleDetails(sale);
	    }
	}

	for (Sale sale : dailySalesDTO.getSalesByTicketRestaurant()) {
	    if (sale.getId() == null) {
		caisseDao.saveSaleDetails(sale);
	    } else {
		caisseDao.updateSaleDetails(sale);
	    }
	}

	for (Sale sale : dailySalesDTO.getSalesByBonDeCommande()) {
	    if (sale.getId() == null) {
		caisseDao.saveSaleDetails(sale);
	    } else {
		caisseDao.updateSaleDetails(sale);
	    }
	}
    }

    public void deleteAllEntities(DailySalesDTO dailySalesDTO) {
	Date dateOperation = dailySalesDTO.getSaleData().getId().getDateOperation();
	Server server = new Server();
	server.setId(dailySalesDTO.getSaleData().getId().getServerId());
	caisseDao.deleteSales(dateOperation, server, MoneyType.CARTE_BANCAIRE);
	caisseDao.deleteSales(dateOperation, server, MoneyType.CHEQUE_BANCAIRE);
	caisseDao.deleteSales(dateOperation, server, MoneyType.TICKET_RESTAURANT);
	caisseDao.deleteSales(dateOperation, server, MoneyType.BON_COMMANDE);
	caisseDao.deleteSales(dateOperation, server, MoneyType.ESPECES);
    }

    @Override
    public DailySalesDTO saleData(DailySalesDTO dailySalesDTO) {
	DailySalesDTO retrievedDailySalesDTO = new DailySalesDTO();
	Server server = new Server();
	server.setId(dailySalesDTO.getSaleData().getId().getServerId());
	SaleData saleData = caisseDao.saleData(server, dailySalesDTO.getSaleData().getId().getDateOperation());
	retrievedDailySalesDTO.setSaleData(saleData);
	return retrievedDailySalesDTO;
    }

    @Override
    public void updateSaleData(DailySalesDTO dailySalesDTO) {
	SaleData saleData = dailySalesDTO.getSaleData();
	caisseDao.updateSaleData(saleData);
    }

    @Override
    public List<String[]> salesByDay(String dateWithFrenchFormat) {
	List<Object[]> list = caisseDao.salesByDay(dateWithFrenchFormat);
	return formattedSalesStats(list, false);
    }

    @Override
    public List<String[]> salesByMonth(String month) {
	List<Object[]> list = caisseDao.salesByMonth(month);
	return formattedSalesStats(list, false);
    }

    @Override
    public List<String[]> salesByYear(String year) {
	List<Object[]> list = caisseDao.salesByYear(year);
	return formattedSalesStats(list, true);
    }

    @Override
    public List<String[]> ticketsByJour(String month) {
	List<Object[]> list = caisseDao.ticketsByJour(month);
	List<Integer> indexesWithDoubleValue = new ArrayList<>();
	indexesWithDoubleValue.add(2);
	return formattedSimpleObjectArrayAsStringArray(list, indexesWithDoubleValue);
    }

    @Override
    public List<String[]> ticketsByCumul(String month) {
	List<Object[]> list = caisseDao.ticketsCumul(month);
	List<Integer> indexesWithDoubleValue = new ArrayList<>();
	indexesWithDoubleValue.add(1);
	return formattedSimpleObjectArrayAsStringArray(list, indexesWithDoubleValue);
    }

    @Override
    public List<String[]> detailsForTicketsJour(String dateWithFrenchFormat) {
	List<Object[]> list = caisseDao.detailsTicketsByJour(dateWithFrenchFormat);
	List<Integer> indexesWithDoubleValue = new ArrayList<>();
	indexesWithDoubleValue.add(1);
	return formattedSimpleObjectArrayAsStringArray(list, indexesWithDoubleValue);
    }

    @Override
    public List<String[]> detailsForCumulTickets(String month) {
	List<Object[]> list = caisseDao.detailsTicketsCumul(month);
	List<Integer> indexesWithDoubleValue = new ArrayList<>();
	indexesWithDoubleValue.add(1);
	return formattedSimpleObjectArrayAsStringArray(list, indexesWithDoubleValue);
    }

    private List<String[]> formattedSalesStats(List<Object[]> list, boolean isYearSpecific) {
	List<String[]> newList = new ArrayList<>();
	for (Object[] objectArray : list) {
	    String[] stringArray = new String[objectArray.length];
	    if (!isYearSpecific) {
		stringArray[0] = objectArray[0].toString();
	    } else {
		stringArray[0] = MonthUtils.getFrenchMonthNameByIndex(Integer.parseInt(objectArray[0].toString()));
	    }
	    Double caHt = DoubleUtils.stringToDouble(objectArray[1].toString());
	    stringArray[1] = DoubleUtils.doubleToString(caHt);
	    Double caTtc = DoubleUtils.stringToDouble(objectArray[2].toString());
	    stringArray[2] = DoubleUtils.doubleToString(caTtc);
	    Double averageTicket = DoubleUtils.stringToDouble(stringArray[1]) / DoubleUtils.stringToDouble(objectArray[3].toString());
	    stringArray[3] = DoubleUtils.doubleToString(averageTicket);
	    newList.add(stringArray);
	}
	return newList;
    }

    private List<String[]> formattedSimpleObjectArrayAsStringArray(List<Object[]> list, List<Integer> indexesWithDoubleValue) {
	List<String[]> newList = new ArrayList<>();
	for (Object[] objectArray : list) {
	    String[] stringArray = new String[objectArray.length];
	    for (int i = 0; i < objectArray.length; i++) {
		if (indexesWithDoubleValue.contains(i)) {
		    Double value = DoubleUtils.stringToDouble(objectArray[i].toString());
		    stringArray[i] = DoubleUtils.doubleToString(value);
		} else {
		    stringArray[i] = objectArray[i].toString();
		}
	    }
	    newList.add(stringArray);
	}
	return newList;
    }

    @Override
    public List<String[]> detailsForCumulCB() {
	List<String[]> data = new ArrayList<>();
	String[] daysArray = daysOfCurrentMonth();

	for (int i = 0; i < daysArray.length; i++) {
	    String[] row = new String[4];
	    row[0] = daysArray[i];

	    Date date = DateUtils.stringToDate(row[0]);
	    Object cbSalesAmount = caisseDao.cbSalesByDate(row[0]).get(0);
	    if (cbSalesAmount == null) {
		cbSalesAmount = 0;
	    }
	    Double value = DoubleUtils.stringToDouble(cbSalesAmount.toString());
	    row[1] = DoubleUtils.doubleToString(value);

	    Double recoltedAmount = caisseDao.cbRecolteByDate(date).getAmount();
	    row[2] = DoubleUtils.doubleToString(recoltedAmount);

	    row[3] = DoubleUtils.doubleToString(delta(DoubleUtils.stringToDouble(row[2]), DoubleUtils.stringToDouble(row[1])));

	    data.add(row);
	}
	return data;
    }

    private String[] daysOfCurrentMonth() {
	Calendar cal = Calendar.getInstance();
	int nbOfDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

	String[] array = new String[nbOfDays];

	for (int i = 1; i <= nbOfDays; i++) {
	    cal.set(Calendar.DAY_OF_MONTH, i);
	    array[i - 1] = DateUtils.formatDateWithFrenchFormat(cal.getTime());
	}
	return array;
    }

    private double delta(double calculatedAmount, double recoltedAmount) {
	return recoltedAmount - calculatedAmount;
    }

    @Override
    public void saveCbRecolte(List<CbRecolte> cbRecoltes) {
	String month = dateUtils.monthOfCurrentDateWithIncrementedStartedIndex();
	deleteCbRecolteOfCurrentMonth(month);
	for (CbRecolte cbRecolte : cbRecoltes) {
	    caisseDao.saveCbRecolte(cbRecolte);
	}
    }

    private void deleteCbRecolteOfCurrentMonth(String month) {
	caisseDao.deleteCbRecolteByMonth(month);
    }
}