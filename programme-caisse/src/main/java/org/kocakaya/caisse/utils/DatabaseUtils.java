package org.kocakaya.caisse.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.h2.tools.RunScript;
import org.kocakaya.caisse.business.MoneyType;
import org.kocakaya.caisse.business.User;
import org.kocakaya.caisse.dao.CaisseDao;
import org.kocakaya.caisse.exception.CaisseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseUtils.class);

    private CaisseDao caisseDao;

    String createFile = "sql/create.sql";
    String insertFile = "sql/insert.sql";

    public DatabaseUtils(CaisseDao caisseDao) {
	super();
	this.caisseDao = caisseDao;
    }

    public void init() throws ClassNotFoundException, SQLException {

	Connection connection = null;
	try {
	    Class.forName("org.h2.Driver");
	    connection = DriverManager.getConnection("jdbc:h2:file:C:/programme-caisse/db/repository", "", "");

	    createDatabaseIfNeeded(connection);

	    List<User> users = caisseDao.users();

	    if (users == null || users.isEmpty()) {
		initUsers();
	    }
	    List<MoneyType> moneyTypes = caisseDao.moneyTypes();

	    if (moneyTypes == null || moneyTypes.isEmpty()) {
		initOtherData(connection);
	    }
	} catch (Exception e) {
	    LOGGER.error(e.getMessage());
	} finally {
	    if (connection != null) {
		connection.close();
	    }
	}
    }

    private void createDatabaseIfNeeded(Connection connection) throws SQLException, FileNotFoundException {
	DatabaseMetaData dbm = connection.getMetaData();
	ResultSet rs = dbm.getTables(null, null, "USER", null);
	if (!rs.next()) {
	    RunScript.execute(connection, new FileReader(createFile));
	    LOGGER.info("Database created from {}", createFile);
	}
    }

    private void initUsers() throws CaisseException {
	String user1Login = "Admin";
	String user1Password = "avenue";

	String encryptedUser1Password = CryptoUtils.getMD5EncryptedPassword(user1Password);

	User user = new User(user1Login, encryptedUser1Password);

	caisseDao.addUser(user);

	String user2Login = "Ikocakaya";
	String user2Password = "boulevard";

	String encryptedUser2Password = CryptoUtils.getMD5EncryptedPassword(user2Password);

	User user2 = new User(user2Login, encryptedUser2Password);

	caisseDao.addUser(user2);

	String user3Login = "Sseri";
	String user3Password = "rue";

	String encryptedUser3Password = CryptoUtils.getMD5EncryptedPassword(user3Password);

	User user3 = new User(user3Login, encryptedUser3Password);

	caisseDao.addUser(user3);

	String user4Login = "Fmeyfeldt";
	String user4Password = "impasse";

	String encryptedUser4Password = CryptoUtils.getMD5EncryptedPassword(user4Password);

	User user4 = new User(user4Login, encryptedUser4Password);

	caisseDao.addUser(user4);
    }

    private void initOtherData(Connection connection) {
	try {
	    RunScript.execute(connection, new FileReader(insertFile));
	    LOGGER.info("Data inserted from {}", insertFile);
	} catch (Exception e) {
	    LOGGER.error(e.getMessage());
	    System.exit(0);
	}
    }
}