package com.manager.pojo;

public class Business {

    private Integer id;

    private String name;

    private String address;

    private String phone;

    private String manger;

    private String date;

    private Integer quota;

    public Business() {
    }

    public Business(Integer id, String name, String address, String phone, String manger, String date, Integer quota) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.manger = manger;
        this.date = date;
        this.quota = quota;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getManger() {
        return manger;
    }

    public void setManger(String manger) {
        this.manger = manger;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getQuota() {
        return quota;
    }

    public void setQuota(Integer quota) {
        this.quota = quota;
    }
}
