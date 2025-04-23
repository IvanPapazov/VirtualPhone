package com.os0.navigation;

public class web_BookmarksItem {
    private String name, url;

    public web_BookmarksItem(String s, String u) {
        name = s;
        url = u;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}