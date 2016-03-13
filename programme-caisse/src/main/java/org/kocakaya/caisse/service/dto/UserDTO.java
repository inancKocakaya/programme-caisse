package org.kocakaya.caisse.service.dto;

import java.util.Date;

import org.kocakaya.caisse.business.Role;
import org.kocakaya.caisse.business.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

    private User user;

    private Date selectedDate = new Date();

    public boolean hasRoleAdmin() {
	return hasRole(Role.ROLE_ADMIN);
    }

    public boolean hasRoleVisuTr() {
	return hasRole(Role.ROLE_VISU_TR);
    }

    private boolean hasRole(Role role) {
	return user.getRoles().contains(role);
    }
}