package com.playpieceAPI.models.pedido;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.playpieceAPI.models.ClienteModel;
import com.playpieceAPI.models.EnderecoModel;
import com.playpieceAPI.models.pagamento.PagamentoModel;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "pedido")
@Entity(name = "pedido")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PedidoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private ClienteModel cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedidoModel> itens;

    @JoinColumn(name = "id_end_entrega", referencedColumnName = "id")
    private EnderecoModel enderecoEntrega;

    @JoinColumn(name = "id_pagamento", referencedColumnName = "id")
    private PagamentoModel pagamento;

    @Column(name = "valor_frete")
    private Double valorFrete;

    @Column(name = "valor_total")
    private double valorTotal;

}
