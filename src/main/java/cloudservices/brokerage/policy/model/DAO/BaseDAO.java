/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudservices.brokerage.policy.model.DAO;

import cloudservices.brokerage.policy.policycommons.model.DAO.DAOException;
import cloudservices.brokerage.policy.setupservice.utils.HibernateUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 *
 * @author Arash Khodadadi http://www.arashkhodadadi.com/
 */
public abstract class BaseDAO {

    private static Session session;

    public BaseDAO() {
    }

    public static void createDatabase(Configuration config, String dbName)
            throws ClassNotFoundException, SQLException {
        Class.forName(config.getProperty("hibernate.connection.driver_class"));
        Connection con = DriverManager.getConnection(config.getProperty("hibernate.connection.url"),
                config.getProperty("hibernate.connection.username"),
                config.getProperty("hibernate.connection.password"));
        con.createStatement().executeUpdate("DROP DATABASE IF EXISTS " + dbName);
        con.createStatement().executeUpdate("CREATE DATABASE " + dbName);

        String url = config.getProperty("hibernate.connection.url");
        config.setProperty("hibernate.connection.url", url + dbName);
        openSession(config);
        SchemaExport schema = new SchemaExport(config);
        schema.create(true, true);
    }

    public static void openSession() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public static void openSession(Configuration config) {
        HibernateUtil.buildSessionFactory(config);
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public static void closeSession() {
        session.close();
        HibernateUtil.getSessionFactory().close();
    }

    protected void startTransaction() throws DAOException {
        if (!getSession().getTransaction().isActive()) {
            getSession().beginTransaction();
        }
    }

    protected void commitTransaction() throws DAOException {
        if (getSession().getTransaction().isActive()) {
            getSession().getTransaction().commit();
        }
    }

    protected Session getSession() throws DAOException {
        if (HibernateUtil.getSessionFactory().isClosed()) {
            throw new DAOException("Session factory already closed");
        } else if (session == null) {
            throw new DAOException("Session is not opened yet");
        } else if (!session.isOpen()) {
            throw new DAOException("Session already closed");
        }
        return session;
    }

    protected List getAll(String className) throws DAOException {
        startTransaction();
        Query query = getSession().createQuery("from " + className);
        List temp = query.list();
        commitTransaction();
        return temp;
    }

    protected Object getById(Long id, Class entity) throws DAOException {
        startTransaction();
        Object temp = getSession().get(entity, id);
        commitTransaction();
        return temp;
    }

    protected Serializable save(Object entity) throws DAOException {
        startTransaction();
        Serializable temp = getSession().save(entity);
        commitTransaction();
        return temp;
    }

    protected void saveOrUpdate(Object entity) throws DAOException {
        startTransaction();
        getSession().saveOrUpdate(entity);
        commitTransaction();
    }
}
