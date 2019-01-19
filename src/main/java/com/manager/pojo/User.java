package com.manager.pojo;

public class User {
    private Integer id;
    private String name;
    private String phone;
    private String password;
    private Integer status;

    public User() {
    }

    public User(Integer id, String name, String phone, String password, Integer status) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.status = status;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
