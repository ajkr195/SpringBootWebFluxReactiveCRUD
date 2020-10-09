package com.springboot.webflux.crud.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

public class Product implements Persistable<Integer> {

    @Id
    private Integer id;
    private String description;
    private Double price;

    @Transient
    private boolean newProduct;

    @Override
    @Transient
    public boolean isNew() {
        return this.newProduct || id == null;
    }

    public Product setAsNew() {
        this.newProduct = true;
        return this;
    }

	public Product(Integer id, String description, Double price, boolean newProduct) {
		super();
		this.id = id;
		this.description = description;
		this.price = price;
		this.newProduct = newProduct;
	}
	
	

	public Product(Integer id, String description, Double price) {
		super();
		this.id = id;
		this.description = description;
		this.price = price;
	}

	public Product() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public boolean isNewProduct() {
		return newProduct;
	}

	public void setNewProduct(boolean newProduct) {
		this.newProduct = newProduct;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", description=" + description + ", price=" + price + ", newProduct=" + newProduct
				+ "]";
	}
    
}
