package org.kocakaya.caisse.service.impl;

import java.util.List;

import org.kocakaya.caisse.business.User;
import org.kocakaya.caisse.dao.CaisseDao;
import org.kocakaya.caisse.service.UserService;
import org.kocakaya.caisse.service.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DatabaseUserService implements UserService {

    private CaisseDao caisseDao;

    @Override
    public UserDTO findUserWithRoles(UserDTO userDTO) {
	User managedUser = caisseDao.findUser(userDTO.getUser());
	UserDTO managedUserDTO = new UserDTO();
	managedUserDTO.setUser(managedUser);
	return managedUserDTO;
    }

    @Override
    public String findAdminPassword() {
	User user = caisseDao.findAdminUser();
	return user.getPassword();
    }
    
    @Override
    public List<String> usersLogin() {
	List<String> usersLogin = caisseDao.usersLogin();
	return usersLogin;
    }

    @Override
    public CaisseDao caisseDao() {
	return caisseDao;
    }
}
