package org.kocakaya.caisse.service.dto;

import java.util.Date;

import org.kocakaya.caisse.business.Role;
import org.kocakaya.caisse.business.User;

public class UserDTO {

    private User user;

    private Date selectedDate = new Date();

    public UserDTO() {
	super();
    }

    public UserDTO(User user, Date selectedDate) {
	super();
	this.user = user;
	this.selectedDate = selectedDate;
    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    public Date getSelectedDate() {
	return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
	this.selectedDate = selectedDate;
    }

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