/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudservices.brokerage.policy.setupservice.service;

import cloudservices.brokerage.policy.model.DAO.BaseDAO;
import cloudservices.brokerage.utils.file_utils.ResourceFileUtil;
import cloudservices.brokerage.utils.logging.LoggerSetup;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Arash Khodadadi http://www.arashkhodadadi.com/
 */
@WebService(serviceName = "SetupWS")
public class SetupWS {

    private final static Logger LOGGER = Logger.getLogger(SetupWS.class
            .getName());

    private void setupLoggers() throws IOException {
        LoggerSetup.setup(ResourceFileUtil.getResourcePath("log.txt"), ResourceFileUtil.getResourcePath("log.html"));
        LoggerSetup.log4jSetup(ResourceFileUtil.getResourcePath("log4j.properties"),
                ResourceFileUtil.getResourcePath("hibernate.log"));
    }

    /**
     * Web service operation to initialize the policy database
     */
    @WebMethod(operationName = "initializePolicyDatabase")
    public boolean initializePolicyDatabase(@WebParam(name = "databaseName") String databaseName)
            throws IOException {
        this.setupLoggers();
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        try {
            BaseDAO.createDatabase(configuration, databaseName);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SetupWS.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            BaseDAO.closeSession();
        }
        return true;
    }
}
