package edu.northeastern.cs5200.modules;

import java.util.Collection;
import java.sql.Date;

public class Website {
    private int id;
    private int developer_id;
    private String name;
    private String description;
    private Date created;
    private Date updated;
    private int visits;

    private Collection<Page> pages;
    private void addPage(Page page){}
    private void removePage(Page page){}


    private Collection<Role> roles;
    private void addRole(Role role){}
    private void removeRole(Role role){}

    private Collection<Privilege> privileges;
    private void addPrivilege(Privilege privilege){}
    private void removePrivilege(Privilege privilege){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeveloper_id() {
        return developer_id;
    }

    public void setDeveloper_id(int developer_id) {
        this.developer_id = developer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public Collection<Page> getPages() {
        return pages;
    }

    public void setPages(Collection<Page> pages) {
        this.pages = pages;
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

    public Website() {}

    public Website(int id, String name, String description, Date created, Date updated, int visits) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
        this.updated = updated;
        this.visits = visits;
    }
}
