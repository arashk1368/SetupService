/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudservices.brokerage.policy.setupservice.utils;

import cloudservices.brokerage.policy.setupservice.service.SetupWS;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static void buildSessionFactory(Configuration config) {
        try {
            // load from different directory
            HibernateUtil.sessionFactory = config
                    .buildSessionFactory();

        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            Logger.getLogger(SetupWS.class.getName()).log(Level.SEVERE,
                    "Initial SessionFactory creation failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
