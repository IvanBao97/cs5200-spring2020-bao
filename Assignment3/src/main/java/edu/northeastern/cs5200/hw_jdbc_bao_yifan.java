package edu.northeastern.cs5200;

import edu.northeastern.cs5200.daos.*;
import edu.northeastern.cs5200.modules.*;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collection;

public class hw_jdbc_bao_yifan {

    public static void main(String[] args) {

        Calendar calendar = Calendar.getInstance();
        Date date = new Date(calendar.getTime().getTime());

        //1.Create Developers and Users
        DeveloperDao developerDao = DeveloperDao.getInstance();
        Developer alice = new Developer(12,"Alice","Wonder","alice","alice","alice@wonder.com",null, "4321rewq");
        Developer bob = new Developer(23,"Bob","Marley","bob","bob","bob@marley.com",null, "5432trew");
        Developer charlie = new Developer(34,"Charlie","Garcia","charlie","charlie","chuch@garcia.com",null,"6543ytre");

        developerDao.createDeveloper(alice);
        developerDao.createDeveloper(bob);
        developerDao.createDeveloper(charlie);

        UserDao userDao = UserDao.getInstance();
        User dan = new User(45, "Dan", "Martin", "dan", "dan", "dan@martin.com", null);
        User ed = new User(56, "Ed", "Karaz", "ed", "ed", "ed@kar.com", null);

        userDao.createUser(dan);
        userDao.createUser(ed);

        System.out.println("Developer finish!");

        //2.Create Websites
        //2.1 Create website
        WebsiteDao websiteDao = WebsiteDao.getInstance();

        Website facebook = new Website(123,"Fackbook","an online social media and social networking service",date,date,1234234);
        Website twitter = new Website(234,"Twitter","an online news and social networking service",date,date,4321543);
        Website wikipedia = new Website(345,"Wikipedia","a free online encyclopedia",date,date,3456654);
        Website cnn = new Website(456,"CNN","an American basic cable and satellite television news channe",date,date,6543345);
        Website cnet = new Website(567,"CNET","an American media website that publishes reviews, news, articles, blogs, podcasts and videos on technology and consumer electronics",date,date,5433455);
        Website gizmodo = new Website(678,"Gizmodo","a design, technology, science and science fiction website that also writes articles on politics",date,date,4322345);

        websiteDao.createWebsiteForDeveloper(alice.getId(),facebook);
        websiteDao.createWebsiteForDeveloper(bob.getId(),twitter);
        websiteDao.createWebsiteForDeveloper(charlie.getId(),wikipedia);
        websiteDao.createWebsiteForDeveloper(alice.getId(),cnn);
        websiteDao.createWebsiteForDeveloper(bob.getId(),cnet);
        websiteDao.createWebsiteForDeveloper(alice.getId(),gizmodo);

        //2.2 Create website_role
        RoleDao website_roleDao = RoleDao.getInstance();
        //Assign facebook role
        website_roleDao.assignWebsiteRole(alice.getId(),facebook.getId(),"owner");
        website_roleDao.assignWebsiteRole(bob.getId(),facebook.getId(),"editor");
        website_roleDao.assignWebsiteRole(charlie.getId(),facebook.getId(),"admin");
        //Assign twitter role
        website_roleDao.assignWebsiteRole(bob.getId(),twitter.getId(),"owner");
        website_roleDao.assignWebsiteRole(charlie.getId(),twitter.getId(),"editor");
        website_roleDao.assignWebsiteRole(alice.getId(),twitter.getId(),"admin");
        //Assign wikipedia role
        website_roleDao.assignWebsiteRole(charlie.getId(),wikipedia.getId(),"owner");
        website_roleDao.assignWebsiteRole(alice.getId(),wikipedia.getId(),"editor");
        website_roleDao.assignWebsiteRole(bob.getId(),wikipedia.getId(),"admin");
        //Assign cnn role
        website_roleDao.assignWebsiteRole(alice.getId(),cnn.getId(),"owner");
        website_roleDao.assignWebsiteRole(bob.getId(),cnn.getId(),"editor");
        website_roleDao.assignWebsiteRole(charlie.getId(),cnn.getId(),"admin");
        //Assign cnet role
        website_roleDao.assignWebsiteRole(bob.getId(),cnet.getId(),"owner");
        website_roleDao.assignWebsiteRole(charlie.getId(),cnet.getId(),"editor");
        website_roleDao.assignWebsiteRole(alice.getId(),cnet.getId(),"admin");
        //Assign gizmodo role
        website_roleDao.assignWebsiteRole(charlie.getId(),gizmodo.getId(),"owner");
        website_roleDao.assignWebsiteRole(alice.getId(),gizmodo.getId(),"owner");
        website_roleDao.assignWebsiteRole(bob.getId(),gizmodo.getId(),"admin");

        System.out.println("Website finish!");

        //3.Create Pages
        //3.1 Create page
        PageDao pageDao = PageDao.getInstance();

        Page home = new Page(123,"Home","Landing page",Date.valueOf("2020-01-06"),Date.valueOf("2020-02-27"),123434);
        Page about = new Page(234,"About","Website description",Date.valueOf("2020-01-06"),Date.valueOf("2020-02-27"),234545);
        Page contact = new Page(345,"Contact","Addresses, phones, and contact info",Date.valueOf("2020-01-06"),Date.valueOf("2020-02-27"),345656);
        Page preferences = new Page(456,"Preferences","Where users can configure preferences their",Date.valueOf("2020-01-06"),Date.valueOf("2020-02-27"),456776);
        Page profile = new Page(567,"Profile","Users can configure their personal information",Date.valueOf("2020-01-06"),Date.valueOf("2020-02-27"),567878);

        pageDao.createPageForWebsite(cnet.getId(),home);
        pageDao.createPageForWebsite(gizmodo.getId(),about);
        pageDao.createPageForWebsite(wikipedia.getId(),contact);
        pageDao.createPageForWebsite(cnn.getId(),preferences);
        pageDao.createPageForWebsite(cnet.getId(),profile);

        //3.2 Create page_role
        RoleDao page_roleDao = RoleDao.getInstance();
        //Assign home role
        page_roleDao.assignPageRole(alice.getId(),home.getId(),"editor");
        page_roleDao.assignPageRole(bob.getId(),home.getId(),"reviewer");
        page_roleDao.assignPageRole(charlie.getId(),home.getId(),"writer");
        //Assign about role
        page_roleDao.assignPageRole(bob.getId(),about.getId(),"editor");
        page_roleDao.assignPageRole(charlie.getId(),about.getId(),"reviewer");
        page_roleDao.assignPageRole(alice.getId(),about.getId(),"writer");
        //Assign contact role
        page_roleDao.assignPageRole(charlie.getId(),contact.getId(),"editor");
        page_roleDao.assignPageRole(alice.getId(),contact.getId(),"reviewer");
        page_roleDao.assignPageRole(bob.getId(),contact.getId(),"writer");
        //Assign preference role
        page_roleDao.assignPageRole(alice.getId(),preferences.getId(),"editor");
        page_roleDao.assignPageRole(bob.getId(),preferences.getId(),"reviewer");
        page_roleDao.assignPageRole(charlie.getId(),preferences.getId(),"writer");
        //Assign profile role
        page_roleDao.assignPageRole(bob.getId(),profile.getId(),"editor");
        page_roleDao.assignPageRole(charlie.getId(),profile.getId(),"reviewer");
        page_roleDao.assignPageRole(alice.getId(),profile.getId(),"writer");

        System.out.println("Page finish!");

        //4.Create Widgets
        WidgetDao widgetDao = WidgetDao.getInstance();

        Widget head123 = new Widget(123, 123, "head123", 0, 0, null, null, "Welcome", 0, 0, null, null, null, false, false, Widget.type.HEADING);
        Widget post234 = new Widget(234, 234, "post234",0,0,null,null,"<p>Lorem</p>",0, 0, null, null, null, false, false, Widget.type.HTML);
        Widget head345 = new Widget(345, 345, "head345",0,0,null,null,"Hi",1,0,null,null,null,false,false, Widget.type.HEADING);
        Widget intro456 = new Widget(456,456, "intro456",0,0,null,null,"<h1>hi</h1>",2, 0, null,null,null,false,false,Widget.type.HTML);
        Widget image345 = new Widget(567,567, "image345",50,100,null,null,null,3,0,"/img/567.png",null,null,false,false,Widget.type.IMAGE);
        Widget video456 = new Widget(678,678, "video456",400,300,null,null,null,0,0,"https://youtu.be/ h67VX51QXiQ",null,null,false,false,Widget.type.IMAGE);

        widgetDao.createWidgetForPage(home.getId(),head123);
        widgetDao.createWidgetForPage(about.getId(),post234);
        widgetDao.createWidgetForPage(contact.getId(),head345);
        widgetDao.createWidgetForPage(contact.getId(),intro456);
        widgetDao.createWidgetForPage(contact.getId(),image345);
        widgetDao.createWidgetForPage(preferences.getId(),video456);

        System.out.println("Widget finish!");

        //5.Update
        //5.1 Update developer - Update Charlie's primary phone number to 333-444-5555
        charlie.setPhone("333-444-5555");
        developerDao.updateDeveloper(charlie.getId(),charlie);

        //5.2 Update widget - Update the relative order of widget head345 on the page so that it's new order is 3. Note that the other widget's order needs to update as well
        head345.setOrder(3);
        widgetDao.updateWidget(head345.getId(), head345);
        int page_id = head345.getPage_id();
        Collection<Widget> widgets = widgetDao.findWidgetsForPage(page_id);
        for(Widget widget : widgets){
            if(widget.getId() != head345.getId()){
                widget.setOrder(widget.getOrder()-1);
                widgetDao.updateWidget(widget.getId(), widget);
            }
        }
        //5.3 Update page - Append 'CNET - ' to the beginning of all CNET's page titles
        int cnetId = cnet.getId();
        Collection<Page> pages = pageDao.findPagesForWebsite(cnetId);
        for(Page page: pages) {
            page.setTitle("CNET - " + page.getTitle());
            pageDao.updatePage(page.getId(), page);
        }
        //5.4 Update roles - Swap Charlie's and Bob's role in CNET's Home page
        Page cnet_home =  new Page();
        for(Page page : pages){
            if(page.getTitle().equals("CNET - Home")){
                cnet_home.setTitle(page.getTitle());
                cnet_home.setId(page.getId());
                cnet_home.setWebsite_id(page.getWebsite_id());
            }
        }
        String bob_role= page_roleDao.findPageRoleForDeveloper(bob.getId(), cnet_home.getId()).getRole();
        String charlie_role = page_roleDao.findPageRoleForDeveloper(charlie.getId(), cnet_home.getId()).getRole();

        page_roleDao.deletePageRole(bob.getId(),cnet_home.getId(), bob_role);
        page_roleDao.deletePageRole(charlie.getId(), cnet_home.getId(), charlie_role);
        page_roleDao.assignPageRole(bob.getId(), cnet_home.getId(), charlie_role);
        page_roleDao.assignPageRole(charlie.getId(), cnet_home.getId(), bob_role);


        System.out.println("Update finish!");

        //6.Delete
        //6.1 Delete developer - Delete Alice's primary address
        alice.setAddress(null);
        developerDao.updateDeveloper(alice.getId(), alice);

        //6.2 Delete widget - Remove the last widget in the Contact page. The last widget is the one with the highest value in the order field
        int max_id_1 = 0;
        int max_order = 0;
        int contactId = contact.getId();
        for (Widget widget : widgetDao.findWidgetsForPage(contactId)) {
            if(max_order<widget.getOrder()){
                max_id_1 = widget.getId();
                max_order = widget.getOrder();
            }
        }
        widgetDao.deleteWidget(max_id_1);

        //6.3 Delete page - Remove the last updated page in Wikipedia
        Date last_date = Date.valueOf("1900-01-01");
        int max_id_2 = 0;
        int wikipediaId = wikipedia.getId();
        for (Page page : pageDao.findPagesForWebsite(wikipediaId)) {
            int i = last_date.compareTo(page.getUpdated());
            if(i < 0){
                last_date = page.getUpdated();
                max_id_2 = page.getId();
            }
        }
        pageDao.deletePage(max_id_2);

        //6.4 Delete website - Remove the CNET web site, as well as all related roles and privileges relating developers to the Website and Pages
        websiteDao.deleteWebsite(cnet.getId());

        System.out.println("All finish!");
    }
}
