package com.playpiece.PlayPiece.models.pedido;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.playpiece.PlayPiece.models.ClienteModel;
import com.playpiece.PlayPiece.models.carrinho.ItemCarrinhoModel;

import jakarta.persistence.*;
import lombok.*;

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
    @JsonBackReference
    private ClienteModel cliente;

    private double valor;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido", orphanRemoval = true)
    @JsonManagedReference
    private List<ItemCarrinhoModel> itens;

}
