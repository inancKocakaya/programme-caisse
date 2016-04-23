package org.kocakaya.caisse.ui.assembler;

import org.kocakaya.caisse.business.Server;
import org.kocakaya.caisse.service.dto.ServerDTO;

public class ServerDTOAssembler {
    
    private ServerDTOAssembler(){
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