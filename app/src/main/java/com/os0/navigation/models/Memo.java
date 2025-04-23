package com.os0.navigation.models;

public class Memo {
    public String tvText;
    public boolean finished;
    public int Id;

    public Memo(String tvText, boolean finished){
        this.tvText = tvText;
        this.finished = finished;
        this.Id = Id;
    }

    public String GetTv(){
        return tvText;
    }
    public boolean GetBtn(){
        return finished;
    }
    public int GetId() {return Id;}
}
