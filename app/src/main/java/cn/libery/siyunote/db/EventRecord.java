package cn.libery.siyunote.db;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Libery on 2015/11/1.
 * Email:libery.szq@qq.com
 */
public class EventRecord extends DataSupport {

    private String time;//事件创建时间

    private long timeStamp;//事件最后一次更改时间

    private String content;//文字

    private String pictures;//图片地址分号分割

    private int type;//事件类型

    public static List<EventRecord> getAllNotes() {
        return DataSupport.select("*").order("time desc").where("type = 0").find(EventRecord.class);
    }

    public static List<EventRecord> getWorkNotes() {
        return DataSupport.select("*").order("time desc").where("type = 1").find(EventRecord.class);
    }

    public static List<EventRecord> getLifeNotes() {
        return DataSupport.select("*").order("time desc").where("type = 2").find(EventRecord.class);
    }

    public static EventRecord getByTimeStamp(long timeStamp) {
        return DataSupport.select("*").where("timeStamp = ?", timeStamp + "").find(EventRecord.class).get(0);
    }

    public static void deleteBy(long timeStamp) {
        DataSupport.deleteAll(EventRecord.class, "timeStamp = ? ", timeStamp + "");
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }



}
