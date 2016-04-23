package org.kocakaya.caisse.service.dto;

import org.kocakaya.caisse.business.Server;

public class ServerDTO {

    private Server server;

    public ServerDTO() {
	super();
    }

    public ServerDTO(Server server) {
	super();
	this.server = server;
    }

    public Server getServer() {
	return server;
    }

    public void setServer(Server server) {
	this.server = server;
    }
}
