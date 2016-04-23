package org.kocakaya.caisse.service;

import org.kocakaya.caisse.dao.impl.DatabaseCaisseDaoImpl;
import org.kocakaya.caisse.service.impl.DatabaseSaleService;
import org.kocakaya.caisse.service.impl.DatabaseServerService;
import org.kocakaya.caisse.service.impl.DatabaseUserService;

public class ServiceFactory {

    private ServiceFactory() {
    }

    public static UserService getUserService(DaoType daoType) {
	if (DaoType.DATABASE.equals(daoType)) {
	    return new DatabaseUserService(new DatabaseCaisseDaoImpl());
	}
	return null;
    }

    public static SaleService getSaleService(DaoType daoType) {
	if (DaoType.DATABASE.equals(daoType)) {
	    return new DatabaseSaleService(new DatabaseCaisseDaoImpl());
	}
	return null;
    }

    public static ServerService getServerService(DaoType daoType) {
	if (DaoType.DATABASE.equals(daoType)) {
	    return new DatabaseServerService(new DatabaseCaisseDaoImpl());
	}
	return null;
    }
}
