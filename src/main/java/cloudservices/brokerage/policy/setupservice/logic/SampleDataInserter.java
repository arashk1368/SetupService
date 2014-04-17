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
    
    public boolean insertSampleData() throws DAOException {
        Proposition firstLevel = new Proposition();
        firstLevel.setName("First Level");
        firstLevel.setValid(false);
        Proposition secondLevel = new Proposition();
        secondLevel.setName("Second Level");
        secondLevel.setValid(false);
        Proposition thirdLevel = new Proposition();
        thirdLevel.setName("Third Level");
        thirdLevel.setValid(false);
        
        firstLevel.setId((Long) propositionDAO.save(firstLevel));
        secondLevel.setId((Long) propositionDAO.save(secondLevel));
        thirdLevel.setId((Long) propositionDAO.save(thirdLevel));
        
        Proposition crawler4jFinished = new Proposition();
        crawler4jFinished.setName("Crawler4j Finished");
        crawler4jFinished.setValid(false);
        Proposition crawler4jFilteredFinished = new Proposition();
        crawler4jFilteredFinished.setName("Crawler4j With Filter Finished");
        crawler4jFilteredFinished.setValid(false);
        Proposition websphinxFinished = new Proposition();
        websphinxFinished.setName("Websphinx Finished");
        websphinxFinished.setValid(false);
        
        crawler4jFinished.setId((Long) propositionDAO.save(crawler4jFinished));
        crawler4jFilteredFinished.setId((Long) propositionDAO.save(crawler4jFilteredFinished));
        websphinxFinished.setId((Long) propositionDAO.save(websphinxFinished));
        
        Proposition filterAvailable = new Proposition();
        filterAvailable.setName("Filter Available");
        filterAvailable.setValid(false);
        Proposition seedsAvailable = new Proposition();
        seedsAvailable.setName("Seeds Available");
        seedsAvailable.setValid(false);
        
        filterAvailable.setId((Long) propositionDAO.save(filterAvailable));
        seedsAvailable.setId((Long) propositionDAO.save(seedsAvailable));
        
        Service crawler4j = new Service();
        crawler4j.setName("crawler4j");
        crawler4j.setWSDLURL("http://localhost:8080/crawler4jService/crawler4jWS?wsdl");
        crawler4j.setId((Long) serviceDAO.save(crawler4j));
        crawler4j.setServicesStr(crawler4j.getId().toString());
        serviceDAO.saveOrUpdate(crawler4j);
        
        Service crawler4jFiltered = new Service();
        crawler4jFiltered.setName("crawler4jFiltered");
        crawler4jFiltered.setWSDLURL("http://localhost:8080/crawler4jService/crawler4jWS?wsdl");
        crawler4jFiltered.setId((Long) serviceDAO.save(crawler4jFiltered));
        crawler4jFiltered.setServicesStr(crawler4jFiltered.getId().toString());
        serviceDAO.saveOrUpdate(crawler4jFiltered);
        
        Service websphinx = new Service();
        websphinx.setName("websphinx");
        websphinx.setWSDLURL("http://localhost:8080/websphinxService/WebsphinxWS?wsdl");
        websphinx.setId((Long) serviceDAO.save(websphinx));
        websphinx.setServicesStr(websphinx.getId().toString());
        serviceDAO.saveOrUpdate(websphinx);
        
        ServiceProposition sp = new ServiceProposition();
        sp.addInputToService(websphinx, seedsAvailable);
        servicePropositionDAO.save(sp);
        sp = new ServiceProposition();
        sp.addOutputToService(websphinx, firstLevel);
        servicePropositionDAO.save(sp);
        sp = new ServiceProposition();
        sp.addOutputToService(websphinx, websphinxFinished);
        servicePropositionDAO.save(sp);
        
        sp = new ServiceProposition();
        sp.addInputToService(crawler4j, firstLevel);
        servicePropositionDAO.save(sp);
        sp = new ServiceProposition();
        sp.addInputToService(crawler4j, seedsAvailable);
        servicePropositionDAO.save(sp);
        sp = new ServiceProposition();
        sp.addOutputToService(crawler4j, crawler4jFinished);
        servicePropositionDAO.save(sp);
        sp = new ServiceProposition();
        sp.addOutputToService(crawler4j, secondLevel);
        servicePropositionDAO.save(sp);
        
        sp = new ServiceProposition();
        sp.addInputToService(crawler4jFiltered, secondLevel);
        servicePropositionDAO.save(sp);
        sp = new ServiceProposition();
        sp.addInputToService(crawler4jFiltered, seedsAvailable);
        servicePropositionDAO.save(sp);
        sp = new ServiceProposition();
        sp.addInputToService(crawler4jFiltered, filterAvailable);
        servicePropositionDAO.save(sp);
        sp = new ServiceProposition();
        sp.addOutputToService(crawler4jFiltered, crawler4jFilteredFinished);
        servicePropositionDAO.save(sp);
        sp = new ServiceProposition();
        sp.addOutputToService(crawler4jFiltered, thirdLevel);
        servicePropositionDAO.save(sp);
        
        Policy policy = new Policy();
        policy.setName("Force Filtered Crawling in Level 3");
        policy.setPriority(1);
        policy.setId((Long) policyDAO.save(policy));
        
        PolicyProposition pp = new PolicyProposition();
        pp.addConditionToPolicy(policy, secondLevel);
        policyPropositionDAO.save(pp);
        pp = new PolicyProposition();
        pp.addConditionToPolicy(policy, filterAvailable);
        policyPropositionDAO.save(pp);
        pp = new PolicyProposition();
        pp.addConditionToPolicy(policy, seedsAvailable);
        policyPropositionDAO.save(pp);
        
        pp = new PolicyProposition();
        pp.addEventToPolicy(policy, thirdLevel);
        policyPropositionDAO.save(pp);
        pp = new PolicyProposition();
        pp.addEventToPolicy(policy, crawler4jFinished);
        policyPropositionDAO.save(pp);
        
        PolicyService ps = new PolicyService();
        ps.addActionToPolicy(policy, crawler4jFiltered);
        policyServiceDAO.save(ps);
        
        serviceDAO.saveOrUpdate(crawler4j);
        serviceDAO.saveOrUpdate(crawler4jFiltered);
        serviceDAO.saveOrUpdate(websphinx);
        policyDAO.saveOrUpdate(policy);
        return true;
    }
}
