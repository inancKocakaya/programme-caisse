package org.kocakaya.caisse.ui.frame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.text.JTextComponent;

import org.kocakaya.caisse.exception.CaisseException;
import org.kocakaya.caisse.service.ResourcesLoader;
import org.kocakaya.caisse.service.dto.UserDTO;
import org.kocakaya.caisse.ui.assembler.UserDTOAssembler;
import org.kocakaya.caisse.ui.utils.MessageType;
import org.kocakaya.caisse.ui.utils.StateMessageLabelBuilder;
import org.kocakaya.caisse.ui.utils.TitleMessageBuilder;
import org.kocakaya.caisse.ui.utils.UIUtils;
import org.kocakaya.caisse.utils.CryptoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class WelcomePanel extends JPanel implements Panel {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(WelcomePanel.class);

    private ResourceBundle resourceBundle = ResourcesLoader.getInstance().getResourceBundle();

    String messageFailConnection = resourceBundle.getString("programme.caisse.fail.connection.lbl");
    String messageFailMissingData = resourceBundle.getString("programme.caisse.fail.missing.data.lbl");

    String title = resourceBundle.getString("programme.caisse.welcome.title.lbl");

    JLabel lblTitle;

    JLabel lblMessage;

    JLabel lblLogin;
    JLabel lblPassword;

    JLabel lblImage;

    JComboBox<String> users;

    JPasswordField txtPassword;

    JButton btnConnection;
    JButton btnClear;

    public WelcomePanel() {
    }

    @Override
    public JPanel get() {
	String connectionText = resourceBundle.getString("programme.caisse.connection.lbl");

	lblTitle = TitleMessageBuilder.create().withText(title).withTextToUpperCase().get();

	lblMessage = StateMessageLabelBuilder.create().get();

	lblLogin = new JLabel(resourceBundle.getString("programme.caisse.connection.login.lbl") + " : ");
	lblPassword = new JLabel(resourceBundle.getString("programme.caisse.connection.password.lbl") + " : ");

	users = new JComboBox<>(users());

	txtPassword = new JPasswordField(10);

	ImageIcon imageConnection = new ImageIcon(getClass().getClassLoader().getResource("validate.png"));
	btnConnection = new JButton(resourceBundle.getString("programme.caisse.connection.validate.lbl"), imageConnection);

	ImageIcon imageClear = new ImageIcon(getClass().getClassLoader().getResource("clear.png"));
	btnClear = new JButton(resourceBundle.getString("programme.caisse.connection.clear.lbl"), imageClear);

	BufferedImage imageLogo = null;
	try {
	    imageLogo = ImageIO.read(getClass().getClassLoader().getResourceAsStream("logo-au-bureau.jpg"));
	} catch (IOException e) {
	    LOGGER.error(e.getMessage());
	}

	lblImage = new JLabel(new ImageIcon(imageLogo));

	String columns = "250dlu, pref, pref, pref, pref, pref, pref";

	String rows = "50dlu, 20dlu, pref, pref, 1dlu, pref, 3dlu, pref, 10dlu, pref, 10dlu, pref";

	FormLayout layout = new FormLayout(columns, rows);

	layout.setColumnGroups(new int[][] { { 3, 5 } });
	layout.setRowGroups(new int[][] { { 3, 5 } });

	PanelBuilder builder = new PanelBuilder(layout);

	CellConstraints cc = new CellConstraints();

	builder.add(lblTitle, cc.xyw(2, 1, 5));
	builder.add(lblMessage, cc.xyw(2, 3, 5));
	builder.addSeparator(connectionText.toUpperCase(), cc.xyw(2, 4, 5));
	builder.add(lblLogin, cc.xy(2, 6));
	builder.add(users, cc.xyw(4, 6, 4));
	builder.add(lblPassword, cc.xy(2, 8));
	builder.add(txtPassword, cc.xyw(4, 8, 4));
	builder.add(btnConnection, cc.xy(3, 10));
	builder.add(btnClear, cc.xy(4, 10));
	builder.add(lblImage, cc.xyw(2, 12, 5));

	builder.border(Borders.DIALOG);

	initButtons();

	return builder.build();
    }

    private String[] users() {
	List<String> users = ApplicationManager.getUserService().usersLogin();
	return users.toArray(new String[users.size()]);
    }

    private void initButtons() {
	btnConnection.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		manageConnection();
	    }
	});
	btnClear.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		initForm();
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

    private void manageConnection() {
	List<JTextComponent> emptyComponents = UIUtils.checkIsNullOrEmpty(txtPassword);
	if (!emptyComponents.isEmpty()) {
	    for (JTextComponent currentComponent : emptyComponents) {
		currentComponent.setBackground(Color.PINK);
	    }
	    StateMessageLabelBuilder.create().withLabel(lblMessage).withText(messageFailMissingData).withVisibilty(true).withState(MessageType.FAIL).withVisibilityInSeconds(2_000).start();
	    LOGGER.debug("Some fields are null or empty");
	} else {
	    String userLogin = null;
	    String encryptedPassword;
	    try {
		encryptedPassword = CryptoUtils.getMD5EncryptedPassword(txtPassword.getPassword());
		if (users.getSelectedItem() != null) {
		    userLogin = users.getSelectedItem().toString();
		}
		UserDTO userDTO = UserDTOAssembler.userDTO(userLogin, encryptedPassword);
		UserDTO managedUserDTO = ApplicationManager.getUserService().findUserWithRoles(userDTO);
		if (managedUserDTO.getUser() != null) {
		    ApplicationManager.setConnectedUser(managedUserDTO);
		    LOGGER.info("User {} connected ", managedUserDTO.getUser().getName());
		    ApplicationManager.changePanel(new MainMenuPanel().get());

		} else {
		    StateMessageLabelBuilder.create().withLabel(lblMessage).withText(messageFailConnection).withVisibilty(true).withState(MessageType.FAIL).withVisibilityInSeconds(2_500).start();
		    LOGGER.debug("User {} cannot be authenticated ", userDTO.getUser().getName());
		}
	    } catch (CaisseException e) {
		LOGGER.error(e.getMessage());
	    }
	}
    }

    private void initForm() {
	resetValue(txtPassword);
	resetColor(txtPassword);
	if (lblMessage.isVisible()) {
	    lblMessage.setVisible(false);
	}
    }
}