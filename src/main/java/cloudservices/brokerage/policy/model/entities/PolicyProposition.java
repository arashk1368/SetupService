/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudservices.brokerage.policy.model.entities;

import cloudservices.brokerage.policy.model.enums.PolicyPropositionType;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Arash Khodadadi http://www.arashkhodadadi.com/
 */
@Entity
public class PolicyProposition implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private PolicyPropositionType type;
    @ManyToOne
    @JoinColumn(name = "proposition_id")
    private Proposition proposition;
    @ManyToOne
    @JoinColumn(name = "policy_id")
    private Policy policy;

    public PolicyProposition() {
    }

    public boolean addEventToPolicy(Policy policy, Proposition event) {
        boolean result = policy.addEvent(event);
        if(!result){
            return false;
        }
        else{
            this.setPolicy(policy);
            this.setProposition(event);
            this.setType(PolicyPropositionType.EVENT);
            return true;
        }
    }
    
    public boolean addConditionToPolicy(Policy policy, Proposition condition) {
        boolean result = policy.addCondition(condition);
        if(!result){
            return false;
        }
        else{
            this.setPolicy(policy);
            this.setProposition(condition);
            this.setType(PolicyPropositionType.CONDITION);
            return true;
        }
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PolicyPropositionType getType() {
        return type;
    }

    public void setType(PolicyPropositionType type) {
        this.type = type;
    }

    public Proposition getProposition() {
        return proposition;
    }

    public void setProposition(Proposition proposition) {
        this.proposition = proposition;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }
}
