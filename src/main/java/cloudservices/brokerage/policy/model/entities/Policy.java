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
public class Policy extends UniObject implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private int priority;
    @OneToMany(mappedBy = "policy")
    private Set<PolicyProposition> policyPropositions;
    @OneToMany(mappedBy = "policy")
    private Set<PolicyService> policyServices;
    

    public Policy() {
        policyPropositions=new HashSet<>();
        policyServices=new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Set<PolicyProposition> getPolicyPropositions() {
        return policyPropositions;
    }

    public Set<PolicyService> getPolicyServices() {
        return policyServices;
    }
    
    
}
