package edu.northeastern.cs5200.daos;

public interface PrivilegeImpl {
    void assignWebsitePrivilege(int developerId, int websiteId, String priviledge);

    void assignPagePrivilege(int developerId, int pageId, String privilege);

    void deleteWebsitePrivilege(int developerId, int websiteId, String privilege);

    void deletePagePrivilege(int developerId, int pageId, String privilege);
}
