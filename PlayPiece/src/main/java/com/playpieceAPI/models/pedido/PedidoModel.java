package com.playpieceAPI.models.pedido;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.playpieceAPI.models.ClienteModel;
import com.playpieceAPI.models.EnderecoModel;
// import com.playpieceAPI.models.pagamento.PagamentoModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "pedido")
@Entity(name = "pedido")
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
    @JsonIgnore
    private ClienteModel cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedidoModel> itens;

    // @JoinColumn(name = "id_end_entrega", referencedColumnName = "id")
    // private EnderecoModel enderecoEntrega;

    // @JoinColumn(name = "id_pagamento", referencedColumnName = "id")
    // private PagamentoModel pagamento;

    @Column(name = "valor_frete")
    private Double valorFrete;

    @Column(name = "valor_total")
    private double valorTotal;

}
