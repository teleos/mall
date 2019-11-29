package com.liang.mall.bean;

public class Property {
    private String name;
    private Category category;
    private int id;

    public Property() {
    }

    public Property(String name, Category category, int id) {
        this.name = name;
        this.category = category;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", category=" + category +
                ", id=" + id +
                '}';
    }
}
