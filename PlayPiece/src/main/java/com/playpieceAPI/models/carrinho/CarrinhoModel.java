package com.playpieceAPI.models.carrinho;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.playpieceAPI.models.ClienteModel;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "carrinho")
@Entity(name = "carrinho")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@AllArgsConstructor
@ToString

public class CarrinhoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private ClienteModel cliente;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL)
    private List<ItemCarrinhoModel> itens;

    private Boolean ativo;

    public CarrinhoModel() {
        itens = new ArrayList<>();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCliente(ClienteModel cliente) {
        this.cliente = cliente;
    }

    public void setItens(List<ItemCarrinhoModel> itens) {
        this.itens = itens;
    }

    public Long getId() {
        return id;
    }

    public ClienteModel getCliente() {
        return cliente;
    }

    public List<ItemCarrinhoModel> getItens() {
        return itens;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

}
