package com.smart.life.domain;

/**
 * Created by 360 on 2015/8/2.
 */
public class HomeMsg {
    private int msgtype;
    private String pic;
    private String title;
    private String date;
    private String content;
    private int piccacheidx;

    public int getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(int msgtype) {
        this.msgtype = msgtype;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPiccacheidx() {
        return piccacheidx;
    }

    public void setPiccacheidx(int piccacheidx) {
        this.piccacheidx = piccacheidx;
    }
}
