package org.kocakaya.caisse.service.impl;

import java.util.List;

import org.kocakaya.caisse.business.User;
import org.kocakaya.caisse.dao.CaisseDao;
import org.kocakaya.caisse.service.UserService;
import org.kocakaya.caisse.service.dto.UserDTO;

public class DatabaseUserService implements UserService {

    private CaisseDao caisseDao;

    public DatabaseUserService(CaisseDao caisseDao) {
	super();
	this.caisseDao = caisseDao;
    }

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
	return caisseDao.usersLogin();
    }

    @Override
    public CaisseDao caisseDao() {
	return caisseDao;
    }

    @Override
    public UserDTO userDTO(User user) {
	return null;
    }
}