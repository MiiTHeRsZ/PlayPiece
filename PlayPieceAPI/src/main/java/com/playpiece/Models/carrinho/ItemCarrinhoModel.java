package com.playpiece.models.carrinho;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.playpiece.models.ProdutoModel;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "item_carrinho")
@Entity(name = "item_carrinho")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemCarrinhoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private ProdutoModel produto;

    private int quantidade;

    @ManyToOne
    private CarrinhoModel carrinho;
}
