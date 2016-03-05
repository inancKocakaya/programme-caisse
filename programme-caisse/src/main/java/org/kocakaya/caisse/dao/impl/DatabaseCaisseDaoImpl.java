package org.kocakaya.caisse.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.kocakaya.caisse.business.CbRecolte;
import org.kocakaya.caisse.business.MoneyType;
import org.kocakaya.caisse.business.Sale;
import org.kocakaya.caisse.business.SaleData;
import org.kocakaya.caisse.business.Server;
import org.kocakaya.caisse.business.User;
import org.kocakaya.caisse.dao.CaisseDao;
import org.kocakaya.caisse.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseCaisseDaoImpl implements CaisseDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseCaisseDaoImpl.class);

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("programme-caisse");

    @Override
    public List<User> users() {
	List<User> users = new ArrayList<>();
	LOGGER.info("Retrieve all users");
	EntityManager em = emf.createEntityManager();
	TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
	try {
	    users = query.getResultList();
	} catch (NoResultException e) {
	    LOGGER.debug("No users found");
	}
	em.close();
	return users;
    }

    @Override
    public List<MoneyType> moneyTypes() {
	List<MoneyType> moneyTypes = new ArrayList<>();
	LOGGER.info("Retrieve all money types");
	EntityManager em = emf.createEntityManager();
	TypedQuery<MoneyType> query = em.createQuery("SELECT mt FROM MoneyType mt", MoneyType.class);
	try {
	    moneyTypes = query.getResultList();
	} catch (NoResultException e) {
	    LOGGER.debug("No money types found");
	}
	em.close();
	return moneyTypes;
    }

    @Override
    public void deleteSales(Date date, Server server, MoneyType moneyType) {
	LOGGER.info("Delete all sales for date {} - money type {}", DateUtils.formatDateWithFrenchFormat(date), moneyType);
	EntityManager em = emf.createEntityManager();
	Query query = em.createQuery("DELETE FROM Sale s WHERE s.dateOperation = :date AND s.paymentType = :paymentType AND s.server = :server");
	query.setParameter("date", date, TemporalType.DATE);
	query.setParameter("paymentType", moneyType);
	query.setParameter("server", server);

	final EntityTransaction tx = em.getTransaction();

	tx.begin();
	query.executeUpdate();
	tx.commit();
	em.close();
    }

    public List<Sale> salesDetailsFromDate(Date date) {
	LOGGER.info("Retrieve all sales for date {}", DateUtils.formatDateWithFrenchFormat(date));
	EntityManager em = emf.createEntityManager();
	TypedQuery<Sale> query = em.createQuery("SELECT s FROM Sale s WHERE s.dateOperation = :date", Sale.class);
	query.setParameter("date", date, TemporalType.DATE);
	List<Sale> sales = query.getResultList();
	em.close();
	return sales;
    }

    public List<Sale> salesDetailsFromDateAndMoneyType(Date date, Server server, MoneyType moneyType) {
	LOGGER.info("Retrieve all sales for date {} and money type {}", DateUtils.formatDateWithFrenchFormat(date), moneyType);
	EntityManager em = emf.createEntityManager();
	TypedQuery<Sale> query = em.createQuery("SELECT s FROM Sale s WHERE s.dateOperation = :date AND s.paymentType = :paymentType AND s.server = :server", Sale.class);
	query.setParameter("date", date, TemporalType.DATE);
	query.setParameter("paymentType", moneyType);
	query.setParameter("server", server);
	List<Sale> sales = query.getResultList();
	em.close();
	return sales;
    }

    @Override
    public SaleData saleData(Server server, Date dateOperation) {
	SaleData managedSaleData = null;
	LOGGER.info("Retrieve Sale info for date {} and server {}", DateUtils.formatDateWithFrenchFormat(dateOperation), server);
	EntityManager em = emf.createEntityManager();
	TypedQuery<SaleData> query = em.createQuery("SELECT sd FROM SaleData sd WHERE sd.id.dateOperation = :date AND sd.id.serverId = :serverId", SaleData.class);
	query.setParameter("serverId", server.getId());
	query.setParameter("date", dateOperation, TemporalType.DATE);
	try {
	    managedSaleData = query.getSingleResult();
	} catch (NoResultException e) {
	    LOGGER.debug("No sale data found");
	}
	em.close();
	return managedSaleData;
    }

    @Override
    public void saveSaleData(SaleData saleData) {
	final EntityManager em = emf.createEntityManager();

	final EntityTransaction tx = em.getTransaction();

	tx.begin();

	em.persist(saleData);

	tx.commit();

	em.close();
    }

    @Override
    public void updateSaleData(SaleData saleData) {
	final EntityManager em = emf.createEntityManager();

	final EntityTransaction tx = em.getTransaction();

	tx.begin();

	em.merge(saleData);

	tx.commit();

	em.close();
    }

    public void saveSaleDetails(Sale sale) {
	final EntityManager em = emf.createEntityManager();

	final EntityTransaction tx = em.getTransaction();

	tx.begin();

	em.persist(sale);

	tx.commit();

	em.close();
    }

    @Override
    public void updateSaleDetails(Sale sale) {
	final EntityManager em = emf.createEntityManager();

	final EntityTransaction tx = em.getTransaction();

	tx.begin();

	em.merge(sale);

	tx.commit();

	em.close();
    }

    @Override
    public void addUser(User user) {
	final EntityManager em = emf.createEntityManager();

	final EntityTransaction tx = em.getTransaction();

	tx.begin();

	em.persist(user);

	tx.commit();

	em.close();
    }

    public User findUser(User user) {
	User managedUser = null;
	LOGGER.info("Retrieve user {}", user.getName());
	EntityManager em = emf.createEntityManager();
	TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.name = :name AND u.password = :password", User.class);
	query.setParameter("name", user.getName());
	query.setParameter("password", user.getPassword());
	try {
	    managedUser = query.getSingleResult();
	} catch (NoResultException e) {
	    LOGGER.debug("No user found");
	}
	if (managedUser != null) {
	    managedUser.getRoles();
	}
	LOGGER.info("User found {}", managedUser);
	em.close();
	return managedUser;
    }

    @Override
    public User findAdminUser() {
	User managedUser = null;
	String adminUsername = "Admin";
	LOGGER.info("Retrieve user {}", adminUsername);
	EntityManager em = emf.createEntityManager();
	TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class);
	query.setParameter("name", adminUsername);
	try {
	    managedUser = query.getSingleResult();
	} catch (NoResultException e) {
	    LOGGER.debug("No user found");
	}
	LOGGER.info("User found {}", managedUser);
	em.close();
	return managedUser;
    }

    @Override
    public void saveServer(Server server) {
	final EntityManager em = emf.createEntityManager();

	final EntityTransaction tx = em.getTransaction();

	tx.begin();

	em.persist(server);

	tx.commit();

	em.close();
    }

    @Override
    public void updateServer(Server server) {
	final EntityManager em = emf.createEntityManager();

	final EntityTransaction tx = em.getTransaction();

	tx.begin();

	em.merge(server);

	tx.commit();

	em.close();
    }

    @Override
    public void updateServers(List<Server> servers) {
	for (Server server : servers) {
	    updateServer(server);
	}
    }

    @Override
    public List<Server> servers() {
	final EntityManager em = emf.createEntityManager();
	TypedQuery<Server> query = em.createNamedQuery("findAll", Server.class);
	List<Server> servers = query.getResultList();
	em.close();
	return servers;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> salesByDay(String dateWithFrenchFormat) {
	EntityManager em = emf.createEntityManager();
	StringBuilder sb = new StringBuilder("SELECT FORMATDATETIME(DATE_OPERATION, 'dd/MM/yyyy') AS DATE_OPERATION, ").append("ROUND(SUM((AMOUNT_10_TAXES / 1.10) + (AMOUNT_20_TAXES / 1.20)), 2) AS TOTAL_HT, ").append("SUM(AMOUNT_10_TAXES + AMOUNT_20_TAXES) AS TOTAL_TTC, ").append("SUM(NB_COUVERT) AS TOTAL_NB_COUVERT ").append("FROM SALE_DATA ").append("WHERE FORMATDATETIME(DATE_OPERATION, 'dd/MM/yyyy') = '" + dateWithFrenchFormat + "' ").append("GROUP BY DATE_OPERATION ORDER BY DATE_OPERATION");
	List<Object[]> result = em.createNativeQuery(sb.toString()).getResultList();
	em.close();
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> salesByMonth(String month) {
	EntityManager em = emf.createEntityManager();
	StringBuilder sb = new StringBuilder("SELECT FORMATDATETIME(DATE_OPERATION, 'dd/MM/yyyy') AS DATE_OPERATION, ").append("ROUND(SUM((AMOUNT_10_TAXES / 1.10) + (AMOUNT_20_TAXES / 1.20)), 2) AS TOTAL_HT, ").append("SUM(AMOUNT_10_TAXES + AMOUNT_20_TAXES) AS TOTAL_TTC, ").append("SUM(NB_COUVERT) AS TOTAL_NB_COUVERT ").append("FROM SALE_DATA ").append("WHERE FORMATDATETIME(DATE_OPERATION, 'MM') = '" + month + "' ").append("GROUP BY DATE_OPERATION ORDER BY DATE_OPERATION");
	List<Object[]> result = em.createNativeQuery(sb.toString()).getResultList();
	em.close();
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> salesByYear(String year) {
	EntityManager em = emf.createEntityManager();
	StringBuilder sb = new StringBuilder("SELECT FORMATDATETIME(DATE_OPERATION, 'MM') AS DATE_OPERATION, ").append("ROUND(SUM((AMOUNT_10_TAXES / 1.10) + (AMOUNT_20_TAXES / 1.20)), 2) AS TOTAL_HT, ").append("SUM(AMOUNT_10_TAXES + AMOUNT_20_TAXES) AS TOTAL_TTC, ").append("SUM(NB_COUVERT) AS TOTAL_NB_COUVERT ").append("FROM SALE_DATA ").append("WHERE FORMATDATETIME(DATE_OPERATION, 'yyyy') = '" + year + "' ").append("GROUP BY FORMATDATETIME(DATE_OPERATION, 'MM') ORDER BY DATE_OPERATION");
	List<Object[]> result = em.createNativeQuery(sb.toString()).getResultList();
	em.close();
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> ticketsByJour(String month) {
	EntityManager em = emf.createEntityManager();
	StringBuilder sb = new StringBuilder("SELECT FORMATDATETIME(DATE_OPERATION, 'dd/MM/yyyy') AS DATE_OPERATION, SUM(QTY) AS TOTAL_QTY, SUM(AMOUNT * QTY) AS TOTAL_AMOUNT ").append("FROM SALE_DETAILS ").append("WHERE PAYMENT_TYPE_ID = 'TICKET_RESTAURANT'").append("AND FORMATDATETIME(DATE_OPERATION, 'MM') = '" + month + "' ").append("GROUP BY DATE_OPERATION ORDER BY DATE_OPERATION");
	List<Object[]> result = em.createNativeQuery(sb.toString()).getResultList();
	em.close();
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> detailsTicketsByJour(String dateWithFrenchFormat) {
	EntityManager em = emf.createEntityManager();
	StringBuilder sb = new StringBuilder("SELECT SUM(QTY) AS TOTAL_QTY, AMOUNT ").append("FROM SALE_DETAILS ").append("WHERE PAYMENT_TYPE_ID = 'TICKET_RESTAURANT'").append("AND FORMATDATETIME(DATE_OPERATION, 'dd/MM/yyyy') = '" + dateWithFrenchFormat + "' ").append("GROUP BY AMOUNT");
	List<Object[]> result = em.createNativeQuery(sb.toString()).getResultList();
	em.close();
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> ticketsCumul(String month) {
	EntityManager em = emf.createEntityManager();
	StringBuilder sb = new StringBuilder("SELECT SUM(QTY) AS TOTAL_QTY, SUM(AMOUNT * QTY) AS TOTAL_AMOUNT ").append("FROM SALE_DETAILS ").append("WHERE PAYMENT_TYPE_ID = 'TICKET_RESTAURANT'").append("AND FORMATDATETIME(DATE_OPERATION, 'MM') = '" + month + "' ").append("GROUP BY FORMATDATETIME(DATE_OPERATION, 'MM')");
	List<Object[]> result = em.createNativeQuery(sb.toString()).getResultList();
	em.close();
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> detailsTicketsCumul(String month) {
	EntityManager em = emf.createEntityManager();
	StringBuilder sb = new StringBuilder("SELECT SUM(QTY) AS TOTAL_QTY, AMOUNT ").append("FROM SALE_DETAILS ").append("WHERE PAYMENT_TYPE_ID = 'TICKET_RESTAURANT'").append("AND FORMATDATETIME(DATE_OPERATION, 'MM') = '" + month + "' ").append("GROUP BY FORMATDATETIME(DATE_OPERATION, 'MM'), AMOUNT");
	List<Object[]> result = em.createNativeQuery(sb.toString()).getResultList();
	em.close();
	return result;
    }

    @Override
    public CbRecolte cbRecolteByDate(Date dateOperation) {
	LOGGER.info("Retrieve CB payments amount for date {}", DateUtils.formatDateWithFrenchFormat(dateOperation));
	EntityManager em = emf.createEntityManager();
	TypedQuery<CbRecolte> query = em.createQuery("SELECT c FROM CbRecolte c WHERE c.dateOperation = :date", CbRecolte.class);
	query.setParameter("date", dateOperation, TemporalType.DATE);
	CbRecolte cbRecolte = null;
	try {
	    cbRecolte = query.getSingleResult();
	} catch (NoResultException e) {
	    cbRecolte = new CbRecolte();
	    cbRecolte.setAmount(0);
	    cbRecolte.setDateOperation(dateOperation);
	}
	em.close();
	return cbRecolte;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> cbSalesByDate(String dateWithFrenchFormat) {
	EntityManager em = emf.createEntityManager();
	StringBuilder sb = new StringBuilder("SELECT SUM(AMOUNT) AS TOTAL ").append("FROM SALE_DETAILS ").append("WHERE PAYMENT_TYPE_ID = 'CARTE_BANCAIRE' ").append("AND FORMATDATETIME(DATE_OPERATION, 'dd/MM/yyyy') = '" + dateWithFrenchFormat + "'");
	List<Object[]> result = em.createNativeQuery(sb.toString()).getResultList();
	em.close();
	return result;
    }

    @Override
    public void saveCbRecolte(CbRecolte cbRecolte) {
	final EntityManager em = emf.createEntityManager();

	final EntityTransaction tx = em.getTransaction();

	tx.begin();

	em.persist(cbRecolte);

	tx.commit();

	em.close();
    }

    @Override
    public void deleteCbRecolteByMonth(String month) {
	EntityManager em = emf.createEntityManager();
	StringBuilder sb = new StringBuilder("DELETE FROM CB_RECOLTE ").append("WHERE FORMATDATETIME(DATE_OPERATION, 'MM') = '" + month + "'");

	Query query = em.createNativeQuery(sb.toString());
	final EntityTransaction tx = em.getTransaction();

	tx.begin();
	query.executeUpdate();
	tx.commit();
	em.close();
    }

    @Override
    public List<String> usersLogin() {
	List<String> usersLogin = new ArrayList<>();
	LOGGER.info("Retrieve all users");
	EntityManager em = emf.createEntityManager();
	String query = "SELECT u.name FROM User u";
	TypedQuery<String> typedQuery = em.createQuery(query, String.class);
	usersLogin = typedQuery.getResultList();
	return usersLogin;
    }
}