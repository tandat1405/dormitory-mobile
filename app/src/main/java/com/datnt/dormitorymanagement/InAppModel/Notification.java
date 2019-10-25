package com.datnt.dormitorymanagement.InAppModel;

public class Notification {
    private int notiId;
    private String notiContent, notiTimeReceived, notiType;

    public Notification(int notiId, String notiContent, String notiTimeReceived, String notiType) {
        this.notiId = notiId;
        this.notiContent = notiContent;
        this.notiTimeReceived = notiTimeReceived;
        this.notiType = notiType;
    }

    public int getNotiId() {
        return notiId;
    }

    public void setNotiId(int notiId) {
        this.notiId = notiId;
    }

    public String getNotiContent() {
        return notiContent;
    }

    public void setNotiContent(String notiContent) {
        this.notiContent = notiContent;
    }

    public String getNotiTimeReceived() {
        return notiTimeReceived;
    }

    public void setNotiTimeReceived(String notiTimeReceived) {
        this.notiTimeReceived = notiTimeReceived;
    }

    public String getNotiType() {
        return notiType;
    }

    public void setNotiType(String notiType) {
        this.notiType = notiType;
    }
}
