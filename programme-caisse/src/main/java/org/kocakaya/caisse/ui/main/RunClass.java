package org.kocakaya.caisse.ui.main;

import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.Date;

import org.kocakaya.caisse.dao.CaisseDao;
import org.kocakaya.caisse.service.DaoType;
import org.kocakaya.caisse.service.SaleService;
import org.kocakaya.caisse.service.ServiceFactory;
import org.kocakaya.caisse.service.UserService;
import org.kocakaya.caisse.ui.frame.ApplicationManager;
import org.kocakaya.caisse.ui.frame.CaisseFrame;
import org.kocakaya.caisse.utils.DatabaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

public class RunClass {

    private static final Logger LOGGER = LoggerFactory.getLogger(RunClass.class);

    public UserService userService;
    public SaleService saleService;

    public RunClass() {
    }

    public void initApplication(DaoType daoType) throws ClassNotFoundException, SQLException {
	initLogger("conf/logback.xml");
	ApplicationManager.setUserService(ServiceFactory.getUserService(daoType));
	ApplicationManager.setSaleService(ServiceFactory.getSaleService(daoType));
	checkDatabaseState();
	ApplicationManager.setFrame(new CaisseFrame());
    }

    private void checkDatabaseState() throws ClassNotFoundException, SQLException {
	CaisseDao caisseDao = ApplicationManager.getUserService().caisseDao();
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
	    LOGGER.error("Error on initialization of logger");
	    je.printStackTrace();
	}
	LOGGER.debug("Logger initialized from : {}", fileName);
    }

    public static void main(String[] args) {

	EventQueue.invokeLater(new Runnable() {

	    public void run() {
		try {
		    LOGGER.info("Application started at {}", new Date());
		    RunClass runClass = new RunClass();
		    runClass.initApplication(DaoType.DATABASE);
		} catch (ClassNotFoundException e) {
		    LOGGER.error(e.getMessage());
		} catch (SQLException e) {
		    LOGGER.error(e.getMessage());
		} catch (Exception e) {
		    LOGGER.error(e.getMessage());
		    System.exit(0);
		}
	    }
	});
    }
}