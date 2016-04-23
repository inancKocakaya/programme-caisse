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
	Application.setUserService(ServiceFactory.getUserService(daoType));
	Application.setSaleService(ServiceFactory.getSaleService(daoType));
	Application.setServerService(ServiceFactory.getServerService(daoType));
	Application.setFrame(this);
	int width = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
	int height = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
	Application.init(width, height);
    }
}
