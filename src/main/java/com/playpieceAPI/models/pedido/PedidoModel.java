package com.playpieceAPI.models.pedido;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.*;
import com.playpieceAPI.models.*;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "valor_frete", nullable = true)
    private Double valorFrete;

    @Column(name = "modo_pagamento", nullable = true)
    private String modoPagamento;

    @Column(name = "pg_status", nullable = true)
    private String statusPagamento;

    @Column(name = "valor_total", nullable = true)
    private Double valorTotal;

    @Column(name = "data_pedido", nullable = true)
    private Date dataPedido;

    public PedidoModel() {
        itens = new ArrayList<>();
    }

}
