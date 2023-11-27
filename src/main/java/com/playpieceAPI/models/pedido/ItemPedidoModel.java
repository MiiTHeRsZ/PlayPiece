package com.playpieceAPI.models.pedido;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.playpieceAPI.models.ProdutoModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "item_pedido")
@Entity(name = "item_pedido")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "itemPedidoId")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemPedidoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_pedido_id")
    private Long itemPedidoId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_produto_id", referencedColumnName = "produto_id")
    private ProdutoModel produto;

    private int quantidade;

    @Column(name = "valor_unitario")
    private double valorUnitario;

    @Column(name = "valor_total")
    private double valorTotal;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_pedido_id", referencedColumnName = "pedido_id")
    @JsonIgnore
    private PedidoModel pedido;

}
