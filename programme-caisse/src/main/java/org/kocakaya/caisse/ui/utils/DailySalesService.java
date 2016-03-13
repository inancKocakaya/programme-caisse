package org.kocakaya.caisse.ui.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kocakaya.caisse.business.MoneyType;
import org.kocakaya.caisse.business.Sale;
import org.kocakaya.caisse.business.Server;
import org.kocakaya.caisse.service.TaxType;
import org.kocakaya.caisse.service.dto.DailySalesDTO;
import org.kocakaya.caisse.ui.component.MyDefaultModelTable;
import org.kocakaya.caisse.utils.DoubleUtils;
import org.kocakaya.caisse.utils.EspecesUtils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DailySalesService {

    private Date dateOperation;
    private Server server;

    public DailySalesService(Date dateOperation, Server server) {
	super();
	this.dateOperation = dateOperation;
	this.server = server;
    }

    public Double averageTicket(Double amount, int nbCouverts) {
	return new Double(amount / nbCouverts);
    }

    public Double amountWithoutTaxes(Double amount, TaxType taxType) {
	Double newAmount = amount * (1 - (taxType.getValue() / 100));
	return newAmount;
    }

    public Double sum(List<String[]> data) {
	Double total = 0d;
	for (String[] row : data) {
	    if (row.length == 2) {
		total += DoubleUtils.stringToDouble(row[1]);
	    } else {
		total += DoubleUtils.stringToDouble(row[1]) * DoubleUtils.stringToDouble(row[2]);
	    }
	}
	return total;
    }

    public Integer sumIntegerColumn(List<String[]> data, int columnNb) {
	Integer total = 0;
	for (String[] row : data) {
	    total += Integer.parseInt(row[columnNb - 1]);
	}
	return total;
    }

    public Double sumDoubleColumn(List<String[]> data, int columnNb) {
	Double total = 0d;
	for (String[] row : data) {
	    total += DoubleUtils.stringToDouble(row[columnNb - 1]);
	}
	return total;
    }

    public List<String[]> tableForCarteBancaireData(DailySalesDTO dailySalesDTO) {
	List<String[]> data = new ArrayList<>();
	for (Sale sale : dailySalesDTO.getSalesByCarteBancaire()) {
	    String[] row = new String[2];
	    row[0] = String.valueOf(sale.getId());
	    row[1] = DoubleUtils.doubleToString(sale.getAmount());
	    data.add(row);
	}
	return data;
    }

    public List<Sale> salesForCarteBancaireData(MyDefaultModelTable table) {
	List<Sale> sales = new ArrayList<>();
	for (String[] row : table.getData()) {
	    Sale sale = new Sale();
	    if (row[0] != null) {
		sale.setId(Integer.parseInt(row[0]));
	    }
	    sale.setDateOperation(dateOperation);
	    sale.setServer(server);
	    sale.setQuantity(1);
	    sale.setAmount(DoubleUtils.stringToDouble(row[1]));
	    sale.setPaymentType(MoneyType.CARTE_BANCAIRE);
	    sales.add(sale);
	}
	return sales;
    }

    public List<String[]> tableForChequeData(DailySalesDTO dailySalesDTO) {
	List<String[]> data = new ArrayList<>();
	for (Sale sale : dailySalesDTO.getSalesByChequeBancaire()) {
	    String[] row = new String[2];
	    row[0] = String.valueOf(sale.getId());
	    row[1] = DoubleUtils.doubleToString(sale.getAmount());
	    data.add(row);
	}
	return data;
    }

    public List<Sale> salesForChequeData(MyDefaultModelTable table) {
	List<Sale> sales = new ArrayList<>();
	for (String[] row : table.getData()) {
	    Sale sale = new Sale();
	    if (row[0] != null) {
		sale.setId(Integer.parseInt(row[0]));
	    }
	    sale.setDateOperation(dateOperation);
	    sale.setServer(server);
	    sale.setQuantity(1);
	    sale.setAmount(DoubleUtils.stringToDouble(row[1]));
	    sale.setPaymentType(MoneyType.CHEQUE_BANCAIRE);
	    sales.add(sale);
	}
	return sales;
    }

    public List<String[]> tableForTicketRestaurantData(DailySalesDTO dailySalesDTO) {
	List<String[]> data = new ArrayList<>();
	for (Sale sale : dailySalesDTO.getSalesByTicketRestaurant()) {
	    String[] row = new String[4];
	    row[0] = String.valueOf(sale.getId());
	    row[1] = String.valueOf(sale.getQuantity());
	    row[2] = DoubleUtils.doubleToString(sale.getAmount());
	    data.add(row);
	}
	return data;
    }

    public List<Sale> salesForTicketRestaurantData(MyDefaultModelTable table) {
	List<Sale> sales = new ArrayList<>();
	for (String[] row : table.getData()) {
	    Sale sale = new Sale();
	    if (row[0] != null) {
		sale.setId(Integer.parseInt(row[0]));
	    }
	    sale.setDateOperation(dateOperation);
	    sale.setServer(server);
	    sale.setQuantity(Integer.parseInt(row[1]));
	    sale.setAmount(DoubleUtils.stringToDouble(row[2]));
	    sale.setPaymentType(MoneyType.TICKET_RESTAURANT);
	    sales.add(sale);
	}
	return sales;
    }

    public List<String[]> tableForBonDeCommandeData(DailySalesDTO dailySalesDTO) {
	List<String[]> data = new ArrayList<>();
	for (Sale sale : dailySalesDTO.getSalesByBonDeCommande()) {
	    String[] row = new String[2];
	    row[0] = String.valueOf(sale.getId());
	    row[1] = DoubleUtils.doubleToString(sale.getAmount());
	    data.add(row);
	}
	return data;
    }

    public List<Sale> salesForBonDeCommandeData(MyDefaultModelTable table) {
	List<Sale> sales = new ArrayList<>();
	for (String[] row : table.getData()) {
	    Sale sale = new Sale();
	    if (row[0] != null) {
		sale.setId(Integer.parseInt(row[0]));
	    }
	    sale.setDateOperation(dateOperation);
	    sale.setServer(server);
	    sale.setQuantity(1);
	    sale.setAmount(DoubleUtils.stringToDouble(row[1]));
	    sale.setPaymentType(MoneyType.BON_COMMANDE);
	    sales.add(sale);
	}
	return sales;
    }

    public List<String[]> tableForEspecesData(DailySalesDTO dailySalesDTO) {
	List<String[]> data = new ArrayList<>();
	for (Sale sale : dailySalesDTO.getSalesByEspeces()) {
	    String[] row = new String[4];
	    row[0] = String.valueOf(sale.getId());
	    row[1] = String.valueOf(sale.getQuantity());
	    row[2] = DoubleUtils.doubleToString(sale.getAmount());
	    data.add(row);
	}
	if (data.size() == 0) {
	    return defaultDataForEspecesTable();
	}
	return data;
    }

    public List<Sale> salesForEspecesData(MyDefaultModelTable table) {
	List<Sale> sales = new ArrayList<>();
	for (String[] row : table.getData()) {
	    Sale sale = new Sale();
	    if (row[0] != null) {
		sale.setId(Integer.parseInt(row[0]));
	    }
	    sale.setDateOperation(dateOperation);
	    sale.setServer(server);
	    sale.setQuantity(Integer.parseInt(row[1]));
	    sale.setAmount(DoubleUtils.stringToDouble(row[2]));
	    sale.setPaymentType(MoneyType.ESPECES);
	    sales.add(sale);
	}
	return sales;
    }

    private List<String[]> defaultDataForEspecesTable() {
	List<String[]> data = new ArrayList<>();
	for (int i = 0; i < EspecesUtils.SUPPORTED_ESPECES_AMOUNT.length; i++) {
	    String[] row = new String[4];
	    row[0] = null;
	    row[1] = "0";
	    row[2] = EspecesUtils.SUPPORTED_ESPECES_AMOUNT[i];
	    row[3] = null;
	    data.add(row);
	}
	return data;
    }

    public Double diffAmount(Double[] valuesToAdd, Double amountToSubstract) {
	Double sumAmount = 0d;
	
	for (Double val : valuesToAdd){
	    sumAmount += val;
	}
	return sumAmount - amountToSubstract;
    }
}
