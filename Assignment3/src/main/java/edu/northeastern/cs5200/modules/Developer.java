package edu.northeastern.cs5200.modules;

import java.util.Collection;
import java.sql.Date;

public class Developer extends Person{

    private String developer_key = null;

    private Collection<Website> websites;
    private void addWebsite(Website website){}
    private void removeWebsite(Website website){}

    private Collection<Role> roles;
    private void addRole(Role role){}
    private void removeRole(Role role){}

    private Collection<Privilege> privileges;
    private void addPrivilege(Privilege privilege){}
    private void removePrivilege(Privilege privilege){}

    public String getDeveloper_key() {
        return developer_key;
    }

    public void setDeveloper_key(String developer_key) {
        this.developer_key = developer_key;
    }

    public Collection<Website> getWebsites() {
        return websites;
    }

    public void setWebsites(Collection<Website> websites) {
        this.websites = websites;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }

    public Developer() {}

    public Developer(int person_id, String first_name, String last_name, String developer_key) {
        this.developer_key = developer_key;
        super.setFirst_name(first_name);
        super.setId(person_id);
        super.setFirst_name(first_name);
        super.setLast_name(last_name);
    }

    public Developer(int person_id, String first_name, String last_name, String username,
                     String password, String email, Date dob, String developer_key) {
        super();
        this.developer_key = developer_key;
        super.setId(person_id);
        super.setFirst_name(first_name);
        super.setLast_name(last_name);
        super.setUsername(username);
        super.setPassword(password);
        super.setEmail(email);
        super.setDob(dob);
    }

    public Developer(int person_id, String first_name,
                     String last_name, String username, String password,
                     String email, Date dob, String address, String phone, String developer_key) {
        super();
        this.developer_key = developer_key;
        super.setId(person_id);
        super.setFirst_name(first_name);
        super.setLast_name(last_name);
        super.setUsername(username);
        super.setPassword(password);
        super.setEmail(email);
        super.setDob(dob);
        super.setAddress(address);
        super.setPhone(phone);
    }
}
