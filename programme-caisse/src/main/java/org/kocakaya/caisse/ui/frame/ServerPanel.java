package org.kocakaya.caisse.ui.frame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import javax.swing.text.JTextComponent;

import org.kocakaya.caisse.business.Server;
import org.kocakaya.caisse.mapper.ObjectMapper;
import org.kocakaya.caisse.service.ResourcesLoader;
import org.kocakaya.caisse.service.dto.ServerDTO;
import org.kocakaya.caisse.ui.component.MyDefaultModelTable;
import org.kocakaya.caisse.ui.utils.MessageType;
import org.kocakaya.caisse.ui.utils.StateMessageLabelBuilder;
import org.kocakaya.caisse.ui.utils.TitleMessageBuilder;
import org.kocakaya.caisse.ui.utils.UIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class ServerPanel extends JPanel implements Panel {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerPanel.class);

    private static ResourceBundle resourceBundle = ResourcesLoader.getInstance().getResourceBundle();

    String messageSuccessSave = resourceBundle.getString("programme.caisse.success.save.lbl");
    String messageFailMissingData = resourceBundle.getString("programme.caisse.fail.missing.data.lbl");
    String messageFailAlreadyExistsServer = resourceBundle.getString("programme.caisse.fail.duplicated.save.lbl");
    String messageSuccessUpdate = resourceBundle.getString("programme.caisse.fail.update.lbl");

    String title = resourceBundle.getString("programme.caisse.servers.mngt.lbl");

    JLabel lblTitle;

    JLabel lblMessage;

    JLabel lblLastName;
    JLabel lblFirstName;

    JTextField txtLastName;
    JTextField txtFirstname;

    JButton btnCreate;
    JButton btnClear;
    JButton btnUpdateUsers;
    JButton btnBackMainMenu;

    MyDefaultModelTable servers;
    JTable serversTable;
    JScrollPane scrollPaneServersTable;

    public ServerPanel() {
	super();
    }

    @Override
    public JPanel get() {

	lblTitle = TitleMessageBuilder.create().withText(title).withTextToUpperCase().get();

	lblMessage = StateMessageLabelBuilder.create().get();

	String serverText = resourceBundle.getString("programme.caisse.server.lbl");
	String mainMenuText = resourceBundle.getString("programme.caisse.back.main.menu.lbl");

	String lblTextLastName = resourceBundle.getString("programme.caisse.user.lastname.lbl");
	String lblTextFirstName = resourceBundle.getString("programme.caisse.user.firstname.lbl");

	lblLastName = new JLabel(lblTextLastName + " * : ");
	lblFirstName = new JLabel(lblTextFirstName + " * : ");

	txtLastName = new JTextField(20);
	txtFirstname = new JTextField(20);

	ImageIcon imageCreate = new ImageIcon(getClass().getClassLoader().getResource("add.png"));
	btnCreate = new JButton(resourceBundle.getString("programme.caisse.user.create.lbl"), imageCreate);

	ImageIcon imageClear = new ImageIcon(getClass().getClassLoader().getResource("clear.png"));
	btnClear = new JButton(resourceBundle.getString("programme.caisse.user.clear.lbl"), imageClear);

	ImageIcon imageBackMainMenu = new ImageIcon(getClass().getClassLoader().getResource("back.png"));
	btnBackMainMenu = new JButton(mainMenuText, imageBackMainMenu);

	List<String[]> data = getServersAsTable();
	String[] columnsName = { "id", lblTextLastName, lblTextFirstName };

	servers = new MyDefaultModelTable(data, columnsName, null, false);
	serversTable = new JTable(servers);
	scrollPaneServersTable = new JScrollPane(serversTable);

	TableColumn tableColumnToHide = serversTable.getColumnModel().getColumn(0);
	serversTable.removeColumn(tableColumnToHide);

	ImageIcon imageUpdate = new ImageIcon(getClass().getClassLoader().getResource("update.png"));
	btnUpdateUsers = new JButton(resourceBundle.getString("programme.caisse.updateusers.lbl"), imageUpdate);

	String columns = "150dlu, pref, pref, 25dlu, pref, 10dlu, pref, 25dlu, pref, pref, pref";
	String rows = "35dlu, 5dlu, 10dlu, 30dlu, pref, pref, 10dlu, pref, pref";

	FormLayout layout = new FormLayout(columns, rows);

	layout.setColumnGroups(new int[][] { { 4, 8 }, { 5, 9 }, { 10, 11 } });
	layout.setRowGroups(new int[][] {});

	PanelBuilder builder = new PanelBuilder(layout);

	CellConstraints cc = new CellConstraints();

	builder.add(lblTitle, cc.xyw(2, 1, 10));
	builder.add(lblMessage, cc.xyw(2, 4, 10));
	builder.addSeparator(serverText.toUpperCase(), cc.xyw(2, 5, 10));
	builder.add(lblLastName, cc.xy(3, 6));
	builder.add(txtLastName, cc.xy(5, 6));
	builder.add(lblFirstName, cc.xy(7, 6));
	builder.add(txtFirstname, cc.xy(9, 6));
	builder.add(btnCreate, cc.xy(10, 6));
	builder.add(btnClear, cc.xy(11, 4));
	builder.add(btnUpdateUsers, cc.xy(9, 8));
	builder.add(btnBackMainMenu, cc.xy(10, 8));
	builder.add(scrollPaneServersTable, cc.xyw(3, 9, 6));

	builder.border(Borders.DIALOG);

	initButtons();

	return builder.build();
    }

    private void initButtons() {
	btnCreate.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		manageServerCreation();
	    }
	});
	btnClear.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		initForm();
	    }
	});
	btnUpdateUsers.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		manageServersUpdate();
	    }
	});
	btnBackMainMenu.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		Application.changePanel(new MainMenuPanel().get());
	    }
	});
    }

    private void resetValue(JTextComponent... components) {
	for (JTextComponent component : components) {
	    component.setText("");
	}
    }

    private void resetColor(JTextComponent... components) {
	for (JTextComponent component : components) {
	    component.setBackground(Color.WHITE);
	}
    }

    private void manageServerCreation() {
	List<JTextComponent> emptyComponents = UIUtils.checkIsNullOrEmpty(txtFirstname, txtLastName);
	if (!emptyComponents.isEmpty()) {
	    for (JTextComponent currentComponent : emptyComponents) {
		currentComponent.setBackground(Color.PINK);
	    }
	    StateMessageLabelBuilder.create().withLabel(lblMessage).withText(messageFailMissingData).withVisibilty(true).withState(MessageType.FAIL).withVisibilityInSeconds(2_000).start();

	    LOGGER.debug("Missing data, server cannot be created");
	} else {
	    ServerDTO serverDTO = ObjectMapper.serverDTO(txtLastName.getText(), txtFirstname.getText());
	    Application.getServerService().saveServer(serverDTO);
	    LOGGER.info("Server : {}-{} created", serverDTO.getServer().getLastName(), serverDTO.getServer().getFirstName());

	    StateMessageLabelBuilder.create().withLabel(lblMessage).withText(messageSuccessSave).withVisibilty(true).withState(MessageType.SUCCESS).withVisibilityInSeconds(2_000).start();

	    resetValue(txtFirstname, txtLastName);
	    servers.setData(getServersAsTable());
	    LOGGER.debug("Servers table updated successfully");
	}
    }

    private void manageServersUpdate() {
	for (String[] row : servers.getData()) {
	    Server server = new Server();
	    server.setId(Integer.parseInt(row[0]));
	    server.setLastName(row[1]);
	    server.setFirstName(row[2]);
	    ServerDTO serverDTO = new ServerDTO(server);
	    Application.getServerService().updateServer(serverDTO);
	    LOGGER.debug("Server : {}-{} updated", row[1], row[2]);

	    StateMessageLabelBuilder.create().withLabel(lblMessage).withText(messageSuccessUpdate).withVisibilty(true).withState(MessageType.SUCCESS).withVisibilityInSeconds(2_000).start();
	}
    }

    private List<String[]> getServersAsTable() {
	List<Server> serversList = Application.getServerService().servers();
	List<String[]> result = new ArrayList<>();
	for (Server server : serversList) {
	    result.add(new String[] { String.valueOf(server.getId()), server.getLastName(), server.getFirstName() });
	}
	return result;
    }

    private void initForm() {
	resetValue(txtFirstname, txtLastName);
	resetColor(txtFirstname, txtLastName);
	if (lblMessage.isVisible()) {
	    lblMessage.setVisible(false);
	}
    }
}