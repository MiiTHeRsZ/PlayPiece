package com.playpiece.PlayPiece.models.carrinho;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.playpiece.PlayPiece.models.ProdutoModel;
import com.playpiece.PlayPiece.models.pedido.PedidoModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Table(name = "item_carrinho")
@Entity(name = "item_carrinho")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemCarrinhoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_produto")
    private ProdutoModel produto;

    @Min(1)
    private int quantidade;

    @ManyToOne
    @JoinColumn(name = "id_carrinho")
    @JsonBackReference
    private CarrinhoModel carrinho;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    @JsonBackReference
    private PedidoModel pedido;
}
