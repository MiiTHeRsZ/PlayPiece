package com.playpiece.PlayPiece.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Table(name = "itemPedido")
@Entity(name = "itemPedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemPedidoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_produto", referencedColumnName = "id")
    private ProdutoModel produto;

    @Min(1)
    private int quantidade;

    private double subtotal;
}
