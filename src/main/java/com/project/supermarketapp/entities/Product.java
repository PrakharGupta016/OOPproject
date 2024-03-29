package com.project.supermarketapp.entities;

import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
//import javax.validation.constraints.NotNull;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @NotNull Integer id;

    private @NotNull String name;
    private @NotNull String imageURL;

    private @NotNull double costPrice;

    private @NotNull double salePrice;
    private @NotNull String description;


    // idMany-to-one relationship
    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costprice) {
        this.costPrice = costprice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice( double salePrice)
    {
        this.salePrice= salePrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}