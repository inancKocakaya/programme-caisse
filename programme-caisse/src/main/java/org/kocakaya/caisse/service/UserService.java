package org.kocakaya.caisse.service;

import java.util.List;

import org.kocakaya.caisse.dao.CaisseDao;
import org.kocakaya.caisse.service.dto.UserDTO;

public interface UserService {
    
    public UserDTO findUserWithRoles(UserDTO userDTO);
    
    public String findAdminPassword();
    
    public List<String> usersLogin();
    
    public CaisseDao caisseDao();
}
