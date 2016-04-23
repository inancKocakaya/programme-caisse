package org.kocakaya.caisse.service.impl;

import java.util.List;

import org.kocakaya.caisse.business.Server;
import org.kocakaya.caisse.dao.CaisseDao;
import org.kocakaya.caisse.service.ServerService;
import org.kocakaya.caisse.service.dto.ServerDTO;

public class DatabaseServerService implements ServerService {

    private CaisseDao caisseDao;

    public DatabaseServerService(CaisseDao caisseDao) {
	super();
	this.caisseDao = caisseDao;
    }

    @Override
    public void saveServer(ServerDTO serverDTO) {
	caisseDao.saveServer(serverDTO.getServer());
    }

    @Override
    public void updateServer(ServerDTO serverDTO) {
	caisseDao.updateServer(serverDTO.getServer());
    }

    @Override
    public List<Server> servers() {
	return caisseDao.servers();
    }
}
