package cn.libery.siyunote.db;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Libery on 2015/11/1.
 * Email:libery.szq@qq.com
 */
public class EventRecord extends DataSupport {

    private String time;

    private long timeStamp;

    private String content;

    private String pictures;

    private String voicePath;

    private String type;

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

    public String getVoicePath() {
        return voicePath;
    }

    public void setVoicePath(String voicePath) {
        this.voicePath = voicePath;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static List<EventRecord> getAll() {
        return DataSupport.select("*").order("timeStamp desc").find(EventRecord.class);
    }

    public static EventRecord getByTimeStamp(long timeStamp) {
        return DataSupport.select("*").where("timeStamp = ?", timeStamp + "").find(EventRecord.class).get(0);
    }

}
