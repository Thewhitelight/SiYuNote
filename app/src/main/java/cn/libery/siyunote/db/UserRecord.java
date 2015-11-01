package cn.libery.siyunote.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Libery on 2015/11/1.
 * Email:libery.szq@qq.com
 */
public class UserRecord extends DataSupport {

    private String Phone;
    private String userName;

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
