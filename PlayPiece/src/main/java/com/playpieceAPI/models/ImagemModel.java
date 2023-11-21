package com.playpieceAPI.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "imagem")
@Entity(name = "imagem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImagemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imagem_id")
    private Long imagemId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_produto_id", referencedColumnName = "produto_id")
    @JsonIgnore
    private ProdutoModel produto;

    private String caminho;

    private boolean padrao;

    private boolean ativo;
}
