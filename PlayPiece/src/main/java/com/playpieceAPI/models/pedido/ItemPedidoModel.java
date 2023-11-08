package com.playpieceAPI.models.pedido;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.playpieceAPI.models.ProdutoModel;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "item_pedido")
@Entity(name = "item_pedido")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemPedidoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ProdutoModel produto;

    private int quantidade;

    @Column(name = "valor_unitario")
    private double valorUnitario;

    @Column(name = "valor_total")
    private double valorTotal;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private PedidoModel pedido;

}
