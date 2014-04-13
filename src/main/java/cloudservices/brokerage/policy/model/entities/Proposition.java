/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudservices.brokerage.policy.model.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Arash Khodadadi http://www.arashkhodadadi.com/
 */
@Entity
public class Proposition extends UniObject implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private boolean valid;
    @Column
    private String name;
    @OneToMany(mappedBy = "proposition")
    private Set<ServiceProposition> servicePropositions;
    @OneToMany(mappedBy = "proposition")
    private Set<PolicyProposition> policyPropositions;

    public Proposition() {
        servicePropositions = new HashSet<>();
        policyPropositions=new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        super.setUniId(id);
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        super.setUniName(name);
    }

    public Set<ServiceProposition> getServicePropositions() {
        return servicePropositions;
    }

    public Set<PolicyProposition> getPolicyPropositions() {
        return policyPropositions;
    }
    
}
