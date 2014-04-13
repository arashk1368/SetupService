/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudservices.brokerage.policy.model.entities;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Arash Khodadadi http://www.arashkhodadadi.com/
 */
public class State extends UniObject {

    private int number;
    private Set<Proposition> propositions;

    public State() {
        super.setUniId((long) number);
        super.setUniName("state");
        this.propositions = new HashSet<>();
    }

    public boolean addProposition(Proposition prop) {
        return this.propositions.add(prop);
    }

    public boolean removeProposition(Proposition prop) {
        return this.propositions.remove(prop);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        super.setUniId((long) number);
    }
}
