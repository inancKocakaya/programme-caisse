package org.kocakaya.caisse.business;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SaleId implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "DATE_OPERATION")
    @Temporal(TemporalType.DATE)
    Date dateOperation;
    @Column(name = "SERVER_ID")
    Integer serverId;
}
