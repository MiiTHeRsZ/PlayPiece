package com.playpiece.PlayPiece.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "PRODUCTS")
@Entity(name = "PRODUCTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductModel extends RepresentationModel<ProductModel> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idProduct;
    @Column(name = "name", columnDefinition = "VARCHAR(40)")
    private String name;
    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;
    @Column(name = "components", columnDefinition = "MEDIUMTEXT")
    private String components;
    @Column(name = "value", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal value; 
    private String image01Product;
    private String image02Product;
    private String image03Product;
    @Column(name = "ativo", columnDefinition = "tinyint(1)")
    private boolean ativo;
    private int desconto;
    @Column(name = "category", columnDefinition = "tinyint(1)")
    private byte category;

    // public UUID getIdProduct() {
    //     return idProduct;
    // }

    // public void setIdProduct(UUID idProduct) {
    //     this.idProduct = idProduct;
    // }

    // public String getName() {
    //     return name;
    // }

    // public void setName(String name) {
    //     this.name = name;
    // }

    // public String getDescription() {
    //     return description;
    // }

    // public void setDescription(String description) {
    //     this.description = description;
    // }

    // public String getComponents() {
    //     return components;
    // }

    // public void setComponents(String components) {
    //     this.components = components;
    // }

    // public BigDecimal getValue() {
    //     return value;
    // }

    // public void setValue(BigDecimal value) {
    //     this.value = value;
    // }

    // public String getImage01Product() {
    //     return image01Product;
    // }

    // public void setImage01Product(String image01Product) {
    //     this.image01Product = image01Product;
    // }

    // public String getImage02Product() {
    //     return image02Product;
    // }

    // public void setImage02Product(String image02Product) {
    //     this.image02Product = image02Product;
    // }

    // public String getImage03Product() {
    //     return image03Product;
    // }

    // public void setImage03Product(String image03Product) {
    //     this.image03Product = image03Product;
    // }

    // public boolean isAtivo() {
    //     return ativo;
    // }

    // public void setAtivo(boolean ativo) {
    //     this.ativo = ativo;
    // }

    // public int getDesconto() {
    //     return desconto;
    // }

    // public void setDesconto(int desconto) {
    //     this.desconto = desconto;
    // }

    // public byte getCategory() {
    //     return category;
    // }

    // public void setCategory(byte category) {
    //     this.category = category;
    // }

}
