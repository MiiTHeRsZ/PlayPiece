package com.playpieceAPI.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
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
    @Column(name = "produto_id")
    private Long produtoId;

    @Column(name = "nome", columnDefinition = "VARCHAR(200)")
    @Size(max = 200, message = "Nome deve conter até 200 caracteres")
    private String nome;

    @Min(0)
    @Max(5)
    private double avaliacao;

    @Column(name = "descricao", columnDefinition = "TEXT")
    @Size(min = 1, max = 3000, message = "A descrição deve contar entre 1 a 3000 caracteres")
    private String descricao;

    @Column(name = "preco", columnDefinition = "DECIMAL(10,2)")
    private double preco;

    @Column(name = "quantidade", columnDefinition = "INT")
    private int quantidade;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private List<ImagemModel> listaImagens;

    private boolean ativo;
}