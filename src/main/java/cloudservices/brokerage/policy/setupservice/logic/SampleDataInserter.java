/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudservices.brokerage.policy.setupservice.logic;

import cloudservices.brokerage.policy.policycommons.model.DAO.DAOException;
import cloudservices.brokerage.policy.policycommons.model.DAO.PolicyDAO;
import cloudservices.brokerage.policy.policycommons.model.DAO.PolicyPropositionDAO;
import cloudservices.brokerage.policy.policycommons.model.DAO.PolicyServiceDAO;
import cloudservices.brokerage.policy.policycommons.model.DAO.PropositionDAO;
import cloudservices.brokerage.policy.policycommons.model.DAO.ServiceDAO;
import cloudservices.brokerage.policy.policycommons.model.DAO.ServicePropositionDAO;
import cloudservices.brokerage.policy.policycommons.model.entities.Policy;
import cloudservices.brokerage.policy.policycommons.model.entities.PolicyProposition;
import cloudservices.brokerage.policy.policycommons.model.entities.PolicyService;
import cloudservices.brokerage.policy.policycommons.model.entities.Proposition;
import cloudservices.brokerage.policy.policycommons.model.entities.Service;
import cloudservices.brokerage.policy.policycommons.model.entities.ServiceProposition;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 *
 * @author Arash Khodadadi http://www.arashkhodadadi.com/
 */
public class SampleDataInserter {

    private final static Logger LOGGER = Logger.getLogger(SampleDataInserter.class
            .getName());
    private ServiceDAO serviceDAO;
    private PropositionDAO propositionDAO;
    private PolicyDAO policyDAO;
    private ServicePropositionDAO servicePropositionDAO;
    private PolicyServiceDAO policyServiceDAO;
    private PolicyPropositionDAO policyPropositionDAO;

    public SampleDataInserter() {
        serviceDAO = new ServiceDAO();
        propositionDAO = new PropositionDAO();
        policyDAO = new PolicyDAO();
        policyServiceDAO = new PolicyServiceDAO();
        policyPropositionDAO = new PolicyPropositionDAO();
        servicePropositionDAO = new ServicePropositionDAO();
    }

    public boolean insertSampleData(int numberOfLevels) throws DAOException {
        Proposition crawler4jFinished = createProposition("Crawler4j Finished", true);
        Proposition crawler4jFilteredFinished = createProposition("Crawler4j With Filter Finished", true);
        Proposition websphinxFinished = createProposition("Websphinx Finished", true);
        Proposition seedsAvailable = createProposition("Seeds Available", true);

        Proposition[] levels = new Proposition[numberOfLevels];
        Service[] crawler4js = new Service[numberOfLevels];
        Service[] crawler4jFiltereds = new Service[numberOfLevels];
        Service[] websphinxs = new Service[numberOfLevels];

        for (int i = 0; i < numberOfLevels; i++) {
            levels[i] = createProposition("Level " + (i + 1) + " Completed", true);
            crawler4js[i] = createCrawler4j(String.valueOf(i));
            addInputToService(crawler4js[i], seedsAvailable);
            addOutputToService(crawler4js[i], crawler4jFinished);
            addOutputToService(crawler4js[i], levels[i]);
            crawler4jFiltereds[i] = createCrawler4jFiltered(String.valueOf(i));
            addInputToService(crawler4jFiltereds[i], seedsAvailable);
            addOutputToService(crawler4jFiltereds[i], crawler4jFilteredFinished);
            addOutputToService(crawler4jFiltereds[i], levels[i]);
            websphinxs[i] = createWebsphinx(String.valueOf(i));
            addInputToService(websphinxs[i], seedsAvailable);
            addOutputToService(websphinxs[i], websphinxFinished);
            addOutputToService(websphinxs[i], levels[i]);
        }

        for (int i = 1; i < numberOfLevels; i++) {
            addInputToService(crawler4js[i], levels[i - 1]);
            addInputToService(crawler4jFiltereds[i], levels[i - 1]);
            addInputToService(websphinxs[i], levels[i - 1]);
            serviceDAO.saveOrUpdate(crawler4js[i]);
            serviceDAO.saveOrUpdate(crawler4jFiltereds[i]);
            serviceDAO.saveOrUpdate(websphinxs[i]);
        }
        serviceDAO.saveOrUpdate(crawler4js[0]);
        serviceDAO.saveOrUpdate(crawler4jFiltereds[0]);
        serviceDAO.saveOrUpdate(websphinxs[0]);

        //adding simple policy
        Policy policy = new Policy();
        policy.setName("Force Filtered Crawling in Level 3");
        policy.setPriority(1);
        policy.setId((Long) policyDAO.save(policy));

        PolicyProposition pp = new PolicyProposition();
        pp.addConditionToPolicy(policy, levels[1]);
        policyPropositionDAO.save(pp);
        pp = new PolicyProposition();
        pp.addConditionToPolicy(policy, seedsAvailable);
        policyPropositionDAO.save(pp);

        pp = new PolicyProposition();
        pp.addEventToPolicy(policy, levels[2]);
        policyPropositionDAO.save(pp);
        pp = new PolicyProposition();
        pp.addEventToPolicy(policy, crawler4jFinished);
        policyPropositionDAO.save(pp);

        PolicyService ps = new PolicyService();
        ps.addActionToPolicy(policy, crawler4jFiltereds[2]);
        policyServiceDAO.save(ps);

        policyDAO.saveOrUpdate(policy);

        Service composite = new Service();
        composite.setName("Composite Level 3 Crawler");
        Set<Service> level1 = new HashSet<>();
        level1.add(websphinxs[0]);
        Set<Service> level2 = new HashSet<>();
        level2.add(crawler4js[1]);
        Set<Service> level3 = new HashSet<>();
        level3.add(crawler4js[2]);
        composite.addServiceLevel(level1);
        composite.addServiceLevel(level2);
        composite.addServiceLevel(level3);
        composite.setId((Long) serviceDAO.save(composite));

        ServiceProposition csp = new ServiceProposition();
        csp.addInputToService(composite, seedsAvailable);
        servicePropositionDAO.save(csp);
        csp = new ServiceProposition();
        csp.addOutputToService(composite, levels[2]);
        servicePropositionDAO.save(csp);
        csp = new ServiceProposition();
        csp.addOutputToService(composite, websphinxFinished);
        servicePropositionDAO.save(csp);
        csp = new ServiceProposition();
        csp.addOutputToService(composite, crawler4jFinished);
        servicePropositionDAO.save(csp);
        csp = new ServiceProposition();
        csp.addOutputToService(composite, crawler4jFilteredFinished);
        servicePropositionDAO.save(csp);

        serviceDAO.saveOrUpdate(composite);
        return insertPolicyServices();
    }

