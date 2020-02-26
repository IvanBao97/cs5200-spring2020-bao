package edu.northeastern.cs5200.daos;

import edu.northeastern.cs5200.modules.Website;

import java.util.Collection;

public interface WebsiteImpl {
    void createWebsiteForDeveloper(int developerId, Website website);

    Collection<Website> findAllWebsite();

    Collection<Website> findWebsitesForDeveloper(int developerId);

    Website findWebsiteById(int WebsiteId);

    int updateWebsite(int websiteId, Website website);

    int deleteWebsite(int websiteId);
}
