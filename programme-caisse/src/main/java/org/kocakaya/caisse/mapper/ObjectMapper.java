package org.kocakaya.caisse.mapper;

import java.util.List;

import org.kocakaya.caisse.business.Sale;
import org.kocakaya.caisse.business.SaleData;
import org.kocakaya.caisse.business.Server;
import org.kocakaya.caisse.business.User;
import org.kocakaya.caisse.service.dto.DailySalesDTO;
import org.kocakaya.caisse.service.dto.ServerDTO;
import org.kocakaya.caisse.service.dto.UserDTO;

public final class ObjectMapper {

    private ObjectMapper() {
    }

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

    public static UserDTO userDTO(Integer id, String login, String password) {
	UserDTO userDTO = new UserDTO();
	User user = new User(login, password);
	if (id != null) {
	    user.setId(id);
	}
	userDTO.setUser(user);
	return userDTO;
    }

    public static UserDTO userDTO(String login, String password) {
	return userDTO(null, login, password);
    }

    public static ServerDTO serverDTO(Integer id, String lastName, String firstName) {
	ServerDTO serverDTO = new ServerDTO();
	Server server = new Server(lastName, firstName);
	if (id != null) {
	    server.setId(id);
	}
	serverDTO.setServer(server);
	return serverDTO;
    }

    public static ServerDTO serverDTO(String lastName, String firstName) {
	return serverDTO(null, lastName, firstName);
    }

}
