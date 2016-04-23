package org.kocakaya.caisse.ui.frame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;
import javax.swing.text.JTextComponent;

import org.kocakaya.caisse.business.MoneyType;
import org.kocakaya.caisse.business.Sale;
import org.kocakaya.caisse.business.SaleData;
import org.kocakaya.caisse.business.SaleId;
import org.kocakaya.caisse.business.Server;
import org.kocakaya.caisse.exception.CaisseException;
import org.kocakaya.caisse.service.ResourcesLoader;
import org.kocakaya.caisse.service.dto.DailySalesDTO;
import org.kocakaya.caisse.ui.assembler.DailySalesDTOAssembler;
import org.kocakaya.caisse.ui.component.MyDefaultModelTable;
import org.kocakaya.caisse.ui.component.MySalesTable;
import org.kocakaya.caisse.ui.utils.DailySalesService;
import org.kocakaya.caisse.ui.utils.MessageType;
import org.kocakaya.caisse.ui.utils.StateMessageLabelBuilder;
import org.kocakaya.caisse.ui.utils.TitleMessageBuilder;
import org.kocakaya.caisse.ui.utils.UIUtils;
import org.kocakaya.caisse.utils.CryptoUtils;
import org.kocakaya.caisse.utils.DateUtils;
import org.kocakaya.caisse.utils.DoubleUtils;
import org.kocakaya.caisse.utils.ValidatorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class DailySalesPanel extends JPanel implements Panel {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(DailySalesPanel.class);

    private static ResourceBundle resourceBundle = ResourcesLoader.getInstance().getResourceBundle();
    private DailySalesService dailySalesService;

    String messageNotNumericFields = resourceBundle.getString("programme.caisse.fail.notnumeric.lbl");
    String messageFailEmptyPassword = resourceBundle.getString("programme.caisse.fail.emptypassword.lbl");
    String messageSuccessLock = resourceBundle.getString("programme.caisse.success.lockline.lbl");
    String messageSuccessUnLock = resourceBundle.getString("programme.caisse.success.unlockline.lbl");
    String messageFailLock = resourceBundle.getString("programme.caisse.fail.lockline.lbl");
    String messageFailDataNotSaved = resourceBundle.getString("programme.caisse.fail.datanotsaved.lbl");
    String messageSuccessDataSaved = resourceBundle.getString("programme.caisse.success.save.lbl");

    String title = resourceBundle.getString("programme.caisse.daily.sales.title.lbl");

    JLabel lblTitle;

    String validateText = resourceBundle.getString("programme.caisse.validate.lbl");
    String unlockText = resourceBundle.getString("programme.caisse.unlock.lbl");

    private boolean isPanelLocked = false;

    private DailySalesDTO dailySalesDTO;
    private Server server;
    private Date dateOperation;

    JLabel lblMessage;

    JTextField txtAmountWithTaxes;
    JTextField txtNbCouverts;
    JTextField txtAverageTicket;
    JTextField txtAmountWith10PercentTaxes;
    JTextField txtAmountWith20PercentTaxes;
    JTextField txtAmountWithoutTaxes;
    JTextField txtCurrentDate;
    JTextField txtSelectedServer;
    JTextField txtAmountDifferencies;

    JTextField txtTotalAmountCarteBancaire;
    JTextField txtTotalAmountCheque;
    JTextField txtTotalBonDeCommande;
    JTextField txtTotalAmountEspeces;

    JLabel lblTotalWithTaxes;
    JLabel lblNbCouverts;
    JLabel lblAverageTicket;
    JLabel lbl10PercentTaxes;
    JLabel lbl20PercentTaxes;
    JLabel lblWithoutTaxes;
    JLabel lblDate;
    JLabel lblServer;
    JLabel lblTotalTicketRestaurant;

    JButton btnSave;
    JButton btnValidate;
    JButton btnBackMainMenu;
    JButton btnApplySum;

    JButton btnAddCarteBancaireSale;
    JButton btnDeleteCarteBancaireSale;

    JButton btnAddChequeSale;
    JButton btnDeleteChequeSale;

    JButton btnAddTicketRestaurantSale;
    JButton btnDeleteTicketRestaurantSale;

    JButton btnAddBonDeCommandeSale;
    JButton btnDeleteBonDeCommandeSale;

    MyDefaultModelTable salesByCarteBancaire;
    MySalesTable salesByCarteBancaireTable;
    JScrollPane scrollPaneSalesByCarteBancaireTable;

    MyDefaultModelTable salesByCheque;
    MySalesTable salesByChequeTable;
    JScrollPane scrollPaneSalesByChequeTable;

    MyDefaultModelTable salesByTicketRestaurant;
    MySalesTable salesByTicketRestaurantTable;
    JScrollPane scrollPaneSalesByTicketRestaurantTable;

    MyDefaultModelTable salesByBonDeCommande;
    MySalesTable salesByBonDeCommandeTable;
    JScrollPane scrollPaneSalesByBonDeCommandeTable;

    MyDefaultModelTable salesByEspeces;
    MySalesTable salesByEspecesTable;
    JScrollPane scrollPaneSalesByEspecesTable;

    JPasswordField txtPassword;

    String enterPasswordText;

    public DailySalesPanel(Server server, Date dateOperation, DailySalesDTO dailySalesDTO) {
	this.server = server;
	this.dateOperation = dateOperation;
	this.dailySalesDTO = dailySalesDTO;
	this.dailySalesService = new DailySalesService(dateOperation, server);
    }

    @Override
    public JPanel get() {

	lblTitle = TitleMessageBuilder.create().withText(title).withTextToUpperCase().get();

	isPanelLocked = (dailySalesDTO.getSaleData() == null) ? false : dailySalesDTO.getSaleData().isLocked();

	txtPassword = new JPasswordField();

	ImageIcon imageAddSale = new ImageIcon(getClass().getClassLoader().getResource("add_small.png"));
	ImageIcon imageDeleteSale = new ImageIcon(getClass().getClassLoader().getResource("delete_small.png"));

	String detailsText = resourceBundle.getString("programme.caisse.details.lbl");
	String salesText = resourceBundle.getString("programme.caisse.sales.lbl");
	String carteBancaireText = resourceBundle.getString("programme.caisse.creditcard.lbl");
	String chequeText = resourceBundle.getString("programme.caisse.bankcheck.lbl");
	String ticketRestaurantText = resourceBundle.getString("programme.caisse.restaurantticket.lbl");
	String bonDeCommandeText = resourceBundle.getString("programme.caisse.orderform.lbl");
	String especesText = resourceBundle.getString("programme.caisse.especes.lbl");

	String serverText = resourceBundle.getString("programme.caisse.server.lbl");
	String dateText = resourceBundle.getString("programme.caisse.date.lbl");

	String amountText = resourceBundle.getString("programme.caisse.amount.lbl");
	String nbText = resourceBundle.getString("programme.caisse.number.lbl");
	String valueText = resourceBundle.getString("programme.caisse.value.lbl");

	String mainMenuText = resourceBundle.getString("programme.caisse.back.main.menu.lbl");
	String applySumText = resourceBundle.getString("programme.caisse.apply.sum.lbl");

	enterPasswordText = resourceBundle.getString("programme.caisse.enterpassword.lbl");

	lblMessage = StateMessageLabelBuilder.create().get();

	txtAmountWithTaxes = new JTextField(10);
	txtAmountWithTaxes.setEditable(false);
	txtAmountWithTaxes.setBackground(UIUtils.classicGrayColorForCalculatedValue());

	txtNbCouverts = new JTextField(3);

	if (dailySalesDTO.getSaleData() != null) {
	    txtNbCouverts.setText(String.valueOf(dailySalesDTO.getSaleData().getNbCouvert()));
	}

	txtAverageTicket = new JTextField(8);
	txtAverageTicket.setEditable(false);
	txtAverageTicket.setBackground(UIUtils.classicGrayColorForCalculatedValue());

	txtAmountWith10PercentTaxes = new JTextField(10);

	if (dailySalesDTO.getSaleData() != null) {
	    txtAmountWith10PercentTaxes.setText(String.valueOf(dailySalesDTO.getSaleData().getAmount10Taxes()));
	}

	txtAmountWith20PercentTaxes = new JTextField(10);

	if (dailySalesDTO.getSaleData() != null) {
	    txtAmountWith20PercentTaxes.setText(String.valueOf(dailySalesDTO.getSaleData().getAmount20Taxes()));
	}

	txtAmountWithoutTaxes = new JTextField(10);
	txtAmountWithoutTaxes.setEditable(false);
	txtAmountWithoutTaxes.setBackground(UIUtils.classicGrayColorForCalculatedValue());

	txtCurrentDate = new JTextField(10);
	txtCurrentDate.setEditable(false);
	txtCurrentDate.setBackground(UIUtils.classicGrayColorForCalculatedValue());
	txtCurrentDate.setText(DateUtils.formatDateWithFrenchFormat(dateOperation));

	txtSelectedServer = new JTextField();
	txtSelectedServer.setEditable(false);
	txtSelectedServer.setBackground(UIUtils.classicGrayColorForCalculatedValue());
	txtSelectedServer.setText(server.toString());

	txtAmountDifferencies = new JTextField(10);
	txtAmountDifferencies.setEditable(false);
	txtAmountDifferencies.setBackground(UIUtils.classicGrayColorForCalculatedValue());

	txtTotalAmountCarteBancaire = new JTextField(10);
	txtTotalAmountCarteBancaire.setEditable(false);
	txtTotalAmountCarteBancaire.setBackground(UIUtils.classicGrayColorForCalculatedValue());

	txtTotalAmountCheque = new JTextField(10);
	txtTotalAmountCheque.setEditable(false);
	txtTotalAmountCheque.setBackground(UIUtils.classicGrayColorForCalculatedValue());

	lblTotalTicketRestaurant = new JLabel("0 / 0", SwingConstants.CENTER);

	txtTotalBonDeCommande = new JTextField(10);
	txtTotalBonDeCommande.setEditable(false);
	txtTotalBonDeCommande.setBackground(UIUtils.classicGrayColorForCalculatedValue());

	txtTotalAmountEspeces = new JTextField(10);
	txtTotalAmountEspeces.setEditable(false);
	txtTotalAmountEspeces.setBackground(UIUtils.classicGrayColorForCalculatedValue());

	lbl10PercentTaxes = new JLabel(resourceBundle.getString("programme.caisse.tva10.lbl"), SwingConstants.CENTER);
	lbl20PercentTaxes = new JLabel(resourceBundle.getString("programme.caisse.tva20.lbl"), SwingConstants.CENTER);
	lblWithoutTaxes = new JLabel(resourceBundle.getString("programme.caisse.withouttaxes.lbl"), SwingConstants.CENTER);

	lblTotalWithTaxes = new JLabel(resourceBundle.getString("programme.caisse.alltaxes.lbl"), SwingConstants.CENTER);
	lblNbCouverts = new JLabel(resourceBundle.getString("programme.caisse.nbrecouverts.lbl"), SwingConstants.CENTER);
	lblAverageTicket = new JLabel(resourceBundle.getString("programme.caisse.averageticket.lbl"), SwingConstants.CENTER);

	lblDate = new JLabel(dateText, SwingConstants.CENTER);
	lblServer = new JLabel(serverText, SwingConstants.CENTER);

	ImageIcon imageIconSave = new ImageIcon(getClass().getClassLoader().getResource("floppy.png"));
	ImageIcon imageIconValidate = new ImageIcon(getClass().getClassLoader().getResource("validate.png"));

	btnSave = new JButton(resourceBundle.getString("programme.caisse.save.lbl"), imageIconSave);
	btnValidate = new JButton(validateText, imageIconValidate);

	ImageIcon imageBackMainMenu = new ImageIcon(getClass().getClassLoader().getResource("back.png"));
	btnBackMainMenu = new JButton(mainMenuText, imageBackMainMenu);

	ImageIcon imageApply = new ImageIcon(getClass().getClassLoader().getResource("sum.png"));
	btnApplySum = new JButton(applySumText, imageApply);

	List<String[]> dataSalesByCarteBancaire = dailySalesService.tableForCarteBancaireData(dailySalesDTO);
	String[] columnsNameSalesByCarteBancaire = { "id", amountText };

	salesByCarteBancaire = new MyDefaultModelTable(dataSalesByCarteBancaire, columnsNameSalesByCarteBancaire, MoneyType.CARTE_BANCAIRE, isPanelLocked);
	salesByCarteBancaireTable = new MySalesTable(salesByCarteBancaire);
	scrollPaneSalesByCarteBancaireTable = new JScrollPane(salesByCarteBancaireTable);

	TableColumn tableSalesByCarteBancaireColumnToHide = salesByCarteBancaireTable.getColumnModel().getColumn(0);
	salesByCarteBancaireTable.removeColumn(tableSalesByCarteBancaireColumnToHide);

	List<String[]> dataSalesByCheque = dailySalesService.tableForChequeData(dailySalesDTO);
	String[] columnsNameSalesByCheque = { "id", amountText };

	btnAddCarteBancaireSale = new JButton(imageAddSale);
	btnDeleteCarteBancaireSale = new JButton(imageDeleteSale);

	salesByCheque = new MyDefaultModelTable(dataSalesByCheque, columnsNameSalesByCheque, MoneyType.CHEQUE_BANCAIRE, isPanelLocked);
	salesByChequeTable = new MySalesTable(salesByCheque);
	scrollPaneSalesByChequeTable = new JScrollPane(salesByChequeTable);

	TableColumn tableSalesByChequeColumnToHide = salesByChequeTable.getColumnModel().getColumn(0);
	salesByChequeTable.removeColumn(tableSalesByChequeColumnToHide);

	btnAddChequeSale = new JButton(imageAddSale);
	btnDeleteChequeSale = new JButton(imageDeleteSale);

	List<String[]> dataSalesByTicketRestaurant = dailySalesService.tableForTicketRestaurantData(dailySalesDTO);
	String[] columnsNameSalesByTicketRestaurant = { "id", nbText, valueText, amountText };

	salesByTicketRestaurant = new MyDefaultModelTable(dataSalesByTicketRestaurant, columnsNameSalesByTicketRestaurant, MoneyType.TICKET_RESTAURANT, isPanelLocked);
	salesByTicketRestaurantTable = new MySalesTable(salesByTicketRestaurant);
	scrollPaneSalesByTicketRestaurantTable = new JScrollPane(salesByTicketRestaurantTable);

	TableColumn tableSalesByTicketRestaurantColumnToHide = salesByTicketRestaurantTable.getColumnModel().getColumn(0);
	salesByTicketRestaurantTable.removeColumn(tableSalesByTicketRestaurantColumnToHide);

	btnAddTicketRestaurantSale = new JButton(imageAddSale);
	btnDeleteTicketRestaurantSale = new JButton(imageDeleteSale);

	List<String[]> dataSalesByBonDeCommande = dailySalesService.tableForBonDeCommandeData(dailySalesDTO);
	String[] columnsNameSalesByBonDeCommande = { "id", amountText };

	salesByBonDeCommande = new MyDefaultModelTable(dataSalesByBonDeCommande, columnsNameSalesByBonDeCommande, MoneyType.BON_COMMANDE, isPanelLocked);
	salesByBonDeCommandeTable = new MySalesTable(salesByBonDeCommande);
	scrollPaneSalesByBonDeCommandeTable = new JScrollPane(salesByBonDeCommandeTable);

	TableColumn tableSalesBonDeCommandeColumnToHide = salesByBonDeCommandeTable.getColumnModel().getColumn(0);
	salesByBonDeCommandeTable.removeColumn(tableSalesBonDeCommandeColumnToHide);

	btnAddBonDeCommandeSale = new JButton(imageAddSale);
	btnDeleteBonDeCommandeSale = new JButton(imageDeleteSale);

	List<String[]> dataSalesByEspeces = dailySalesService.tableForEspecesData(dailySalesDTO);
	String[] columnsNameSalesByEspeces = { "id", nbText, valueText, amountText };

	salesByEspeces = new MyDefaultModelTable(dataSalesByEspeces, columnsNameSalesByEspeces, MoneyType.ESPECES, isPanelLocked);
	salesByEspecesTable = new MySalesTable(salesByEspeces);
	scrollPaneSalesByEspecesTable = new JScrollPane(salesByEspecesTable);

	TableColumn tableSalesEspecesColumnToHide = salesByEspecesTable.getColumnModel().getColumn(0);
	salesByEspecesTable.removeColumn(tableSalesEspecesColumnToHide);

	String columns = "25dlu, 25dlu, 25dlu, 25dlu, 30dlu, 25dlu, 25dlu, 25dlu, 30dlu, 25dlu, 25dlu, 25dlu, pref, 50dlu," + " 30dlu, 30dlu, 30dlu, 25dlu, 25dlu, 30dlu, 30dlu, 25dlu, 25dlu, 25dlu, 30dlu, 75dlu";

	String rows = "15dlu, 5dlu, 5dlu, pref, 30dlu, pref, pref, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref," + " 5dlu, pref, 10dlu, 10dlu, 20dlu, 165dlu, 5dlu, pref, 10dlu";

	FormLayout layout = new FormLayout(columns, rows);

	layout.setColumnGroups(new int[][] {});
	layout.setRowGroups(new int[][] {});

	PanelBuilder builder = new PanelBuilder(layout);

	CellConstraints cc = new CellConstraints();

	builder.add(lblTitle, cc.xyw(2, 1, 21));
	builder.add(lblMessage, cc.xyw(2, 4, 19));
	builder.addSeparator(detailsText, cc.xyw(2, 5, 19));
	builder.add(lblTotalWithTaxes, cc.xyw(2, 6, 3));
	builder.add(lblNbCouverts, cc.xyw(6, 6, 3));
	builder.add(lblAverageTicket, cc.xyw(10, 6, 3));
	builder.add(txtAmountWithTaxes, cc.xyw(2, 8, 3));
	builder.add(txtNbCouverts, cc.xyw(6, 8, 3));
	builder.add(txtAverageTicket, cc.xyw(10, 8, 3));
	builder.add(txtAmountDifferencies, cc.xyw(2, 10, 3));
	builder.add(lbl20PercentTaxes, cc.xyw(2, 12, 3));
	builder.add(txtAmountWith20PercentTaxes, cc.xyw(6, 12, 3));
	builder.add(lbl10PercentTaxes, cc.xyw(2, 14, 3));
	builder.add(txtAmountWith10PercentTaxes, cc.xyw(6, 14, 3));
	builder.add(lblWithoutTaxes, cc.xyw(2, 16, 3));
	builder.add(txtAmountWithoutTaxes, cc.xyw(6, 16, 3));

	builder.add(lblDate, cc.xyw(15, 6, 2));
	builder.add(txtCurrentDate, cc.xyw(15, 8, 2));

	builder.add(lblServer, cc.xyw(18, 6, 3));
	builder.add(txtSelectedServer, cc.xyw(18, 8, 3));

	builder.add(btnSave, cc.xyw(21, 5, 4));
	builder.add(btnValidate, cc.xyw(25, 5, 2));
	builder.add(btnBackMainMenu, cc.xyw(21, 6, 4));
	builder.add(btnApplySum, cc.xyw(25, 6, 2));

	builder.addSeparator(salesText, cc.xyw(2, 17, 24));
	builder.addLabel(carteBancaireText, cc.xyw(2, 18, 3));
	builder.add(btnAddCarteBancaireSale, cc.xy(2, 19));
	builder.add(btnDeleteCarteBancaireSale, cc.xy(3, 19));
	builder.add(scrollPaneSalesByCarteBancaireTable, cc.xyw(2, 20, 3));
	builder.add(txtTotalAmountCarteBancaire, cc.xyw(2, 22, 3));

	builder.addLabel(chequeText, cc.xyw(6, 18, 3));
	builder.add(btnAddChequeSale, cc.xy(6, 19));
	builder.add(btnDeleteChequeSale, cc.xy(7, 19));
	builder.add(scrollPaneSalesByChequeTable, cc.xyw(6, 20, 3));
	builder.add(txtTotalAmountCheque, cc.xyw(6, 22, 3));

	builder.addLabel(ticketRestaurantText, cc.xyw(10, 18, 4));
	builder.add(btnAddTicketRestaurantSale, cc.xy(10, 19));
	builder.add(btnDeleteTicketRestaurantSale, cc.xy(11, 19));
	builder.add(scrollPaneSalesByTicketRestaurantTable, cc.xyw(10, 20, 6));
	builder.add(lblTotalTicketRestaurant, cc.xyw(10, 22, 6));

	builder.addLabel(bonDeCommandeText, cc.xyw(17, 18, 3));
	builder.add(btnAddBonDeCommandeSale, cc.xy(17, 19));
	builder.add(btnDeleteBonDeCommandeSale, cc.xy(18, 19));
	builder.add(scrollPaneSalesByBonDeCommandeTable, cc.xyw(17, 20, 3));
	builder.add(txtTotalBonDeCommande, cc.xyw(17, 22, 3));

	builder.addLabel(especesText, cc.xyw(21, 18, 3));
	builder.add(scrollPaneSalesByEspecesTable, cc.xyw(21, 20, 5));
	builder.add(txtTotalAmountEspeces, cc.xyw(23, 22, 3));

	builder.addSeparator("", cc.xyw(2, 23, 24));

	builder.border(Borders.DIALOG);

	initButtons();
	lockAllFieldsIfNeeded();

	salesByCarteBancaireTable.alignData();
	salesByChequeTable.alignData();
	salesByTicketRestaurantTable.alignData();
	salesByBonDeCommandeTable.alignData();
	salesByEspecesTable.alignData();

	return builder.build();
    }

    private void initButtons() {
	btnSave.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		resetColor(txtNbCouverts, txtAmountWith10PercentTaxes, txtAmountWith20PercentTaxes, txtAmountWithTaxes);
		if (checkAreTableValid() && validateForm()) {
		    saveOrUpdateData();
		    StateMessageLabelBuilder.create().withLabel(lblMessage).withState(MessageType.SUCCESS).withText(messageSuccessDataSaved).withVisibilty(true).withVisibilityInSeconds(2_000).start();
		} else {
		    StateMessageLabelBuilder.create().withLabel(lblMessage).withState(MessageType.FAIL).withText(messageFailDataNotSaved).withVisibilty(true).withVisibilityInSeconds(2_000).start();
		}
	    }
	});
	btnValidate.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		boolean status = false;
		if (dailySalesDTO.getSaleData() == null) {
		    StateMessageLabelBuilder.create().withLabel(lblMessage).withText(messageFailDataNotSaved).withVisibilty(true).withState(MessageType.FAIL).withVisibilityInSeconds(2_000).start();
		} else {
		    int okCxl = JOptionPane.showConfirmDialog(null, txtPassword, enterPasswordText, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		    if (okCxl == JOptionPane.OK_OPTION) {
			String password = new String(txtPassword.getPassword());
			if (!"".equals(password)) {
			    try {
				status = processValidation(password);
			    } catch (CaisseException e1) {
				LOGGER.error("Error on process validation", e1);
			    }
			    if (status) {
				if (dailySalesDTO.getSaleData().isLocked()) {
				    StateMessageLabelBuilder.create().withLabel(lblMessage).withText(messageSuccessLock).withVisibilty(true).withState(MessageType.SUCCESS).withVisibilityInSeconds(2_000).start();
				} else {
				    StateMessageLabelBuilder.create().withLabel(lblMessage).withText(messageSuccessUnLock).withVisibilty(true).withState(MessageType.SUCCESS).withVisibilityInSeconds(2_000).start();
				}
			    } else {
				StateMessageLabelBuilder.create().withLabel(lblMessage).withText(messageFailLock).withVisibilty(true).withState(MessageType.FAIL).withVisibilityInSeconds(2_000).start();
			    }
			} else {
			    StateMessageLabelBuilder.create().withLabel(lblMessage).withText(messageFailEmptyPassword).withVisibilty(true).withState(MessageType.FAIL).withVisibilityInSeconds(2_000).start();
			}
		    }
		    txtPassword.setText(null);
		}
	    }
	});
	btnApplySum.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		processForm();
	    }
	});
	btnBackMainMenu.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		Application.changePanel(new MainMenuPanel().get());
	    }
	});

	btnAddCarteBancaireSale.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		salesByCarteBancaireTable.addRow();
	    }
	});
	btnDeleteCarteBancaireSale.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		salesByCarteBancaireTable.removeRow();
	    }
	});
	btnAddChequeSale.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		salesByChequeTable.addRow();
	    }
	});
	btnDeleteChequeSale.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		salesByChequeTable.removeRow();
	    }
	});
	btnAddTicketRestaurantSale.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		salesByTicketRestaurantTable.addRow();
	    }
	});
	btnDeleteTicketRestaurantSale.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		salesByTicketRestaurantTable.removeRow();

	    }
	});
	btnAddBonDeCommandeSale.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		salesByBonDeCommandeTable.addRow();
	    }
	});
	btnDeleteBonDeCommandeSale.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		salesByBonDeCommandeTable.removeRow();
	    }
	});
    }

    private void saveOrUpdateData() {
	// TODO Dozer
	SaleData saleData = new SaleData();
	saleData.setId(new SaleId(dateOperation, server.getId()));
	saleData.setLocked(false);
	List<Sale> salesCarteBancaire = dailySalesService.salesForCarteBancaireData(salesByCarteBancaire);
	List<Sale> salesCheque = dailySalesService.salesForChequeData(salesByCheque);
	List<Sale> salesTicketRestaurant = dailySalesService.salesForTicketRestaurantData(salesByTicketRestaurant);
	List<Sale> salesBonDeCommande = dailySalesService.salesForBonDeCommandeData(salesByBonDeCommande);
	List<Sale> salesEspeces = dailySalesService.salesForEspecesData(salesByEspeces);
	saleData.setNbCouvert(Integer.parseInt(txtNbCouverts.getText()));
	saleData.setAmount10Taxes(DoubleUtils.stringToDouble(txtAmountWith10PercentTaxes.getText()));
	saleData.setAmount20Taxes(DoubleUtils.stringToDouble(txtAmountWith20PercentTaxes.getText()));

	DailySalesDTO dto = DailySalesDTOAssembler.dailySalesDTO(saleData, salesEspeces, salesBonDeCommande, salesCheque, salesTicketRestaurant, salesCarteBancaire);
	Application.getSaleService().saveSales(dto);
    }

    private void lockAllFieldsIfNeeded() {
	if (isPanelLocked) {
	    btnSave.setEnabled(false);
	    btnApplySum.setEnabled(false);
	    btnAddCarteBancaireSale.setEnabled(false);
	    btnDeleteCarteBancaireSale.setEnabled(false);
	    btnAddChequeSale.setEnabled(false);
	    btnDeleteChequeSale.setEnabled(false);
	    btnAddTicketRestaurantSale.setEnabled(false);
	    btnDeleteTicketRestaurantSale.setEnabled(false);
	    btnAddBonDeCommandeSale.setEnabled(false);
	    btnDeleteBonDeCommandeSale.setEnabled(false);
	    btnValidate.setText(unlockText);

	    txtNbCouverts.setEnabled(false);
	    txtAmountWith10PercentTaxes.setEnabled(false);
	    txtAmountWith20PercentTaxes.setEnabled(false);

	    salesByCarteBancaire.setAllLocked(true);
	    salesByCheque.setAllLocked(true);
	    salesByTicketRestaurant.setAllLocked(true);
	    salesByBonDeCommande.setAllLocked(true);
	    salesByEspeces.setAllLocked(true);
	}
    }

    private void unlockAllFields() {
	btnSave.setEnabled(true);
	btnApplySum.setEnabled(true);
	btnAddCarteBancaireSale.setEnabled(true);
	btnDeleteCarteBancaireSale.setEnabled(true);
	btnAddChequeSale.setEnabled(true);
	btnDeleteChequeSale.setEnabled(true);
	btnAddTicketRestaurantSale.setEnabled(true);
	btnDeleteTicketRestaurantSale.setEnabled(true);
	btnAddBonDeCommandeSale.setEnabled(true);
	btnDeleteBonDeCommandeSale.setEnabled(true);
	btnValidate.setText(validateText);

	txtNbCouverts.setEnabled(true);
	txtAmountWith10PercentTaxes.setEnabled(true);
	txtAmountWith20PercentTaxes.setEnabled(true);

	salesByCarteBancaire.setAllLocked(false);
	salesByCheque.setAllLocked(false);
	salesByTicketRestaurant.setAllLocked(false);
	salesByBonDeCommande.setAllLocked(false);
	salesByEspeces.setAllLocked(false);
    }

    private void processForm() {
	resetColor(txtNbCouverts, txtAmountWith10PercentTaxes, txtAmountWith20PercentTaxes);
	validateForm();
    }

    private boolean checkAreTableValid() {
	boolean resultValidation = checkTableData(salesByCarteBancaire.getData()) && checkTableData(salesByCheque.getData())
		&& checkTableData(salesByBonDeCommande.getData()) && checkTableData(salesByEspeces.getData());

	// TR
	for (String[] row : salesByTicketRestaurant.getData()) {
	    if ((row[1] == null || !ValidatorUtils.validateIsNumericWithFrenchFormat(row[1])) || (row[2] == null || !ValidatorUtils.validateIsNumericWithFrenchFormat(row[2]))) {
		return false;
	    }
	}
	return resultValidation;
    }

    private boolean checkTableData(List<String[]> data) {
	for (String[] row : data) {
	    if (row[1] == null || !ValidatorUtils.validateIsNumericWithFrenchFormat(row[1])) {
		return false;
	    }
	}
	return true;
    }
    
    private boolean validateForm() {
	List<JTextComponent> invalidComponents = UIUtils.checkIsNumeric(txtNbCouverts, txtAmountWith10PercentTaxes, txtAmountWith20PercentTaxes);
	if (!invalidComponents.isEmpty()) {
	    for (JTextComponent currentComponent : invalidComponents) {
		currentComponent.setBackground(Color.PINK);
	    }
	    StateMessageLabelBuilder.create().withLabel(lblMessage).withText(messageNotNumericFields).withVisibilty(true).withState(MessageType.FAIL).withVisibilityInSeconds(2_000).start();
	    LOGGER.debug("Some fields are not numeric");
	    return false;
	} else {
	    calculateValues();
	    return true;
	}
    }

    // TODO Sortir dans une classe
    private void calculateValues() {
	Double txtAmountWithout10Taxes = DoubleUtils.stringToDouble(txtAmountWith10PercentTaxes.getText()) / 1.10;
	Double txtAmountWithout20Taxes = DoubleUtils.stringToDouble(txtAmountWith20PercentTaxes.getText()) / 1.20;

	Double value = txtAmountWithout10Taxes + txtAmountWithout20Taxes;

	txtAmountWithoutTaxes.setText(DoubleUtils.doubleToString(value));

	Double amountWithTaxes = DoubleUtils.stringToDouble(txtAmountWith10PercentTaxes.getText()) + DoubleUtils.stringToDouble(txtAmountWith20PercentTaxes.getText());
	txtAmountWithTaxes.setText(DoubleUtils.doubleToString(amountWithTaxes));

	Double averageTicket = dailySalesService.averageTicket(DoubleUtils.stringToDouble(txtAmountWithTaxes.getText()), Integer.parseInt(txtNbCouverts.getText()));
	txtAverageTicket.setText(DoubleUtils.doubleToString(averageTicket));

	calculateTableValues();

	txtTotalAmountCarteBancaire.setText(DoubleUtils.doubleToString(dailySalesService.sum(salesByCarteBancaire.getData())));
	txtTotalAmountCheque.setText(DoubleUtils.doubleToString(dailySalesService.sum(salesByCheque.getData())));
	txtTotalBonDeCommande.setText(DoubleUtils.doubleToString(dailySalesService.sum(salesByBonDeCommande.getData())));

	Integer totalNb = dailySalesService.sumIntegerColumn(salesByTicketRestaurant.getData(), 2);
	Double totalAmount = dailySalesService.sumDoubleColumn(salesByTicketRestaurant.getData(), 4);
	lblTotalTicketRestaurant.setText(String.valueOf(totalNb) + "  /  " + DoubleUtils.doubleToString(totalAmount));

	txtTotalAmountEspeces.setText(DoubleUtils.doubleToString(dailySalesService.sum(salesByEspeces.getData())));

	calculateDiffField(totalAmount);
    }

    // TODO Sortir dans une classe
    private void calculateDiffField(Double totalAmount) {
	Double[] valuesToAdd = new Double[5];

	valuesToAdd[0] = DoubleUtils.stringToDouble(txtTotalAmountCarteBancaire.getText());
	valuesToAdd[1] = DoubleUtils.stringToDouble(txtTotalAmountCheque.getText());
	valuesToAdd[2] = totalAmount;
	valuesToAdd[3] = DoubleUtils.stringToDouble(txtTotalBonDeCommande.getText());
	valuesToAdd[4] = DoubleUtils.stringToDouble(txtTotalAmountEspeces.getText());

	Double amountToSubstract = DoubleUtils.stringToDouble(txtAmountWithTaxes.getText());

	Double amountWithDiff = dailySalesService.diffAmount(valuesToAdd, amountToSubstract);

	txtAmountDifferencies.setText(DoubleUtils.doubleToString(amountWithDiff));
	txtAmountDifferencies.setForeground(UIUtils.colorByAmount(amountWithDiff));
    }

    // TODO Sortir dans une classe
    private void calculateTableValues() {
	// TR
	for (String[] row : salesByTicketRestaurant.getData()) {
	    try {
		row[3] = DoubleUtils.doubleToString(DoubleUtils.stringToDouble(row[1]) * DoubleUtils.stringToDouble(row[2]));
	    } catch (Exception e) {
		LOGGER.debug("Error on compute", e);
	    }
	}
	salesByTicketRestaurant.fireTableDataChanged();
	// Especes
	for (String[] row : salesByEspeces.getData()) {
	    try {
		row[3] = DoubleUtils.doubleToString(DoubleUtils.stringToDouble(row[1]) * DoubleUtils.stringToDouble(row[2]));
	    } catch (Exception e) {
		LOGGER.debug("Error on compute", e);
	    }
	}
	salesByEspeces.fireTableDataChanged();
    }

    private boolean processValidation(String password) throws CaisseException {
	String encryptedAdminPassword = Application.getUserService().findAdminPassword();
	if (CryptoUtils.getMD5EncryptedPassword(password).equals(encryptedAdminPassword)) {
	    processLockOrUnLockScreen();
	    return true;
	} else {
	    return false;
	}
    }

    private void processLockOrUnLockScreen() {
	if (!dailySalesDTO.getSaleData().isLocked()) {
	    isPanelLocked = true;
	    dailySalesDTO.getSaleData().setLocked(isPanelLocked);
	    Application.getSaleService().updateSaleData(dailySalesDTO);
	    lockAllFieldsIfNeeded();
	    LOGGER.debug("Sales for {} - Server {} => Locked", dateOperation, server);
	} else {
	    isPanelLocked = false;
	    dailySalesDTO.getSaleData().setLocked(isPanelLocked);
	    unlockAllFields();
	    LOGGER.debug("Sales for {} - Server {} => Unlocked", dateOperation, server);
	}
	Application.getSaleService().updateSaleData(dailySalesDTO);
    }

    private void resetColor(JTextComponent... components) {
	for (JTextComponent component : components) {
	    component.setBackground(Color.WHITE);
	}
    }
}