package org.kocakaya.caisse.service.dto;

import java.util.Date;

import org.kocakaya.caisse.business.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

    private User user;
    
    private Date selectedDate = new Date();
    
}
