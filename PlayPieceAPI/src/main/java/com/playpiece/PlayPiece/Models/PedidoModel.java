package com.playpiece.PlayPiece.Models;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "carrinho")
@Entity(name = "carrinho")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PedidoModel {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_itemCarrrinho", referencedColumnName = "id")
    private List<ItemPedidoModel> itens;
}
