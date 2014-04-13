/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudservices.brokerage.policy.model.entities;

/**
 * The class represents an object with a unique id
 *
 * @author Arash Khodadadi http://www.arashkhodadadi.com/
 *
 */
public abstract class UniObject extends Object implements Comparable<UniObject> {

    private Long uniId;
    private String uniName;

    public Long getUniId() {
        return uniId;
    }

    public void setUniId(Long uniId) {
        this.uniId = uniId;
    }

    public String getUniName() {
        return uniName;
    }

    public void setUniName(String uniName) {
        this.uniName = uniName;
    }

    @Override
    public boolean equals(Object o) {
        if (UniObject.class.isAssignableFrom(o.getClass())) {
            UniObject n = (UniObject) o;
            return this.uniId.equals(n.getUniId());
        } else {
            return this.toString().equals(o);
        }
    }

    @Override
    public int hashCode() {
        return this.uniId.hashCode();
    }

    @Override
    public String toString() {
        return uniName + "-" + uniId.toString();
    }

    @Override
    public int compareTo(UniObject o) {
        return this.uniId.compareTo(o.getUniId());
    }
}
