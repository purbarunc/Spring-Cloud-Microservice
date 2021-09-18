package com.codex.ecom.inventory.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Inventory {
	@Id
	@GenericGenerator(name = "skuCode", strategy = "com.codex.ecom.inventory.model.generator.SkuCodeGenerator")
	@GeneratedValue(generator = "skuCode")
	private String skuCode;
	private String productId;
	private Integer units;
	private Date createdDate;
	private Date updatedDate;
}
