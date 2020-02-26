package edu.northeastern.cs5200.daos;

import edu.northeastern.cs5200.modules.Page;

import java.util.Collection;

public interface PageImpl {
    void createPageForWebsite(int websiteId, Page page);

    Collection<Page> findAllPages();

    Page findPageById(int pageId);

    Collection<Page> findPagesForWebsite(int websiteId);

    int updatePage(int pageId, Page page);

    int deletePage(int pageId);
}
