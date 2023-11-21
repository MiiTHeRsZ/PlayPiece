package com.playpieceAPI.models.pedido;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.playpieceAPI.models.ClienteModel;
import com.playpieceAPI.models.EnderecoModel;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table(name = "pedido")
@Entity(name = "pedido")
@Getter
@Setter
@AllArgsConstructor
@ToString
public class PedidoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pedido_id")
    private Long pedidoId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_cliente_id", referencedColumnName = "cliente_id", nullable = true)
    @JsonIgnore
    private ClienteModel cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedidoModel> itens;

    @OneToOne
    @JoinColumn(name = "end_entrega_id", referencedColumnName = "endereco_id", nullable = true)
    private EnderecoModel enderecoEntrega;

    @Column(name = "valor_frete")
    private Double valorFrete;

    @Column(name = "modo_pagamento")
    private String modoPagamento;

    @Column(name = "pg_status")
    private String statusPagamento;

    @Column(name = "valor_total")
    private double valorTotal;

    public PedidoModel() {
        itens = new ArrayList<>();
    }

}
