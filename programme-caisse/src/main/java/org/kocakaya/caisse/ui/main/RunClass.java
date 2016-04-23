package org.kocakaya.caisse.ui.main;

import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.Date;

import org.kocakaya.caisse.dao.CaisseDao;
import org.kocakaya.caisse.service.DaoType;
import org.kocakaya.caisse.service.ServiceFactory;
import org.kocakaya.caisse.ui.frame.Application;
import org.kocakaya.caisse.ui.frame.CaisseFrame;
import org.kocakaya.caisse.utils.DatabaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

public class RunClass {

    private static final Logger LOGGER = LoggerFactory.getLogger(RunClass.class);

    public void initApplication(DaoType daoType) throws ClassNotFoundException, SQLException {
	initLogger("conf/logback.xml");
	Application.setUserService(ServiceFactory.getUserService(daoType));
	Application.setSaleService(ServiceFactory.getSaleService(daoType));
	checkDatabaseState();
	Application.setFrame(new CaisseFrame());
    }

    private void checkDatabaseState() throws ClassNotFoundException, SQLException {
	CaisseDao caisseDao = Application.getUserService().caisseDao();
	DatabaseUtils databaseUtils = new DatabaseUtils(caisseDao);
	databaseUtils.init();
    }

    private void initLogger(String fileName) {
	LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
	try {
	    JoranConfigurator configurator = new JoranConfigurator();
	    configurator.setContext(lc);
	    lc.reset();
	    configurator.doConfigure(fileName);
	} catch (JoranException je) {
	    LOGGER.error("Error on logger initialization", je);
	}
	LOGGER.debug("Logger initialized from : {}", fileName);
    }

    public static void main(String[] args) {

	EventQueue.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		try {
		    LOGGER.info("Application started at {}", new Date());
		    RunClass runClass = new RunClass();
		    runClass.initApplication(DaoType.DATABASE);
		} catch (ClassNotFoundException e) {
		    LOGGER.error("Class not found exception", e);
		} catch (SQLException e) {
		    LOGGER.error("SQL exception", e);
		} catch (Exception e) {
		    LOGGER.error("Unknown error", e);
		    System.exit(0);
		}
	    }
	});
    }
}