package com.playpieceAPI.models;

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
    private Long id;

    @Column(name = "id_produto")
    private Long produtoId;
    private String caminho;
    private boolean padrao;
    private boolean ativo;
}
