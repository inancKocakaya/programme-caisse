package org.kocakaya.caisse.ui.frame;

import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import org.kocakaya.caisse.service.DaoType;
import org.kocakaya.caisse.service.ServiceFactory;

public class CaisseFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public CaisseFrame() {

	initApplication(DaoType.DATABASE);
    }

    private void initApplication(DaoType daoType) {
	ApplicationManager.setUserService(ServiceFactory.getUserService(daoType));
	ApplicationManager.setSaleService(ServiceFactory.getSaleService(daoType));
	ApplicationManager.setServerService(ServiceFactory.getServerService(daoType));
	ApplicationManager.setFrame(this);
	int width = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
	int height = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
	ApplicationManager.init(width, height);
    }
}
