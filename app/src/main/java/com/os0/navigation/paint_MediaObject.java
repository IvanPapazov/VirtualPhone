package com.os0.navigation;

public class paint_MediaObject {
    private String mediaFilePath;
    private boolean isImage;

    public String getMediaFilePath(){return mediaFilePath;}

    public void setMediaFilePath(String newMediaFilePath){this.mediaFilePath = newMediaFilePath;}

    public boolean getIsImage(){return isImage;}

    public void setIsImage(boolean newIsImage){this.isImage = newIsImage;}

    public paint_MediaObject(String mediaFilePath, boolean isImage){
        setMediaFilePath(mediaFilePath);
        setIsImage(isImage);
    }



}
