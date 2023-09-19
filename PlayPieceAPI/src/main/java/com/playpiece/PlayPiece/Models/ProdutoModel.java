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

    @Column(name = "nome", columnDefinition = "VARCHAR(40)")
    private String nome;
    private double avaliacao;
    @Column(name = "descricao", columnDefinition = "TEXT") 
    private String descricao;
    @Column(name = "preco", columnDefinition = "DECIMAL(10,2)")
    private double preco;
    @Column(name = "quantidade", columnDefinition = "INT")
    private int quantidade;

    @Transient
    private List<ImagemModel> listaImagens;

    private boolean ativo;
}