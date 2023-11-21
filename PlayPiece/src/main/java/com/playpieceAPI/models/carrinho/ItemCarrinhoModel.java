package com.playpieceAPI.models.carrinho;

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

@Table(name = "item_carrinho")
@Entity(name = "item_carrinho")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "itemCarrinhoId")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemCarrinhoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_carrinho_id")
    private Long itemCarrinhoId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_produto_id", referencedColumnName = "produto_id")
    private ProdutoModel produto;

    private int quantidade;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_carrinho_id", referencedColumnName = "carrinho_id")
    @JsonIgnore
    private CarrinhoModel carrinho;
}
