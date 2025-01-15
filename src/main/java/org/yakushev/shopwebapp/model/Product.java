package org.yakushev.shopwebapp.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.yakushev.shopwebapp.util.MergeableBean;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "swa_product")
@Getter
@Setter
public class Product extends MergeableBean implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private Float price;

	@CreationTimestamp
	private Date createdAt;

	private String createdBy;

	public Product() {
	}

	public Product(String name, Float price, String createdBy) {
		this.name = name;
		this.price = price;
		this.createdBy = createdBy;
	}
}
