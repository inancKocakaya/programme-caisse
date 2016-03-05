package org.kocakaya.caisse.business;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "create", buildMethodName = "get")
@Entity
@Table(name = "ROLE")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    public static Role ROLE_ADMIN = Role.create().id(1).name("ADMIN").description("ADMIN").get();
    public static Role ROLE_SAISIE = Role.create().id(2).name("SAISIE").description("SAISIE CAISSE").get();
    public static Role ROLE_VISU_TR = Role.create().id(3).name("VISU_TR").description("VISUALISATION TR").get();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
}
