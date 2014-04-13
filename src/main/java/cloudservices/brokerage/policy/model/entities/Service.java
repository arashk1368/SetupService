/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudservices.brokerage.policy.model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * A service which can be a part of a plan or complete composition plan
 *
 * @author Arash Khodadadi http://www.arashkhodadadi.com/
 */
@Entity
public class Service extends UniObject implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @Column
    private String WSDLURL;
    @Column
    private String servicesStr;
    @OneToMany(mappedBy = "service")
    private Set<ServiceProposition> servicePropositions;
    @OneToMany(mappedBy = "service")
    private Set<PolicyService> policyServices;
    @Transient
    private List<Set<Service>> services;

    public Service() {
        this.services = new ArrayList<>();
        this.servicePropositions = new HashSet<>();
        this.policyServices=new HashSet<>();
        servicesStr = "";
    }

    public boolean addServiceLevel(Set<Service> serviceLevel) {
        boolean suc = services.add(serviceLevel);
        if (suc) {
            if (isComposite()) {
                servicesStr += "-";
            }
            servicesStr += getServicesStr(serviceLevel);
        }
        return suc;
    }

    public boolean isComposite() {
        return levels() > 1 ? true : false;
    }

    public int levels() {
        return this.services.size();
    }

    public Set<Service> getLevel(int level) {
        return this.services.get(level);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        super.setUniId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        super.setUniName(name);
    }

    public String getWSDLURL() {
        return WSDLURL;
    }

    public void setWSDLURL(String WSDLURL) {
        this.WSDLURL = WSDLURL;
    }

    public Set<ServiceProposition> getServicePropositions() {
        return servicePropositions;
    }

    public String getServicesStr() {
        return servicesStr;
    }

    public Set<PolicyService> getPolicyServices() {
        return policyServices;
    }
    
    private String getServicesStr(Set<Service> services) {
        String str = "";
        for (Service service : services) {
            str += service.getId().toString();
            str += ",";
        }
        if (str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }
}