    public boolean insertPolicyServices() throws DAOException {
        Service setupService = new Service();
        setupService.setName("Setup");
        setupService.setWSDLURL("http://localhost:8080/SetupService/SetupWS?wsdl");
        setupService.setId((Long) serviceDAO.save(setupService));
        setupService.setServicesStr(setupService.getId().toString());
        serviceDAO.saveOrUpdate(setupService);

        Service serviceExecutor = new Service();
        serviceExecutor.setName("Service Executor");
        serviceExecutor.setWSDLURL("http://localhost:8080/ServiceExecutor/ServiceExecutorWS?wsdl");
        serviceExecutor.setId((Long) serviceDAO.save(serviceExecutor));
        serviceExecutor.setServicesStr(serviceExecutor.getId().toString());
        serviceDAO.saveOrUpdate(serviceExecutor);

        Service policyManager = new Service();
        policyManager.setName("Policy Manager");
        policyManager.setWSDLURL("http://localhost:8080/PolicyManager/PolicyManagerWS?wsdl");
        policyManager.setId((Long) serviceDAO.save(policyManager));
        policyManager.setServicesStr(policyManager.getId().toString());
        serviceDAO.saveOrUpdate(policyManager);
        return true;
    }

    private Long addInputToService(Service service, Proposition input) throws DAOException {
        ServiceProposition sp = new ServiceProposition();
        sp.addInputToService(service, input);
        return (Long) servicePropositionDAO.save(sp);
    }

    private Long addOutputToService(Service service, Proposition output) throws DAOException {
        ServiceProposition sp = new ServiceProposition();
        sp.addOutputToService(service, output);
        return (Long) servicePropositionDAO.save(sp);
    }

    private Service createCrawler4j(String level) throws DAOException {
        Service crawler4j = new Service();
        crawler4j.setName("crawler4j " + level);
        crawler4j.setWSDLURL("http://localhost:8080/crawler4jService/crawler4jWS?wsdl");
        crawler4j.setId((Long) serviceDAO.save(crawler4j));
        crawler4j.setServicesStr(crawler4j.getId().toString());
        serviceDAO.saveOrUpdate(crawler4j);
        return crawler4j;
    }

    private Service createCrawler4jFiltered(String level) throws DAOException {
        Service crawler4jFiltered = new Service();
        crawler4jFiltered.setName("crawler4jFiltered " + level);
        crawler4jFiltered.setWSDLURL("http://localhost:8080/crawler4jService/crawler4jWS?wsdl");
        crawler4jFiltered.setId((Long) serviceDAO.save(crawler4jFiltered));
        crawler4jFiltered.setServicesStr(crawler4jFiltered.getId().toString());
        serviceDAO.saveOrUpdate(crawler4jFiltered);
        return crawler4jFiltered;
    }

    private Service createWebsphinx(String level) throws DAOException {
        Service websphinx = new Service();
        websphinx.setName("websphinx " + level);
        websphinx.setWSDLURL("http://localhost:8080/websphinxService/WebsphinxWS?wsdl");
        websphinx.setId((Long) serviceDAO.save(websphinx));
        websphinx.setServicesStr(websphinx.getId().toString());
        serviceDAO.saveOrUpdate(websphinx);
        return websphinx;
    }

    private Proposition createProposition(String name, boolean valid) throws DAOException {
        Proposition prop = new Proposition();
        prop.setName(name);
        prop.setValid(true);
        prop.setId((Long) propositionDAO.save(prop));
        return prop;
    }
}
