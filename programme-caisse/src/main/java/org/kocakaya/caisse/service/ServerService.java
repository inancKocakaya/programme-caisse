package org.kocakaya.caisse.service;

import java.util.List;

import org.kocakaya.caisse.business.Server;
import org.kocakaya.caisse.service.dto.ServerDTO;

public interface ServerService {
    
    public void saveServer(ServerDTO serverDTO);
    
    public void updateServer(ServerDTO serverDTO);
    
    public List<Server> servers();
    
}
