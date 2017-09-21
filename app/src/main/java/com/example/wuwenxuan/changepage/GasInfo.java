package com.example.wuwenxuan.changepage;

/**
 * Created by wuwenxuan on 2017/8/29 13:22.
 * 功能 ：GasItem信息
 */

public class GasInfo {
    private int id;
    private String date;
    private String address_id;
    private String type;//是否自动

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }
    public String getAddress_id() {
        return address_id;
    }

}
