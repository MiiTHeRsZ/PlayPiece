package com.playpiece.PlayPiece.models.carrinho;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.playpiece.PlayPiece.models.ClienteModel;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "carrinho")
@Entity(name = "carrinho")
@AllArgsConstructor
@ToString

public class CarrinhoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private ClienteModel cliente;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "carrinho", orphanRemoval = true)
    @JsonManagedReference
    private List<ItemCarrinhoModel> itens;

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

}
