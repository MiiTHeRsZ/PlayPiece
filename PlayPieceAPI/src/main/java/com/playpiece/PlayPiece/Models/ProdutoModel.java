package com.playpiece.PlayPiece.Models;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "produto")
@Entity(name = "produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProdutoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private double avaliacao;

    private String descricao;

    private double preco;

    private int quantidade;

    @Transient
    private List<ImagemModel> listaImagens;

    private boolean ativo;
}