package org.kocakaya.caisse.ui.assembler;

import java.util.List;

import org.kocakaya.caisse.business.Sale;
import org.kocakaya.caisse.business.SaleData;
import org.kocakaya.caisse.service.dto.DailySalesDTO;

import lombok.Data;

@Data
public class DailySalesDTOAssembler {

    public static DailySalesDTO dailySalesDTO(SaleData saleData) {
	return dailySalesDTO(saleData, null, null, null, null, null);
    }

    public static DailySalesDTO dailySalesDTO(SaleData saleData, List<Sale> salesByEspeces, List<Sale> salesByBonDeCommande, List<Sale> salesByChequeBancaire, List<Sale> salesByTicketRestaurant, List<Sale> salesByCarteBancaire) {

	DailySalesDTO dailySalesDTO = new DailySalesDTO();

	dailySalesDTO.setSaleData(saleData);
	dailySalesDTO.setSalesByEspeces(salesByEspeces);
	dailySalesDTO.setSalesByBonDeCommande(salesByBonDeCommande);
	dailySalesDTO.setSalesByChequeBancaire(salesByChequeBancaire);
	dailySalesDTO.setSalesByTicketRestaurant(salesByTicketRestaurant);
	dailySalesDTO.setSalesByCarteBancaire(salesByCarteBancaire);
	return dailySalesDTO;
    }
}
