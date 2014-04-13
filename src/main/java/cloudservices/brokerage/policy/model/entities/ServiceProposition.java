/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudservices.brokerage.policy.model.entities;

import cloudservices.brokerage.policy.model.enums.ServicePropositionType;
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
public class ServiceProposition implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private ServicePropositionType type;
    @ManyToOne
    @JoinColumn(name = "proposition_id")
    private Proposition proposition;
    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    public ServiceProposition() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServicePropositionType getType() {
        return type;
    }

    public void setType(ServicePropositionType type) {
        this.type = type;
    }

    public Proposition getProposition() {
        return proposition;
    }

    public void setProposition(Proposition proposition) {
        this.proposition = proposition;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
