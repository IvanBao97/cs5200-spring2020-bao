package edu.northeastern.cs5200.modules;

import java.util.Collection;
import java.sql.Date;

public class Page {
    private int id;
    private int website_id;
    private String title = null;
    private String description = null;
    private Date created = null;
    private Date updated = null;
    private int views;

    private Website website;

    private Collection<Widget> widgets = null;
    public void addWidgets(Widget widget){};
    public void removeWidget(Widget widget) {}

    private Collection<Role> roles;
    private void addRole(Role role){}
    private void removeRole(Role role){}

    private Collection<Privilege> privileges;
    private void addPrivilege(Privilege privilege){}
    private void removePrivilege(Privilege privilege){}

    public Page() {}

    public Page(int id, String title, String description, Date created, Date updated, int views) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.created = created;
        this.updated = updated;
        this.views = views;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWebsite_id() {
        return website_id;
    }

    public void setWebsite_id(int website_id) {
        this.website_id = website_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Website getWebsite() {
        return website;
    }

    public void setWebsite(Website website) {
        this.website = website;
    }

    public Collection<Widget> getWidgets() {
        return widgets;
    }

    public void setWidgets(Collection<Widget> widgets) {
        this.widgets = widgets;
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
}
