package com.ryanst.app.bean;

/**
 * Created by kevin on 16/5/11.
 */
public class PersonBean {
    private String personName;
    private String personAddress;

    public PersonBean(String personName, String personAddress) {
        super();
        this.personName = personName;
        this.personAddress = personAddress;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonAddress() {
        return personAddress;
    }

    public void setPersonAddress(String personAddress) {
        this.personAddress = personAddress;
    }
}