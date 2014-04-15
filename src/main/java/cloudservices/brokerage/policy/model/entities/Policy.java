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
    private String name;
    @Column
    private int priority;
    @Column
    private String events;
    @Column
    private int numberOfEvents;
    @Column
    private String conditions;
    @Column
    private int numberOfConditions;
    @Column
    private String actions;
    @Column
    private int numberOfActions;
    @OneToMany(mappedBy = "policy")
    private Set<PolicyProposition> policyPropositions;
    @OneToMany(mappedBy = "policy")
    private Set<PolicyService> policyServices;
    

    public Policy() {
        policyPropositions=new HashSet<>();
        policyServices=new HashSet<>();
        events="";
        numberOfEvents=0;
        conditions="";
        numberOfConditions=0;
        actions="";
        numberOfActions=0;
    }
    
    protected boolean addEvent(Proposition prop) {
        String eventStr = prop.getName() + "-" + prop.getId();
        if (events.contains(eventStr)) {
            return false;
        }
        if (numberOfEvents > 0) {
            events += ",";
        }
        events += eventStr;
        numberOfEvents++;
        return true;
    }
    
    protected boolean addCondition(Proposition prop) {
        String conditionStr = prop.getName() + "-" + prop.getId();
        if (conditions.contains(conditionStr)) {
            return false;
        }
        if (numberOfConditions > 0) {
            conditions += ",";
        }
        conditions += conditionStr;
        numberOfConditions++;
        return true;
    }
    
    protected boolean addAction(Service service) {
        String serviceStr = service.getName() + "-" + service.getId();
        if (actions.contains(serviceStr)) {
            return false;
        }
        if (numberOfActions > 0) {
            actions += ",";
        }
        actions += serviceStr;
        numberOfActions++;
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        super.setUniId(id);
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

    public String getEvents() {
        return events;
    }

    public int getNumberOfEvents() {
        return numberOfEvents;
    }

    public String getConditions() {
        return conditions;
    }

    public int getNumberOfConditions() {
        return numberOfConditions;
    }

    public String getActions() {
        return actions;
    }

    public int getNumberOfActions() {
        return numberOfActions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        super.setUniName(name);
    }
    
    
}
