package org.kocakaya.caisse.ui.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.kocakaya.caisse.business.CbRecolte;
import org.kocakaya.caisse.business.MoneyType;
import org.kocakaya.caisse.business.Server;
import org.kocakaya.caisse.service.ResourcesLoader;
import org.kocakaya.caisse.service.dto.DailySalesDTO;
import org.kocakaya.caisse.ui.component.MyCumulCBTable;
import org.kocakaya.caisse.ui.component.MyDefaultModelTable;
import org.kocakaya.caisse.ui.component.MyDefaultModelTableCumulCB;
import org.kocakaya.caisse.ui.component.MyStatsTable;
import org.kocakaya.caisse.ui.utils.DateLabelFormatter;
import org.kocakaya.caisse.ui.utils.MessageType;
import org.kocakaya.caisse.ui.utils.StateMessageLabelBuilder;
import org.kocakaya.caisse.ui.utils.TitleMessageBuilder;
import org.kocakaya.caisse.utils.DateUtils;
import org.kocakaya.caisse.utils.DoubleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class MainMenuPanel extends JPanel implements Panel {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(MainMenuPanel.class);

    private static ResourceBundle resourceBundle = ResourcesLoader.getInstance().getResourceBundle();

    DecimalFormat mFormat = new DecimalFormat("00");
    
    DateUtils dateUtils = new DateUtils();

    private String periodText = resourceBundle.getString("programme.caisse.period.lbl");
    private String caHtText = resourceBundle.getString("programme.caisse.caht.lbl");
    private String caTtcText = resourceBundle.getString("programme.caisse.cattc.lbl");
    private String averageTicketText = resourceBundle.getString("programme.caisse.averageticket.lbl");
    private String statsText = resourceBundle.getString("programme.caisse.stats.lbl");
    private String numberText = resourceBundle.getString("programme.caisse.number.lbl");
    private String amountText = resourceBundle.getString("programme.caisse.amount.lbl");
    private String sumText = resourceBundle.getString("programme.caisse.sum.lbl");
    private String computedAmountText = resourceBundle.getString("programme.caisse.computed.amount.lbl");
    private String collectedAmountText = resourceBundle.getString("programme.caisse.collected.amount.lbl");
    private String deltaText = resourceBundle.getString("programme.caisse.delta.lbl");

    String messageFailNoServerSelected = resourceBundle.getString("programme.caisse.no.server.selected.lbl");
    String messageSuccessDataSaved = resourceBundle.getString("programme.caisse.success.save.lbl");

    String title = resourceBundle.getString("programme.caisse.mainmenu.title.lbl");

    JLabel lblTitle;

    private String[] columnsForSalesStats = { periodText.toUpperCase(), caHtText.toUpperCase(), caTtcText.toUpperCase(), averageTicketText.toUpperCase() };
    private List<String[]> dataForSalesStats = new ArrayList<>();

    private String[] columnsForTrStats = { periodText.toUpperCase(), numberText.toUpperCase(), sumText.toUpperCase() };

    private List<String[]> dataForTrStats = new ArrayList<>();

    private List<String[]> dataForCumulCB = new ArrayList<>();

    private String[] columnsForCumulTr = { numberText.toUpperCase(), sumText.toUpperCase() };

    private String[] columnsForDetailsTr = { numberText.toUpperCase(), amountText.toUpperCase() };

    private String[] columnsForCumulCB = { periodText.toUpperCase(), computedAmountText.toUpperCase(), collectedAmountText.toUpperCase(), deltaText.toUpperCase() };

    private UtilDateModel model;
    private JDatePanelImpl datePanel;
    private JDatePickerImpl datePicker;

    private JComboBox<Server> servers;

    private JLabel lblMessage;

    private JLabel lblDate;
    private JLabel lblServer;

    private JButton btnNewSale;
    private JButton btnDaySales;
    private JButton btnMonthSales;
    private JButton btnYearSales;

    private JButton btnTRJour;
    private JButton btnCumulTR;

    private JButton btnServersManagement;

    private JButton btnDetails;

    private JButton btnCumulCB;

    private JButton btnSaveRecolteCB;

    MyDefaultModelTable stats;
    MyStatsTable statsTable;
    JScrollPane scrollPaneStatsTable;

    MyDefaultModelTable tickets;
    MyStatsTable ticketsTable;
    JScrollPane scrollPaneTicketsTable;

    MyDefaultModelTableCumulCB cumulCB;
    MyCumulCBTable cumulCBTable;
    JScrollPane scrollPaneCumulCBTable;

    public MainMenuPanel() {
	super();
    }

    @Override
    public JPanel get() {

	lblTitle = TitleMessageBuilder.create().withText(title).withTextToUpperCase().get();

	String caisseText = resourceBundle.getString("programme.caisse.caisse.lbl");

	lblMessage = StateMessageLabelBuilder.create().get();

	lblDate = new JLabel(resourceBundle.getString("programme.caisse.date.lbl"));
	lblServer = new JLabel(resourceBundle.getString("programme.caisse.server.lbl"));

	ImageIcon imageIconAddSale = new ImageIcon(getClass().getClassLoader().getResource("add_sales.png"));
	btnNewSale = new JButton(resourceBundle.getString("programme.caisse.sale.add.lbl"), imageIconAddSale);

	ImageIcon imageIconStats = new ImageIcon(getClass().getClassLoader().getResource("stats.png"));

	btnDaySales = new JButton(resourceBundle.getString("programme.caisse.day.lbl").toUpperCase(), imageIconStats);
	btnMonthSales = new JButton(resourceBundle.getString("programme.caisse.month.lbl").toUpperCase(), imageIconStats);
	btnCumulTR = new JButton(resourceBundle.getString("programme.caisse.cumul.tickets.lbl").toUpperCase(), imageIconStats);
	btnYearSales = new JButton(resourceBundle.getString("programme.caisse.year.lbl").toUpperCase(), imageIconStats);

	btnTRJour = new JButton(resourceBundle.getString("programme.caisse.ticket.day.lbl").toUpperCase(), imageIconStats);

	btnCumulCB = new JButton(resourceBundle.getString("programme.caisse.cumul.cb.lbl").toUpperCase(), imageIconStats);

	ImageIcon imageIconSave = new ImageIcon(getClass().getClassLoader().getResource("floppy.png"));
	btnSaveRecolteCB = new JButton(resourceBundle.getString("programme.caisse.save.lbl"), imageIconSave);

	ImageIcon imageIconServers = new ImageIcon(getClass().getClassLoader().getResource("servers.png"));
	btnServersManagement = new JButton(resourceBundle.getString("programme.caisse.servers.management.lbl"), imageIconServers);

	servers = new JComboBox<>(servers());

	model = new UtilDateModel();

	Date bufferedDate = Application.getConnectedUser().getSelectedDate();

	model.setDate(DateUtils.year(bufferedDate), DateUtils.month(bufferedDate), DateUtils.day(bufferedDate));
	model.setSelected(true);
	datePanel = new JDatePanelImpl(model);
	datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

	stats = new MyDefaultModelTable(dataForSalesStats, columnsForSalesStats, MoneyType.ALL, false);
	statsTable = new MyStatsTable(stats, MyStatsTable.DAY_STATS);
	scrollPaneStatsTable = new JScrollPane(statsTable);

	tickets = new MyDefaultModelTable(dataForTrStats, columnsForTrStats, MoneyType.ALL, false);
	ticketsTable = new MyStatsTable(tickets, MyStatsTable.TR_JOUR);
	scrollPaneTicketsTable = new JScrollPane(ticketsTable);

	cumulCB = new MyDefaultModelTableCumulCB(dataForCumulCB, columnsForCumulCB);
	cumulCBTable = new MyCumulCBTable(cumulCB);
	scrollPaneCumulCBTable = new JScrollPane(cumulCBTable);

	ImageIcon imageIconDetails = new ImageIcon(getClass().getClassLoader().getResource("glasses.png"));
	btnDetails = new JButton(resourceBundle.getString("programme.caisse.details.lbl"), imageIconDetails);

	String columns = "25dlu, pref, 10dlu, pref, 10dlu, pref, 10dlu, pref, 10dlu," + "pref, 10dlu, pref, 15dlu, pref, 15dlu, pref, 15dlu, pref, 15dlu, pref, 15dlu, pref";

	String rows = "25dlu, 5dlu, 25dlu, pref, pref, 5dlu, pref, 15dlu, 15dlu, pref, 160dlu, 15dlu, 100dlu";

	FormLayout layout = new FormLayout(columns, rows);

	layout.setColumnGroups(new int[][] { { 3, 5, 7, 9 } });
	layout.setRowGroups(new int[][] {});

	PanelBuilder builder = new PanelBuilder(layout);

	CellConstraints cc = new CellConstraints();

	builder.add(lblTitle, cc.xyw(2, 1, 18));
	builder.add(lblMessage, cc.xyw(2, 4, 9));
	builder.addSeparator(caisseText.toUpperCase(), cc.xyw(2, 5, 9));
	builder.add(lblDate, cc.xy(2, 7));
	builder.add(datePicker, cc.xy(4, 7));
	builder.add(lblServer, cc.xy(6, 7));
	builder.add(servers, cc.xy(8, 7));
	builder.add(btnNewSale, cc.xy(10, 7));

	if (Application.getConnectedUser().hasRoleAdmin() || Application.getConnectedUser().hasRoleVisuTr()) {

	    builder.add(btnDaySales, cc.xy(12, 5));
	    builder.add(btnMonthSales, cc.xy(14, 5));
	    builder.add(btnYearSales, cc.xy(16, 5));
	    builder.add(btnTRJour, cc.xy(12, 7));
	    builder.add(btnCumulTR, cc.xy(14, 7));
	    builder.add(btnCumulCB, cc.xy(16, 7));

	    builder.addSeparator(statsText.toUpperCase(), cc.xyw(12, 9, 6));
	    builder.add(scrollPaneStatsTable, cc.xyw(12, 11, 6));

	    builder.add(scrollPaneCumulCBTable, cc.xyw(12, 11, 6));

	    scrollPaneCumulCBTable.setVisible(false);
	}

	if (Application.getConnectedUser().hasRoleAdmin()) {
	    builder.add(btnServersManagement, cc.xy(18, 5));
	}

	builder.add(scrollPaneTicketsTable, cc.xyw(12, 13, 6));

	scrollPaneTicketsTable.setVisible(false);

	builder.add(btnDetails, cc.xywh(18, 8, 1, 2));
	btnDetails.setVisible(false);

	builder.add(btnSaveRecolteCB, cc.xywh(18, 8, 1, 2));

	btnSaveRecolteCB.setVisible(false);

	initButtons();

	return builder.build();
    }

    private void initButtons() {
	btnNewSale.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (servers.getSelectedItem() != null) {
		    Server server = (Server) servers.getSelectedItem();
		    Date dateOperation = (Date) datePicker.getModel().getValue();
		    Application.getConnectedUser().setSelectedDate(dateOperation);
		    DailySalesDTO dailySalesDTO = Application.getSaleService().salesDetails(dateOperation, server);
		    LOGGER.debug("Found data {}", dailySalesDTO.getSaleData());
		    LOGGER.info("Selected date {} - server {}", DateUtils.formatDateWithFrenchFormat(dateOperation), server);
		    Application.getConnectedUser().setSelectedDate(dateOperation);
		    Application.changeToDailySalesPanel(server, dateOperation, dailySalesDTO);
		} else {
		    StateMessageLabelBuilder.create().withLabel(lblMessage).withText(messageFailNoServerSelected).withState(MessageType.FAIL).withVisibilty(true).withVisibilityInSeconds(2_000).start();
		}
	    }
	});
	btnDaySales.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		btnDetails.setVisible(false);
		btnSaveRecolteCB.setVisible(false);
		scrollPaneTicketsTable.setVisible(false);
		scrollPaneStatsTable.setVisible(true);
		statsTable.setTableType(MyStatsTable.DAY_STATS);
		stats.setColumnsName(columnsForSalesStats);
		stats.setData(dataForDaySales());
		stats.fireTableStructureChanged();
	    }
	});
	btnMonthSales.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		btnDetails.setVisible(false);
		btnSaveRecolteCB.setVisible(false);
		scrollPaneTicketsTable.setVisible(false);
		scrollPaneStatsTable.setVisible(true);
		statsTable.setTableType(MyStatsTable.MONTH_STATS);
		stats.setColumnsName(columnsForSalesStats);
		stats.setData(dataForMonthSales());
		stats.fireTableStructureChanged();
	    }
	});
	btnYearSales.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		btnDetails.setVisible(false);
		btnSaveRecolteCB.setVisible(false);
		scrollPaneTicketsTable.setVisible(false);
		scrollPaneStatsTable.setVisible(true);
		statsTable.setTableType(MyStatsTable.YEAR_STATS);
		stats.setColumnsName(columnsForSalesStats);
		stats.setData(dataForYearSales());
		stats.fireTableStructureChanged();
	    }
	});
	btnTRJour.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		btnDetails.setVisible(true);
		btnSaveRecolteCB.setVisible(false);
		scrollPaneTicketsTable.setVisible(false);
		scrollPaneStatsTable.setVisible(true);
		statsTable.setTableType(MyStatsTable.TR_JOUR);
		ticketsTable.setTableType(MyStatsTable.TR_JOUR);
		stats.setColumnsName(columnsForTrStats);
		stats.setData(dataForTrJour());
		stats.fireTableStructureChanged();
	    }
	});
	btnCumulTR.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		btnDetails.setVisible(true);
		btnSaveRecolteCB.setVisible(false);
		scrollPaneTicketsTable.setVisible(false);
		scrollPaneStatsTable.setVisible(true);
		statsTable.setTableType(MyStatsTable.CUMUL_TR);
		ticketsTable.setTableType(MyStatsTable.CUMUL_TR);
		stats.setColumnsName(columnsForCumulTr);
		stats.setData(dataForCumulTr());
		stats.fireTableStructureChanged();
	    }
	});
	btnCumulCB.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		btnDetails.setVisible(false);
		btnSaveRecolteCB.setVisible(true);
		scrollPaneTicketsTable.setVisible(false);
		scrollPaneStatsTable.setVisible(false);
		scrollPaneCumulCBTable.setVisible(true);
		cumulCB.setColumnsName(columnsForCumulCB);
		cumulCB.setData(dataDetailsForCumulCb());
		cumulCB.fireTableStructureChanged();
	    }
	});
	btnDetails.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		int selectedRow = statsTable.getSelectedRow();
		if (selectedRow != -1) {
		    if (MyStatsTable.TR_JOUR.equals(ticketsTable.getTableType())) {
			String[] row = stats.getData().get(selectedRow);
			Date selectedDate = DateUtils.stringToDate(row[0]);
			tickets.setColumnsName(columnsForDetailsTr);
			tickets.setData(dataDetailsForTrJour(selectedDate));
		    } else if (MyStatsTable.CUMUL_TR.equals(ticketsTable.getTableType())) {
			tickets.setColumnsName(columnsForDetailsTr);
			tickets.setData(dataDetailsForCumulTr());
		    }
		    scrollPaneTicketsTable.setVisible(true);
		    tickets.fireTableStructureChanged();
		}
	    }
	});
	btnSaveRecolteCB.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		List<CbRecolte> cbRecoltes = new ArrayList<>();
		for (String[] array : cumulCB.getData()) {
		    CbRecolte cbRecolte = new CbRecolte();
		    cbRecolte.setDateOperation(DateUtils.stringToDate(array[0]));
		    cbRecolte.setAmount(DoubleUtils.stringToDouble(array[2]));
		    cbRecoltes.add(cbRecolte);
		}
		Application.getSaleService().saveCbRecolte(cbRecoltes);
		cumulCB.setData(dataDetailsForCumulCb());
		cumulCB.fireTableDataChanged();
		StateMessageLabelBuilder.create().withLabel(lblMessage).withText(messageSuccessDataSaved).withState(MessageType.SUCCESS).withVisibilty(true).withVisibilityInSeconds(2_000).start();
	    }
	});
	btnServersManagement.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		Application.changePanel(new ServerPanel().get());
	    }
	});
    }

    private Server[] servers() {
	List<Server> serversList = Application.getServerService().servers();
	return serversList.toArray(new Server[serversList.size()]);
    }

    private List<String[]> dataForDaySales() {
	Date currentDate = new Date();
	String dateWithFrenchFormat = DateUtils.formatDateWithFrenchFormat(currentDate);
	return Application.getSaleService().salesByDay(dateWithFrenchFormat);
    }

    private List<String[]> dataForMonthSales() {
	return Application.getSaleService().salesByMonth(currentMonthWithAppropriateIndex());
    }

    private List<String[]> dataForYearSales() {
	return Application.getSaleService().salesByYear(String.valueOf(DateUtils.year(new Date())));
    }

    private List<String[]> dataForTrJour() {
	return Application.getSaleService().ticketsByJour(currentMonthWithAppropriateIndex());
    }

    private List<String[]> dataForCumulTr() {
	return Application.getSaleService().ticketsByCumul(currentMonthWithAppropriateIndex());
    }

    private List<String[]> dataDetailsForTrJour(Date date) {
	String dateWithFrenchFormat = DateUtils.formatDateWithFrenchFormat(date);
	return Application.getSaleService().detailsForTicketsJour(dateWithFrenchFormat);
    }

    private List<String[]> dataDetailsForCumulTr() {
	return Application.getSaleService().detailsForCumulTickets(currentMonthWithAppropriateIndex());
    }

    private List<String[]> dataDetailsForCumulCb() {
	return Application.getSaleService().detailsForCumulCB();
    }

    private String currentMonthWithAppropriateIndex() {
	return dateUtils.monthOfCurrentDateWithIncrementedStartedIndex();
    }
}