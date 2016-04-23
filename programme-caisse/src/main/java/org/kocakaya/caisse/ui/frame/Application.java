package org.kocakaya.caisse.ui.frame;

import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.WindowConstants;

import org.kocakaya.caisse.business.Server;
import org.kocakaya.caisse.dao.CaisseDao;
import org.kocakaya.caisse.service.ResourcesLoader;
import org.kocakaya.caisse.service.SaleService;
import org.kocakaya.caisse.service.ServerService;
import org.kocakaya.caisse.service.UserService;
import org.kocakaya.caisse.service.dto.DailySalesDTO;
import org.kocakaya.caisse.service.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    private static UserService userService = null;
    private static SaleService saleService = null;
    private static ServerService serverService = null;
    private static CaisseFrame frame = null;
    private static UserDTO connectedUser = null;
    private static ResourceBundle resourceBundle = ResourcesLoader.getInstance().getResourceBundle();

    private Application() {
    }

    public static UserService getUserService() {
	return userService;
    }

    public static void init(int width, int height) {
	try {
	    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		if ("Nimbus".equals(info.getName())) {
		    UIManager.setLookAndFeel(info.getClassName());
		    break;
		}
	    }
	} catch (Exception e) {
	    LOGGER.debug("Error on changing application Look & Feel", e);
	}
	frame.setTitle(resourceBundle.getString("programme.caisse.title.lbl"));
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	frame.setSize(width, height);
	frame.setResizable(false);

	frame.setContentPane(new WelcomePanel().get());
	frame.setVisible(true);
    }

    public static void setUserService(UserService userService) {
	Application.userService = userService;
    }

    public static SaleService getSaleService() {
	return saleService;
    }

    public static ServerService getServerService() {
	return serverService;
    }

    public static void setServerService(ServerService serverService) {
	Application.serverService = serverService;
    }

    public static void setSaleService(SaleService saleService) {
	Application.saleService = saleService;
    }

    public static CaisseFrame getFrame() {
	return frame;
    }

    public static void setFrame(CaisseFrame frame) {
	Application.frame = frame;
    }

    public static UserDTO getConnectedUser() {
	return connectedUser;
    }

    public static void setConnectedUser(UserDTO connectedUser) {
	Application.connectedUser = connectedUser;
    }

    public static CaisseDao getCaisseDao() {
	return userService.caisseDao();
    }

    public static void changePanel(JPanel panel) {

	Application.frame.getContentPane().removeAll();

	Application.frame.setContentPane(panel);

	Application.frame.invalidate();
	Application.frame.validate();
	Application.frame.repaint();
    }

    public static void changeToDailySalesPanel(Server server, Date dateOperation, DailySalesDTO dailySalesDTO) {
	Application.frame.getContentPane().removeAll();
	frame.setContentPane(new DailySalesPanel(server, dateOperation, dailySalesDTO).get());

	Application.frame.invalidate();
	Application.frame.validate();
	Application.frame.repaint();
    }
}
