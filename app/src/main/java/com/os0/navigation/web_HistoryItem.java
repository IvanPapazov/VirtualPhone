package com.os0.navigation;

public class web_HistoryItem {
    private String time, url;
    public web_HistoryItem(String t, String u){
        time = t;
        url = u;
    }

    public String getTime(){
        return time;
    }

    public String getUrl(){
        return url;
    }
}
