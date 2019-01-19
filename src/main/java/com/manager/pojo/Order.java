package com.manager.pojo;

public class Order {
    private Integer id;
    private String orderId;
    private String address;
    private Double price;
    private Integer status;// 0准备中 1派送中
    private String principal;
    private String phone;

    public Order() {
    }

    public Order(Integer id, String orderId, String address, Double price, Integer status, String principal, String phone) {
        this.id = id;
        this.orderId = orderId;
        this.address = address;
        this.price = price;
        this.status = status;
        this.principal = principal;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
