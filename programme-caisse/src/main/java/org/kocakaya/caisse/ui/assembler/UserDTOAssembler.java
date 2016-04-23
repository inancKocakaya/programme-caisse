package org.kocakaya.caisse.ui.assembler;

import org.kocakaya.caisse.business.User;
import org.kocakaya.caisse.service.dto.UserDTO;

public class UserDTOAssembler {
   
    private UserDTOAssembler(){
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
}