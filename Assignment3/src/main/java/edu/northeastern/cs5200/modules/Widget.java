package edu.northeastern.cs5200.modules;

public class Widget {
    private int id;
    private int page_id;
    private String name = null;
    private int width;
    private int height;
    private String cssClass = null;
    private String cssStyle = null;
    private String text = null;
    private int order = -1;
    private int size = -1;
    private String html;
    private String src = null;
    private String url = null;
    private boolean shareable;
    private boolean expandable;

    public Widget() {

    }

    public enum type{
        HEADING, HTML, IMAGE, YOUTUBE
    }
    private type dtype=null;

    private Page page;

    public Widget(int id, String name, int width, int height, String cssClass, String cssStyle, String text, int order) {
        this.id = id;
        this.name = name;
        this.width = width;
        this.height = height;
        this.cssClass = cssClass;
        this.cssStyle = cssStyle;
        this.text = text;
        this.order = order;
    }

    public Widget(int id, int page_id, String name, int width, int height, String cssClass, String cssStyle, String text, int order, int size, String html, String src, String url, boolean shareable, boolean expandable, type dtype) {
        this.id = id;
        this.page_id = page_id;
        this.name = name;
        this.width = width;
        this.height = height;
        this.cssClass = cssClass;
        this.cssStyle = cssStyle;
        this.text = text;
        this.order = order;
        this.size = size;
        this.html = html;
        this.src = src;
        this.url = url;
        this.shareable = shareable;
        this.expandable = expandable;
        this.dtype = dtype;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage_id() {
        return page_id;
    }

    public void setPage_id(int page_id) {
        this.page_id = page_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getCssStyle() {
        return cssStyle;
    }

    public void setCssStyle(String cssStyle) {
        this.cssStyle = cssStyle;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isShareable() {
        return shareable;
    }

    public void setShareable(boolean shareable) {
        this.shareable = shareable;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public type getDtype() {
        return dtype;
    }

    public void setDtype(type dtype) {
        this.dtype = dtype;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

}
