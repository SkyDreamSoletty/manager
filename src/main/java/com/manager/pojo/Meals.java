package com.manager.pojo;

public class Meals {

    private Integer id;

    private String name;

    private String image;

    private String species;

    private String taste;

    private Double price;

    public Meals() {
    }

    public Meals(Integer id, String name, String image, String species, String taste, Double price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.species = species;
        this.taste = taste;
        this.price = price;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
